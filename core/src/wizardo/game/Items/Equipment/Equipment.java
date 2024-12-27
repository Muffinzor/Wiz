package wizardo.game.Items.Equipment;

import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.Equipment.Amulet.Amulet;
import wizardo.game.Items.Equipment.Book.Book;
import wizardo.game.Items.Equipment.Hat.Hat;
import wizardo.game.Items.Equipment.Ring.Ring;
import wizardo.game.Items.Equipment.Robes.Robes;
import wizardo.game.Items.Equipment.SoulStone.SoulStone;
import wizardo.game.Items.Equipment.Staff.Frozenorb_EpicStaff;
import wizardo.game.Items.Equipment.Staff.Staff;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Spell;
import wizardo.game.Spells.Fire.Fireball.Fireball_Spell;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;

public abstract class Equipment {

    public ItemUtils.EquipQuality quality = ItemUtils.EquipQuality.NORMAL;

    public Sprite sprite;
    public Sprite spriteOver;
    public int displayRotation;
    public float displayScale = 1;

    public String name;
    public String title;
    public String description;
    public String flavorText;

    public ArrayList<SpellUtils.Spell_Name> masteries = new ArrayList<>();
    public ArrayList<Integer> quantity_masteries = new ArrayList<>();
    public ArrayList<ItemUtils.GearStat> gearStats = new ArrayList<>();
    public ArrayList<Integer> quantity_gearStats = new ArrayList<>();

    public abstract void equip();
    public abstract void unequip();
    public void store() {
        for (int i = 0; i < player.inventory.holdingBox.length; i++) {
            if(player.inventory.holdingBox[i] == null) {
                player.inventory.holdingBox[i] = this;
                this.unequip();
                break;
            }
        }
    }

    public String getDescription() {
        return description;
    }

    /** returns the value of an item's gearStat according to its order in the gearStat list */
    public int getStatValue(int index) {
        return quantity_gearStats.get(index);
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void checkForSpellRemoval() {

        int knownLoops = player.spellbook.knownSpells.size();
        for (int i = 0; i < knownLoops; i++) {
            for(Spell spell : player.spellbook.knownSpells) {
                if(!spell.isLearnable()) {
                    spell.forget();
                    break;
                }
            }
        }

        int equippedloops = player.spellbook.equippedSpells.size();
        for (int i = 0; i < equippedloops; i++) {
            for(Spell spell : player.spellbook.equippedSpells) {
                if(!spell.isLearnable()) {
                    spell.forget();
                    break;
                }
            }
        }


    }

}
