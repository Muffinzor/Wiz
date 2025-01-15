package wizardo.game.Items.Drop;

import wizardo.game.Items.Equipment.Amulet.Epic_StormAmulet;
import wizardo.game.Items.Equipment.Amulet.Legendary_FireballAmulet;
import wizardo.game.Items.Equipment.Book.Epic_FireAcaneBook;
import wizardo.game.Items.Equipment.Book.Epic_OrbitBook;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.Equipment.Hat.*;
import wizardo.game.Items.Equipment.Ring.Epic_OculusRing;
import wizardo.game.Items.Equipment.Robes.Legendary_FreezeRobes;
import wizardo.game.Items.Equipment.Robes.Rare_Robes;
import wizardo.game.Items.Equipment.Staff.*;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Maps.Chest;
import wizardo.game.Resources.EffectAnims.GearFlareAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Items.ItemUtils.EquipSlot.ALL;

public class DropManager {

    Wizardo game;

    ArrayList<String> alreadyDroppedNameList;
    ArrayList<Drop> drops;

    public DropManager(Wizardo game) {
        this.game = game;
        alreadyDroppedNameList = new ArrayList<>();
        drops = new ArrayList<>();
    }

    public void update(float delta) {
        for(Drop drop : drops) {
            drop.update(delta);
        }

        if(delta > 0) {
            for (Drop drop : drops) {
                if (drop.pickedUp && drop.stateTime >= GearFlareAnims.gear_pop.getAnimationDuration()) {
                    drop.dispose();
                }
            }

            drops.removeIf(drop -> drop.alpha <= 0.05f);
            drops.removeIf(drop -> drop.pickedUp && drop.stateTime >= GearFlareAnims.gear_pop.getAnimationDuration());
        }
    }

    public void addDrop(Drop drop) {
        drops.add(drop);
        if(game.currentScreen instanceof BattleScreen) {
            drop.screen = (BattleScreen) game.currentScreen;
        } else {
            drop.screen = (BattleScreen) game.getPreviousScreen();
        }
    }

    public void dropChestLoot(Chest chest) {

        ArrayList<Drop> drops = chest.loot.getDrops();
        for(Drop drop : drops) {
            if(drop != null) {
                if(drop instanceof EquipmentDrop) {
                    if(((EquipmentDrop) drop).piece != null) {
                        addDrop(drop);
                    }
                } else {
                    addDrop(drop);
                }
            }
        }

    }

    /** returns a single piece of equipment from the selection, after verifying if it hasn't already been dropped.
     If specificDropName is not null, it will disregard the verification and simply return that item **/
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
            ItemUtils.EquipSlot randomSlot = EquipmentUtils.getRandomEquipSlot();
            possibleDrops.addAll(EquipmentUtils.get_all_items_from_class(randomSlot, quality));
            possibleDrops.removeIf(equip -> alreadyDroppedNameList.contains(equip.name));

            if(possibleDrops.isEmpty()) {
                possibleDrops.addAll(EquipmentUtils.get_all_items_from_class(ALL, quality));
                possibleDrops.removeIf(equip -> alreadyDroppedNameList.contains(equip.name));
            }

            if(!possibleDrops.isEmpty()) {
                drop = possibleDrops.getFirst();
                alreadyDroppedNameList.add(drop.name);
            }
        }

        return drop;
    }

}
