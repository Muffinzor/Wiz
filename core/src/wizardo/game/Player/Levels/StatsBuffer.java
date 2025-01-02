package wizardo.game.Player.Levels;

import wizardo.game.Player.Levels.LevelUpEnums.LevelUps;
import wizardo.game.Player.Levels.LevelUpEnums.LevelUpQuality;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class StatsBuffer {

    public static void apply_Scroll(SpellUtils.Spell_Name spell) {
        switch(spell) {
            case FROSTBOLT -> {
                if(player.spellbook.frostbolt_lvl - player.stats.bonusMastery_frostbolt < 10) {
                    player.spellbook.frostbolt_lvl++;
                }
            }
            case ICESPEAR -> {
                if(player.spellbook.icespear_lvl - player.stats.bonusMastery_icespear< 10) {
                    player.spellbook.icespear_lvl++;
                }
            }
            case FROZENORB -> {
                if(player.spellbook.frozenorb_lvl - player.stats.bonusMastery_frozenorb< 10) {
                    player.spellbook.frozenorb_lvl++;
                }
            }
            case CHARGEDBOLTS -> {
                if(player.spellbook.chargedbolt_lvl - player.stats.bonusMastery_chargedbolt < 10) {
                    player.spellbook.chargedbolt_lvl++;
                }
            }
            case CHAIN -> {
                if(player.spellbook.icespear_lvl - player.stats.bonusMastery_chainlightning < 10) {
                    player.spellbook.chainlightning_lvl++;
                }
            }
            case THUNDERSTORM -> {
                if(player.spellbook.thunderstorm_lvl - player.stats.bonusMastery_thunderstorm < 10) {
                    player.spellbook.thunderstorm_lvl++;
                }
            }
            case MISSILES -> {
                if(player.spellbook.arcanemissile_lvl - player.stats.bonusMastery_missiles < 10) {
                    player.spellbook.arcanemissile_lvl++;
                }
            }
            case BEAM -> {
                if(player.spellbook.energybeam_lvl - player.stats.bonusMastery_beam < 10) {
                    player.spellbook.energybeam_lvl++;
                }
            }
            case RIFTS -> {
                if(player.spellbook.rift_lvl - player.stats.bonusMastery_rifts< 10) {
                    player.spellbook.rift_lvl++;
                }
            }
            case FLAMEJET -> {
                if(player.spellbook.flamejet_lvl - player.stats.bonusMastery_flamejet< 10) {
                    player.spellbook.flamejet_lvl++;
                }
            }
            case FIREBALL -> {
                if(player.spellbook.fireball_lvl - player.stats.bonusMastery_fireball < 10) {
                    player.spellbook.fireball_lvl++;
                }
            }
            case OVERHEAT -> {
                if(player.spellbook.overheat_lvl - player.stats.bonusMastery_overheat < 10) {
                    player.spellbook.overheat_lvl++;
                }
            }

        }
    }

    public static void apply_LevelUp(LevelUps type, LevelUpQuality quality) {
        switch(type) {
            case FROSTBOLT -> frostbolt();
            case FLAMEJET -> flamejet();
            case CHARGEDBOLT -> chargedbolt();
            case MISSILES -> missiles();
            case ICESPEAR -> icespear();
            case FIREBALL -> fireball();
            case CHAIN -> chain();
            case BEAM -> beam();

            case EXPLOSIVES -> player.spellbook.explosivesBonusDmg += basicValue(quality);
            case ENERGY -> player.spellbook.energyBonusDmg += basicValue(quality);
            case FLARE -> player.spellbook.flashBonusDmg += basicValue(quality);
            case GRAVITY -> player.spellbook.gravityBonusDmg += basicValue(quality);
            case SHARP -> player.spellbook.sharpBonusDmg += basicValue(quality);
            case VOLTAGE -> player.spellbook.voltageBonusDmg += basicValue(quality);

            case EMPYREAN -> empyrean(quality);
            case FORCE -> force(quality);
            case ICE -> ice(quality);
            case PROJECTILES -> speed(quality);
            case SPACE -> space(quality);

            case LUCK -> luck(quality);
            case RED -> defense(quality);
            case REGEN -> regen(quality);
            case CRYSTAL -> {
                player.stats.maxShield += basicValue(quality);
                player.stats.shield += basicValue(quality);
            }
        }
    }

    private static int basicValue(LevelUpQuality quality) {
        int value = 0;
        switch(quality) {
            case NORMAL -> value = 10;
            case RARE -> value = 15;
            case EPIC -> value = 20;
            case LEGENDARY -> value = 30;
        }
        return value;
    }

    private static void flamejet() {
        if(player.spellbook.flamejet_lvl < 5) {
            player.spellbook.flamejet_lvl++;
        } else {
            player.spellbook.flamejetBonus += 2;
        }
    }
    private static void frostbolt() {
        if(player.spellbook.frostbolt_lvl < 5) {
            player.spellbook.frostbolt_lvl++;
        } else {
            player.spellbook.frostboltBonus += 8;
        }
    }
    private static void chargedbolt() {
        if(player.spellbook.chargedbolt_lvl < 5) {
            player.spellbook.chargedbolt_lvl++;
        } else {
            player.spellbook.chargedboltBonus += 8;
        }
    }
    private static void missiles() {
        if(player.spellbook.arcanemissile_lvl < 5) {
            player.spellbook.arcanemissile_lvl++;
        } else {
            player.spellbook.arcanemissileBonus += 8;
        }
    }
    private static void icespear() {
        if(player.spellbook.icespear_lvl < 5) {
            player.spellbook.icespear_lvl++;
        } else {
            player.spellbook.icespearBonus += 8;
        }
    }
    private static void fireball() {
        if(player.spellbook.fireball_lvl < 5) {
            player.spellbook.fireball_lvl++;
        } else {
            player.spellbook.fireballBonus += 8;
        }
    }
    private static void beam() {
        if(player.spellbook.energybeam_lvl < 5) {
            player.spellbook.energybeam_lvl++;
        } else {
            player.spellbook.energybeamBonus += 20;
        }
    }
    private static void chain() {
        if(player.spellbook.chainlightning_lvl < 5) {
            player.spellbook.chainlightning_lvl++;
        } else {
            player.spellbook.chainlightningBonus += 6;
        }
    }

    private static void empyrean(LevelUpQuality quality) {
        switch(quality) {
            case NORMAL -> player.spellbook.empyreanFrequencyBonus += 4;
            case RARE -> player.spellbook.empyreanFrequencyBonus += 6;
            case EPIC -> player.spellbook.empyreanFrequencyBonus += 8;
            case LEGENDARY -> player.spellbook.empyreanFrequencyBonus += 12;
        }
    }
    private static void force(LevelUpQuality quality) {
        switch(quality) {
            case EPIC -> player.spellbook.pushbackBonus += 12;
            case LEGENDARY -> player.spellbook.pushbackBonus += 20;
        }
    }
    private static void ice(LevelUpQuality quality) {
        switch(quality) {
            case NORMAL -> player.spellbook.iceRadiusBonus += 4;
            case RARE -> player.spellbook.iceRadiusBonus += 6;
            case EPIC -> player.spellbook.iceRadiusBonus += 8;
            case LEGENDARY -> player.spellbook.iceRadiusBonus += 12;
        }
    }
    private static void luck(LevelUpQuality quality) {
        switch(quality) {
            case EPIC ->  {
                player.stats.luck += 5;
                player.stats.pickupRadiusBonus += 10;
            }
            case LEGENDARY ->  {
                player.stats.luck += 10;
                player.stats.pickupRadiusBonus += 20;
            }
        }
    }
    private static void speed(LevelUpQuality quality) {
        switch(quality) {
            case NORMAL -> player.spellbook.projSpeedBonus += 4;
            case RARE -> player.spellbook.projSpeedBonus += 6;
            case EPIC -> player.spellbook.projSpeedBonus += 8;
            case LEGENDARY -> player.spellbook.projSpeedBonus += 12;
        }
    }
    private static void defense(LevelUpQuality quality) {
        switch(quality) {
            case EPIC -> player.stats.damageReduction += 2;
            case LEGENDARY -> player.stats.damageReduction += 3;
        }
    }

    private static void regen(LevelUpQuality quality) {
        switch(quality) {
            case NORMAL -> player.stats.bonusRechargeRate += 8;
            case RARE -> player.stats.bonusRechargeRate += 12;
            case EPIC -> player.stats.bonusRechargeRate += 16;
            case LEGENDARY -> player.stats.bonusRechargeRate += 24;
        }
    }
    private static void space(LevelUpQuality quality) {
        int value = 0;
        switch(quality) {
            case NORMAL -> value = 6;
            case RARE -> value = 10;
            case EPIC -> value = 14;
            case LEGENDARY -> value = 20;
        }
        player.spellbook.repulsionRadiusBonus += value;
        player.spellbook.orbitingIceRadius += value;
        player.spellbook.riftPullBonus += value;
    }

    private static String xx(LevelUpEnums.LevelUpQuality quality) {

        int value = 0;
        switch(quality) {
            case NORMAL -> value = 6;
            case RARE -> value = 10;
            case EPIC -> value = 14;
            case LEGENDARY -> value = 20;
        }

        String text = String.format("""
            SPACE FOLDING
            
            +%d %% Repulsion Field radius
            +%d %% Orbiting Ice orbit radius
            +%d %% Rifts pulling strength
            """, value, value, value/2);

        return text;

    }



}
