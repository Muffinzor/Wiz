package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Spells.SpellBank.Arcane_Spells.arcane_spells;
import static wizardo.game.Wizardo.player;

public class M_Rifts extends PanelButton {

    int roll = 0;

    int MAX_BONUS_SPREAD = 50;
    int MAX_BONUS_QUANTITY = 50;

    public M_Rifts(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.RIFTS;
        super.setup();
        pick_type();
        set_text();
        learned_spell = arcane_spells[2];

    }

    public void pick_type() {
        if(player.spellbook.rift_lvl - player.stats.bonusMastery_rifts < 1) return;

        // Default
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.rifts_bonus_spread < MAX_BONUS_SPREAD ||
                player.spellbook.rifts_bonus_quantity < MAX_BONUS_QUANTITY)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_jump = MathUtils.randomBoolean();
            roll = (bonus_jump && player.spellbook.rifts_bonus_spread < MAX_BONUS_SPREAD) ||
                    (!bonus_jump && player.spellbook.rifts_bonus_quantity >= MAX_BONUS_QUANTITY)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.rift_lvl++;
            case 1 -> player.spellbook.rifts_bonus_spread += 10;
            case 2 -> player.spellbook.rifts_bonus_quantity += 10;
            case 3 -> player.spellbook.rifts_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Rifts";
            case 1 -> s = "+10% Spread Radius";
            case 2 -> s = "10% More Rifts";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            RIFTS
            
            %s
            """, s);
        setText(text);
    }
}
