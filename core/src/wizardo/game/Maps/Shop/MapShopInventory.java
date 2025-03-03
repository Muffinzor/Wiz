package wizardo.game.Maps.Shop;

import wizardo.game.Items.Drop.DropManager;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

public class MapShopInventory {

    ArrayList<Equipment> gear;
    ArrayList<SpellUtils.Spell_Name> scrolls;
    Boolean[] scroll_solds;
    Boolean[] reagent_solds;

    boolean legendaryRolled;

    DropManager dropManager;

    public MapShopInventory(MapShop shop) {
        this.dropManager = shop.screen.dropManager;
        this.gear = new ArrayList<>();
        this.scrolls = new ArrayList<>();
        scroll_solds = new Boolean[]{false, false, false, false};
        reagent_solds = new Boolean[]{false, false};
    }

    public void createInventory() {
        for (int i = 0; i < 4; i++) {
            gear.add(dropManager.getEquipmentForDrop(ItemUtils.EquipSlot.ALL, randomQuality(), null));
            Collections.shuffle(gear);
        }
        for (int i = 0; i < 4; i++) {
            scrolls.add(SpellUtils.getRandomMastery(null, 3, true));
        }
    }

    public ItemUtils.EquipQuality randomQuality() {
        ItemUtils.EquipQuality quality = ItemUtils.EquipQuality.NORMAL;
        double random = Math.random();

        if(random >= 0.9f && !legendaryRolled) {
            quality = ItemUtils.EquipQuality.LEGENDARY;
            legendaryRolled = true;
        } else if(random >= 0.7f) {
            quality = ItemUtils.EquipQuality.EPIC;
        } else if(random >= 0.4f) {
            quality = ItemUtils.EquipQuality.RARE;
        }

        return quality;
    }
}
