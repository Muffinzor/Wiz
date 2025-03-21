package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Player.Levels.LevelUpUtils;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Resources.ScreenResources.LevelUpResources.*;
import static wizardo.game.Wizardo.player;

public class B_Luck extends PanelButton {

    int value = 0;

    public B_Luck(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.LUCK;
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
        player.stats.luck += value;
    }

    private void set_text() {
        String q = String.format("""
            
            STAR ALIGNED
            
            +%d Luck
            
            Luck improves loot and gold drops
            """, value);
        setText(q);
    }

    public void drawPanel(float delta) {
        drawLiteralFrame(delta);
        drawQualityGem();
    }

}
