package wizardo.game.Resources.EffectAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class BlackHoleAnims {

    public static Animation<Sprite> blackhole_anim;
    public static String blackhole_anim_path = "Spells/Blackhole/Blackhole.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(blackhole_anim_path, TextureAtlas.class);

        Sprite[] frames = new Sprite[155];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("blackhole" + (i+1));
        }
        blackhole_anim = new Animation<>(0.028f, frames);

    }

    public static void loadAtlas() {
        assetManager.load(blackhole_anim_path, TextureAtlas.class);
    }
}
