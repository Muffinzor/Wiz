package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class H_Forkedlightning extends PanelButton {

    int roll;
    int MAX_BONUS_RANGE = 30;
    int MAX_BONUS_QUANTITY = 30;

    public H_Forkedlightning(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.FORKEDLIGHTNING;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.forkedlightning_bonus_range < MAX_BONUS_RANGE ||
                player.spellbook.forkedlightning_bonus_quantity < MAX_BONUS_QUANTITY)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_freeze = MathUtils.randomBoolean();
            roll = (bonus_freeze && player.spellbook.forkedlightning_bonus_range < MAX_BONUS_RANGE) ||
                    (!bonus_freeze && player.spellbook.forkedlightning_bonus_quantity >= MAX_BONUS_QUANTITY)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 1 -> player.spellbook.forkedlightning_bonus_range += 15;
            case 2 -> player.spellbook.forkedlightning_bonus_quantity += 10;
            case 3 -> player.spellbook.forkedlightning_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 1 -> s = "+15% Range";
            case 2 -> s = "10% Increased Frequency";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            FORKED LIGHTNING
            
            %s
            """, s);
        setText(text);
    }
}
