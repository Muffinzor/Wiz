package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class H_Repulsionfield extends PanelButton {

    int roll;
    int MAX_BONUS_RADIUS = 40;
    int MAX_BONUS_CDREDUCTION = 4;  // 0.5s each

    public H_Repulsionfield(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.REPULSIONFIELD;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.orbit_bonus_speed < MAX_BONUS_RADIUS ||
                player.spellbook.orbit_bonus_duration < MAX_BONUS_CDREDUCTION)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_freeze = MathUtils.randomBoolean();
            roll = (bonus_freeze && player.spellbook.orbit_bonus_speed < MAX_BONUS_RADIUS) ||
                    (!bonus_freeze && player.spellbook.orbit_bonus_duration >= MAX_BONUS_CDREDUCTION)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 1 -> player.spellbook.repulsion_bonus_radius += 10;
            case 2 -> player.spellbook.repulsion_bonus_cdreduction += 1;
            case 3 -> player.spellbook.repulsion_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 1 -> s = "+10% Radius";
            case 2 -> s = "-0.5s Base Cooldown";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            REPULSION FIELD
            
            %s
            """, s);
        setText(text);
    }
}
