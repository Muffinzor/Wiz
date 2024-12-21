package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class RepulsionFieldAnims {

    public static Animation<Sprite> repulsion_field;
    public static Sprite purple_proj = new Sprite(new Texture("Spells/RepulsionField/small_purple.png"));

    public static String repulsion_field_path = "Spells/RepulsionField/repulsion_field.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(repulsion_field_path, TextureAtlas.class);

        Sprite[] frames = new Sprite[90];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("overheat" + (i+1));
        }
        repulsion_field = new Animation<>(0.016f, frames);

    }

    public static void loadAtlas() {
        assetManager.load(repulsion_field_path, TextureAtlas.class);
    }
}
