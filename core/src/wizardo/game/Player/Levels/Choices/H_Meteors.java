package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class H_Meteors extends PanelButton {

    int roll;
    int MAX_BONUS_FREQUENCY = 60;
    int MAX_BONUS_DURATION = 3;

    public H_Meteors(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.METEORS;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.meteors_bonus_frequency < MAX_BONUS_FREQUENCY ||
                player.spellbook.meteors_bonus_duration < MAX_BONUS_DURATION)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_freeze = MathUtils.randomBoolean();
            roll = (bonus_freeze && player.spellbook.meteors_bonus_frequency < MAX_BONUS_FREQUENCY) ||
                    (!bonus_freeze && player.spellbook.meteors_bonus_duration >= MAX_BONUS_DURATION)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 1 -> player.spellbook.meteors_bonus_frequency += 15;
            case 2 -> player.spellbook.meteors_bonus_duration += 1;
            case 3 -> player.spellbook.meteors_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 1 -> s = "15% More Meteors";
            case 2 -> s = "+1s Duration";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            METEORS
            
            %s
            """, s);
        setText(text);
    }
}
