package wizardo.game.Display;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import wizardo.game.Screens.BaseScreen;

import java.util.ArrayList;

public class SpriteRenderer {

    public ArrayList<Sprite> character_spell_sprites;
    public ArrayList<Sprite> ui_sprites;
    public SpritePool pool;
    public BaseScreen screen;
    public SpriteBatch batch;

    public SpriteRenderer(BaseScreen screen) {
        character_spell_sprites = new ArrayList<>();
        ui_sprites = new ArrayList<>();
        pool = new SpritePool();
        this.screen = screen;
        batch = new SpriteBatch();

    }

    public void renderSprites() {
        batch.begin();
        batch.setProjectionMatrix(screen.camera.combined);

        for (Sprite sprite : character_spell_sprites) {
            sprite.draw(batch);
            pool.poolSprite(sprite);
        }

        batch.end();
    }

    public void renderUI() {
        batch.begin();

        for (Sprite sprite : ui_sprites) {
            sprite.draw(batch);
            pool.poolSprite(sprite);
        }

        batch.end();

    }

    public void clearSpriteArrays() {
        character_spell_sprites.clear();
        ui_sprites.clear();
    }
}
