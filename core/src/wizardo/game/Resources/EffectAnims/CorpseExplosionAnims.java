package wizardo.game.Resources.EffectAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class CorpseExplosionAnims {

    public static Animation<Sprite> corpse_explosion;
    public static String corpse_explosion_path = "Spells/GearSpells/CorpseExplosion/DistortionFireExplosion.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(corpse_explosion_path, TextureAtlas.class);

        Sprite[] frames = new Sprite[55];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("explosion" + (i+1));
        }
        corpse_explosion = new Animation<>(0.024f, frames);

    }

    public static void loadAtlas() {
        assetManager.load(corpse_explosion_path, TextureAtlas.class);
    }
}
