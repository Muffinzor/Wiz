package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Player.Levels.LevelUpUtils;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import java.util.ArrayList;

import static wizardo.game.Player.Levels.LevelUpEnums.LevelUpQuality.NORMAL;
import static wizardo.game.Player.Levels.LevelUpEnums.LevelUpQuality.RARE;
import static wizardo.game.Wizardo.player;

public class B_Element extends PanelButton {

    int type_roll;
    int ele_roll;
    int value;
    LevelUpEnums.LevelUps element;

    public B_Element(LevelUpScreen screen, LevelUpEnums.LevelUps element) {
        super(screen);
        this.element = element;
        pick_type();
        super.setup();
        calculate_value();
        setText(string_builder());
    }

    public void pick_type() {
        quality = LevelUpUtils.getRandomQuality();
        if(quality == NORMAL || quality == RARE) {
            quality = RARE;
            type_roll = 1;
        } else {
            type_roll = MathUtils.random(1, 3);
        }

        if(element != null) {
            type = element;
        } else {
            ele_roll = MathUtils.random(1,4);
            switch (ele_roll) {
                case 1 -> type = LevelUpEnums.LevelUps.FIRE;
                case 2 -> type = LevelUpEnums.LevelUps.FROST;
                case 3 -> type = LevelUpEnums.LevelUps.LIGHTNING;
                case 4 -> type = LevelUpEnums.LevelUps.ARCANE;
            }
        }
        set_gem_sprite();
    }

    public void calculate_value() {
        switch(type_roll) {
            case 1 -> {
                switch(quality) {
                    case RARE -> value = 10;
                    case EPIC -> value = 15;
                    case LEGENDARY -> value = 20;
                }
            }
            case 2,3 -> {
                switch(quality) {
                    case EPIC -> value = 10;
                    case LEGENDARY -> value = 15;
                }
            }
        }
    }

    public void apply_stats() {
        switch(type_roll) {
            case 1 -> buff_dmg();
            case 2 -> buff_cooldown();
            case 3 -> buff_multicast();
        }
    }
    private void buff_dmg() {
        switch(type) {
            case FIRE -> player.spellbook.fireBonusDmg += value;
            case FROST -> player.spellbook.frostBonusDmg += value;
            case LIGHTNING -> player.spellbook.lightningBonusDmg += value;
            case ARCANE -> player.spellbook.arcaneBonusDmg += value;
        }
    }
    private void buff_cooldown() {
        switch(type) {
            case FIRE -> player.spellbook.fire_bonus_cdreduction += value;
            case FROST -> player.spellbook.frost_bonus_cdreduction += value;
            case LIGHTNING -> player.spellbook.lightning_bonus_cdreduction += value;
            case ARCANE -> player.spellbook.arcane_bonus_cdreduction += value;
        }
    }
    private void buff_multicast() {
        switch(type) {
            case FIRE -> player.spellbook.fire_bonus_multicast += value;
            case FROST -> player.spellbook.frost_bonus_multicast += value;
            case LIGHTNING -> player.spellbook.lightning_bonus_multicast += value;
            case ARCANE -> player.spellbook.arcane_bonus_multicast += value;
        }
    }


    private String string_builder() {
        String title = "";
        String element = "";
        switch(type) {
            case FIRE -> {
                title = "FIRE SUPREMACY";
                element = "Fire";
            }
            case FROST -> {
                title = "FROST DOMINANCE";
                element = "Frost";
            }
            case ARCANE -> {
                title = "ARCANE AUTHORITY";
                element = "Arcane";
            }
            case LIGHTNING -> {
                title = "LIGHTNING HEGEMONY";
                element = "Lightning";
            }
        }
        String stat_line = "";
        switch(type_roll) {
            case 1 -> {
                stat_line = String.format("%s spells deal %d%% more damage", element, value);
            }
            case 2 -> {
                stat_line = String.format("%s spells gain %d%% cooldown reduction", element, value);
            }
            case 3 -> {
                stat_line = String.format("%s spells gain %d%% multicast chance", element, value);
            }
        }

        return String.format("""
            %s
            
            %s
            """, title, stat_line);
    }


    public void drawPanel(float delta) {
        drawLiteralFrame(delta);
        drawQualityGem();
    }

}
