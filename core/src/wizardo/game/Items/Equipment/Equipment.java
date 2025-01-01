package wizardo.game.Items.Equipment;

import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;

public abstract class Equipment {

    public ItemUtils.EquipQuality quality = ItemUtils.EquipQuality.NORMAL;
    public ItemUtils.EquipSlot slot;

    public Sprite sprite;
    public Sprite spriteOver;
    public boolean reversedSprite;
    public int displayRotation;
    public float displayScale = 1;

    public String name;
    public String title;
    public String description;
    public String flavorText = null;

    public ArrayList<SpellUtils.Spell_Name> masteries = new ArrayList<>();
    public ArrayList<Integer> quantity_masteries = new ArrayList<>();
    public ArrayList<ItemUtils.GearStat> gearStats = new ArrayList<>();
    public ArrayList<Integer> quantity_gearStats = new ArrayList<>();

    public void update(float delta) {

    }
    public abstract void pickup();

    public void equip() {

    }

    public static void checkForGearConditionalEffects() {
        for (Equipment piece : player.inventory.equippedGear) {
            piece.applySelfVerification();
        }
    }
    /** Helper method for checkForConditionalEffects, to be implemented to gear itself if conditional effects */
    public void applySelfVerification() {

    }
    public abstract void unequip();
    public void storeAfterUnequip() {
        for (int i = 0; i < player.inventory.holdingBox.length; i++) {
            if(player.inventory.holdingBox[i] == null) {
                player.inventory.holdingBox[i] = this;
                this.unequip();
                break;
            }
        }
    }
    public void storeAfterPickup() {
        for (int i = 0; i < player.inventory.holdingBox.length; i++) {
            if(player.inventory.holdingBox[i] == null) {
                player.inventory.holdingBox[i] = this;
                break;
            }
        }
    }

    public String getDescription() {
        return description;
    }

    /** returns the value of an item's gearStat according to its order in the gearStat list */
    public float getStatValue(int index) {
        if(gearStats.get(index) == ItemUtils.GearStat.REGEN) {
            float regen = quantity_gearStats.get(index);
            regen = regen/60f;
            return regen;
        } else {
            return quantity_gearStats.get(index);
        }
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

    public void removalSteps() {
        EquipmentUtils.applyGearStats(this, true);
        checkForSpellRemoval();
        applySelfVerification();
        checkForGearConditionalEffects();
    }

}
