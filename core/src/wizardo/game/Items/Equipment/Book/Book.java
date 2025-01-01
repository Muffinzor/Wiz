package wizardo.game.Items.Equipment.Book;

import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.player;

public abstract class Book extends Equipment {

    public Book() {
        slot = ItemUtils.EquipSlot.SPELLBOOK;
        name = EquipmentUtils.getRandomName(this);
    }

    public void equip() {

        Equipment toStore;
        toStore = player.inventory.equippedBook;

        for (int i = 0; i < 15; i++) {
            if(player.inventory.holdingBox[i] != null && player.inventory.holdingBox[i].equals(this)) {
                player.inventory.holdingBox[i] = null;
            }
        }

        if(toStore != null) {
            toStore.storeAfterUnequip();
        }
        player.inventory.equippedBook = this;
        player.inventory.equippedGear.add(this);
        EquipmentUtils.applyGearStats(this, false);
        checkForGearConditionalEffects();
    }

    public void pickup() {
        if(player.inventory.equippedBook == null) {
            this.equip();
        } else {
            this.storeAfterPickup();
        }
    }

    @Override
    public void unequip() {
        player.inventory.equippedBook = null;
        player.inventory.equippedGear.remove(this);
        removalSteps();
    }

    public static ArrayList<Equipment> getAllBooks() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.addAll(getNormalBooks());
        list.addAll(getRareBooks());
        list.addAll(getEpicBooks());
        list.addAll(getLegendaryBooks());

        Collections.shuffle(list);
        return list;
    }

    public static ArrayList<Equipment> getNormalBooks() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }

    public static ArrayList<Equipment> getRareBooks() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }

    public static ArrayList<Equipment> getEpicBooks() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Epic_VogonBook());
        list.add(new Epic_FireAcaneBook());
        list.add(new Epic_OrbitBook());

        return list;
    }

    public static ArrayList<Equipment> getLegendaryBooks() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Legendary_GoldBook());

        return list;
    }
}
