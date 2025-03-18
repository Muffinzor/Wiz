package wizardo.game.Screens.LevelUp.Choices;

import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class M_Frostbolt extends PanelButton {

    int roll = 0;

    public M_Frostbolt(LevelUpScreen screen, LevelUpEnums.LevelUps type, LevelUpEnums.LevelUpQuality quality) {
        super(screen, type, quality);

        pick_type();
        set_text();
    }

    public void pick_type() {
        if(player.spellbook.frostbolt_lvl - player.stats.bonusMastery_frostbolt < 1) return;

        float bonus_proj_chance = 0.6f;
        if(player.spellbook.frostbolts_bonus_proj < 5 && Math.random() > bonus_proj_chance) {
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
            case 0 -> s = "+1 Mastery";
            case 1 -> s = "+1 Projectile";
            case 2 -> s = "+30% Damage";
        }
        String text = String.format("""
            FROSTBOLTS
            
            %s
            """, s);
        setText(text);
    }

    public void draw(float delta) {
        drawLiteralFrame(delta);
    }

}
