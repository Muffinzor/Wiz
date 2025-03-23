package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Spells.SpellBank.Arcane_Spells.arcane_spells;
import static wizardo.game.Wizardo.player;

public class M_Arcanemissiles extends PanelButton {

    int roll = 0;

    int MAX_BONUS_PROJ = 3;
    int MAX_BONUS_ROTATION = 3;

    public M_Arcanemissiles(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.MISSILES;
        super.setup();
        pick_type();
        set_text();
        learned_spell = arcane_spells[0];
    }

    public void pick_type() {
        if(player.spellbook.arcanemissile_lvl - player.stats.bonusMastery_missiles < 1) return;

        // Default
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.arcane_missile_bonus_proj < MAX_BONUS_PROJ ||
                player.spellbook.arcane_missile_bonus_rotation < MAX_BONUS_ROTATION)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_jump = MathUtils.randomBoolean();
            roll = (bonus_jump && player.spellbook.arcane_missile_bonus_proj < MAX_BONUS_PROJ) ||
                    (!bonus_jump && player.spellbook.arcane_missile_bonus_rotation >= MAX_BONUS_ROTATION)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.arcanemissile_lvl++;
            case 1 -> player.spellbook.arcane_missile_bonus_proj++;
            case 2 -> player.spellbook.arcane_missile_bonus_rotation++;
            case 3 -> player.spellbook.arcane_missile_bonus_dmg += spell_dmg_buff;
        }
    }



    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Arcane Missiles";
            case 1 -> s = "+1 Projectile";
            case 2 -> s = "20% Faster Tracking";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            ARCANE MISSILES
            
            %s
            """, s);
        setText(text);
    }
}
