package wizardo.game.Maps.Shop;

import wizardo.game.Items.Drop.DropManager;
import wizardo.game.Items.Drop.ScrollDrop;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

public class MapShopInventory {

    ArrayList<Equipment> gear;
    ArrayList<SpellUtils.Spell_Name> scrolls;
    Boolean[] solds;

    boolean legendaryRolled;

    DropManager dropManager;

    public MapShopInventory(MapShop shop) {
        this.dropManager = shop.screen.dropManager;
        this.gear = new ArrayList<>();
        this.scrolls = new ArrayList<>();
        solds = new Boolean[4];
        for (int i = 0; i < 4; i++) {
            solds[i] = false;
        }
    }

    public void createInventory() {
        for (int i = 0; i < 3; i++) {
            gear.add(dropManager.getEquipmentForDrop(ItemUtils.EquipSlot.ALL, randomQuality(), null));
        }
        for (int i = 0; i < 4; i++) {
            scrolls.add(SpellUtils.getRandomMastery(null, 3, true));
        }
    }

    public ItemUtils.EquipQuality randomQuality() {
        ItemUtils.EquipQuality quality = ItemUtils.EquipQuality.NORMAL;
        double random = Math.random();

        if(random >= 0.95f && !legendaryRolled) {
            quality = ItemUtils.EquipQuality.LEGENDARY;
            legendaryRolled = true;
        } else if(random >= 0.75f) {
            quality = ItemUtils.EquipQuality.EPIC;
        } else if(random >= 0.4f) {
            quality = ItemUtils.EquipQuality.RARE;
        }

        return quality;
    }
}
