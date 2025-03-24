package wizardo.game.Player.Levels;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.Choices.*;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;
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
                list.set(replaced_index, get_learnable_mastery(3, false));
            else if(player.level >= 10)
                list.set(replaced_index, get_learnable_mastery(2, true));
            else
                list.set(replaced_index, get_learnable_mastery(1, false));
        }

        return list;
    }

    public PanelButton get_random_t1(LevelUpScreen screen, ArrayList<LevelUpEnums.LevelUps> current_choices) {
        ArrayList<LevelUpEnums.LevelUps> list = new ArrayList<>();

        if(player.level/4 >= masteries[0] && !current_choices.contains(LevelUpEnums.LevelUps.FROSTBOLT)) {
            list.add(LevelUpEnums.LevelUps.FROSTBOLT);
        }
        if(player.level/4 >= masteries[3] && !current_choices.contains(LevelUpEnums.LevelUps.FLAMEJET)) {
            list.add(LevelUpEnums.LevelUps.FLAMEJET);
        }
        if(player.level/4 >= masteries[6] && !current_choices.contains(LevelUpEnums.LevelUps.CHARGEDBOLT)) {
            list.add(LevelUpEnums.LevelUps.CHARGEDBOLT);
        }
        if(player.level/4 >= masteries[9] && !current_choices.contains(LevelUpEnums.LevelUps.MISSILES)) {
            list.add(LevelUpEnums.LevelUps.MISSILES);
        }

        PanelButton level_up = null;
        Collections.shuffle(list);
        switch(list.getFirst()) {
            case FROSTBOLT -> level_up = new M_Frostbolt(screen);
            case FLAMEJET -> level_up = new M_Flamejet(screen);
            case CHARGEDBOLT -> level_up = new M_Chargedbolt(screen);
            case MISSILES -> level_up = new M_Arcanemissiles(screen);
        }
        current_choices.add(list.getFirst());
        return level_up;
    }
    public PanelButton get_random_t2(LevelUpScreen screen, ArrayList<LevelUpEnums.LevelUps> current_choices) {
        ArrayList<LevelUpEnums.LevelUps> list = new ArrayList<>();

        if(player.level/4 >= masteries[1] && !current_choices.contains(LevelUpEnums.LevelUps.ICESPEAR)) {
            list.add(LevelUpEnums.LevelUps.ICESPEAR);
        }
        if(player.level/4 >= masteries[4] && !current_choices.contains(LevelUpEnums.LevelUps.FIREBALL)) {
            list.add(LevelUpEnums.LevelUps.FIREBALL);
        }
        if(player.level/4 >= masteries[7] && !current_choices.contains(LevelUpEnums.LevelUps.CHAIN)) {
            list.add(LevelUpEnums.LevelUps.CHAIN);
        }
        if(player.level/4 >= masteries[10] && !current_choices.contains(LevelUpEnums.LevelUps.BEAM)) {
            list.add(LevelUpEnums.LevelUps.BEAM);
        }

        PanelButton level_up = null;
        Collections.shuffle(list);
        switch(list.getFirst()) {
            case ICESPEAR -> level_up = new M_Icespear(screen);
            case FIREBALL -> level_up = new M_Fireball(screen);
            case CHAIN -> level_up = new M_Chainlightning(screen);
            case BEAM -> level_up = new M_Energybeam(screen);
        }
        current_choices.add(list.getFirst());
        return level_up;
    }
    public PanelButton get_random_t3(LevelUpScreen screen, ArrayList<LevelUpEnums.LevelUps> current_choices) {
        ArrayList<LevelUpEnums.LevelUps> list = new ArrayList<>();

        if(player.level/4 >= masteries[2] && !current_choices.contains(LevelUpEnums.LevelUps.FROZENORB)) {
            list.add(LevelUpEnums.LevelUps.FROZENORB);
        }
        if(player.level/4 >= masteries[5] && !current_choices.contains(LevelUpEnums.LevelUps.OVERHEAT)) {
            list.add(LevelUpEnums.LevelUps.OVERHEAT);
        }
        if(player.level/4 >= masteries[8] && !current_choices.contains(LevelUpEnums.LevelUps.THUNDERSTORM)) {
            list.add(LevelUpEnums.LevelUps.THUNDERSTORM);
        }
        if(player.level/4 >= masteries[11] && !current_choices.contains(LevelUpEnums.LevelUps.RIFTS)) {
            list.add(LevelUpEnums.LevelUps.RIFTS);
        }

        PanelButton level_up = null;
        Collections.shuffle(list);
        switch(list.getFirst()) {
            case FROZENORB -> level_up = new M_Frozenorb(screen);
            case OVERHEAT -> level_up = new M_Overheat(screen);
            case THUNDERSTORM -> level_up = new M_Thunderstorm(screen);
            case RIFTS -> level_up = new M_Rifts(screen);
        }
        current_choices.add(list.getFirst());
        return level_up;
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
            case 9,10,11 -> list.add(get_learnable_mastery(2, false));
            case 12 -> list.add(get_learnable_mastery(3, true));
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
            list.add(get_learnable_mastery(1, true));
        }
        Collections.shuffle(list);
        return list.getFirst();
    }
    /** Infinite **/
    public LevelUpEnums.LevelUps get_equipped_parts_buff(ArrayList<LevelUpEnums.LevelUps> current_choices) {
        ArrayList<LevelUpEnums.LevelUps> list = new ArrayList<>();
        for(Spell spell : player.spellbook.equippedSpells) {
            for(SpellUtils.Spell_Name part : spell.spellParts) {
                switch(part) {
                    case FROSTBOLT -> list.add(LevelUpEnums.LevelUps.FROSTBOLT);
                    case ICESPEAR -> list.add(LevelUpEnums.LevelUps.ICESPEAR);
                    case FROZENORB -> list.add(LevelUpEnums.LevelUps.FROZENORB);
                    case FLAMEJET -> list.add(LevelUpEnums.LevelUps.FLAMEJET);
                    case FIREBALL -> list.add(LevelUpEnums.LevelUps.FIREBALL);
                    case OVERHEAT -> list.add(LevelUpEnums.LevelUps.OVERHEAT);
                    case CHARGEDBOLT -> list.add(LevelUpEnums.LevelUps.CHARGEDBOLT);
                    case CHAIN -> list.add(LevelUpEnums.LevelUps.CHAIN);
                    case THUNDERSTORM -> list.add(LevelUpEnums.LevelUps.THUNDERSTORM);
                    case MISSILES -> list.add(LevelUpEnums.LevelUps.MISSILES);
                    case BEAM -> list.add(LevelUpEnums.LevelUps.BEAM);
                    case RIFTS -> list.add(LevelUpEnums.LevelUps.RIFTS);
                }
            }
        }
        Set<LevelUpEnums.LevelUps> seen = new HashSet<>();
        list.removeIf(n -> !seen.add(n));
        list.removeAll(current_choices);

        if(list.isEmpty()) {
            list.add(get_learnable_mastery(1, true));
        }

        Collections.shuffle(list);
        return list.getFirst();
    }
    /** Returns ELEMENT if all masteries from that selection are already learned **/
    public LevelUpEnums.LevelUps get_learnable_mastery(int max_tier, boolean fixed_to_max) {
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
