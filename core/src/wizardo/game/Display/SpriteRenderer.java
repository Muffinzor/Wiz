package wizardo.game.Display;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import wizardo.game.Screens.BaseScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpriteRenderer {

    public ArrayList<Sprite> regular_sorted_sprites;
    public ArrayList<Sprite> ui_sprites;
    public Map<Sprite, Float> spritePositionMap;
    public SpritePool pool;
    public BaseScreen screen;
    public SpriteBatch batch;

    public SpriteRenderer(BaseScreen screen) {
        regular_sorted_sprites = new ArrayList<>();
        spritePositionMap = new HashMap<>();
        ui_sprites = new ArrayList<>();
        pool = new SpritePool();
        this.screen = screen;
        batch = new SpriteBatch();

    }

    public void renderSprites() {
        batch.begin();
        batch.setProjectionMatrix(screen.mainCamera.combined);

        sortRegularSprites();

        for (Sprite sprite : regular_sorted_sprites) {
            sprite.draw(batch);
            pool.poolSprite(sprite);
        }

        batch.end();
    }

    public void renderUI() {
        batch.begin();
        batch.setProjectionMatrix(screen.uiCamera.combined);

        for (Sprite sprite : ui_sprites) {
            sprite.draw(batch);
            pool.poolSprite(sprite);
        }

        batch.end();

    }

    public void clearSpriteArrays() {
        regular_sorted_sprites.clear();
        ui_sprites.clear();
    }

    public void sortRegularSprites() {
        regular_sorted_sprites.sort((sprite1, sprite2) -> {
            float y1 = spritePositionMap.containsKey(sprite1)
                    ? spritePositionMap.get(sprite1)
                    : sprite1.getY();

            float y2 = spritePositionMap.containsKey(sprite2)
                    ? spritePositionMap.get(sprite2)
                    : sprite2.getY();

            return Float.compare(y2, y1);
        });
    }
}
