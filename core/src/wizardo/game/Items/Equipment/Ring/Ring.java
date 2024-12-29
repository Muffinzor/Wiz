package wizardo.game.Items.Equipment.Ring;

import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.player;

public abstract class Ring extends Equipment {


    public void equip() {

        Equipment toStore;
        toStore = player.inventory.equippedRing;

        for (int i = 0; i < 15; i++) {
            if(player.inventory.holdingBox[i] != null && player.inventory.holdingBox[i].equals(this)) {
                player.inventory.holdingBox[i] = null;
            }
        }

        if(toStore != null) {
            toStore.storeAfterUnequip();
        }
        player.inventory.equippedRing = this;

        EquipmentUtils.applyGearStats(this, false);
    }

    public void pickup() {
        if(player.inventory.equippedRing == null) {
            this.equip();
        } else {
            this.storeAfterPickup();
        }
    }

    @Override
    public void unequip() {
        player.inventory.equippedRing = null;
        EquipmentUtils.applyGearStats(this, true);
        checkForSpellRemoval();
    }

    public static ArrayList<Equipment> getAllRings() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.addAll(getNormalRings());
        list.addAll(getRareRings());
        list.addAll(getEpicRings());
        list.addAll(getLegendaryRings());

        Collections.shuffle(list);
        return list;
    }

    public static ArrayList<Equipment> getNormalRings() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }

    public static ArrayList<Equipment> getRareRings() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }

    public static ArrayList<Equipment> getEpicRings() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Epic_OculusRing());

        return list;
    }

    public static ArrayList<Equipment> getLegendaryRings() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }
}
