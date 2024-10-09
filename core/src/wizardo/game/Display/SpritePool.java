package wizardo.game.Display;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayDeque;

public class SpritePool {

    public static Sprite whitePixel = new Sprite(new Texture("placeholderTexture.png"));

    private final ArrayDeque<Sprite> array;

    public SpritePool() {
        array = new ArrayDeque<>();
    }

    public Sprite getSprite() {
        if(array.isEmpty()) {
            return new Sprite();
        } else {
            return array.removeLast();
        }
    }

    public void poolSprite(Sprite sprite) {
        sprite.set(whitePixel);
        array.add(sprite);
    }

    public int getSize() {
        return array.size();
    }
}
