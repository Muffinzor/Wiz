package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Spells.SpellBank.Frost_Spells.frost_spells;
import static wizardo.game.Wizardo.player;

public class M_Frostbolt extends PanelButton {

    int roll = 0;

    int MAX_BONUS_PROJ = 4;
    int MAX_BONUS_SPLASH = 40;

    public M_Frostbolt(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.FROSTBOLT;
        super.setup();
        pick_type();
        set_text();
        learned_spell = frost_spells[0];
    }

    public void pick_type() {
        if(player.spellbook.frostbolt_lvl == 0 && player.stats.bonusMastery_frostbolt == 0) return;

        // Default
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.frostbolts_bonus_proj < MAX_BONUS_PROJ ||
                player.spellbook.frostbolts_bonus_splash < MAX_BONUS_SPLASH)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_jump = MathUtils.randomBoolean();
            roll = (bonus_jump && player.spellbook.frostbolts_bonus_proj < MAX_BONUS_PROJ) ||
                    (!bonus_jump && player.spellbook.frostbolts_bonus_splash >= MAX_BONUS_SPLASH)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.frostbolt_lvl++;
            case 1 -> player.spellbook.frostbolts_bonus_proj++;
            case 2 -> player.spellbook.frostbolts_bonus_splash += 10;
            case 3 -> player.spellbook.frostbolts_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Frostbolts";
            case 1 -> s = "+1 Projectile";
            case 2 -> s = "+10% AoE Radius";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            FROSTBOLTS
            
            %s
            """, s);
        setText(text);
    }
}
