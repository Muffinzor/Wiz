package wizardo.game.Display.Text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Screens.BaseScreen;

public class FloatingDamage extends FloatingText {

    public FloatingDamage() {
        super();
    }

    public void update(float delta) {
        stateTime += delta;
        if(delta > 0) {
            y_increase += 0.3f;
        }
        if(stateTime >= 0.1f) {
            alpha -= .1f;
            stateTime = 0;
        }


        if (alpha <= 0) {
            text = "";
        }
    }
}
