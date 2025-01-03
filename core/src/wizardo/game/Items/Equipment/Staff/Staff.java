package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.player;

public abstract class Staff extends Equipment {

    public Staff() {
        slot = ItemUtils.EquipSlot.STAFF;
        name = EquipmentUtils.getRandomName(this);
        displayRotation = 25;
    }

    public void equip() {

        Equipment toStore;
        toStore = player.inventory.equippedStaff;

        for (int i = 0; i < 15; i++) {
            if(player.inventory.holdingBox[i] != null && player.inventory.holdingBox[i].equals(this)) {
                player.inventory.holdingBox[i] = null;
            }
        }

        if(toStore != null) {
            toStore.storeAfterUnequip();
        }
        player.inventory.equippedStaff = this;
        player.inventory.equippedGear.add(this);
        EquipmentUtils.applyGearStats(this, false);
        checkForGearConditionalEffects();
    }

    public void pickup() {
        super.pickup();
        if(player.inventory.equippedStaff == null) {
            this.equip();
        } else {
            this.storeAfterPickup();
        }
    }

    @Override
    public void unequip() {
        player.inventory.equippedStaff = null;
        player.inventory.equippedGear.remove(this);
        removalSteps();
    }

    public static ArrayList<Equipment> getAllStaffs() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.addAll(getNormalStaffs());
        list.addAll(getRareStaffs());
        list.addAll(getEpicStaffs());
        list.addAll(getLegendaryStaffs());

        Collections.shuffle(list);
        return list;
    }

    public static ArrayList<Equipment> getNormalStaffs() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Normal_Staff());
        list.add(new Normal_Staff());

        return list;
    }

    public static ArrayList<Equipment> getRareStaffs() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Rare_Staff());
        list.add(new Rare_Staff());
        list.add(new Rare_Staff());
        list.add(new Rare_Staff());
        list.add(new Rare_TwigStaff());
        list.add(new Rare_StoneStaff());

        return list;
    }

    public static ArrayList<Equipment> getEpicStaffs() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Epic_Staff());
        list.add(new Epic_Staff());
        list.add(new Epic_Staff());
        list.add(new Epic_Staff());

        list.add(new Epic_FireballStaff());
        list.add(new Epic_IcespearStaff());
        list.add(new Epic_FrozenorbStaff());
        list.add(new Epic_ChainStaff());
        list.add(new Epic_ChargedboltStaff());
        list.add(new Epic_EnergybeamStaff());
        list.add(new Epic_RiftStaff());
        list.add(new Epic_FrostboltStaff());
        list.add(new Epic_MissileStaff());

        return list;
    }

    public static ArrayList<Equipment> getLegendaryStaffs() {
        ArrayList<Equipment> list = new ArrayList<>();

        list.add(new Legendary_ArcaneStaff());
        list.add(new Legendary_LightningStaff());
        list.add(new Legendary_FireStaff());
        list.add(new Legendary_FrostStaff());
        list.add(new Legendary_WeaveStaff());

        return list;
    }

    public static void getNormalStaffStats(Equipment piece, int quantity) {

        ArrayList<Integer> picks = new ArrayList<>();

        do {
            int roll = MathUtils.random(1, 4);
            while (picks.contains(roll)) {
                roll = MathUtils.random(1, 4);
            }
            picks.add(roll);

            switch(roll) {
                case 1 -> {
                    piece.gearStats.add(ItemUtils.getEleDmgStat());
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
                    piece.gearStats.add(ItemUtils.GearStat.MULTICAST);
                    piece.quantity_gearStats.add(MathUtils.random(4,6));
                }

            }



        } while(picks.size() < quantity);



    }
    public static void getRareStaffStats(Equipment piece, int quantity) {

        ArrayList<Integer> picks = new ArrayList<>();

        do {
            int roll = MathUtils.random(1, 5);
            while (picks.contains(roll)) {
                roll = MathUtils.random(1, 5);
            }
            picks.add(roll);

            switch(roll) {
                case 1 -> {
                    piece.gearStats.add(ItemUtils.getEleDmgStat());
                    piece.quantity_gearStats.add(MathUtils.random(8,15));
                }
                case 2 -> {
                    piece.masteries.add(SpellUtils.getRandomMastery(null,2, true));
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
                    piece.masteries.add(SpellUtils.getRandomMastery(null,1, true));
                    piece.quantity_masteries.add(1);
                }
                case 6 -> {
                    piece.gearStats.add(ItemUtils.GearStat.PROJSPEED);
                    piece.quantity_gearStats.add(MathUtils.random(6,15));
                }

            }
        } while(picks.size() < quantity);
    }
    public static void getEpicStaffStats(Equipment piece, int quantity) {

        ArrayList<Integer> picks = new ArrayList<>();

        do {
            int roll = MathUtils.random(1, 6);
            while (picks.contains(roll)) {
                roll = MathUtils.random(1, 6);
            }
            picks.add(roll);

            switch(roll) {

                case 1 -> {
                    piece.masteries.add(SpellUtils.getRandomMastery(null,2, true));
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
                    piece.masteries.add(SpellUtils.getRandomMastery(null,3, false));
                    piece.quantity_masteries.add(2);
                }

            }
        } while(picks.size() < quantity);
    }

}
