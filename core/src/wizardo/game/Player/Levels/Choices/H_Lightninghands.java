package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class H_Lightninghands extends PanelButton {

    int roll;
    int MAX_BONUS_BRANCHES = 3;
    int MAX_BONUS_CDREDUCTION = 30;

    public H_Lightninghands(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.LIGHTNINGHANDS;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.lightninghands_bonus_branch < MAX_BONUS_BRANCHES ||
                player.spellbook.lightninghands_reducedcd < MAX_BONUS_CDREDUCTION)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_freeze = MathUtils.randomBoolean();
            roll = (bonus_freeze && player.spellbook.lightninghands_bonus_branch < MAX_BONUS_BRANCHES) ||
                    (!bonus_freeze && player.spellbook.lightninghands_reducedcd >= MAX_BONUS_CDREDUCTION)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 1 -> player.spellbook.lightninghands_bonus_branch += 1;
            case 2 -> player.spellbook.lightninghands_reducedcd += 10;
            case 3 -> player.spellbook.lightninghands_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 1 -> s = "+1 Lightning Branch";
            case 2 -> s = "-10% Cooldown Duration";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            LIGHTNING HANDS
            
            %s
            """, s);
        setText(text);
    }
}
