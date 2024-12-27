package wizardo.game.Items.Equipment.Amulet;

import wizardo.game.Items.Equipment.Equipment;

import static wizardo.game.Wizardo.player;

public abstract class Amulet extends Equipment {

    public void equip() {

        Equipment toStore;
        toStore = player.inventory.equippedAmulet;

        for (int i = 0; i < 15; i++) {
            if(player.inventory.holdingBox[i] != null && player.inventory.holdingBox[i].equals(this)) {
                player.inventory.holdingBox[i] = null;
            }
        }

        if(toStore != null) {
            toStore.store();
        }
        player.inventory.equippedAmulet = this;
    }
}
