package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class H_Orbit extends PanelButton {

    int roll;
    int MAX_BONUS_SPEED = 100;
    int MAX_BONUS_DURATION = 3;

    public H_Orbit(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.ORBIT;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.orbit_bonus_speed < MAX_BONUS_SPEED ||
                player.spellbook.orbit_bonus_duration < MAX_BONUS_DURATION)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_freeze = MathUtils.randomBoolean();
            roll = (bonus_freeze && player.spellbook.orbit_bonus_speed < MAX_BONUS_SPEED) ||
                    (!bonus_freeze && player.spellbook.orbit_bonus_duration >= MAX_BONUS_DURATION)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 1 -> player.spellbook.orbit_bonus_speed += 25;
            case 2 -> player.spellbook.orbit_bonus_duration += 1;
            case 3 -> player.spellbook.orbit_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 1 -> s = "+25% Speed";
            case 2 -> s = "+1s Duration";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            ORBIT
            
            %s
            """, s);
        setText(text);
    }
}
