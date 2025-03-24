package wizardo.game.Player.Levels.Choices;

import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Player.Levels.LevelUpUtils;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class B_Magnet extends PanelButton {

    int value = 0;

    public B_Magnet(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.MAGNET;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        quality = LevelUpUtils.getRandomQuality();
        set_gem_sprite();
        switch(quality) {
            case NORMAL -> value = 10;
            case RARE -> value = 15;
            case EPIC -> value = 20;
            case LEGENDARY -> value = 30;
        }
    }

    public void apply_stats() {
        player.stats.bonus_pickup_radius += value;
    }

    private void set_text() {
        String q = String.format("""
            YEARNING WIZARD
            
            +%d%% Pickup Radius
            """, value);
        setText(q);
    }

    public void drawPanel(float delta) {
        drawLiteralFrame(delta);
        drawQualityGem();
    }

}
