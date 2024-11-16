package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class ThunderstormAnims {

    public static Animation<Sprite> thunder_lightning_anim;
    public static String thunder_atlas_path_lightning = "Spells/Thunderstorm/thunder_lightning.atlas";

    public static Animation<Sprite> thunder_arcane_anim;
    public static String thunder_atlas_path_arcane = "Spells/Thunderstorm/thunder_arcane.atlas";


    public static Animation<Sprite> thunder_frost_anim;
    public static String thunder_atlas_path_frost = "Spells/Thunderstorm/thunder_frost.atlas";


    public static Animation<Sprite> thunder_fire_anim;
    public static String thunder_atlas_path_fire = "Spells/Thunderstorm/thunder_fire.atlas";

    public static void loadAnimations() {

        TextureAtlas fire_atlas = assetManager.get(thunder_atlas_path_fire, TextureAtlas.class);

        Sprite[] fire_frames = new Sprite[40];
        for (int i = 0; i < fire_frames.length; i++) {
            fire_frames[i] = fire_atlas.createSprite("thunder" + (i+1));
        }
        thunder_fire_anim = new Animation<>(0.04f, fire_frames);

        TextureAtlas frost_atlas = assetManager.get(thunder_atlas_path_frost, TextureAtlas.class);

        Sprite[] frost_frames = new Sprite[40];
        for (int i = 0; i < frost_frames.length; i++) {
            frost_frames[i] = frost_atlas.createSprite("thunder" + (i+1));
        }
        thunder_frost_anim = new Animation<>(0.04f, frost_frames);

        TextureAtlas atlas = assetManager.get(thunder_atlas_path_lightning, TextureAtlas.class);

        Sprite[] lightning_frames = new Sprite[40];
        for (int i = 0; i < lightning_frames.length; i++) {
            lightning_frames[i] = atlas.createSprite("thunder" + (i+1));
        }
        thunder_lightning_anim = new Animation<>(0.04f, lightning_frames);

        TextureAtlas arc_atlas = assetManager.get(thunder_atlas_path_arcane, TextureAtlas.class);

        Sprite[] arc_frames = new Sprite[40];
        for (int i = 0; i < arc_frames.length; i++) {
            arc_frames[i] = arc_atlas.createSprite("thunder" + (i+1));
        }
        thunder_arcane_anim = new Animation<>(0.04f, arc_frames);

    }

    public static void loadAtlas() {
        assetManager.load(thunder_atlas_path_lightning, TextureAtlas.class);
        assetManager.load(thunder_atlas_path_arcane, TextureAtlas.class);
        assetManager.load(thunder_atlas_path_frost, TextureAtlas.class);
        assetManager.load(thunder_atlas_path_fire, TextureAtlas.class);
    }
}
