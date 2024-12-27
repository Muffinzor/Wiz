package wizardo.game.Items.Equipment.Hat;

import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static wizardo.game.Wizardo.player;

public abstract class Hat extends Equipment {

    public void equip() {

        Equipment toStore;
        toStore = player.inventory.equippedHat;

        for (int i = 0; i < 15; i++) {
            if(player.inventory.holdingBox[i] != null && player.inventory.holdingBox[i].equals(this)) {
                player.inventory.holdingBox[i] = null;
            }
        }

        if(toStore != null) {
            toStore.store();
        }
        player.inventory.equippedHat = this;

        EquipmentUtils.applyGearStats(this, false);
    }

    @Override
    public void unequip() {
        player.inventory.equippedHat = null;
        EquipmentUtils.applyGearStats(this, true);
        checkForSpellRemoval();
    }

    public static ItemUtils.GearStat getRareStat() {
        List<ItemUtils.GearStat> list = new ArrayList<>();

        list.add(ItemUtils.GearStat.FIREDMG);
        list.add(ItemUtils.GearStat.FROSTDMG);
        list.add(ItemUtils.GearStat.LITEDMG);
        list.add(ItemUtils.GearStat.ARCANEDMG);

        Collections.shuffle(list);
        return list.getFirst();
    }
}
