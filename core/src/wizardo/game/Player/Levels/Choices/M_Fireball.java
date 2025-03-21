package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Spells.SpellBank.Fire_Spells.fire_spells;
import static wizardo.game.Wizardo.player;

public class M_Fireball extends PanelButton {

    int roll = 0;

    int MAX_BONUS_KNOCKBACK = 30;
    int MAX_BONUS_SPLASH = 30;

    public M_Fireball(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.FIREBALL;
        super.setup();
        pick_type();
        set_text();
        learned_spell = fire_spells[1];
    }

    public void pick_type() {
        if(player.spellbook.fireball_lvl - player.stats.bonusMastery_fireball < 1) return;

        // Default
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.fireball_bonus_knockback < MAX_BONUS_KNOCKBACK ||
                player.spellbook.fireball_bonus_radius < MAX_BONUS_SPLASH)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_jump = MathUtils.randomBoolean();
            roll = (bonus_jump && player.spellbook.fireball_bonus_knockback < MAX_BONUS_KNOCKBACK) ||
                    (!bonus_jump && player.spellbook.fireball_bonus_radius >= MAX_BONUS_SPLASH)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.fireball_lvl++;
            case 1 -> player.spellbook.fireball_bonus_knockback += 10;
            case 2 -> player.spellbook.fireball_bonus_radius += 10;
            case 3 -> player.spellbook.fireball_bonus_dmg += mastery_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Fireball";
            case 1 -> s = "+10% Explosion Knockback";
            case 2 -> s = "+10% Explosion Radius";
            case 3 -> s = String.format("+%d%% Damage", mastery_dmg_buff);
        }
        String text = String.format("""
            FIREBALL
            
            %s
            """, s);
        setText(text);
    }
}
