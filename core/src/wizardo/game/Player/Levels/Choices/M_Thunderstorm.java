package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class M_Thunderstorm extends PanelButton {

    int roll = 0;

    int MAX_BONUS_DURATION = 30;
    int MAX_BONUS_RADIUS = 30;

    public M_Thunderstorm(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.THUNDERSTORM;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        if(player.spellbook.thunderstorm_lvl - player.stats.bonusMastery_thunderstorm < 1) return;

        // Default
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.thunderstorm_bonus_duration < MAX_BONUS_DURATION ||
                player.spellbook.thunderstorm_bonus_splash < MAX_BONUS_RADIUS)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_jump = MathUtils.randomBoolean();
            roll = (bonus_jump && player.spellbook.thunderstorm_bonus_duration < MAX_BONUS_DURATION) ||
                    (!bonus_jump && player.spellbook.thunderstorm_bonus_splash >= MAX_BONUS_RADIUS)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.thunderstorm_lvl++;
            case 1 -> player.spellbook.thunderstorm_bonus_duration += 10;
            case 2 -> player.spellbook.thunderstorm_bonus_splash += 10;
            case 3 -> player.spellbook.thunderstorm_bonus_dmg += mastery_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Thunderstorm";
            case 1 -> s = "+10% Duration";
            case 2 -> s = "+10% Splash Radius";
            case 3 -> s = "+30% Damage";
        }
        String text = String.format("""
            THUNDERSTORM
            
            %s
            """, s);
        setText(text);
    }
}
