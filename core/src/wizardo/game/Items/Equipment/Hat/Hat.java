package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

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
            toStore.storeAfterUnequip();
        }
        player.inventory.equippedHat = this;

        EquipmentUtils.applyGearStats(this, false);
    }

    public void pickup() {
        if(player.inventory.equippedHat == null) {
            this.equip();
        } else {
            this.storeAfterPickup();
        }
    }

    @Override
    public void unequip() {
        player.inventory.equippedHat = null;
        EquipmentUtils.applyGearStats(this, true);
        checkForSpellRemoval();
    }

    public static ArrayList<Equipment> getAllHats() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.addAll(getNormalHats());
        list.addAll(getRareHats());
        list.addAll(getEpicHats());
        list.addAll(getLegendaryHats());

        Collections.shuffle(list);
        return list;
    }

    public static ArrayList<Equipment> getNormalHats() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Normal_Hat());

        return list;
    }

    public static ArrayList<Equipment> getRareHats() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Rare_Hat());

        return list;
    }

    public static ArrayList<Equipment> getEpicHats() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Epic_OrbitHat());

        return list;
    }

    public static ArrayList<Equipment> getLegendaryHats() {
        ArrayList<Equipment> list = new ArrayList<>();

        return list;
    }
    public static void getNormalHatStats(Equipment piece, int quantity) {

        ArrayList<Integer> picks = new ArrayList<>();

        do {
            int roll = MathUtils.random(1, 3);
            while (picks.contains(roll)) {
                roll = MathUtils.random(1, 3);
            }
            picks.add(roll);

            switch(roll) {
                case 1 -> {
                    piece.gearStats.add(ItemUtils.GearStat.PROJSPEED);
                    piece.quantity_gearStats.add(MathUtils.random(12,20));
                }
                case 2 -> {
                    piece.masteries.add(SpellUtils.getRandomMastery(null,1, false));
                    piece.quantity_masteries.add(1);
                }
                case 3 -> {
                    piece.gearStats.add(ItemUtils.GearStat.CASTSPEED);
                    piece.quantity_gearStats.add(MathUtils.random(4,6));
                }

            }



        } while(picks.size() < quantity);



    }
    public static void getRareHatStats(Equipment piece, int quantity) {

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
                    piece.quantity_gearStats.add(MathUtils.random(5,10));
                }
                case 2 -> {
                    piece.masteries.add(SpellUtils.getRandomMastery(null,2, false));
                    piece.quantity_masteries.add(1);
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
                    piece.gearStats.add(ItemUtils.GearStat.LUCK);
                    piece.quantity_gearStats.add(MathUtils.random(5,10));
                }
            }
        } while(picks.size() < quantity);
    }

}
