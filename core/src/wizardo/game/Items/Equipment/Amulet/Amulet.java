package wizardo.game.Items.Equipment.Amulet;

import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.player;

public abstract class Amulet extends Equipment {

    public void pickup() {
        if(player.inventory.equippedAmulet == null) {
            this.equip();
        } else {
            this.storeAfterPickup();
        }
    }

    public void equip() {
        Equipment toStore;
        toStore = player.inventory.equippedAmulet;

        for (int i = 0; i < 15; i++) {
            if(player.inventory.holdingBox[i] != null && player.inventory.holdingBox[i].equals(this)) {
                player.inventory.holdingBox[i] = null;
            }
        }

        if(toStore != null) {
            toStore.storeAfterUnequip();
        }
        player.inventory.equippedAmulet = this;

        EquipmentUtils.applyGearStats(this, false);
    }

    @Override
    public void unequip() {
        player.inventory.equippedAmulet = null;
        EquipmentUtils.applyGearStats(this, true);
        checkForSpellRemoval();
    }

    public static ArrayList<Equipment> getAllAmulets() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.addAll(getNormalAmulets());
        list.addAll(getRareAmulets());
        list.addAll(getEpicAmulets());
        list.addAll(getLegendaryAmulets());

        Collections.shuffle(list);
        return list;
    }

    public static ArrayList<Equipment> getNormalAmulets() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }

    public static ArrayList<Equipment> getRareAmulets() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }

    public static ArrayList<Equipment> getEpicAmulets() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Epic_StormAmulet());

        return list;
    }

    public static ArrayList<Equipment> getLegendaryAmulets() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Legendary_FireballAmulet());

        return list;
    }
}
