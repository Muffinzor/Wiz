package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class ChainLightningAnims {

    public static Animation<Sprite> chainlightning_lightning_anim;
    public static Animation<Sprite> chainlightning_long_lightning_anim;
    public static String chainlightning_atlas_path_lightning = "Spells/ChainLightning/lightning.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(chainlightning_atlas_path_lightning, TextureAtlas.class);

        Sprite[] normal_frames = new Sprite[22];
        for (int i = 0; i < normal_frames.length; i++) {
            normal_frames[i] = atlas.createSprite("chain" + (i+1));
        }
        chainlightning_lightning_anim = new Animation<>(0.03f, normal_frames);

        Sprite[] long_frames = new Sprite[8];
        for (int i = 0; i < long_frames.length; i++) {
            long_frames[i] = atlas.createSprite("long" + (i+1));
        }
        chainlightning_long_lightning_anim = new Animation<>(0.03f, long_frames);

    }

    public static void loadAtlas() {
        assetManager.load(chainlightning_atlas_path_lightning, TextureAtlas.class);
    }
}
