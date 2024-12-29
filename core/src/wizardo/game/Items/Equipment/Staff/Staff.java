package wizardo.game.Items.Equipment.Staff;

import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.player;

public abstract class Staff extends Equipment {

    public void equip() {

        Equipment toStore;
        toStore = player.inventory.equippedStaff;

        for (int i = 0; i < 15; i++) {
            if(player.inventory.holdingBox[i] != null && player.inventory.holdingBox[i].equals(this)) {
                player.inventory.holdingBox[i] = null;
            }
        }

        if(toStore != null) {
            toStore.storeAfterUnequip();
        }
        player.inventory.equippedStaff = this;

        EquipmentUtils.applyGearStats(this, false);

    }

    public void pickup() {
        if(player.inventory.equippedStaff == null) {
            this.equip();
        } else {
            this.storeAfterPickup();
        }
    }

    @Override
    public void unequip() {
        player.inventory.equippedStaff = null;
        EquipmentUtils.applyGearStats(this, true);
        checkForSpellRemoval();
    }

    public static ArrayList<Equipment> getAllUniques() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Epic_FireballStaff());
        list.add(new Epic_FrozenorbStaff());
        list.add(new Epic_IcespearStaff());

        Collections.shuffle(list);
        return list;
    }

    public static ArrayList<Equipment> getAllStaffs() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.addAll(getNormalStaffs());
        list.addAll(getRareStaffs());
        list.addAll(getEpicStaffs());
        list.addAll(getLegendaryStaffs());

        Collections.shuffle(list);
        return list;
    }

    public static ArrayList<Equipment> getNormalStaffs() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }

    public static ArrayList<Equipment> getRareStaffs() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }

    public static ArrayList<Equipment> getEpicStaffs() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Epic_FireballStaff());
        list.add(new Epic_IcespearStaff());
        list.add(new Epic_FrozenorbStaff());

        return list;
    }

    public static ArrayList<Equipment> getLegendaryStaffs() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Legendary_LightningStaff());

        return list;
    }

}
