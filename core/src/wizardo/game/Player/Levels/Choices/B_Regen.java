package wizardo.game.Player.Levels.Choices;

import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Player.Levels.LevelUpUtils;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Player.Levels.LevelUpEnums.LevelUpQuality.RARE;
import static wizardo.game.Wizardo.player;

public class B_Regen extends PanelButton {

    public B_Regen(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.REGEN;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        quality = LevelUpUtils.getRandomQuality();
        if(quality == LevelUpEnums.LevelUpQuality.NORMAL) {
            quality = RARE;
        }
        set_gem_sprite();
    }

    public void apply_stats() {
        buff_regen();
    }

    private void buff_regen() {
        switch(quality) {
            case RARE -> player.stats.baseRecharge += 0.6f;
            case EPIC -> player.stats.baseRecharge += 0.8f;
            case LEGENDARY -> player.stats.baseRecharge += 1.2f;
        }
    }

    private String regen_string() {
        String q = "";
        switch (quality) {
            case RARE -> q = "0.6";
            case EPIC -> q = "0.8";
            case LEGENDARY -> q = "1.2";
        }
        return String.format("""
            RESOLVE
            
            +%s Shield per second
            Current regen: %.1f
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
