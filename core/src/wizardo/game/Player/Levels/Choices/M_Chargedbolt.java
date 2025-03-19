package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class M_Chargedbolt extends PanelButton {

    int roll = 0;

    int MAX_BONUS_PROJ = 3;
    int MAX_BONUS_DURATION = 60;

    public M_Chargedbolt(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.CHARGEDBOLT;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        if(player.spellbook.chargedbolt_lvl - player.stats.bonusMastery_chargedbolt < 1) return;

        // Default
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.chargedbolts_bonus_proj < MAX_BONUS_PROJ ||
                player.spellbook.chargedbolts_bonus_duration < MAX_BONUS_DURATION)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_jump = MathUtils.randomBoolean();
            roll = (bonus_jump && player.spellbook.chargedbolts_bonus_proj < MAX_BONUS_PROJ) ||
                    (!bonus_jump && player.spellbook.chargedbolts_bonus_duration >= MAX_BONUS_DURATION)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.chargedbolt_lvl++;
            case 1 -> player.spellbook.chargedbolts_bonus_proj++;
            case 2 -> player.spellbook.chargedbolts_bonus_duration += 20;
            case 3 -> player.spellbook.chargedbolts_bonus_dmg += mastery_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Chargedbolts";
            case 1 -> s = "+1 Projectile";
            case 2 -> s = "+20% Duration";
            case 3 -> s = "+30% Damage";
        }
        String text = String.format("""
            CHARGEDBOLTS
            
            %s
            """, s);
        setText(text);
    }
}
