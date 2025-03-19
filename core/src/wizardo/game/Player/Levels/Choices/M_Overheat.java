package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class M_Overheat extends PanelButton {

    int roll = 0;

    int MAX_BONUS_COOLDOWN_REDUCTION = 30;
    int MAX_BONUS_RADIUS = 30;

    public M_Overheat(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.OVERHEAT;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        if(player.spellbook.overheat_lvl - player.stats.bonusMastery_overheat < 1) return;

        // Default
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.overheat_bonus_cdreduction < MAX_BONUS_COOLDOWN_REDUCTION ||
                player.spellbook.overheat_bonus_radius < MAX_BONUS_RADIUS)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_jump = MathUtils.randomBoolean();
            roll = (bonus_jump && player.spellbook.overheat_bonus_cdreduction < MAX_BONUS_COOLDOWN_REDUCTION) ||
                    (!bonus_jump && player.spellbook.overheat_bonus_radius >= MAX_BONUS_RADIUS)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.overheat_lvl++;
            case 1 -> player.spellbook.overheat_bonus_cdreduction += 10;
            case 2 -> player.spellbook.overheat_bonus_radius += 10;
            case 3 -> player.spellbook.overheat_bonus_dmg += mastery_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Overheat";
            case 1 -> s = "-10% Cooldown";
            case 2 -> s = "+10% Explosion Radius";
            case 3 -> s = "+30% Damage";
        }
        String text = String.format("""
            OVERHEAT
            
            %s
            """, s);
        setText(text);
    }
}
