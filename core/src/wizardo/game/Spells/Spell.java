package wizardo.game.Spells;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import wizardo.game.Account.Unlocked;
import wizardo.game.Audio.Sounds.SoundPlayer;
import wizardo.game.Display.Text.FloatingDamage;
import wizardo.game.Items.Equipment.Amulet.Rare_EliteAmulet;
import wizardo.game.Items.Equipment.Book.Epic_VogonBook;
import wizardo.game.Items.Equipment.Hat.Legendary_SentientHat;
import wizardo.game.Items.Equipment.Staff.Legendary_ArcaneStaff;
import wizardo.game.Items.Equipment.Staff.Legendary_FireStaff;
import wizardo.game.Items.Equipment.Staff.Legendary_FrostStaff;
import wizardo.game.Items.Equipment.Staff.Legendary_LightningStaff;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Spell;
import wizardo.game.Spells.SpellUtils.*;
import wizardo.game.Spells.Unique.Brand.Brand_Explosion;
import wizardo.game.Spells.Unique.ThundergodBolt.ThundergodBolt_Spell;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.GameSettings.dmg_text_on;
import static wizardo.game.GameSettings.sound_volume;
import static wizardo.game.Resources.Skins.mainMenuSkin;
import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public abstract class Spell implements Cloneable {

    public boolean raycasted;
    public float aimReach = 15;

    public Animation<Sprite> anim;
    public float red = 0;
    public float green = 0;
    public float blue = 0;
    public float lightAlpha = 1;
    public Color textColor = null;

    public Vector2 spawnPosition;
    public Vector2 targetPosition;
    public boolean castByPawn;
    public Body body;
    public Body originBody;
    public float stateTime = 0;
    public boolean initialized;
    public String soundPath;
    public String name;

    public int dmg;
    public float dmgVariance = 0.15f;   //      0.85 - 1.15 by default
    public float speed;
    public float radius;
    public float cooldown;

    public boolean defensive;
    public boolean utility;

    public Spell nested_spell;
    public BaseScreen screen;

    public boolean multicastable = true;
    public boolean multicasted;
    public boolean arcaneCasted; //From Arcane Staff Legendary
    public boolean autoaimable; //For Sentient Hat Legendary


    public Spell_Element anim_element;
    public Spell_Element main_element;
    public Spell_Element bonus_element;
    public ArrayList<Spell_Name> spellParts = new ArrayList<>();

    public Spell() {

    }

    public abstract void update(float delta);

    public void handleCollision(Monster monster) {

    }

    public void handleCollision(Fixture obstacle) {

    }

    public void handleCollision(EnvironmentObject decor) {
        decor.collided = true;
    }

    public void handleCollision(MonsterSpell monsterSpell) {

    }

    @Override
    public Spell clone() {
        try {
            return (Spell) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public abstract void dispose();


    /**
     * if no spawnPosition is given to the spell, it will take the player's position
     * @return Vector containing coordinates of spawn position
     */
    public Vector2 getSpawnPosition() {
        if(spawnPosition != null) {
            return spawnPosition;
        } else {
            return player.pawn.getPosition();
        }
    }

    /**
     * Mouse position or Controller position normalized to 300
     * works for projectile spells
     * @return Vector of the target position in worlds coordinate
     */
    public Vector2 getTargetPosition() {
        if(targetPosition == null) {
            if (!controllerActive) {
                Vector3 mouseUnprojected = screen.mainCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                return new Vector2(mouseUnprojected.x / PPM, mouseUnprojected.y / PPM);
            } else {
                return controllerAimAssist();
            }
        } else {
            return new Vector2(targetPosition);
        }
    }

    public Vector2 controllerAimAssist() {
        Vector2 target;
        if(raycasted) {
            Monster closestTargeted = rayCastTargeting();
            if (closestTargeted != null) {
                target = new Vector2(closestTargeted.body.getPosition().scl(0.9999f)); // Scaled to avoid crashy comparisons
            } else {
                Vector2 direction = new Vector2(player.pawn.targetVector);
                if (direction.isZero()) {
                    direction.set(0, 1);
                }
                direction.setLength(5);
                target = new Vector2(player.pawn.body.getPosition().add(direction));
            }
        } else {
            Vector2 direction = new Vector2(player.pawn.targetVector);
            if (direction.isZero()) {
                direction.set(0, 1);
            }
            direction.setLength(5);
            target = new Vector2(player.pawn.body.getPosition().add(direction));
        }

        return target;
    }

    public Monster rayCastTargeting() {
        Vector2 direction = new Vector2(player.pawn.targetVector);
        if (direction.isZero()) {
            direction.set(0, 1);
        }
        direction.setLength(aimReach);
        float angleRange = 16;
        int RAY_COUNT = 13;
        final Monster[] targetLock = {null};
        final float[] shortestDistance = {Float.MAX_VALUE};

        RayCastCallback callback = (fixture, point, _, _) -> {
            Body body = fixture.getBody();

            if (body.getUserData() instanceof Monster) {
                float distance = player.pawn.body.getPosition().dst(point);

                if (distance < shortestDistance[0]) {
                    shortestDistance[0] = distance;
                    targetLock[0] = (Monster) body.getUserData();
                }
                return -1;
            }
            return 0;
        };


        for (int i = 0; i < RAY_COUNT; i++) {
            float angleOffset = -angleRange + i * (2 * angleRange / (RAY_COUNT - 1));
            Vector2 rotatedDirection = direction.cpy().rotateDeg(angleOffset);  // Convert radians to degrees

            Vector2 endPoint =  player.pawn.body.getPosition().cpy().add(rotatedDirection);

            world.rayCast(callback, player.pawn.body.getPosition(), endPoint);
        }



        return targetLock[0];
    }

    public void playSound(Vector2 position) {
        float dst_factor = 1;
        float dst = player.pawn.body.getPosition().dst(position);
        dst_factor -= dst * 0.025f;
        SoundPlayer.getSoundPlayer().playSound(soundPath, sound_volume);
    }

    public String toString() {
        return name;
    }

    public void setElements(Spell spellParent) {
        arcaneCasted = spellParent.arcaneCasted;
        multicasted = spellParent.multicasted;
        textColor = spellParent.textColor;
        if(main_element != spellParent.main_element) {
            this.bonus_element = spellParent.main_element;
        }
        if(this.bonus_element == null && spellParent.bonus_element != null) {
            bonus_element = spellParent.bonus_element;
        }
        if(anim_element == null) {
            anim_element = spellParent.anim_element;
        }
        if(main_element == null && anim_element != null) {
            main_element = anim_element;
        }
    }

    /**
     * finds if the spell is already somewhere in the inventory of the player
     * @return
     */
    public boolean alreadyOwned() {

        boolean alreadyOwned = false;

        ArrayList<Spell> spells_in_inventory = new ArrayList<>();
        spells_in_inventory.addAll(player.spellbook.equippedSpells);
        spells_in_inventory.addAll(player.spellbook.knownSpells);
        if(player.spellbook.defensive_spell != null) {
            spells_in_inventory.add(player.spellbook.defensive_spell);
        }
        if(player.spellbook.utility_spell != null) {
            spells_in_inventory.add(player.spellbook.utility_spell);
        }

        Collections.sort(spellParts);
        for(Spell spell : spells_in_inventory) {
            Collections.sort(spell.spellParts);
            if(spell.spellParts.equals(this.spellParts)) {
                alreadyOwned = true;
            }
        }

        return alreadyOwned;
    }

    /**
     * checks if the player can craft this spell at the moment
     * @return true if there is space in spellbook + not duplicate spell
     */
    public boolean canMix() {

        boolean duplicate = this.alreadyOwned();
        boolean spaceInEquipped = player.spellbook.equippedSpells.size() < Unlocked.max_equipped_spells;
        boolean spaceInKnown = player.spellbook.knownSpells.size() < Unlocked.max_known_spells;
        boolean sameTypeEquipped = false;
        for(Spell equipped : player.spellbook.equippedSpells) {
            if(equipped.toString().equals(this.toString())) {
                sameTypeEquipped = true;
            }
        }

        if(duplicate) {
            return false;
        }

        // Defensive spell in empty defensive slot
        if(this.defensive && player.spellbook.defensive_spell == null) {
            return true;
        }

        // Utility spell in empty utility slot
        if(this.utility && player.spellbook.utility_spell == null) {
            return true;
        }

        // Able to equip spell after crafting
        if(spaceInEquipped && !sameTypeEquipped) {
            return true;
        }

        // There is space in known list
        return spaceInKnown;

    }

    public void learn() {

        boolean spaceInEquipped = player.spellbook.equippedSpells.size() < Unlocked.max_equipped_spells;
        boolean spaceInKnown = player.spellbook.knownSpells.size() < Unlocked.max_known_spells;
        boolean sameTypeEquipped = false;
        for(Spell equipped : player.spellbook.equippedSpells) {
            if(equipped.toString().equals(this.toString())) {
                sameTypeEquipped = true;
            }
        }

        // Defensive spell in empty defensive slot
        if(this.defensive && player.spellbook.defensive_spell == null) {
            player.spellbook.defensive_spell = this;
        } else

        // Utility spell in empty utility slot
        if(this.utility && player.spellbook.utility_spell == null) {
            player.spellbook.utility_spell = this;
        } else

        if(spaceInEquipped && !sameTypeEquipped) {
            player.spellbook.equippedSpells.add(this);
            resetCD();
        } else

        if(spaceInKnown) {
            player.spellbook.knownSpells.add(this);
        }
    }
    public void forget() {
        boolean equipped = player.spellbook.equippedSpells.contains(this);

        if(equipped && player.spellbook.equippedSpells.size() == 1) {
            player.spellbook.equippedSpells.remove(this);
            if(!player.spellbook.knownSpells.isEmpty()) {
                Spell spell_to_move = player.spellbook.knownSpells.getFirst();
                spell_to_move.resetCD();
                player.spellbook.equippedSpells.add(spell_to_move);
                player.spellbook.knownSpells.remove(spell_to_move);
            }
            return;
        }

        if(equipped) {
            int index = player.spellbook.equippedSpells.indexOf(this);
            cascadeCooldowns(index);

            player.spellbook.equippedSpells.remove(this);
            ArrayList<String> spells_equipped = new ArrayList<>();

            for(Spell spell : player.spellbook.equippedSpells) {
                spells_equipped.add(spell.name);
            }

            for(Spell spell : player.spellbook.knownSpells) {
                if(!spells_equipped.contains(spell.name)) {
                    player.spellbook.equippedSpells.add(spell);
                    player.spellbook.knownSpells.remove(spell);
                    spell.resetCD();
                    return;
                }
            }
        }

        if(!equipped) {
            player.spellbook.knownSpells.remove(this);
        }
    }
    private void cascadeCooldowns(int index) {
        if(index == 0) {
            player.spellManager.cooldown1 = player.spellManager.cooldown2;
            player.spellManager.cooldown2 = player.spellManager.cooldown3;
        }
        if(index == 1) {
            player.spellManager.cooldown2 = player.spellManager.cooldown3;
        }
    }

    public void resetCD() {
        for (int i = 0; i < player.spellbook.equippedSpells.size(); i++) {

            if(player.spellbook.equippedSpells.get(i).equals(this)) {
                if(i == 0) {
                    player.spellManager.cooldown1 = this.cooldown;
                }
                if(i == 1) {
                    player.spellManager.cooldown2 = this.cooldown;
                }
                if(i == 2) {
                    player.spellManager.cooldown3 = this.cooldown;
                }
                if(i == 3) {
                    player.spellManager.cooldown4 = this.cooldown;
                }
                break;
            }
        }
    }

    public abstract int getLvl();

    /**
     * Base dmg of the spell, according to current masteries
     * @return
     */
    public abstract int getDmg();

    public float getCooldown() {
        float ratio = player.spellbook.castSpeed/100;
        if(player.inventory.equippedHat instanceof Legendary_SentientHat && autoaimable) {
            ratio += 0.2f;
        }
        return cooldown * (1 - ratio);
    }


    public float getScaledSpeed() {
        return speed * (1 + player.spellbook.projSpeedBonus/100f);
    }

    /**
     * Dmg after scaling modifiers, elemental + allDmg
     * @return
     */
    public int getScaledDmg(float unscaledDmg) {
        float scaledDmg = unscaledDmg;
        switch(main_element) {
            case ARCANE -> scaledDmg *= (1 + player.spellbook.arcaneBonusDmg/100);
            case FROST -> scaledDmg *= (1 + player.spellbook.frostBonusDmg/100);
            case FIRE -> scaledDmg *= (1 + player.spellbook.fireBonusDmg/100);
            case LIGHTNING -> scaledDmg *= (1 + player.spellbook.lightningBonusDmg/100);
        }
        if(bonus_element != null) {
            switch (bonus_element) {
                case ARCANE -> scaledDmg *= (1 + player.spellbook.arcaneBonusDmg/100 / 2);
                case FROST -> scaledDmg *= (1 + player.spellbook.frostBonusDmg/100 / 2);
                case FIRE -> scaledDmg *= (1 + player.spellbook.fireBonusDmg/100 / 2);
                case LIGHTNING -> scaledDmg *= (1 + player.spellbook.lightningBonusDmg/100 / 2);
            }
        }
        scaledDmg *= (1 + player.spellbook.allBonusDmg/100f);
        return (int) scaledDmg;
    }

    public void dealDmg(Monster monster) {
        float dmg = getDmg();
        dmg = getScaledDmg(dmg);
        float randomFactor = MathUtils.random(1 - dmgVariance, 1 + dmgVariance);
        dmg *= randomFactor;

        checkGearProcs(monster);
        dmg = applyGearModifiers(monster, dmg);
        dmg = checkOtherModifiers(monster, dmg);

        monster.hp -= dmg;

        if(dmg_text_on) {
            dmgText( (int)dmg, monster);
        }
    }

    public float applyGearModifiers(Monster monster, float dmg) {
        float modifiedDmg = dmg;

        if(player.inventory.equippedStaff instanceof Legendary_FrostStaff && monster.freezeTimer > 0) {
            modifiedDmg = modifiedDmg * 1.5f;
        }

        if(player.inventory.equippedAmulet instanceof Rare_EliteAmulet && monster.heavy) {
            modifiedDmg = modifiedDmg * 1.2f;
        }

        if(player.inventory.equippedBook instanceof Epic_VogonBook) {
            float dst = monster.body.getPosition().dst(player.pawn.getPosition());
            if(dst <= 5.8) {
                modifiedDmg *= 1.3f;
            }
        }

        return modifiedDmg;
    }
    public void checkGearProcs(Monster monster) {
        if(player.inventory.equippedStaff instanceof Legendary_LightningStaff) {
              if(anim_element == Spell_Element.LIGHTNING && !(this instanceof ThundergodBolt_Spell)) {
                  Legendary_LightningStaff.castThunderbolt(monster, this);
              }
        }
        if(player.inventory.equippedStaff instanceof Legendary_FireStaff) {
            if(anim_element == Spell_Element.FIRE && !(this instanceof Brand_Explosion)) {
                Legendary_FireStaff staff = (Legendary_FireStaff) player.inventory.equippedStaff;
                staff.castBrand(monster, this);
            }
        }
        if(player.inventory.equippedStaff instanceof Legendary_ArcaneStaff) {
            if(anim_element == Spell_Element.ARCANE && !arcaneCasted) {
                Legendary_ArcaneStaff staff = (Legendary_ArcaneStaff) player.inventory.equippedStaff;
                staff.castArcaneMissile(monster);
            }
        }
    }
    public float checkOtherModifiers(Monster monster, float dmg) {
        float scaledDmg = dmg;
        if(this instanceof ChainLightning_Spell) {
            if(Math.random() >= 1 - player.spellbook.chainlightningBonus/100f) {
                scaledDmg = scaledDmg * 2;
            }
        }
        return scaledDmg;
    }


    public void dmgText(int dmg, Monster monster) {
        String s = "" + dmg;

        Color color = Color.RED;
        if(textColor != null) {
            color = textColor;
        } else {
            switch(anim_element) {
                case FIRE -> color = mainMenuSkin.getColor("LightOrange");
                case FROST -> color = mainMenuSkin.getColor("LightBlue");
                case ARCANE -> color = mainMenuSkin.getColor("LightPink");
                case LIGHTNING -> color = mainMenuSkin.getColor("LightYellow");
                case COLDLITE -> color = mainMenuSkin.getColor("LightTeal");
                case FIRELITE -> {
                    if(MathUtils.randomBoolean()) {
                        color = mainMenuSkin.getColor("LightOrange");
                    } else {
                        color = mainMenuSkin.getColor("LightYellow");
                    }
                }
            }
        }

        FloatingDamage text = screen.displayManager.textManager.pool.getDmgText();
        Vector2 monsterTextHeight = new Vector2(monster.body.getPosition().x, monster.body.getPosition().y + monster.height/PPM/2);
        Vector2 randomPosition = SpellUtils.getRandomVectorInRadius(monsterTextHeight, 0.5f);
        text.setAll(s, randomPosition.scl(PPM), mainMenuSkin.getFont("DamageNumbers"), color);
        screen.displayManager.textManager.addDmgText(text);
    }

    public boolean isLearnable() {
        for(Spell_Name part : spellParts) {
            switch(part) {
                case FROSTBOLT -> {
                    if(player.spellbook.frostbolt_lvl < 1) {
                        return false;
                    }
                }
                case ICESPEAR -> {
                    if(player.spellbook.icespear_lvl < 1) {
                        return false;
                    }
                }
                case FROZENORB -> {
                    if(player.spellbook.frozenorb_lvl < 1) {
                        return false;
                    }
                }
                case FLAMEJET -> {
                    if(player.spellbook.flamejet_lvl < 1) {
                        return false;
                    }
                }
                case FIREBALL -> {
                    if(player.spellbook.fireball_lvl < 1) {
                        return false;
                    }
                }
                case OVERHEAT -> {
                    if(player.spellbook.overheat_lvl < 1) {
                        return false;
                    }
                }
                case CHARGEDBOLTS -> {
                    if(player.spellbook.chargedbolt_lvl < 1) {
                        return false;
                    }
                }
                case CHAIN -> {
                    if(player.spellbook.chainlightning_lvl < 1) {
                        return false;
                    }
                }
                case THUNDERSTORM -> {
                    if(player.spellbook.thunderstorm_lvl < 1) {
                        return false;
                    }
                }
                case MISSILES -> {
                    if(player.spellbook.arcanemissile_lvl < 1) {
                        return false;
                    }
                }
                case BEAM -> {
                    if(player.spellbook.energybeam_lvl < 1) {
                        return false;
                    }
                }
                case RIFTS -> {
                    if(player.spellbook.rift_lvl < 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void autoAimCheck() {
        if(player.inventory.equippedHat instanceof Legendary_SentientHat && castByPawn) {
            Monster target = Legendary_SentientHat.findTarget();
            if(target != null) {
                targetPosition = new Vector2(target.body.getPosition());
            } else {
                screen.spellManager.remove(this);
            }
        } else {
            targetPosition = getTargetPosition();
        }
    }

}
