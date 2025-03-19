package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class M_Icespear extends PanelButton {

    int roll = 0;

    int MAX_BONUS_SHARDS = 3;
    int MAX_BONUS_SPLITCHANCE = 30;

    public M_Icespear(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.ICESPEAR;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        if(player.spellbook.icespear_lvl - player.stats.bonusMastery_icespear < 1) return;

        // Default
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.icespear_bonus_shard < MAX_BONUS_SHARDS ||
                player.spellbook.icespear_bonus_split_chance < MAX_BONUS_SPLITCHANCE)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_jump = MathUtils.randomBoolean();
            roll = (bonus_jump && player.spellbook.icespear_bonus_shard < MAX_BONUS_SHARDS) ||
                    (!bonus_jump && player.spellbook.icespear_bonus_split_chance >= MAX_BONUS_SPLITCHANCE)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.icespear_lvl++;
            case 1 -> player.spellbook.icespear_bonus_shard++;
            case 2 -> player.spellbook.icespear_bonus_split_chance += 10;
            case 3 -> player.spellbook.icespear_bonus_dmg += mastery_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Icespear";
            case 1 -> s = "+1 Shard when Splitting";
            case 2 -> s = "+10% Chance to Split";
            case 3 -> s = "+30% Damage";
        }
        String text = String.format("""
            ICESPEAR
            
            %s
            """, s);
        setText(text);
    }
}
