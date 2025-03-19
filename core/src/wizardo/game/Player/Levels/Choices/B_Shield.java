package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Player.Levels.LevelUpUtils;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Resources.ScreenResources.LevelUpResources.*;
import static wizardo.game.Wizardo.player;

public class B_Shield extends PanelButton {

    int type_roll;

    public B_Shield(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.REGEN;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        type_roll = MathUtils.random(0,1);
        quality = LevelUpUtils.getRandomQuality();
        set_gem_sprite();
    }

    public void apply_stats() {
        switch(type_roll) {
            case 0 -> buff_shield();
            case 1 -> buff_regen();
        }
    }

    private void buff_shield() {
        int value = 0;
        switch(quality) {
            case NORMAL -> value = 10;
            case RARE -> value = 15;
            case EPIC -> value = 20;
            case LEGENDARY -> value = 30;
        }
        player.stats.maxShield += value;
        player.stats.shield += value;
    }
    private void buff_regen() {
        switch(quality) {
            case NORMAL -> player.stats.baseRecharge += 0.2f;
            case RARE -> player.stats.baseRecharge += 0.4f;
            case EPIC -> player.stats.baseRecharge += 0.6f;
            case LEGENDARY -> player.stats.baseRecharge += 1f;
        }
    }

    private String shield_string() {
        String q = "";
        switch (quality) {
            case NORMAL -> q = "10";
            case RARE -> q = "15";
            case EPIC -> q = "20";
            case LEGENDARY -> q = "30";
        }
        return String.format("""
            WIZARD'S TENACITY
            
            +%s Maximum shield
            Current max: %.0f
            """, q, player.stats.maxShield);
    }

    private String regen_string() {
        String q = "";
        switch (quality) {
            case NORMAL -> q = "0.2";
            case RARE -> q = "0.4";
            case EPIC -> q = "0.6";
            case LEGENDARY -> q = "1";
        }
        return String.format("""
            WIZARD'S RESOLVE
            
            +%s Shield per second
            Current regen: %.1f
            """, q, player.stats.baseRecharge);
    }

    public void set_text() {
        switch(type_roll) {
            case 0 -> setText(shield_string());
            case 1 -> setText(regen_string());
        }
    }

    public void drawPanel(float delta) {
        drawLiteralFrame(delta);
        drawQualityGem();
    }

}
