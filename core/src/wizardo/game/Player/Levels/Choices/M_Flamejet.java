package wizardo.game.Player.Levels.Choices;

import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class M_Flamejet extends PanelButton {

    int roll = 0;

    int MAX_BONUS_FLAMES = 5;

    public M_Flamejet(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.FLAMEJET;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        if(player.spellbook.flamejet_lvl - player.stats.bonusMastery_flamejet < 1) return;

        if(player.spellbook.flamejet_bonus_flames < MAX_BONUS_FLAMES && Math.random() > bonus_effect_chance) {
            roll = 1;
            return;
        }

        roll = 2;
    }

    public void apply_stats() {
        switch(roll) {
            case 0 -> player.spellbook.flamejet_lvl++;
            case 1 -> player.spellbook.flamejet_bonus_flames ++;
            case 2 -> player.spellbook.flamejet_bonus_dmg += mastery_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 0 -> s = "Learn Flamejet";
            case 1 -> s = "+10% More Flames";
            case 2 -> s = "+30% Damage";
        }
        String text = String.format("""
            FLAMEJET
            
            %s
            """, s);
        setText(text);
    }
}
