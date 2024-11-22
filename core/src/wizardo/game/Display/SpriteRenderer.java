package wizardo.game.Display;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import wizardo.game.Screens.BaseScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpriteRenderer {

    public ArrayList<Sprite> under_sprites;
    public ArrayList<Sprite> regular_sorted_sprites;
    public ArrayList<Sprite> over_sprites;
    public ArrayList<Sprite> ui_sprites;
    public Map<Sprite, Float> spritePositionMap;
    public SpritePool pool;
    public BaseScreen screen;
    public SpriteBatch batch;

    public SpriteRenderer(BaseScreen screen) {
        under_sprites = new ArrayList<>();
        over_sprites = new ArrayList<>();
        regular_sorted_sprites = new ArrayList<>();
        spritePositionMap = new HashMap<>();
        ui_sprites = new ArrayList<>();
        pool = new SpritePool();
        this.screen = screen;
    }
    public void renderSprites() {
        batch = screen.displayManager.mainBatch;
        batch.begin();
        batch.setProjectionMatrix(screen.mainCamera.combined);

        sortRegularSprites();

        for (Sprite sprite : under_sprites) {
            sprite.draw(batch);
            pool.poolSprite(sprite);
        }

        for (Sprite sprite : regular_sorted_sprites) {
            sprite.draw(batch);
            pool.poolSprite(sprite);
        }

        for (Sprite sprite : over_sprites) {
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
        under_sprites.clear();
        over_sprites.clear();
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
        spritePositionMap.clear();
    }


}
