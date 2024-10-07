package wizardo.game.Display;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class SpriteRenderer {

    public static ArrayList<Sprite> toRender;

    public SpriteRenderer() {
        toRender = new ArrayList<>();
    }

    public void renderAll() {
        SpriteBatch batch = new SpriteBatch();
        batch.begin();

        for (Sprite sprite : toRender) {
            sprite.draw(batch);
        }

        batch.end();
        clearSpriteArrays();
    }

    public void clearSpriteArrays() {
        toRender.clear();
    }
}
