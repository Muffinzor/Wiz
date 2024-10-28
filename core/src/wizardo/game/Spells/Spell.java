package wizardo.game.Spells;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Account.Unlocked;
import wizardo.game.Audio.Sounds.SoundPlayer;
import wizardo.game.Monsters.Monster;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Spells.Fire.Fireball.Fireball_Spell;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.SpellUtils.*;
import wizardo.game.Wizardo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static wizardo.game.GameSettings.sound_volume;
import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public abstract class Spell implements Cloneable {

    public Animation<Sprite> anim;
    public float red = 0;
    public float green = 0;
    public float blue = 0;
    public float lightAlpha = 1;


    public Vector2 spawnPosition;
    public Vector2 targetPosition;
    public boolean castByPawn;
    public Body originBody;
    public float stateTime = 0;
    public boolean initialized;
    public boolean finished;
    public String soundPath;
    public String name;

    public float dmg;
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
                Vector3 mouseUnprojected = currentScreen.mainCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                return new Vector2(mouseUnprojected.x / PPM, mouseUnprojected.y / PPM);
            }
        } else {
            return targetPosition;
        }
    }

    public void playSound(Vector2 position) {
        float dst_factor = 1;
        float dst = player.pawn.body.getPosition().dst(position);
        dst_factor -= dst * 0.025f;
        SoundPlayer.getSoundPlayer().playSound(soundPath, dst_factor * sound_volume);
    }

    public String toString() {
        return name;
    }

    public void setElements(Spell spellParent) {
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

    public boolean alreadyCrafted() {

        boolean alreadyCrafted = false;

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
                alreadyCrafted = true;
            }
        }

        return alreadyCrafted;
    }

    /**
     * checks if the player can craft this spell at the moment
     * @return true if there is space in spellbook + not duplicate spell
     */
    public boolean canMix() {

        boolean duplicate = this.alreadyCrafted();
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

    public void inherit(Spell parent) {
        this.screen = parent.screen;
    }

}
