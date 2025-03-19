package wizardo.game.Player.Levels;

import wizardo.game.Player.Levels.Choices.*;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.player;

public class LevelUp_Manager {

    int[] masteries; // 0-2 FROST, 3-5 FIRE, 6-8 LIGHTNING, 9-11 ARCANE

    public LevelUp_Manager() {
        masteries = new int[12];
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


}
