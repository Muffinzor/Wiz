package wizardo.game.Player.Levels.Choices;

import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class H_Judgement extends PanelButton {

    int roll = 0;

    int MAX_BONUS_CDREDUCTION = 4;

    public H_Judgement(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.JUDGEMENT;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        // Default
        roll = 2;

        // If bonus effect is rolled
        if (player.spellbook.judgement_bonus_cdreduction < MAX_BONUS_CDREDUCTION && Math.random() > bonus_effect_chance) {
            roll = 1;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 1 -> player.spellbook.judgement_bonus_cdreduction++;
            case 2 -> player.spellbook.judgement_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 1 -> s = "-0.4s Base Cooldown";
            case 2 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            JUDGEMENT
            
            %s
            """, s);
        setText(text);
    }
}
