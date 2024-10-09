package wizardo.game.Display;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class SpriteRenderer {

    public ArrayList<Sprite> character_spell_sprites;
    public ArrayList<Sprite> ui_sprites;
    public SpritePool pool;

    public SpriteRenderer() {
        character_spell_sprites = new ArrayList<>();
        ui_sprites = new ArrayList<>();
        pool = new SpritePool();
    }

    public void renderAll() {
        SpriteBatch batch = new SpriteBatch();
        batch.begin();

        for (Sprite sprite : character_spell_sprites) {
            sprite.draw(batch);
            pool.poolSprite(sprite);
        }

        for (Sprite sprite : ui_sprites) {
            sprite.draw(batch);
            pool.poolSprite(sprite);
        }

        batch.end();
        clearSpriteArrays();
    }

    public void clearSpriteArrays() {
        character_spell_sprites.clear();
        ui_sprites.clear();
    }
}
