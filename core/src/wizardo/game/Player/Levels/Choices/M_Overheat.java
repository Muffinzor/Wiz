package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Spells.SpellBank.Fire_Spells.fire_spells;
import static wizardo.game.Wizardo.player;

public class M_Overheat extends PanelButton {

    int roll = 0;

    int MAX_BONUS_COOLDOWN_REDUCTION = 20;
    int MAX_BONUS_TOFULL = 45;

    public M_Overheat(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.OVERHEAT;
        super.setup();
        pick_type();
        set_text();
        learned_spell = fire_spells[2];
    }

    public void pick_type() {
        if(player.spellbook.overheat_lvl == 0 && player.stats.bonusMastery_overheat == 0) return;

        // Default
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.overheat_bonus_cdreduction < MAX_BONUS_COOLDOWN_REDUCTION ||
                player.spellbook.overheat_bonus_fullhpdmg < MAX_BONUS_TOFULL)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_jump = MathUtils.randomBoolean();
            roll = (bonus_jump && player.spellbook.overheat_bonus_cdreduction < MAX_BONUS_COOLDOWN_REDUCTION) ||
                    (!bonus_jump && player.spellbook.overheat_bonus_fullhpdmg >= MAX_BONUS_TOFULL)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.overheat_lvl++;
            case 1 -> player.spellbook.overheat_bonus_cdreduction += 10;
            case 2 -> player.spellbook.overheat_bonus_fullhpdmg += 15;
            case 3 -> player.spellbook.overheat_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Overheat";
            case 1 -> s = "-10% Cooldown";
            case 2 -> s = string_build();
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            OVERHEAT
            
            %s
            """, s);
        setText(text);
    }

    public String string_build() {
        return String.format("+15%% Damage to High Hp Monsters");
    }
}
