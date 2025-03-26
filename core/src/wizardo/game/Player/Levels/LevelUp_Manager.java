package wizardo.game.Player.Levels;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.Choices.*;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;
import wizardo.game.Spells.Hybrid.Blizzard.Blizzard_Spell;
import wizardo.game.Spells.Hybrid.CelestialStrike.CelestialStrike_Spell;
import wizardo.game.Spells.Hybrid.DragonBreath.DragonBreath_Spell;
import wizardo.game.Spells.Hybrid.EnergyRain.EnergyRain_Spell;
import wizardo.game.Spells.Hybrid.ForkedLightning.ForkedLightning_Spell;
import wizardo.game.Spells.Hybrid.FrostNova.FrostNova_Spell;
import wizardo.game.Spells.Hybrid.Judgement.Judgement_Spell;
import wizardo.game.Spells.Hybrid.Laser.Laser_Spell;
import wizardo.game.Spells.Hybrid.LightningHands.LightningHands_Spell;
import wizardo.game.Spells.Hybrid.MeteorShower.MeteorShower_Spell;
import wizardo.game.Spells.Hybrid.Orbit.Orbit_Spell;
import wizardo.game.Spells.Hybrid.RepulsionField.RepulsionField_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static wizardo.game.Account.Unlocked.max_levelup_choices;
import static wizardo.game.Player.Levels.LevelUpEnums.LevelUps.*;
import static wizardo.game.Wizardo.player;

public class LevelUp_Manager {

    int[] masteries; // 0-2 FROST, 3-5 FIRE, 6-8 LIGHTNING, 9-11 ARCANE

    public LevelUp_Manager() {
        masteries = new int[12];
    }

    public ArrayList<LevelUpEnums.LevelUps> get_selection(LevelUpScreen screen) {
        ArrayList<LevelUpEnums.LevelUps> list = new ArrayList<>();

        for (int i = 0; i < player.levelup_choices; i++) {
            double random = Math.random();
            if(random >= 0.9f) {
                list.add(get_rare_level_up(screen, list));
            } else if (random >= 0.5f){
                if(Math.random() >= 0.7f) {
                    list.add(get_equipped_parts_buff(list));
                } else {
                    list.add(get_equipped_spell_buff(list));
                }
            } else {
                list.add(get_basic_level_up(list));
            }
        }

        if(player.level % 12 == 2) {
            int replaced_index = MathUtils.random(0, max_levelup_choices-1);
            if(player.level >= 20)
                list.set(replaced_index, get_learnable_mastery(3, false, list));
            else if(player.level >= 10)
                list.set(replaced_index, get_learnable_mastery(2, true, list));
            else
                list.set(replaced_index, get_learnable_mastery(1, false, list));
        }

        return list;
    }

    public LevelUpEnums.LevelUps get_basic_level_up(ArrayList<LevelUpEnums.LevelUps> current_choices) {
        ArrayList<LevelUpEnums.LevelUps> list = new ArrayList<>();
        list.add(LevelUpEnums.LevelUps.MAGNET);
        list.add(LevelUpEnums.LevelUps.LUCK);
        list.add(LevelUpEnums.LevelUps.EXPERIENCE);
        list.add(LevelUpEnums.LevelUps.SHIELD);
        list.removeAll(current_choices);
        Collections.shuffle(list);
        return list.getFirst();
    }

    public LevelUpEnums.LevelUps get_rare_level_up(LevelUpScreen screen, ArrayList<LevelUpEnums.LevelUps> current_choices) {
        ArrayList<LevelUpEnums.LevelUps> list = new ArrayList<>();
        int random = MathUtils.random(1, 18);
        switch(random) {
            case 1,2,3,4,5,6,7,8 -> list.add(ELEMENT);
            case 9,10,11 -> list.add(get_learnable_mastery(2, false, current_choices));
            case 12 -> list.add(get_learnable_mastery(3, true, current_choices));
            case 13,14,15 -> list.add(REGEN);
            case 16,17,18 -> list.add(DEFENSE);
        }
        Collections.shuffle(list);
        return list.getFirst();
    }

    /** Infinite **/
    public LevelUpEnums.LevelUps get_equipped_spell_buff(ArrayList<LevelUpEnums.LevelUps> current_choices) {
        ArrayList<LevelUpEnums.LevelUps> list = new ArrayList<>();
        for(Spell spell : player.spellbook.equippedSpells) {
            if(!current_choices.contains(spell.levelup_enum))
                list.add(spell.levelup_enum);
        }
        if(list.isEmpty()) {
            list.add(get_learnable_mastery(1, true, current_choices));
        }
        Collections.shuffle(list);
        return list.getFirst();
    }
    /** Infinite **/
    public LevelUpEnums.LevelUps get_equipped_parts_buff(ArrayList<LevelUpEnums.LevelUps> current_choices) {
        ArrayList<LevelUpEnums.LevelUps> list = new ArrayList<>();
        for(Spell spell : player.spellbook.equippedSpells) {
            switch (spell) {
                case MeteorShower_Spell _ -> {
                    list.add(METEORS);
                    if (spell.spellParts.contains(SpellUtils.Spell_Name.OVERHEAT)) list.add(OVERHEAT);
                    if (spell.spellParts.contains(SpellUtils.Spell_Name.CHARGEDBOLT)) list.add(CHARGEDBOLT);
                }
                case FrostNova_Spell _ -> {
                    list.add(FROSTNOVA);
                    if(spell.spellParts.contains(SpellUtils.Spell_Name.FROSTBOLT)) list.add(FROSTBOLT);
                }
                case Blizzard_Spell _ -> {
                    list.add(BLIZZARD);
                    if(spell.spellParts.contains(SpellUtils.Spell_Name.FROSTBOLT)) list.add(FROSTBOLT);
                }
                case ForkedLightning_Spell _ -> {
                    list.add(FORKEDLIGHTNING);
                    if(spell.spellParts.contains(SpellUtils.Spell_Name.CHARGEDBOLT)) list.add(CHARGEDBOLT);
                    if(spell.spellParts.contains(SpellUtils.Spell_Name.FIREBALL)) list.add(FIREBALL);
                }
                case RepulsionField_Spell _ -> list.add(REPULSIONFIELD);
                case Judgement_Spell _ -> list.add(JUDGEMENT);
                case EnergyRain_Spell _ -> list.add(ENERGYRAIN);
                case CelestialStrike_Spell _ -> {
                    list.add(CELESTIALSTRIKE);
                    if(spell.spellParts.contains(SpellUtils.Spell_Name.CHARGEDBOLT)) list.add(CHARGEDBOLT);
                    if(spell.spellParts.contains(SpellUtils.Spell_Name.FROSTBOLT)) list.add(FROSTBOLT);
                }
                case DragonBreath_Spell _ -> {
                    list.add(DRAGONBREATH);
                    if(spell.spellParts.contains(SpellUtils.Spell_Name.FIREBALL)) list.add(FIREBALL);
                    if(spell.spellParts.contains(SpellUtils.Spell_Name.FROSTBOLT)) list.add(FROSTBOLT);
                }
                case LightningHands_Spell _ -> {
                    list.add(LIGHTNINGHANDS);
                    if(spell.spellParts.contains(SpellUtils.Spell_Name.CHARGEDBOLT)) list.add(CHARGEDBOLT);
                    if(spell.spellParts.contains(SpellUtils.Spell_Name.CHAIN)) list.add(CHAIN);
                }
                case Laser_Spell _ -> {
                    list.add(LASERS);
                    if (spell.spellParts.size() > 2) {
                        list.add(spellpart_to_levelup(spell.spellParts.get(2)));
                    }
                }
                case Orbit_Spell _ -> list.add(ORBIT);
                default -> {
                    for (SpellUtils.Spell_Name part : spell.spellParts) {
                        list.add(spellpart_to_levelup(part));
                    }
                }
            }
        }
        Set<LevelUpEnums.LevelUps> seen = new HashSet<>();
        list.removeIf(n -> !seen.add(n));
        list.removeAll(current_choices);

        if(list.isEmpty()) {
            list.add(get_learnable_mastery(1, true, current_choices));
        }

        Collections.shuffle(list);
        return list.getFirst();
    }
    /** Embrace the Spaghetti, be one with the Spaghetti **/
    public LevelUpEnums.LevelUps spellpart_to_levelup(SpellUtils.Spell_Name part) {
        LevelUpEnums.LevelUps level = null;
        switch(part) {
            case FROSTBOLT -> level = FROSTBOLT;
            case ICESPEAR -> level = ICESPEAR;
            case FROZENORB -> level = FROZENORB;
            case FLAMEJET -> level = FLAMEJET;
            case FIREBALL -> level = FIREBALL;
            case OVERHEAT -> level = OVERHEAT;
            case CHARGEDBOLT -> level = CHARGEDBOLT;
            case CHAIN -> level = CHAIN;
            case THUNDERSTORM -> level = THUNDERSTORM;
            case MISSILES -> level = MISSILES;
            case BEAM -> level = BEAM;
            case RIFTS -> level = RIFTS;
        }
        return level;
    }
    /** Returns ELEMENT if all masteries from that selection are already learned **/
    public LevelUpEnums.LevelUps get_learnable_mastery(int max_tier, boolean fixed_to_max, ArrayList<LevelUpEnums.LevelUps> current_choices) {
        ArrayList<LevelUpEnums.LevelUps> list = new ArrayList<>();
        switch(max_tier) {
            case 1 -> learnable_t1_masteries(list);
            case 2 -> {
                if(!fixed_to_max) learnable_t1_masteries(list);
                learnable_t2_masteries(list);
            }
            case 3 -> {
                learnable_t3_masteries(list);
                if(!fixed_to_max) {
                    learnable_t1_masteries(list);
                    learnable_t2_masteries(list);
                }
            }
        }
        list.removeAll(current_choices);
        if(!list.isEmpty()) {
            Collections.shuffle(list);
            return list.getFirst();
        } else {
            return LevelUpEnums.LevelUps.ELEMENT;
        }
    }
    private void learnable_t1_masteries(ArrayList<LevelUpEnums.LevelUps> list_to_modify) {
        if(player.spellbook.frostbolt_lvl - player.stats.bonusMastery_frostbolt <= 0)
            list_to_modify.add(LevelUpEnums.LevelUps.FROSTBOLT);
        if(player.spellbook.chargedbolt_lvl - player.stats.bonusMastery_chargedbolt <= 0)
            list_to_modify.add(LevelUpEnums.LevelUps.CHARGEDBOLT);
        if(player.spellbook.flamejet_lvl - player.stats.bonusMastery_flamejet <= 0)
            list_to_modify.add(LevelUpEnums.LevelUps.FLAMEJET);
        if(player.spellbook.arcanemissile_lvl - player.stats.bonusMastery_missiles <= 0)
            list_to_modify.add(LevelUpEnums.LevelUps.MISSILES);
    }
    private void learnable_t2_masteries(ArrayList<LevelUpEnums.LevelUps> list_to_modify) {
        if(player.spellbook.icespear_lvl - player.stats.bonusMastery_icespear <= 0)
            list_to_modify.add(LevelUpEnums.LevelUps.ICESPEAR);
        if(player.spellbook.fireball_lvl - player.stats.bonusMastery_fireball <= 0)
            list_to_modify.add(LevelUpEnums.LevelUps.FIREBALL);
        if(player.spellbook.chainlightning_lvl - player.stats.bonusMastery_chainlightning <= 0)
            list_to_modify.add(LevelUpEnums.LevelUps.CHAIN);
        if(player.spellbook.energybeam_lvl - player.stats.bonusMastery_beam <= 0)
            list_to_modify.add(LevelUpEnums.LevelUps.BEAM);
    }
    private void learnable_t3_masteries(ArrayList<LevelUpEnums.LevelUps> list_to_modify) {
        if(player.spellbook.frozenorb_lvl - player.stats.bonusMastery_frozenorb <= 0)
            list_to_modify.add(LevelUpEnums.LevelUps.FROZENORB);
        if(player.spellbook.thunderstorm_lvl - player.stats.bonusMastery_thunderstorm <= 0)
            list_to_modify.add(LevelUpEnums.LevelUps.THUNDERSTORM);
        if(player.spellbook.overheat_lvl - player.stats.bonusMastery_overheat <= 0)
            list_to_modify.add(LevelUpEnums.LevelUps.OVERHEAT);
        if(player.spellbook.rift_lvl - player.stats.bonusMastery_rifts <= 0)
            list_to_modify.add(LevelUpEnums.LevelUps.RIFTS);
    }




}
