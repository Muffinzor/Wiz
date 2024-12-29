package wizardo.game.Items.Drop;

import wizardo.game.Items.Equipment.Amulet.Epic_StormAmulet;
import wizardo.game.Items.Equipment.Book.Epic_OrbitBook;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.Equipment.Hat.Epic_OrbitHat;
import wizardo.game.Items.Equipment.Hat.Normal_Hat;
import wizardo.game.Items.Equipment.Hat.Rare_Hat;
import wizardo.game.Items.Equipment.Ring.Epic_OculusRing;
import wizardo.game.Items.Equipment.Robes.Legendary_FreezeRobes;
import wizardo.game.Items.Equipment.Robes.Rare_Robes;
import wizardo.game.Items.Equipment.Staff.Epic_FireballStaff;
import wizardo.game.Items.Equipment.Staff.Legendary_LightningStaff;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Maps.Chest;
import wizardo.game.Resources.EffectAnims.GearFlareAnims;
import wizardo.game.Screens.Battle.BattleScreen;

import java.util.ArrayList;

import static wizardo.game.Items.ItemUtils.EquipSlot.ALL;

public class DropManager {

    ArrayList<String> alreadyDroppedNameList;
    BattleScreen screen;

    ArrayList<Drop> drops;

    public DropManager(BattleScreen screen) {
        this.screen = screen;
        alreadyDroppedNameList = new ArrayList<>();
        drops = new ArrayList<>();
    }

    public void update(float delta) {
        for(Drop drop : drops) {
            drop.update(delta);
        }

        for(Drop drop : drops) {
            if(drop.pickedUp && drop.stateTime >= GearFlareAnims.gear_pop.getAnimationDuration()) {
                drop.dispose();
            }
        }

        drops.removeIf(drop -> drop.pickedUp && drop.stateTime >= GearFlareAnims.gear_pop.getAnimationDuration());
    }

    public void addDrop(Drop drop) {
        drops.add(drop);
        drop.screen = screen;
    }

    public void dropChestLoot(Chest chest) {
        Equipment piece3 = new Epic_OrbitBook();
        Drop drop3 = new EquipmentDrop(chest.body.getPosition(), piece3);
        addDrop(drop3);


        Equipment piece4 = new Epic_OrbitHat();
        Drop drop4 = new EquipmentDrop(chest.body.getPosition(), piece4);
        addDrop(drop4);
    }

    /** returns a single piece of equipment from the selection, after verifying if it hasn't already been dropped
     if specificDropName is not null, it will disregard the verification and simply return that item **/
    public Equipment getEquipmentForDrop(ItemUtils.EquipSlot slot, ItemUtils.EquipQuality quality, String specificDropName) {

        Equipment drop = null;

        ArrayList<Equipment> possibleDrops = new ArrayList<>();
        if(specificDropName != null) {
            possibleDrops.addAll(EquipmentUtils.get_all_items_from_class(ALL, ItemUtils.EquipQuality.ALL));
            for(Equipment equip : possibleDrops) {
                if(equip.name.equals(specificDropName)) {
                    return equip;
                }
            }
        } else {
            possibleDrops.addAll(EquipmentUtils.get_all_items_from_class(slot, quality));
            possibleDrops.removeIf(equip -> alreadyDroppedNameList.contains(equip.name));

            if(!possibleDrops.isEmpty()) {
                drop = possibleDrops.getFirst();
                alreadyDroppedNameList.add(drop.name);
            }
        }

        return drop;
    }

}
