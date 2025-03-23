package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class H_Celestialstrike extends PanelButton {

    int roll;
    int MAX_BONUS_FREEZECHANCE = 100;
    int MAX_BONUS_CDREDUCTION = 3;

    public H_Celestialstrike(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.CELESTIALSTRIKE;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.celestialstrike_bonus_freezechance < MAX_BONUS_FREEZECHANCE ||
                player.spellbook.celestialstrike_bonus_cdreduction < MAX_BONUS_CDREDUCTION)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_freeze = MathUtils.randomBoolean();
            roll = (bonus_freeze && player.spellbook.celestialstrike_bonus_freezechance < MAX_BONUS_FREEZECHANCE) ||
                    (!bonus_freeze && player.spellbook.celestialstrike_bonus_cdreduction >= MAX_BONUS_CDREDUCTION)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 1 -> player.spellbook.celestialstrike_bonus_freezechance += 25;
            case 2 -> player.spellbook.celestialstrike_bonus_cdreduction++;
            case 3 -> player.spellbook.celestialstrike_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 1 -> s = freeze_string();
            case 2 -> s = "-0.25s Base Cooldown";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            CELESTIAL STRIKE
            
            %s
            """, s);
        setText(text);
    }

    public String freeze_string() {
        return String.format("+25%% chance to freeze\n\nCurrent chance: %d%%", player.spellbook.celestialstrike_bonus_freezechance);
    }
}
