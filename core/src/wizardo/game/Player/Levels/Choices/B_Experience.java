package wizardo.game.Player.Levels.Choices;

import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Player.Levels.LevelUpUtils;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class B_Experience extends PanelButton {

    int value = 0;

    public B_Experience(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.EXPERIENCE;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        quality = LevelUpUtils.getRandomQuality();
        set_gem_sprite();
        switch(quality) {
            case NORMAL -> value = 5;
            case RARE -> value = 10;
            case EPIC -> value = 15;
            case LEGENDARY -> value = 25;
        }
    }

    public void apply_stats() {
        player.stats.bonus_experience += value;
    }

    private void set_text() {
        String q = String.format("""
            PEER REVIEWED WIZARDRY
            
            +%d%% Experience Gains
            """, value);
        setText(q);
    }

    public void drawPanel(float delta) {
        drawLiteralFrame(delta);
        drawQualityGem();
    }

}
