package wizardo.game.Display;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Screens.BaseScreen;

public class DisplayUtils {


    /**
     * @param screen The current screen
     * @return a sprite from that screen's SpriteRenderer's pool
     */
    public static Sprite getSprite(BaseScreen screen) {
        return new Sprite();
    }

    public static RoundLight getLight(RayHandler rayHandler) {
        return new RoundLight(rayHandler);
    }

}
