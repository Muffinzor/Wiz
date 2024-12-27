package wizardo.game.Items.Equipment;

import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

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

                float value = piece.quantity_gearStats.get(i);
                if(remove) {
                    value = -value;
                }

                switch(gearStats.get(i)) {
                    case FIREDMG -> player.spellbook.fireBonusDmg += value;
                    case FROSTDMG -> player.spellbook.frostBonusDmg += value;
                    case LITEDMG -> player.spellbook.lightningBonusDmg += value;
                    case ARCANEDMG -> player.spellbook.arcaneBonusDmg += value;
                    case ALLDMG -> player.spellbook.allBonusDmg += value;
                }

            }
        }
    }

}
