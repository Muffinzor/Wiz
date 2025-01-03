package wizardo.game.Items.Equipment.Ring;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.player;

public abstract class Ring extends Equipment {

    public Ring() {
        slot = ItemUtils.EquipSlot.RING;
        name = EquipmentUtils.getRandomName(this);
    }


    public void equip() {

        Equipment toStore;
        toStore = player.inventory.equippedRing;

        for (int i = 0; i < 15; i++) {
            if(player.inventory.holdingBox[i] != null && player.inventory.holdingBox[i].equals(this)) {
                player.inventory.holdingBox[i] = null;
            }
        }

        if(toStore != null) {
            toStore.storeAfterUnequip();
        }
        player.inventory.equippedRing = this;
        player.inventory.equippedGear.add(this);
        EquipmentUtils.applyGearStats(this, false);
        checkForGearConditionalEffects();
    }

    public void pickup() {
        super.pickup();
        if(player.inventory.equippedRing == null) {
            this.equip();
        } else {
            this.storeAfterPickup();
        }
    }

    @Override
    public void unequip() {
        player.inventory.equippedRing = null;
        player.inventory.equippedGear.remove(this);
        removalSteps();
    }

    public static ArrayList<Equipment> getAllRings() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.addAll(getNormalRings());
        list.addAll(getRareRings());
        list.addAll(getEpicRings());
        list.addAll(getLegendaryRings());

        Collections.shuffle(list);
        return list;
    }

    public static ArrayList<Equipment> getNormalRings() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Normal_Ring());
        list.add(new Normal_Ring());

        return list;
    }

    public static ArrayList<Equipment> getRareRings() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Rare_Ring());
        list.add(new Rare_Ring());

        return list;
    }

    public static ArrayList<Equipment> getEpicRings() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Epic_OculusRing());

        return list;
    }

    public static ArrayList<Equipment> getLegendaryRings() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Legendary_DukeRing());

        return list;
    }

    public static void getNormalRingStats(Equipment piece, int quantity) {

        ArrayList<Integer> picks = new ArrayList<>();

        do {
            int roll = MathUtils.random(1, 4);
            while (picks.contains(roll)) {
                roll = MathUtils.random(1, 4);
            }
            picks.add(roll);

            switch(roll) {
                case 1 -> {
                    piece.gearStats.add(ItemUtils.GearStat.LUCK);
                    piece.quantity_gearStats.add(MathUtils.random(5,10));
                }
                case 2 -> {
                    piece.masteries.add(SpellUtils.getRandomMastery(null,1, false));
                    piece.quantity_masteries.add(1);
                }
                case 3 -> {
                    piece.gearStats.add(ItemUtils.GearStat.CASTSPEED);
                    piece.quantity_gearStats.add(MathUtils.random(4,6));
                }
                case 4 -> {
                    piece.gearStats.add(ItemUtils.GearStat.REGEN);
                    piece.quantity_gearStats.add(MathUtils.random(45,90));
                }

            }



        } while(picks.size() < quantity);



    }
    public static void getRareRingStats(Equipment piece, int quantity) {

        ArrayList<Integer> picks = new ArrayList<>();

        do {
            int roll = MathUtils.random(1, 6);
            while (picks.contains(roll)) {
                roll = MathUtils.random(1, 6);
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
                    piece.quantity_gearStats.add(MathUtils.random(5,10));
                }
                case 2 -> {
                    piece.masteries.add(SpellUtils.getRandomMastery(null,2, true));
                    piece.quantity_masteries.add(1);
                }
                case 3 -> {
                    piece.gearStats.add(ItemUtils.GearStat.DEFENSE);
                    piece.quantity_gearStats.add(MathUtils.random(1,2));
                }
                case 4 -> {
                    piece.gearStats.add(ItemUtils.GearStat.MULTICAST);
                    piece.quantity_gearStats.add(MathUtils.random(5,10));
                }
                case 5 -> {
                    piece.gearStats.add(ItemUtils.GearStat.LUCK);
                    piece.quantity_gearStats.add(MathUtils.random(5,10));
                }
                case 6 -> {
                    piece.gearStats.add(ItemUtils.GearStat.REGEN);
                    piece.quantity_gearStats.add(MathUtils.random(60,120));
                }
            }
        } while(picks.size() < quantity);
    }
    public static void getEpicRingStats(Equipment piece, int quantity) {

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
                    list.add(ItemUtils.GearStat.MASTERY_FROST);
                    list.add(ItemUtils.GearStat.MASTERY_FIRE);
                    list.add(ItemUtils.GearStat.MASTERY_ARCANE);
                    list.add(ItemUtils.GearStat.MASTERY_LIGHTNING);

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
                    piece.gearStats.add(ItemUtils.GearStat.REGEN);
                    piece.quantity_gearStats.add(MathUtils.random(90,150));
                }

            }
        } while(picks.size() < quantity);
    }
}
