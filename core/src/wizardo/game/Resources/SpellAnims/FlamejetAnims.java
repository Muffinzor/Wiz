package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class FlamejetAnims {

    public static Animation<Sprite> flamejet_fire_anim;
    public static Animation<Sprite> flamejet_fire_anim2;
    public static String flamejet_fire_atlas_path = "Spells/Flamejet/fire1.atlas";
    public static String flamejet_fire_atlas_path2 = "Spells/Flamejet/fire2.atlas";


    public static Animation<Sprite> flamejet_frost_anim;
    public static Animation<Sprite> flamejet_frost_anim2;
    public static String flamejet_frost_atlas_path = "Spells/Flamejet/frost1.atlas";
    public static String flamejet_frost_atlas_path2 = "Spells/Flamejet/frost2.atlas";

    public static void loadAnimations() {

        TextureAtlas fire_atlas = assetManager.get(flamejet_fire_atlas_path, TextureAtlas.class);
        TextureAtlas fire_atlas2 = assetManager.get(flamejet_fire_atlas_path2, TextureAtlas.class);

        Sprite[] fire_frames = new Sprite[61];
        for (int i = 0; i < fire_frames.length; i++) {
            fire_frames[i] = fire_atlas.createSprite("firespark" + (i+1));
        }
        flamejet_fire_anim = new Animation<>(0.012f, fire_frames);

        Sprite[] fire_frames2 = new Sprite[56];
        for (int i = 0; i < fire_frames2.length; i++) {
            fire_frames2[i] = fire_atlas2.createSprite("firespark" + (i+1));
        }
        flamejet_fire_anim2 = new Animation<>(0.012f, fire_frames2);


        TextureAtlas frost_atlas = assetManager.get(flamejet_frost_atlas_path, TextureAtlas.class);
        TextureAtlas frost_atlas2 = assetManager.get(flamejet_frost_atlas_path2, TextureAtlas.class);

        Sprite[] frost_frames = new Sprite[61];
        for (int i = 0; i < frost_frames.length; i++) {
            frost_frames[i] = frost_atlas.createSprite("firespark" + (i+1));
        }
        flamejet_frost_anim = new Animation<>(0.012f, frost_frames);

        Sprite[] frost_frames2 = new Sprite[56];
        for (int i = 0; i < frost_frames2.length; i++) {
            frost_frames2[i] = frost_atlas2.createSprite("firespark" + (i+1));
        }
        flamejet_frost_anim2 = new Animation<>(0.012f, frost_frames2);

    }

    public static void loadAtlas() {
        assetManager.load(flamejet_fire_atlas_path, TextureAtlas.class);
        assetManager.load(flamejet_fire_atlas_path2, TextureAtlas.class);
        assetManager.load(flamejet_frost_atlas_path, TextureAtlas.class);
        assetManager.load(flamejet_frost_atlas_path2, TextureAtlas.class);
    }
}
