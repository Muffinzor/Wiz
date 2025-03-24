package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class H_Dragonbreath extends PanelButton {

    int roll;
    int MAX_BONUS_BURNDMG = 100;
    int MAX_BONUS_BURNDURATION = 4;

    public H_Dragonbreath(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.DRAGONBREATH;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.dragonbreath_bonus_burndmg < MAX_BONUS_BURNDMG ||
                player.spellbook.dragonbreath_bonus_burnduration < MAX_BONUS_BURNDURATION)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_freeze = MathUtils.randomBoolean();
            roll = (bonus_freeze && player.spellbook.dragonbreath_bonus_burndmg < MAX_BONUS_BURNDMG) ||
                    (!bonus_freeze && player.spellbook.dragonbreath_bonus_burnduration >= MAX_BONUS_BURNDURATION)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 1 -> player.spellbook.dragonbreath_bonus_burndmg += 20;
            case 2 -> player.spellbook.dragonbreath_bonus_burnduration++;
            case 3 -> player.spellbook.dragonbreath_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 1 -> s = "+20% DoT Intensity";
            case 2 -> s = "+1s DoT Duration";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            DRAGONBREATH
            
            %s
            """, s);
        setText(text);
    }
}
