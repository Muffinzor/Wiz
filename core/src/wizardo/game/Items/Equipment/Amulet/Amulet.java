package wizardo.game.Items.Equipment.Amulet;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.player;

public abstract class Amulet extends Equipment {

    public Amulet() {
        slot = ItemUtils.EquipSlot.AMULET;
        name = EquipmentUtils.getRandomName(this);
    }

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
        player.inventory.equippedGear.add(this);
        EquipmentUtils.applyGearStats(this, false);
        checkForGearConditionalEffects();
    }

    @Override
    public void unequip() {
        player.inventory.equippedAmulet = null;
        player.inventory.equippedGear.remove(this);
        removalSteps();
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

        list.add(new Rare_Amulet());
        list.add(new Rare_Amulet());

        return list;
    }

    public static ArrayList<Equipment> getEpicAmulets() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Epic_Amulet());
        list.add(new Epic_Amulet());
        list.add(new Epic_StormAmulet());

        return list;
    }

    public static ArrayList<Equipment> getLegendaryAmulets() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Legendary_FireballAmulet());

        return list;
    }

    public static void getNormalAmuletStats(Equipment piece, int quantity) {

        ArrayList<Integer> picks = new ArrayList<>();

        do {
            int roll = MathUtils.random(1, 4);
            while (picks.contains(roll)) {
                roll = MathUtils.random(1, 4);
            }
            picks.add(roll);

            switch(roll) {
                case 1 -> {
                    piece.gearStats.add(ItemUtils.GearStat.PROJSPEED);
                    piece.quantity_gearStats.add(MathUtils.random(8,15));
                }
                case 2 -> {
                    piece.masteries.add(SpellUtils.getRandomMastery(null,1, true));
                    piece.quantity_masteries.add(1);
                }
                case 3 -> {
                    piece.gearStats.add(ItemUtils.GearStat.REGEN);
                    piece.quantity_gearStats.add(MathUtils.random(45,90));
                }
                case 4 -> {
                    piece.gearStats.add(ItemUtils.GearStat.MULTICAST);
                    piece.quantity_gearStats.add(MathUtils.random(5,10));
                }

            }
        } while(picks.size() < quantity);
    }
    public static void getRareAmuletStats(Equipment piece, int quantity) {

        ArrayList<Integer> picks = new ArrayList<>();

        do {
            int roll = MathUtils.random(1, 5);
            while (picks.contains(roll)) {
                roll = MathUtils.random(1, 5);
            }
            picks.add(roll);

            switch(roll) {
                case 1 -> {
                    ArrayList<ItemUtils.GearStat> list = new ArrayList<>();
                    list.add(ItemUtils.GearStat.FIREDMG);
                    list.add(ItemUtils.GearStat.FROSTDMG);
                    list.add(ItemUtils.GearStat.LITEDMG);
                    list.add(ItemUtils.GearStat.ARCANEDMG);

                    Collections.shuffle(list);
                    piece.gearStats.add(list.getFirst());
                    piece.quantity_gearStats.add(MathUtils.random(8,15));
                }
                case 2 -> {
                    piece.masteries.add(SpellUtils.getRandomMastery(null,1, false));
                    piece.quantity_masteries.add(2);
                }
                case 3 -> {
                    piece.gearStats.add(ItemUtils.GearStat.CASTSPEED);
                    piece.quantity_gearStats.add(MathUtils.random(5,10));
                }
                case 4 -> {
                    piece.gearStats.add(ItemUtils.GearStat.MULTICAST);
                    piece.quantity_gearStats.add(MathUtils.random(5,10));
                }
                case 5 -> {
                    piece.gearStats.add(ItemUtils.GearStat.PROJSPEED);
                    piece.quantity_gearStats.add(MathUtils.random(12,20));
                }
            }
        } while(picks.size() < quantity);
    }
    public static void getEpicAmuletStats(Equipment piece, int quantity) {

        ArrayList<Integer> picks = new ArrayList<>();

        do {
            int roll = MathUtils.random(1, 6);
            while (picks.contains(roll)) {
                roll = MathUtils.random(1, 6);
            }
            picks.add(roll);

            switch(roll) {

                case 1 -> {
                    piece.masteries.add(SpellUtils.getRandomMastery(null,2, false));
                    piece.quantity_masteries.add(2);
                }
                case 2 -> {
                    ArrayList<ItemUtils.GearStat> list = new ArrayList<>();
                    list.add(ItemUtils.GearStat.MASTERY_LIGHTNING);
                    list.add(ItemUtils.GearStat.MASTERY_ARCANE);
                    list.add(ItemUtils.GearStat.MASTERY_FIRE);
                    list.add(ItemUtils.GearStat.MASTERY_FROST);

                    Collections.shuffle(list);
                    piece.gearStats.add(list.getFirst());
                    piece.quantity_gearStats.add(1);
                }
                case 3 -> {
                    piece.gearStats.add(ItemUtils.GearStat.CASTSPEED);
                    piece.quantity_gearStats.add(MathUtils.random(11,15));
                }
                case 4 -> {
                    piece.gearStats.add(ItemUtils.GearStat.MULTICAST);
                    piece.quantity_gearStats.add(MathUtils.random(11,15));
                }
                case 5 -> {
                    piece.gearStats.add(ItemUtils.GearStat.PROJSPEED);
                    piece.quantity_gearStats.add(MathUtils.random(15,25));
                }
                case 6 -> {
                    ArrayList<ItemUtils.GearStat> list = new ArrayList<>();
                    list.add(ItemUtils.GearStat.FIREDMG);
                    list.add(ItemUtils.GearStat.ARCANEDMG);
                    list.add(ItemUtils.GearStat.FROSTDMG);
                    list.add(ItemUtils.GearStat.LITEDMG);

                    Collections.shuffle(list);
                    piece.gearStats.add(list.getFirst());
                    piece.quantity_gearStats.add(MathUtils.random(10, 15));
                }
            }
        } while(picks.size() < quantity);
    }
}
