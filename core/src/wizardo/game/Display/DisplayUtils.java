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
        return screen.displayManager.spriteRenderer.pool.getSprite();
    }

    /**
     * @param screen The current screen
     * @return a RoundLight object from that screen's LightManager's pool
     */
    public static RoundLight getLight(BaseScreen screen) {
        return screen.lightManager.pool.getLight();
    }

}
