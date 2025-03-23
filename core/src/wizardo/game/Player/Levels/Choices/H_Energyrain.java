package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class H_Energyrain extends PanelButton {

    int roll;
    int MAX_BONUS_PROJS = 8;
    int MAX_BONUS_SPLASH = 40;

    public H_Energyrain(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.ENERGYRAIN;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.energyrain_bonus_proj < MAX_BONUS_PROJS ||
                player.spellbook.energyrain_bonus_radius < MAX_BONUS_SPLASH)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_freeze = MathUtils.randomBoolean();
            roll = (bonus_freeze && player.spellbook.energyrain_bonus_proj < MAX_BONUS_PROJS) ||
                    (!bonus_freeze && player.spellbook.energyrain_bonus_radius >= MAX_BONUS_SPLASH)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 1 -> player.spellbook.energyrain_bonus_proj += 2;
            case 2 -> player.spellbook.energyrain_bonus_radius += 10;
            case 3 -> player.spellbook.energybeam_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 1 -> s = "+2 Beams";
            case 2 -> s = "+10% Explosion Radius";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            ENERGY RAIN
            
            %s
            """, s);
        setText(text);
    }
}
