package wizardo.game.Display;

import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Screens.BaseScreen;

public class DisplayUtils {


    /**
     * @param screen The current screen
     * @return a sprite from that screen's SpriteRenderer's pool
     */
    public static Sprite getSprite(BaseScreen screen) {
        return screen.spriteRenderer.pool.getSprite();
    }

}
