package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class FrostboltAnims {

    public static Animation<Sprite> frostbolt_anim;
    public static String frostbolt_anim_path = "Spells/Frostbolt/Frostbolt.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(frostbolt_anim_path, TextureAtlas.class);

        Sprite[] frames = new Sprite[6];
        for (int i = 0; i < 5; i++) {
            frames[i] = atlas.createSprite("frostbolt" + i);
        }
        frostbolt_anim = new Animation<>(0.1f, frames);
    }

    public static void loadAtlas() {
        assetManager.load(frostbolt_anim_path, TextureAtlas.class);
    }
}
