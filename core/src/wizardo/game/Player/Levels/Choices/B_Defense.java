package wizardo.game.Player.Levels.Choices;

import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Player.Levels.LevelUpUtils;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Player.Levels.LevelUpEnums.LevelUpQuality.EPIC;
import static wizardo.game.Player.Levels.LevelUpEnums.LevelUpQuality.RARE;
import static wizardo.game.Wizardo.player;

public class B_Defense extends PanelButton {

    public B_Defense(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.DEFENSE;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        quality = LevelUpUtils.getRandomQuality();
        if(quality == LevelUpEnums.LevelUpQuality.NORMAL || quality == RARE) {
            quality = EPIC;
        }
        set_gem_sprite();
    }

    public void apply_stats() {
        buff_defense();
    }

    private void buff_defense() {
        switch(quality) {
            case EPIC -> player.stats.defense += 1;
            case LEGENDARY -> player.stats.defense += 2;
        }
    }

    private String regen_string() {
        String q = "";
        switch (quality) {
            case EPIC -> q = "1";
            case LEGENDARY -> q = "2";
        }
        return String.format("""
            STURDY ROBES
            
            +%s Defense
            """, q, player.stats.baseRecharge);
    }

    public void set_text() {
        setText(regen_string());
    }

    public void drawPanel(float delta) {
        drawLiteralFrame(delta);
        drawQualityGem();
    }

}
