package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class ChainLightningAnims {

    public static Animation<Sprite> chainlightning_lightning_anim;
    public static Animation<Sprite> chainlightning_long_lightning_anim;
    public static String chainlightning_atlas_path_lightning = "Spells/ChainLightning/lightning.atlas";

    public static Animation<Sprite> chainlightning_beam_anim;
    public static Animation<Sprite> chainlightning_long_beam_anim;
    public static String chainlightning_atlas_path_beam = "Spells/ChainLightning/laser.atlas";
    public static String chainlightning_atlas_path_beam_long = "Spells/ChainLightning/laser_long.atlas";

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


        TextureAtlas beam_atlas = assetManager.get(chainlightning_atlas_path_beam, TextureAtlas.class);
        TextureAtlas long_beam_atlas = assetManager.get(chainlightning_atlas_path_beam_long, TextureAtlas.class);

        Sprite[] beam_frames = new Sprite[10];
        for (int i = 0; i < beam_frames.length; i++) {
            beam_frames[i] = beam_atlas.createSprite("chain" + (i));
        }
        chainlightning_beam_anim = new Animation<>(0.03f, beam_frames);

        Sprite[] long_beam_frames = new Sprite[8];
        for (int i = 0; i < long_beam_frames.length; i++) {
            long_beam_frames[i] = long_beam_atlas.createSprite("long" + (i));
        }
        chainlightning_long_beam_anim = new Animation<>(0.03f, long_beam_frames);

    }

    public static void loadAtlas() {
        assetManager.load(chainlightning_atlas_path_lightning, TextureAtlas.class);
        assetManager.load(chainlightning_atlas_path_beam, TextureAtlas.class);
        assetManager.load(chainlightning_atlas_path_beam_long, TextureAtlas.class);
    }
}
