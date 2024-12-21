package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class MeteorAnims {

    public static Animation<Sprite> meteor_anim_lightning;
    public static String meteor_path_lightning = "Spells/MeteorShower/meteor_lightning.atlas";

    public static Animation<Sprite> meteor_anim_fire;
    public static String meteor_path_fire = "Spells/MeteorShower/meteor_fire.atlas";

    public static Animation<Sprite> meteor_crater_anim;
    public static String meteor_crater_path = "Spells/MeteorShower/Fire_Impact.atlas";

    public static void loadAnimations() {
        TextureAtlas crater_atlas = assetManager.get(meteor_crater_path, TextureAtlas.class);
        Sprite[] crater_frames = new Sprite[38];
        for (int i = 0; i < crater_frames.length; i++) {
            crater_frames[i] = crater_atlas.createSprite("fire_impact" + (i+1));
        }
        meteor_crater_anim = new Animation<>(0.04f, crater_frames);

        TextureAtlas fire_atlas = assetManager.get(meteor_path_fire, TextureAtlas.class);
        Sprite[] fire_frames = new Sprite[7];
        for (int i = 0; i < fire_frames.length; i++) {
            fire_frames[i] = fire_atlas.createSprite("small" + (i+1));
        }
        meteor_anim_fire = new Animation<>(0.04f, fire_frames);


        TextureAtlas lite_atlas = assetManager.get(meteor_path_lightning, TextureAtlas.class);
        Sprite[] lite_frames = new Sprite[7];
        for (int i = 0; i < lite_frames.length; i++) {
            lite_frames[i] = lite_atlas.createSprite("small" + (i+1));
        }
        meteor_anim_lightning = new Animation<>(0.04f, lite_frames);



    }

    public static void loadAtlas() {
        assetManager.load(meteor_path_lightning, TextureAtlas.class);
        assetManager.load(meteor_path_fire, TextureAtlas.class);
        assetManager.load(meteor_crater_path, TextureAtlas.class);
    }
}
