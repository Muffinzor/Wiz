package wizardo.game.Items.Equipment.SoulStone;

import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.player;

public abstract class SoulStone extends Equipment {

    public SoulStone() {
        slot = ItemUtils.EquipSlot.RUNESTONE;
        name = EquipmentUtils.getRandomName(this);
    }

    public void equip() {

        Equipment toStore;
        toStore = player.inventory.equippedStone;

        for (int i = 0; i < 15; i++) {
            if(player.inventory.holdingBox[i] != null && player.inventory.holdingBox[i].equals(this)) {
                player.inventory.holdingBox[i] = null;
            }
        }

        if(toStore != null) {
            toStore.storeAfterUnequip();
        }
        player.inventory.equippedStone = this;
        player.inventory.equippedGear.add(this);
        EquipmentUtils.applyGearStats(this, false);
        checkForGearConditionalEffects();
    }

    public void pickup() {
        super.pickup();
        if(player.inventory.equippedStone == null) {
            this.equip();
        } else {
            this.storeAfterPickup();
        }
    }

    @Override
    public void unequip() {
        player.inventory.equippedStone = null;
        player.inventory.equippedGear.remove(this);
        removalSteps();
    }

    public static ArrayList<Equipment> getAllStones() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.addAll(getNormalStones());
        list.addAll(getRareStones());
        list.addAll(getEpicStones());
        list.addAll(getLegendaryStones());

        Collections.shuffle(list);
        return list;
    }

    public static ArrayList<Equipment> getNormalStones() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }

    public static ArrayList<Equipment> getRareStones() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }

    public static ArrayList<Equipment> getEpicStones() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }

    public static ArrayList<Equipment> getLegendaryStones() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }
}
