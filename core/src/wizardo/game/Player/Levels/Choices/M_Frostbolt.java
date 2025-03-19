package wizardo.game.Player.Levels.Choices;

import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class M_Frostbolt extends PanelButton {

    int roll = 0;

    int MAX_BONUS_PROJ = 5;

    public M_Frostbolt(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.FROSTBOLT;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        if(player.spellbook.frostbolt_lvl - player.stats.bonusMastery_frostbolt < 1) return;

        if(player.spellbook.frostbolts_bonus_proj < MAX_BONUS_PROJ && Math.random() > bonus_effect_chance) {
            roll = 1;
            return;
        }

        roll = 2;
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.frostbolt_lvl++;
            case 1 -> player.spellbook.frostbolts_bonus_proj++;
            case 2 -> player.spellbook.frostbolts_bonus_dmg += mastery_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Frostbolts";
            case 1 -> s = "+1 Projectile";
            case 2 -> s = "+30% Damage";
        }
        String text = String.format("""
            FROSTBOLTS
            
            %s
            """, s);
        setText(text);
    }
}
