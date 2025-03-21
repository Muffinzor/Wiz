package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Spells.SpellBank.Arcane_Spells.arcane_spells;
import static wizardo.game.Spells.SpellBank.Lightning_Spells.lightning_spells;
import static wizardo.game.Wizardo.player;

public class M_Energybeam extends PanelButton {

    int roll = 0;

    int MAX_BONUS_WIDTH = 60;
    int MAX_BONUS_FIRSTHITS = 100;

    public M_Energybeam(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.BEAM;
        super.setup();
        pick_type();
        set_text();
        learned_spell = arcane_spells[1];
    }

    public void pick_type() {
        if(player.spellbook.energybeam_lvl - player.stats.bonusMastery_beam < 1) return;

        // Default
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.energybeam_bonus_width < MAX_BONUS_WIDTH ||
                player.spellbook.energybeam_bonus_firsthits < MAX_BONUS_FIRSTHITS)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_jump = MathUtils.randomBoolean();
            roll = (bonus_jump && player.spellbook.energybeam_bonus_width < MAX_BONUS_WIDTH) ||
                    (!bonus_jump && player.spellbook.energybeam_bonus_firsthits >= MAX_BONUS_FIRSTHITS)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.energybeam_lvl++;
            case 1 -> player.spellbook.energybeam_bonus_width += 20;
            case 2 -> player.spellbook.energybeam_bonus_firsthits += 20;
            case 3 -> player.spellbook.energybeam_bonus_dmg += mastery_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Energy Beam";
            case 1 -> s = "+10% Beam Width";
            case 2 -> s = first_hit_string();
            case 3 -> s = String.format("+%d%% Damage", mastery_dmg_buff);
        }
        String text = String.format("""
            ENERGY BEAM
            
            %s
            """, s);
        setText(text);
    }

    public String first_hit_string() {
        String b = "";
        switch(player.spellbook.energybeam_bonus_firsthits) {
            case 0 -> b = "1.2x";
            case 20 -> b = "1.4x";
            case 40 -> b = "1.6x";
            case 60 -> b = "1.8x";
            case 80 -> b = "2x";
        }
        return String.format("%s Damage to the first\n3 enemies hit", b);
    }
}
