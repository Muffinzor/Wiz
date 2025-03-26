package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class H_Lasers extends PanelButton {

    int roll;
    int MAX_BONUS_PRISMCHANCE = 45;
    int MAX_BONUS_PROJS = 2;

    public H_Lasers(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.LASERS;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.lasers_bonus_prismchance < MAX_BONUS_PRISMCHANCE ||
                player.spellbook.lasers_bonus_proj < MAX_BONUS_PROJS)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_freeze = MathUtils.randomBoolean();
            roll = (bonus_freeze && player.spellbook.lasers_bonus_prismchance < MAX_BONUS_PRISMCHANCE) ||
                    (!bonus_freeze && player.spellbook.lasers_bonus_proj >= MAX_BONUS_PROJS)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 1 -> player.spellbook.lasers_bonus_prismchance += 15;
            case 2 -> player.spellbook.lasers_bonus_proj += 1;
            case 3 -> player.spellbook.lasers_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 1 -> s = "+15% Chance to Create Prism";
            case 2 -> s = "+1 Projectile";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            LASERS
            
            %s
            """, s);
        setText(text);
    }
}
