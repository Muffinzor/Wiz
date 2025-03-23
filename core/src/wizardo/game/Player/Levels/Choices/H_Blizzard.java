package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class H_Blizzard extends PanelButton {

    int roll;
    int MAX_BONUS_DURATION = 4;
    int MAX_BONUS_SPLASH_CHANCE = 40;

    public H_Blizzard(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.BLIZZARD;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        // Default
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.blizzard_bonus_duration < MAX_BONUS_DURATION ||
                player.spellbook.blizzard_bonus_splash_chance < MAX_BONUS_SPLASH_CHANCE)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_duration = MathUtils.randomBoolean();
            roll = (bonus_duration && player.spellbook.blizzard_bonus_duration < MAX_BONUS_DURATION) ||
                    (!bonus_duration && player.spellbook.blizzard_bonus_splash_chance >= MAX_BONUS_SPLASH_CHANCE)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 1 -> player.spellbook.blizzard_bonus_duration++;
            case 2 -> player.spellbook.blizzard_bonus_splash_chance += 10;
            case 3 -> player.spellbook.judgement_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 1 -> s = "+0.5s Duration";
            case 2 -> s = splash_string();
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            BLIZZARD
            
            %s
            """, s);
        setText(text);
    }

    public String splash_string() {
        return String.format("+10%% chance to double splash\n\nCurrent chance: %d%%", player.spellbook.blizzard_bonus_splash_chance);
    }
}
