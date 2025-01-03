package wizardo.game.Items.Equipment;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.Amulet.Amulet;
import wizardo.game.Items.Equipment.Book.Book;
import wizardo.game.Items.Equipment.Hat.Hat;
import wizardo.game.Items.Equipment.Ring.Ring;
import wizardo.game.Items.Equipment.Robes.Legendary_ShieldRobes;
import wizardo.game.Items.Equipment.Robes.Robes;
import wizardo.game.Items.Equipment.SoulStone.SoulStone;
import wizardo.game.Items.Equipment.Staff.Staff;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static wizardo.game.Items.ItemUtils.EquipSlot.RING;
import static wizardo.game.Wizardo.player;

public class EquipmentUtils {

    public static void applyGearStats(Equipment piece, boolean remove) {
        applyGearMasteries(piece, remove);
        applyOtherStats(piece, remove);
    }

    private static void applyGearMasteries(Equipment piece, boolean remove) {
        ArrayList<SpellUtils.Spell_Name> masteries = piece.masteries;

        if(!masteries.isEmpty()) {
            for (int i = 0; i < masteries.size(); i++) {
                int value = piece.quantity_masteries.get(i);
                if(remove) {
                    value = -value;
                }
                switch(masteries.get(i)) {
                    case FROSTBOLT -> {
                        player.spellbook.frostbolt_lvl += value;
                        player.stats.bonusMastery_frostbolt += value;
                    }
                    case ICESPEAR -> {
                        player.spellbook.icespear_lvl += value;
                        player.stats.bonusMastery_icespear += value;
                    }
                    case FROZENORB -> {
                        player.spellbook.frozenorb_lvl += value;
                        player.stats.bonusMastery_frozenorb += value;
                    }
                    case FLAMEJET -> {
                        player.spellbook.flamejet_lvl += value;
                        player.stats.bonusMastery_flamejet += value;
                    }
                    case FIREBALL -> {
                        player.spellbook.fireball_lvl += value;
                        player.stats.bonusMastery_fireball += value;
                    }
                    case OVERHEAT -> {
                        player.spellbook.overheat_lvl += value;
                        player.stats.bonusMastery_overheat += value;
                    }
                    case CHARGEDBOLTS -> {
                        player.spellbook.chargedbolt_lvl += value;
                        player.stats.bonusMastery_overheat += value;
                    }
                    case CHAIN -> {
                        player.spellbook.chainlightning_lvl += value;
                        player.stats.bonusMastery_chainlightning += value;
                    }
                    case THUNDERSTORM -> {
                        player.spellbook.thunderstorm_lvl += value;
                        player.stats.bonusMastery_thunderstorm += value;
                    }
                    case MISSILES -> {
                        player.spellbook.arcanemissile_lvl += value;
                        player.stats.bonusMastery_missiles += value;
                    }
                    case BEAM -> {
                        player.spellbook.energybeam_lvl += value;
                        player.stats.bonusMastery_beam += value;
                    }
                    case RIFTS -> {
                        player.spellbook.rift_lvl += value;
                        player.stats.bonusMastery_rifts += value;
                    }
                }
            }
        }
    }
    private static void applyOtherStats(Equipment piece, boolean remove) {
        ArrayList<ItemUtils.GearStat> gearStats = piece.gearStats;

        if(!gearStats.isEmpty()) {
            for (int i = 0; i < gearStats.size(); i++) {
                if(gearStats.get(i) == null) {
                    continue;
                }

                int value = piece.quantity_gearStats.get(i);
                if(remove) {
                    value = -value;
                }

                switch(gearStats.get(i)) {
                    case FIREDMG -> player.spellbook.fireBonusDmg += value;
                    case FROSTDMG -> player.spellbook.frostBonusDmg += value;
                    case LITEDMG -> player.spellbook.lightningBonusDmg += value;
                    case ARCANEDMG -> player.spellbook.arcaneBonusDmg += value;
                    case ALLDMG -> player.spellbook.allBonusDmg += value;
                    case CASTSPEED -> player.spellbook.castSpeed += value;
                    case MULTICAST -> player.spellbook.multicast += value;
                    case LUCK -> player.stats.luck += value;
                    case REGEN -> player.stats.baseRecharge += value/100f;
                    case DEFENSE -> player.stats.damageReduction += value;
                    case PROJSPEED -> player.spellbook.projSpeedBonus += value;
                    case WALKSPEED -> player.stats.runSpeed += (value/100f) * 2.7f;
                    case MAXSHIELD -> {
                        if(player.inventory.equippedRobes instanceof Legendary_ShieldRobes) {
                            Legendary_ShieldRobes robes = (Legendary_ShieldRobes) player.inventory.equippedRobes;
                            player.stats.maxShield += robes.taxShield(value);
                        } else {
                            player.stats.maxShield += value;
                        }
                    }
                    case MASTERY_FROST -> {
                        player.spellbook.frostbolt_lvl += value;
                        player.spellbook.icespear_lvl += value;
                        player.spellbook.frozenorb_lvl += value;
                    }
                    case MASTERY_FIRE -> {
                        player.spellbook.flamejet_lvl += value;
                        player.spellbook.fireball_lvl += value;
                        player.spellbook.overheat_lvl += value;
                    }
                    case MASTERY_ARCANE -> {
                        player.spellbook.arcanemissile_lvl += value;
                        player.spellbook.energybeam_lvl += value;
                        player.spellbook.rift_lvl += value;
                    }
                    case MASTERY_LIGHTNING -> {
                        player.spellbook.chargedbolt_lvl += value;
                        player.spellbook.chainlightning_lvl += value;
                        player.spellbook.thunderstorm_lvl += value;
                    }
                    case MASTERY_ALL -> {
                        player.spellbook.chargedbolt_lvl += value;
                        player.spellbook.chainlightning_lvl += value;
                        player.spellbook.thunderstorm_lvl += value;
                        player.spellbook.flamejet_lvl += value;
                        player.spellbook.fireball_lvl += value;
                        player.spellbook.overheat_lvl += value;
                        player.spellbook.arcanemissile_lvl += value;
                        player.spellbook.energybeam_lvl += value;
                        player.spellbook.rift_lvl += value;
                        player.spellbook.frostbolt_lvl += value;
                        player.spellbook.icespear_lvl += value;
                        player.spellbook.frozenorb_lvl += value;
                    }

                }

            }
        }
    }


    /**
     * Returns a list with ALL items of specified type/quality that exists
     * @param slot gear slot
     * @param quality gear tier
     * @return equipment list
     */
    public static ArrayList<Equipment> get_all_items_from_class(ItemUtils.EquipSlot slot, ItemUtils.EquipQuality quality) {
        ArrayList<Equipment> list = new ArrayList<>();

        if(slot.equals(ItemUtils.EquipSlot.ALL)) {
            switch (quality) {
                case NORMAL -> {
                    list.addAll(Staff.getNormalStaffs());
                    list.addAll(Book.getNormalBooks());
                    list.addAll(Amulet.getNormalAmulets());
                    list.addAll(Ring.getNormalRings());
                    list.addAll(SoulStone.getNormalStones());
                    list.addAll(Hat.getNormalHats());
                    list.addAll(Robes.getNormalRobes());
                }
                case RARE -> {
                    list.addAll(Staff.getRareStaffs());
                    list.addAll(Book.getRareBooks());
                    list.addAll(Amulet.getRareAmulets());
                    list.addAll(Ring.getRareRings());
                    list.addAll(SoulStone.getRareStones());
                    list.addAll(Hat.getRareHats());
                    list.addAll(Robes.getRareRobes());
                }
                case EPIC -> {
                    list.addAll(Staff.getEpicStaffs());
                    list.addAll(Book.getEpicBooks());
                    list.addAll(Amulet.getEpicAmulets());
                    list.addAll(Ring.getEpicRings());
                    list.addAll(SoulStone.getEpicStones());
                    list.addAll(Hat.getEpicHats());
                    list.addAll(Robes.getRareRobes());
                }
                case LEGENDARY -> {
                    list.addAll(Staff.getLegendaryStaffs());
                    list.addAll(Book.getLegendaryBooks());
                    list.addAll(Amulet.getLegendaryAmulets());
                    list.addAll(Ring.getLegendaryRings());
                    list.addAll(SoulStone.getLegendaryStones());
                    list.addAll(Hat.getLegendaryHats());
                    list.addAll(Robes.getLegendaryRobes());
                }
                case ALL -> {
                    list.addAll(Staff.getAllStaffs());
                    list.addAll(Book.getAllBooks());
                    list.addAll(Amulet.getAllAmulets());
                    list.addAll(Ring.getAllRings());
                    list.addAll(SoulStone.getAllStones());
                    list.addAll(Hat.getAllHats());
                    list.addAll(Robes.getAllRobes());
                }
            }
            Collections.shuffle(list);
            return list;
        }

        if(quality.equals(ItemUtils.EquipQuality.ALL)) {
            switch (slot) {
                case HAT -> list.addAll(Hat.getAllHats());
                case AMULET -> list.addAll(Amulet.getAllAmulets());
                case RING -> list.addAll(Ring.getAllRings());
                case STAFF -> list.addAll(Staff.getAllStaffs());
                case SPELLBOOK -> list.addAll(Book.getAllBooks());
                case RUNESTONE -> list.addAll(SoulStone.getAllStones());
                case ROBE -> list.addAll(Robes.getAllRobes());
            }
            Collections.shuffle(list);
            return list;
        }

        if(slot.equals(ItemUtils.EquipSlot.STAFF)) {
            switch(quality) {
                case NORMAL -> list.addAll(Staff.getNormalStaffs());
                case RARE -> list.addAll(Staff.getRareStaffs());
                case EPIC -> list.addAll(Staff.getEpicStaffs());
                case LEGENDARY -> list.addAll(Staff.getLegendaryStaffs());
            }
        }

        if(slot.equals(ItemUtils.EquipSlot.SPELLBOOK)) {
            switch(quality) {
                case NORMAL -> list.addAll(Book.getNormalBooks());
                case RARE -> list.addAll(Book.getRareBooks());
                case EPIC -> list.addAll(Book.getEpicBooks());
                case LEGENDARY -> list.addAll(Book.getLegendaryBooks());
            }
        }

        if(slot.equals(ItemUtils.EquipSlot.AMULET)) {
            switch(quality) {
                case NORMAL -> list.addAll(Amulet.getNormalAmulets());
                case RARE -> list.addAll(Amulet.getRareAmulets());
                case EPIC -> list.addAll(Amulet.getEpicAmulets());
                case LEGENDARY -> list.addAll(Amulet.getLegendaryAmulets());
            }
        }

        if(slot.equals(RING)) {
            switch(quality) {
                case NORMAL -> list.addAll(Ring.getNormalRings());
                case RARE -> list.addAll(Ring.getRareRings());
                case EPIC -> list.addAll(Ring.getEpicRings());
                case LEGENDARY -> list.addAll(Ring.getLegendaryRings());
            }
        }

        if(slot.equals(ItemUtils.EquipSlot.RUNESTONE)) {
            switch(quality) {
                case NORMAL -> list.addAll(SoulStone.getNormalStones());
                case RARE -> list.addAll(SoulStone.getRareStones());
                case EPIC -> list.addAll(SoulStone.getEpicStones());
                case LEGENDARY -> list.addAll(SoulStone.getLegendaryStones());
            }
        }

        if(slot.equals(ItemUtils.EquipSlot.ROBE)) {
            switch(quality) {
                case NORMAL -> list.addAll(Robes.getNormalRobes());
                case RARE -> list.addAll(Robes.getRareRobes());
                case EPIC -> list.addAll(Robes.getEpicRobes());
                case LEGENDARY -> list.addAll(Robes.getLegendaryRobes());
            }
        }

        if(slot.equals(ItemUtils.EquipSlot.HAT)) {
            switch(quality) {
                case NORMAL -> list.addAll(Hat.getNormalHats());
                case RARE -> list.addAll(Hat.getRareHats());
                case EPIC -> list.addAll(Hat.getEpicHats());
                case LEGENDARY -> list.addAll(Hat.getLegendaryHats());
            }
        }

        Collections.shuffle(list);
        return list;
    }

    public static ItemUtils.EquipSlot getRandomEquipSlot() {
        ArrayList<ItemUtils.EquipSlot> slots = new ArrayList<ItemUtils.EquipSlot>(Arrays.asList(ItemUtils.EquipSlot.values()));
        Collections.shuffle(slots);
        return slots.getFirst();
    }

    public static String getRandomName(Equipment piece) {
        ItemUtils.EquipSlot slot = piece.slot;
        String prefix = randomPrefix();
        String suffix = randomSuffix(slot);

        return prefix + " " + suffix;
    }
    public static String randomPrefix() {
        String prefix = "";
        int random = MathUtils.random(1,10);
        switch (random) {
            case 1 -> prefix = "Ancient";
            case 2 -> prefix = "Lost";
            case 3 -> prefix = "Huming";
            case 4 -> prefix = "Valuable";
            case 5 -> prefix = "Shiny";
            case 6 -> prefix = "Scratched";
            case 7 -> prefix = "Peculiar";
            case 8 -> prefix = "Restored";
            case 9 -> prefix = "Strange";
            case 10 -> prefix = "Abandonned";
        }
        return prefix;
    }
    public static String randomSuffix(ItemUtils.EquipSlot slot) {
        String prefix = "";
        int random = MathUtils.random(1,4);
        switch(slot) {
            case ROBE -> {
                switch (random) {
                    case 1 -> prefix = "Rags";
                    case 2 -> prefix = "Robes";
                    case 3 -> prefix = "Cloak";
                    case 4 -> prefix = "Cape";
                }
            }
            case RING -> {
                switch (random) {
                    case 1 -> prefix = "Band";
                    case 2 -> prefix = "Ring";
                    case 3 -> prefix = "Loop";
                    case 4 -> prefix = "Halo";
                }
            }
            case AMULET -> {
                switch (random) {
                    case 1 -> prefix = "Trinket";
                    case 2 -> prefix = "Pendant";
                    case 3 -> prefix = "Charm";
                    case 4 -> prefix = "Locket";
                }
            }
            case STAFF -> {
                switch (random) {
                    case 1 -> prefix = "Rod";
                    case 2 -> prefix = "Spire";
                    case 3 -> prefix = "Cane";
                    case 4 -> prefix = "Catalyst";
                }
            }
            case SPELLBOOK -> {
                switch (random) {
                    case 1 -> prefix = "Grimoire";
                    case 2 -> prefix = "Tome";
                    case 3 -> prefix = "Volume";
                    case 4 -> prefix = "Journal";
                }
            }
            case HAT -> {
                switch (random) {
                    case 1 -> prefix = "Cap";
                    case 2 -> prefix = "Cloche";
                    case 3 -> prefix = "Chapeau";
                    case 4 -> prefix = "Hood";
                }
            }
        }
        return prefix;
    }

}
