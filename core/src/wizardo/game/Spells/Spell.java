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
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Spells.SpellUtils.*;

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
    public int aimReach = 15;

    public Animation<Sprite> anim;
    public Sprite spell_icon;
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

    public int baseDmg;
    public float dmgVariance = 0.15f;   //      0.85 - 1.15 by default
    public float speed;
    public float radius;
    public float cooldown;

    public boolean defensive;
    public boolean utility;

    public Spell nested_spell;
    public BaseScreen screen;


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
                Vector3 mouseUnprojected = currentScreen.mainCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                return new Vector2(mouseUnprojected.x / PPM, mouseUnprojected.y / PPM);
            } else {
                return controllerAimAssist();
            }
        } else {
            return targetPosition;
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
        } else

        if(spaceInKnown) {
            player.spellbook.knownSpells.add(this);
        }

    }

    public abstract int getLvl();

    /**
     * Base dmg of the spell, according to current masteries
     * @return
     */
    public abstract int getDmg();


    /**
     * Dmg after scaling modifiers, elemental + allDmg
     * @return
     */
    public int getScaledDmg() {
        float scaledDmg = getDmg();
        switch(main_element) {
            case ARCANE -> scaledDmg *= (1 + player.spellbook.arcaneBonusDmg);
            case FROST -> scaledDmg *= (1 + player.spellbook.frostBonusDmg);
            case FIRE -> scaledDmg *= (1 + player.spellbook.fireBonusDmg);
            case LIGHTNING -> scaledDmg *= (1 + player.spellbook.lightningBonusDmg);
        }
        if(bonus_element != null) {
            switch (bonus_element) {
                case ARCANE -> scaledDmg *= (1 + player.spellbook.arcaneBonusDmg / 2);
                case FROST -> scaledDmg *= (1 + player.spellbook.frostBonusDmg / 2);
                case FIRE -> scaledDmg *= (1 + player.spellbook.fireBonusDmg / 2);
                case LIGHTNING -> scaledDmg *= (1 + player.spellbook.lightningBonusDmg / 2);
            }
        }
        scaledDmg *= (1 + player.spellbook.allBonusDmg);
        return (int) scaledDmg;
    }

    public void dealDmg(Monster monster) {
        float dmg = getScaledDmg();
        float randomFactor = MathUtils.random(1 - dmgVariance, 1 + dmgVariance);
        dmg *= randomFactor;
        monster.hp -= dmg;

        if(dmg_text_on) {
            dmgText( (int)dmg, monster);
        }
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

}
