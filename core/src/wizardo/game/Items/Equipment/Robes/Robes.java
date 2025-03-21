package wizardo.game.Items.Equipment.Robes;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.player;

public abstract class Robes extends Equipment {

    public Robes() {
        slot = ItemUtils.EquipSlot.ROBE;
        name = EquipmentUtils.getRandomName(this);
    }


    public void equip() {

        Equipment toStore;
        toStore = player.inventory.equippedRobes;

        for (int i = 0; i < 15; i++) {
            if(player.inventory.holdingBox[i] != null && player.inventory.holdingBox[i].equals(this)) {
                player.inventory.holdingBox[i] = null;
            }
        }

        if(toStore != null) {
            toStore.storeAfterUnequip();
        }
        player.inventory.equippedRobes = this;
        player.inventory.equippedGear.add(this);
        EquipmentUtils.applyGearStats(this, false);
    }

    public void pickup() {
        super.pickup();
        if(player.inventory.equippedRobes == null) {
            this.equip();
        } else {
            this.storeAfterPickup();
        }
    }

    @Override
    public void unequip() {
        player.inventory.equippedRobes = null;
        player.inventory.equippedGear.remove(this);
        removalSteps();
    }

    public static ArrayList<Equipment> getAllRobes() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.addAll(getNormalRobes());
        list.addAll(getRareRobes());
        list.addAll(getEpicRobes());
        list.addAll(getLegendaryRobes());

        Collections.shuffle(list);
        return list;
    }

    public static ArrayList<Equipment> getNormalRobes() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Normal_Robes());
        list.add(new Normal_Robes());

        return list;
    }

    public static ArrayList<Equipment> getRareRobes() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Rare_Robes());
        list.add(new Rare_Robes());

        return list;
    }

    public static ArrayList<Equipment> getEpicRobes() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Epic_IronRobes());
        list.add(new Epic_HasteCloak());

        return list;
    }

    public static ArrayList<Equipment> getLegendaryRobes() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Legendary_FrostRobes());
        list.add(new Legendary_FireRobes());
        list.add(new Legendary_ShieldRobes());
        list.add(new Legendary_LightningRobes());
        list.add(new Legendary_ArcaneRobes());

        return list;
    }

    public static void getNormalRobesStats(Equipment piece, int quantity) {

        ArrayList<Integer> picks = new ArrayList<>();

        do {
            int roll = MathUtils.random(1, 4);
            while (picks.contains(roll)) {
                roll = MathUtils.random(1, 4);
            }
            picks.add(roll);

            switch(roll) {
                case 1 -> {
                    piece.gearStats.add(ItemUtils.GearStat.MULTICAST);
                    piece.quantity_gearStats.add(5);
                }
                case 2 -> {
                    piece.gearStats.add(ItemUtils.GearStat.WALKSPEED);
                    piece.quantity_gearStats.add(5);
                }
                case 3 -> {
                    ArrayList<ItemUtils.GearStat> list = new ArrayList<>();
                    list.add(ItemUtils.GearStat.FIREDMG);
                    list.add(ItemUtils.GearStat.FROSTDMG);
                    list.add(ItemUtils.GearStat.LITEDMG);
                    list.add(ItemUtils.GearStat.ARCANEDMG);

                    Collections.shuffle(list);
                    piece.gearStats.add(list.getFirst());
                    piece.quantity_gearStats.add(5);
                }
                case 4 -> {
                    piece.gearStats.add(ItemUtils.GearStat.REGEN);
                    piece.quantity_gearStats.add(30);
                }
            }
        } while(picks.size() < quantity);



    }
    public static void getRareRobesStats(Equipment piece, int quantity) {

        ArrayList<Integer> picks = new ArrayList<>();

        do {
            int roll = MathUtils.random(1, 5);
            while (picks.contains(roll)) {
                roll = MathUtils.random(1, 5);
            }
            picks.add(roll);

            switch(roll) {
                case 1 -> {
                    piece.gearStats.add(ItemUtils.GearStat.DEFENSE);
                    piece.quantity_gearStats.add(MathUtils.random(1,2));
                }
                case 2 -> {
                    piece.masteries.add(SpellUtils.getRandomMastery(null,2, false));
                    piece.quantity_masteries.add(1);
                }
                case 3 -> {
                    piece.gearStats.add(ItemUtils.GearStat.REGEN);
                    piece.quantity_gearStats.add(MathUtils.random(30,60));
                }
                case 4 -> {
                    piece.gearStats.add(ItemUtils.GearStat.ALLDMG);
                    piece.quantity_gearStats.add(5);
                }
                case 5 -> {
                    piece.gearStats.add(ItemUtils.GearStat.LUCK);
                    piece.quantity_gearStats.add(MathUtils.random(5,10));
                }
            }
        } while(picks.size() < quantity);
    }

}
