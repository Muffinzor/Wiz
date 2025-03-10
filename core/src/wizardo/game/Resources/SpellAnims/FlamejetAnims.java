package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class FlamejetAnims {

    public static Animation<Sprite> flamejet_fire_anim;
    public static Animation<Sprite> flamejet_fire_anim2;
    public static String flamejet_fire_atlas_path = "Spells/Flamejet/Flamejet_Fire.atlas";

    public static Animation<Sprite> flamejet_frost_anim;
    public static Animation<Sprite> flamejet_frost_anim2;
    public static String flamejet_frost_atlas_path = "Spells/Flamejet/Flamejet_Frost.atlas";


    public static Animation<Sprite> flamejet_arcane_anim;
    public static Animation<Sprite> flamejet_arcane_anim2;
    public static String flamejet_arcane_atlas_path = "Spells/Flamejet/Flamejet_Arcane.atlas";

    public static Animation<Sprite> flamejet_lightning_anim;
    public static Animation<Sprite> flamejet_lightning_anim2;
    public static String flamejet_lightning_atlas_path = "Spells/Flamejet/Flamejet_Lightning.atlas";


    public static void loadAnimations() {

        TextureAtlas lite_atlas = assetManager.get(flamejet_lightning_atlas_path, TextureAtlas.class);
        Sprite[] lite_frames = new Sprite[75];
        for (int i = 0; i < lite_frames.length; i++) {
            lite_frames[i] = lite_atlas.createSprite("flamejet" + (i+1));
        }
        flamejet_lightning_anim = new Animation<>(0.012f, lite_frames);

        Sprite[] lite_frames2 = new Sprite[75];
        for (int i = 0; i < lite_frames2.length; i++) {
            lite_frames2[i] = lite_atlas.createSprite("flamejetTWO" + (i+1));
        }
        flamejet_lightning_anim2 = new Animation<>(0.012f, lite_frames2);

        TextureAtlas fire_atlas = assetManager.get(flamejet_fire_atlas_path, TextureAtlas.class);
        Sprite[] fire_frames = new Sprite[75];
        for (int i = 0; i < fire_frames.length; i++) {
            fire_frames[i] = fire_atlas.createSprite("flamejet" + (i+1));
        }
        flamejet_fire_anim = new Animation<>(0.012f, fire_frames);

        Sprite[] fire_frames2 = new Sprite[75];
        for (int i = 0; i < fire_frames2.length; i++) {
            fire_frames2[i] = fire_atlas.createSprite("flamejetTWO" + (i+1));
        }
        flamejet_fire_anim2 = new Animation<>(0.012f, fire_frames2);


        TextureAtlas frost_atlas = assetManager.get(flamejet_frost_atlas_path, TextureAtlas.class);
        Sprite[] frost_frames = new Sprite[75];
        for (int i = 0; i < frost_frames.length; i++) {
            frost_frames[i] = frost_atlas.createSprite("flamejet" + (i+1));
        }
        flamejet_frost_anim = new Animation<>(0.012f, frost_frames);

        Sprite[] frost_frames2 = new Sprite[75];
        for (int i = 0; i < frost_frames2.length; i++) {
            frost_frames2[i] = frost_atlas.createSprite("flamejet" + (i+1));
        }
        flamejet_frost_anim2 = new Animation<>(0.012f, frost_frames2);


        TextureAtlas arcane_atlas = assetManager.get(flamejet_arcane_atlas_path, TextureAtlas.class);
        Sprite[] arc_frames = new Sprite[75];
        for (int i = 0; i < arc_frames.length; i++) {
            arc_frames[i] = arcane_atlas.createSprite("flamejet" + (i+1));
        }
        flamejet_arcane_anim = new Animation<>(0.012f, arc_frames);

        Sprite[] arc_frames2 = new Sprite[75];
        for (int i = 0; i < arc_frames2.length; i++) {
            arc_frames2[i] = arcane_atlas.createSprite("flamejet" + (i+1));
        }
        flamejet_arcane_anim2 = new Animation<>(0.012f, arc_frames2);

    }

    public static void loadAtlas() {
        assetManager.load(flamejet_fire_atlas_path, TextureAtlas.class);
        assetManager.load(flamejet_frost_atlas_path, TextureAtlas.class);
        assetManager.load(flamejet_arcane_atlas_path, TextureAtlas.class);
        assetManager.load(flamejet_lightning_atlas_path, TextureAtlas.class);
    }
}
