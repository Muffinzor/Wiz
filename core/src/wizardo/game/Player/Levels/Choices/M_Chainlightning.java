package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Spells.SpellBank.Lightning_Spells.lightning_spells;
import static wizardo.game.Wizardo.player;

public class M_Chainlightning extends PanelButton {

    private static final int MAX_BONUS_JUMP = 4;
    private static final float MAX_BONUS_CRIT = 30;
    int roll = 0;

    public M_Chainlightning(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.CHAIN;
        super.setup();
        pick_type();
        set_text();
        learned_spell = lightning_spells[1];
    }

    public void pick_type() {
        // If spell is not learned
        if(player.spellbook.chainlightning_lvl == 0 && player.stats.bonusMastery_chainlightning == 0) return;

        // Default
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.chainlightning_bonus_jump < MAX_BONUS_JUMP ||
                player.spellbook.chainlightning_bonus_crit < MAX_BONUS_CRIT)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_jump = MathUtils.randomBoolean();
            roll = (bonus_jump && player.spellbook.chainlightning_bonus_jump < MAX_BONUS_JUMP) ||
                    (!bonus_jump && player.spellbook.chainlightning_bonus_crit >= MAX_BONUS_CRIT)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.chainlightning_lvl++;
            case 1 -> player.spellbook.chainlightning_bonus_jump++;
            case 2 -> player.spellbook.chainlightning_bonus_crit += 10;
            case 3 -> player.spellbook.chainlightning_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Chain Lightning";
            case 1 -> s = "+1 Jump";
            case 2 -> s = "+10% Chance to deal double damage";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            CHAIN LIGHTNING
            
            %s
            """, s);
        setText(text);
    }
}
