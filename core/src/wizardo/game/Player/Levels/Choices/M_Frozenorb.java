package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Spells.SpellBank.Frost_Spells.frost_spells;
import static wizardo.game.Wizardo.player;

public class M_Frozenorb extends PanelButton {

    int roll = 0;

    int MAX_BONUS_RADIUS = 30;
    int MAX_BONUS_PROJECTILES = 30;

    public M_Frozenorb(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.FROZENORB;
        super.setup();
        pick_type();
        set_text();
        learned_spell = frost_spells[2];
    }

    public void pick_type() {
        if(player.spellbook.frozenorb_lvl - player.stats.bonusMastery_frozenorb < 1) return;

        // Default
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.frozenorb_bonus_radius < MAX_BONUS_RADIUS ||
                player.spellbook.frozenorb_bonus_proj_quantity < MAX_BONUS_PROJECTILES)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_jump = MathUtils.randomBoolean();
            roll = (bonus_jump && player.spellbook.frozenorb_bonus_radius < MAX_BONUS_RADIUS) ||
                    (!bonus_jump && player.spellbook.frozenorb_bonus_proj_quantity >= MAX_BONUS_PROJECTILES)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.frozenorb_lvl++;
            case 1 -> player.spellbook.frozenorb_bonus_radius += 10;
            case 2 -> player.spellbook.frozenorb_bonus_proj_quantity += 10;
            case 3 -> player.spellbook.frozenorb_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Frozen Orb";
            case 1 -> s = "+10% Area of Effect";
            case 2 -> s = "Shoots projectiles 10% faster";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            FROZEN ORB
            
            %s
            """, s);
        setText(text);
    }
}
