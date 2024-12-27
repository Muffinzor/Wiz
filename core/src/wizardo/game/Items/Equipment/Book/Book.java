package wizardo.game.Items.Equipment.Book;

import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;

import static wizardo.game.Wizardo.player;

public abstract class Book extends Equipment {

    public void equip() {

        Equipment toStore;
        toStore = player.inventory.equippedBook;

        for (int i = 0; i < 15; i++) {
            if(player.inventory.holdingBox[i] != null && player.inventory.holdingBox[i].equals(this)) {
                player.inventory.holdingBox[i] = null;
            }
        }

        if(toStore != null) {
            toStore.store();
        }
        player.inventory.equippedBook = this;

        EquipmentUtils.applyGearStats(this, false);
    }

    @Override
    public void unequip() {
        player.inventory.equippedBook = null;
        EquipmentUtils.applyGearStats(this, true);
        checkForSpellRemoval();
    }
}
