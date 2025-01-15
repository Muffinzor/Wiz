package wizardo.game.Maps;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Items.Drop.*;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.Staff.Legendary_WeaveStaff;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;

import static wizardo.game.Items.ItemUtils.EquipSlot.ALL;
import static wizardo.game.Wizardo.player;

public class ChestLoot {

    Chest chest;
    int tier;
    ArrayList<Drop> drops;

    public ChestLoot(Chest chest) {
        this.chest = chest;
        tier = chest.tier;
        drops = new ArrayList<>();
    }

    public void buildDrops() {
        if(tier == 4) {
            GoldChest();
        }
        if(tier == 3) {
            StoneChest();
        }
        if(tier == 2) {
            RedChest();
        }
        if(tier == 1) {
            MetalChest();
        }
        if(tier == 0) {
            WoodenChest();
        }
    }

    public void GoldChest() {
        Equipment legend = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.LEGENDARY, null);
        drops.add(new EquipmentDrop(chest.body.getPosition(), legend));

        int extraLoot = MathUtils.random(1,3);
        for (int i = 0; i < extraLoot; i++) {
            drops.add(goldChestDropTable());
        }

    }
    public void StoneChest() {
        Equipment epic = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.EPIC, null);
        drops.add(new EquipmentDrop(chest.body.getPosition(), epic));

        int extraLoot = MathUtils.random(0,3);
        for (int i = 0; i < extraLoot; i++) {
            drops.add(stoneChestDropTable());
        }
    }
    public void RedChest() {
        double itemRoll = Math.random();
        if(itemRoll >= 0.66f) {
            Equipment epic = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.EPIC, null);
            drops.add(new EquipmentDrop(chest.body.getPosition(), epic));
        } else {
            Equipment rare = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.RARE, null);
            drops.add(new EquipmentDrop(chest.body.getPosition(), rare));
        }

        int extraLoot = MathUtils.random(0,2);
        for (int i = 0; i < extraLoot; i++) {
            drops.add(redChestDropTable());
        }


    }
    public void MetalChest() {
        int loots = MathUtils.random(1,2);
        for (int i = 0; i < loots; i++) {
            drops.add(metalChestDropTable());
        }
    }
    public void WoodenChest() {
        int loots = MathUtils.random(1,2);
        for (int i = 0; i < loots; i++) {
            drops.add(woodenChestDropTable());
        }
    }

    public void powerLoot() {
        Equipment rare = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.RARE, "The Phoenix Pelt");
        drops.add(new EquipmentDrop(chest.body.getPosition(), rare));
        Equipment rare2 = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.RARE, "The Duke's Signet");
        drops.add(new EquipmentDrop(chest.body.getPosition(), rare2));
    }

    public Drop goldChestDropTable() {
        Drop drop = null;
        int roll = MathUtils.random(1,19);
        switch(roll) {
            case 1,2 -> {
                Equipment legend = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.LEGENDARY, null);
                drop = new EquipmentDrop(chest.body.getPosition(), legend);
            }
            case 3,4,5,6 -> {
                Equipment epic = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.EPIC, null);
                drop = new EquipmentDrop(chest.body.getPosition(), epic);
            }
            case 7,8 -> {
                Equipment rare = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.RARE, null);
                drop = new EquipmentDrop(chest.body.getPosition(), rare);
            }
            case 9,10,11,12 -> {
                drop = new ScrollDrop(chest.body.getPosition(), null, null, 3, true);
            }
            case 13,14,15 -> {
                drop = new GoldDrop(chest.body.getPosition(), 6,7);
            }
            case 16,17 -> {
                drop = new ReagentDrop(chest.body.getPosition(), 100);
            }
            case 18,19 -> {
                drop = new ReagentDrop(chest.body.getPosition(), 25);
            }
        }
        return drop;
    }
    public Drop stoneChestDropTable() {
        Drop drop = null;
        int roll = MathUtils.random(1,19);
        switch(roll) {
            case 1,2 -> {
                Equipment legend = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.LEGENDARY, null);
                drop = new EquipmentDrop(chest.body.getPosition(), legend);
            }
            case 3,4,5 -> {
                Equipment epic = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.EPIC, null);
                drop = new EquipmentDrop(chest.body.getPosition(), epic);
            }
            case 6,7,8 -> {
                Equipment rare = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.RARE, null);
                drop = new EquipmentDrop(chest.body.getPosition(), rare);
            }
            case 9,10,11 -> {
                drop = new ScrollDrop(chest.body.getPosition(), null, null, 3, true);
            }
            case 12,13,14,15 -> {
                drop = new GoldDrop(chest.body.getPosition(), 5,6);
            }
            case 16-> {
                drop = new ReagentDrop(chest.body.getPosition(), 100);
            }
            case 17,18,19 -> {
                drop = new ReagentDrop(chest.body.getPosition(), 25);
            }
        }
        return drop;
    }
    public Drop redChestDropTable() {
        Drop drop = null;
        int roll = MathUtils.random(1,22);
        switch(roll) {
            case 1 -> {
                Equipment legend = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.LEGENDARY, null);
                drop = new EquipmentDrop(chest.body.getPosition(), legend);
            }
            case 2,3,4 -> {
                Equipment epic = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.EPIC, null);
                drop = new EquipmentDrop(chest.body.getPosition(), epic);
            }
            case 5,6,7,8,21 -> {
                Equipment rare = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.RARE, null);
                drop = new EquipmentDrop(chest.body.getPosition(), rare);
            }
            case 9,10,11,19 -> {
                drop = new ScrollDrop(chest.body.getPosition(), null, null, 3, true);
            }
            case 12,13,14,15,20 -> {
                drop = new GoldDrop(chest.body.getPosition(), 4,6);
            }
            case 16-> {
                drop = new ReagentDrop(chest.body.getPosition(), 100);
            }
            case 17,18,22 -> {
                drop = new ReagentDrop(chest.body.getPosition(), 20);
            }
        }
        return drop;
    }
    public Drop metalChestDropTable() {
        Drop drop = null;
        int roll = MathUtils.random(1,30);
        switch(roll) {
            case 1 -> {
                if(Math.random() >= .5) {
                    Equipment legend = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.LEGENDARY, null);
                    drop = new EquipmentDrop(chest.body.getPosition(), legend);
                } else {
                    Equipment epic = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.EPIC, null);
                    drop = new EquipmentDrop(chest.body.getPosition(), epic);
                }
            }
            case 2,3 -> {
                Equipment epic = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.EPIC, null);
                drop = new EquipmentDrop(chest.body.getPosition(), epic);
            }
            case 4,5,6,7 -> {
                Equipment rare = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.RARE, null);
                drop = new EquipmentDrop(chest.body.getPosition(), rare);
            }
            case 8,9,10 -> {
                Equipment common = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.NORMAL, null);
                drop = new EquipmentDrop(chest.body.getPosition(), common);
            }
            case 11,12,13,14,15,16 -> {
                drop = new ScrollDrop(chest.body.getPosition(), null, null, 3, true);
            }
            case 17,18,19,20,21,22,23,24,25,26,27 -> {
                drop = new GoldDrop(chest.body.getPosition(), 3,4);
            }
            case 28, 29 -> {
                drop = new ReagentDrop(chest.body.getPosition(), 20);
            }
            case 30 -> {
                drop = new ReagentDrop(chest.body.getPosition(), 40);
            }
        }
        return drop;
    }
    public Drop woodenChestDropTable() {
        Drop drop = null;
        int roll = MathUtils.random(1,22);
        switch(roll) {
            case 1 -> {
                if(Math.random() >= .75) {
                    Equipment legend = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.LEGENDARY, null);
                    drop = new EquipmentDrop(chest.body.getPosition(), legend);
                } else {
                    Equipment epic = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.EPIC, null);
                    drop = new EquipmentDrop(chest.body.getPosition(), epic);
                }
            }
            case 2 -> {
                Equipment epic = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.EPIC, null);
                drop = new EquipmentDrop(chest.body.getPosition(), epic);
            }
            case 3,4 -> {
                Equipment rare = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.RARE, null);
                drop = new EquipmentDrop(chest.body.getPosition(), rare);
            }
            case 5,6,7 -> {
                Equipment common = chest.screen.dropManager.getEquipmentForDrop(ALL, ItemUtils.EquipQuality.NORMAL, null);
                drop = new EquipmentDrop(chest.body.getPosition(), common);
            }
            case 8,9,10,11 -> {
                drop = new ScrollDrop(chest.body.getPosition(), null, null, 3, true);
            }
            case 12,13,14,15,16,17,18,19,20 -> {
                drop = new GoldDrop(chest.body.getPosition(), 2,4);
            }
            case 21 -> {
                drop = new ReagentDrop(chest.body.getPosition(), 20);
            }
            case 22 -> {
                drop = new ReagentDrop(chest.body.getPosition(), 40);
            }
        }
        return drop;
    }

    public ArrayList<Drop> getDrops() {
        buildDrops();
        return drops;
    }
}
