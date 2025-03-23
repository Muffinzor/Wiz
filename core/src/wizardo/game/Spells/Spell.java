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
import wizardo.game.Items.Equipment.Amulet.Legendary_MarkAmulet;
import wizardo.game.Items.Equipment.Amulet.Rare_EliteAmulet;
import wizardo.game.Items.Equipment.Book.Epic_VogonBook;
import wizardo.game.Items.Equipment.Book.Legendary_PulseBook;
import wizardo.game.Items.Equipment.Hat.Epic_TeleportHat;
import wizardo.game.Items.Equipment.Hat.Legendary_SentientHat;
import wizardo.game.Items.Equipment.Ring.Epic_FrostRing;
import wizardo.game.Items.Equipment.Staff.Legendary_ArcaneStaff;
import wizardo.game.Items.Equipment.Staff.Legendary_FireStaff;
import wizardo.game.Items.Equipment.Staff.Legendary_FrostStaff;
import wizardo.game.Items.Equipment.Staff.Legendary_LightningStaff;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Spell;
import wizardo.game.Spells.Arcane.Rifts.Rifts_Spell;
import wizardo.game.Spells.Fire.Fireball.Fireball_Spell;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Hybrid.Judgement.Judgement_Spell;
import wizardo.game.Spells.Hybrid.Laser.Laser_Spell;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Hit;
import wizardo.game.Spells.SpellUtils.*;
import wizardo.game.Spells.Unique.Brand.Brand_Explosion;
import wizardo.game.Spells.Unique.TeleportMonster;
import wizardo.game.Spells.Unique.ThundergodBolt.ThundergodBolt_Spell;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.GameSettings.*;
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
    public String string_name;
    public LevelUpEnums.LevelUps levelup_enum;


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
    public ArrayList<LevelUpEnums.LevelUps> spellParts = new ArrayList<>();


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

    public void exitCollision(MonsterSpell monsterSpell) {

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
        if(true) {
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
            direction.set(0, 1); // Default direction when targetVector is zero
        }
        direction.setLength(aimReach);

        float angleRange = 15;  // Total angle range of the cone
        int RAY_COUNT = 15;     // Number of rays in the cone

        final Monster[] overallClosestTarget = {null};
        final float[] shortestDistance = {Float.MAX_VALUE};
        Monster middleRayTarget = null; // To store the result of the middle ray separately

        // Raycasting callback
        RayCastCallback callback = (fixture, point, _, _) -> {
            Body body = fixture.getBody();
            if (body.getUserData() instanceof Monster) {
                float distance = player.pawn.body.getPosition().dst(point);
                if (distance < shortestDistance[0]) {
                    shortestDistance[0] = distance;
                    overallClosestTarget[0] = (Monster) body.getUserData();
                }
                return -1; // End this ray immediately if it hits a Monster
            }
            return 0; // Keep going if no Monster is hit
        };

        // Cast all rays in the cone
        for (int i = 0; i < RAY_COUNT; i++) {
            float angleOffset = -angleRange + i * (2 * angleRange / (RAY_COUNT - 1)); // Angle for this ray
            Vector2 rotatedDirection = direction.cpy().rotateDeg(angleOffset);       // Adjust direction by angle offset
            Vector2 endPoint = player.pawn.body.getPosition().cpy().add(rotatedDirection); // Compute ray endpoint

            // Handle the middle ray separately
            if (i == RAY_COUNT / 2) {
                // Cast the middle ray directly and check for its specific target
                final Monster[] middleRayClosestTarget = {null};
                final float[] middleRayShortestDistance = {Float.MAX_VALUE};

                world.rayCast((fixture, point, _, _) -> {
                    Body body = fixture.getBody();
                    if (body.getUserData() instanceof Monster) {
                        float distance = player.pawn.body.getPosition().dst(point);
                        if (distance < middleRayShortestDistance[0]) {
                            middleRayShortestDistance[0] = distance;
                            middleRayClosestTarget[0] = (Monster) body.getUserData();
                        }
                        return -1;
                    }
                    return 0;
                }, player.pawn.body.getPosition(), endPoint);

                middleRayTarget = middleRayClosestTarget[0];
            } else {
                world.rayCast(callback, player.pawn.body.getPosition(), endPoint);
            }
        }

        // Prioritize the middle ray's target if it exists, fallback to the closest overall target
        return middleRayTarget != null ? middleRayTarget : overallClosestTarget[0];
    }

    public void playSound(Vector2 position) {
        float dst_factor = 1;
        float dst = player.pawn.body.getPosition().dst(position);
        dst_factor -= dst * 0.025f;
        SoundPlayer.getSoundPlayer().playSound(soundPath, sound_volume);
    }

    public String toString() {
        return string_name;
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

        ArrayList<LevelUpEnums.LevelUps> thisSpellPartsCopy = new ArrayList<>(spellParts);

        if (spellParts.size() > 2) {
            Collections.sort(thisSpellPartsCopy);
        }

        for (Spell spell : spells_in_inventory) {
            ArrayList<LevelUpEnums.LevelUps> spellPartsCopy = new ArrayList<>(spell.spellParts);

            if (spellPartsCopy.size() > 2) {
                Collections.sort(spellPartsCopy);
            }

            if (spellPartsCopy.equals(thisSpellPartsCopy)) {
                alreadyOwned = true;
                break;
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
                spells_equipped.add(spell.string_name);
            }

            for(Spell spell : player.spellbook.knownSpells) {
                if(!spells_equipped.contains(spell.string_name)) {
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
        float castspeed_bonus = player.spellbook.castSpeed/100;
        if(player.inventory.equippedHat instanceof Legendary_SentientHat && autoaimable) {
            castspeed_bonus += 0.2f;
        }
        return Math.max(cooldown * (1 - castspeed_bonus), cooldown/2);
    }


    public float getScaledSpeed() {
        return speed * (1 + player.spellbook.projSpeedBonus/100f);
    }

    /**
     * Dmg after scaling modifiers, elemental + allDmg
     * @return
     */
    public int apply_elemental_dmg_bonus(float unscaledDmg) {
        float scaledDmg = unscaledDmg;
        if(anim_element != null) {
            switch (anim_element) {
                case ARCANE -> scaledDmg *= (1 + player.spellbook.arcaneBonusDmg / 100);
                case FROST -> scaledDmg *= (1 + player.spellbook.frostBonusDmg / 100);
                case FIRE -> scaledDmg *= (1 + player.spellbook.fireBonusDmg / 100);
                case LIGHTNING -> scaledDmg *= (1 + player.spellbook.lightningBonusDmg / 100);
                case COLDLITE -> {
                    float liteBonus = (player.spellbook.lightningBonusDmg / 100 * 0.75f);
                    float coldBonus = (player.spellbook.frostBonusDmg / 100 * 0.75f);
                    scaledDmg *= (1 + liteBonus + coldBonus);
                }
                case FIRELITE -> {
                    float liteBonus = (player.spellbook.lightningBonusDmg / 100 * 0.75f);
                    float fireBonus = (player.spellbook.fireBonusDmg / 100 * 0.75f);
                    scaledDmg *= (1 + liteBonus + fireBonus);
                }
            }
        }
        scaledDmg *= (1 + player.spellbook.allBonusDmg/100f);
        return (int) scaledDmg;
    }

    public void dealDmg(Monster monster) {
        float dmg = getDmg();
        dmg = apply_specific_spell_dmg_bonus(dmg);
        dmg = apply_elemental_dmg_bonus(dmg);
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

    public float apply_specific_spell_dmg_bonus(float dmg) {
        switch (this) {
            case Frostbolt_Spell _ -> dmg *= (1 + player.spellbook.frostbolts_bonus_dmg / 100f);
            case Icespear_Spell _ -> dmg *= (1 + player.spellbook.icespear_bonus_dmg / 100f);
            case Frozenorb_Spell _ -> dmg *= (1 + player.spellbook.frozenorb_bonus_dmg / 100f);
            case Flamejet_Spell _ -> dmg *= (1 + player.spellbook.flamejet_bonus_dmg / 100f);
            case Fireball_Spell _ -> dmg *= (1 + player.spellbook.fireball_bonus_dmg / 100f);
            case Overheat_Spell _ -> dmg *= (1 + player.spellbook.overheat_bonus_dmg / 100f);
            case ChargedBolts_Spell _ -> dmg *= (1 + player.spellbook.chargedbolts_bonus_dmg / 100f);
            case ChainLightning_Spell _ -> dmg *= (1 + player.spellbook.chainlightning_bonus_dmg / 100f);
            case Thunderstorm_Hit _ -> dmg *= (1 + player.spellbook.thunderstorm_bonus_dmg / 100f);
            case ArcaneMissile_Spell _ -> dmg *= (1 + player.spellbook.arcane_missile_bonus_dmg / 100f);
            case EnergyBeam_Spell _ -> dmg *= (1 + player.spellbook.energybeam_bonus_dmg / 100f);
            case Rifts_Spell _ -> dmg *= (1 + player.spellbook.rifts_bonus_dmg / 100f);
            case Judgement_Spell _ -> dmg += (1 + player.spellbook.judgement_bonus_dmg / 100f);
            default -> {}
        }
        return dmg;
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
        } else
        if(player.inventory.equippedStaff instanceof Legendary_FireStaff) {
            if(anim_element == Spell_Element.FIRE && !(this instanceof Brand_Explosion)) {
                Legendary_FireStaff staff = (Legendary_FireStaff) player.inventory.equippedStaff;
                staff.castBrand(monster, this);
            }
        } else
        if(player.inventory.equippedStaff instanceof Legendary_ArcaneStaff) {
            if(anim_element == Spell_Element.ARCANE && !arcaneCasted) {
                Legendary_ArcaneStaff staff = (Legendary_ArcaneStaff) player.inventory.equippedStaff;
                staff.castArcaneMissile(monster);
            }
        }

        if(player.inventory.equippedAmulet instanceof Legendary_MarkAmulet) {
            if(this instanceof Icespear_Spell || this instanceof ArcaneMissile_Spell ||
                    this instanceof Laser_Spell || this instanceof EnergyBeam_Spell) {
                if(monster.marked) {
                    Legendary_MarkAmulet ammy = (Legendary_MarkAmulet) player.inventory.equippedAmulet;
                    ammy.detonateMonster(monster);
                }
            }
        }

        if(player.inventory.equippedRing instanceof Epic_FrostRing && anim_element.equals(Spell_Element.FROST)) {
            if(Math.random() > 0.8f) {
                monster.applyFreeze(2, 3.5f);
            }
        }

        if(player.inventory.equippedHat instanceof Epic_TeleportHat && anim_element.equals(Spell_Element.ARCANE)) {
            float procRate = Epic_TeleportHat.getProcTreshold(this);
            if(!monster.elite && Math.random() >= procRate) {
                TeleportMonster teleport = new TeleportMonster(monster);
                screen.spellManager.add(teleport);
            }
        }

        if(player.inventory.equippedBook instanceof Legendary_PulseBook book) {
            book.createPulsar(monster, this);
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
        Color color;

        if(textColor != null) {
            color = textColor;
        } else {
            color = get_element_color();
        }

        FloatingDamage text = screen.displayManager.textManager.pool.getDmgText();
        Vector2 monsterTextHeight = new Vector2(monster.body.getPosition().x, monster.body.getPosition().y + monster.height/PPM/2);
        Vector2 randomPosition = SpellUtils.getRandomVectorInRadius(monsterTextHeight, 0.5f);
        text.setAll(s, randomPosition.scl(PPM), mainMenuSkin.getFont("DamageNumbers"), color);
        screen.displayManager.textManager.addDmgText(text);
    }

    public Color get_element_color() {
        Color color = null;
        switch(anim_element) {
            case FIRE -> color = mainMenuSkin.getColor("LightRed");
            case FROST -> color = mainMenuSkin.getColor("LightBlue");
            case ARCANE -> color = mainMenuSkin.getColor("LightPink");
            case LIGHTNING -> color = mainMenuSkin.getColor("LightYellow");
            case COLDLITE -> color = mainMenuSkin.getColor("LightTeal");
            case FIRELITE -> color = mainMenuSkin.getColor("LightOrange");
        }
        return color;
    }

    public String get_element_string() {
        String s = "";
        switch(anim_element) {
            case FIRE -> s = "Fire";
            case FROST -> s = "Frost";
            case ARCANE -> s = "Arcane";
            case LIGHTNING -> s = "Lightning";
            case COLDLITE -> s = "Coldlite";
            case FIRELITE -> s = "Firelite";
        }
        return s;
    }

    public boolean isLearnable() {
        for(LevelUpEnums.LevelUps part : spellParts) {
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
        if(player.inventory.equippedHat instanceof Legendary_SentientHat && castByPawn || autoAim_On) {
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

    public void completeAutoAimCheck() {
        if(autoAim_On && castByPawn) {
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
