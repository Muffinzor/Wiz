package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class OverheatAnims {


    public static Animation<Sprite> overheat_anim_fire;
    public static String overheat_atlas_path_fire = "Spells/Overheat/fire.atlas";
    public static Animation<Sprite> overheat_anim_frost;
    public static String overheat_atlas_path_frost = "Spells/Overheat/frost.atlas";
    public static Animation<Sprite> overheat_anim_lightning;
    public static String overheat_atlas_path_lightning = "Spells/Overheat/lightning.atlas";


    /** Mini Fireball anims */
    public static Animation<Sprite> minifireball_anim_fire;
    public static String minifireball_atlas_path_fire = "Spells/Overheat/mini_fireball/miniFire_fireball.atlas";
    public static Animation<Sprite> minifireball_anim_frost;
    public static String minifireball_atlas_path_frost = "Spells/Overheat/mini_fireball/miniFrost_fireball.atlas";


    public static void loadAnimations() {

        TextureAtlas fire = assetManager.get(overheat_atlas_path_fire, TextureAtlas.class);
        TextureAtlas frost = assetManager.get(overheat_atlas_path_frost, TextureAtlas.class);
        TextureAtlas lite = assetManager.get(overheat_atlas_path_lightning, TextureAtlas.class);

        Sprite[] lite_frames = new Sprite[32];
        for (int i = 0; i < lite_frames.length; i++) {
            lite_frames[i] = lite.createSprite("litenova" + (i+1));
        }
        overheat_anim_lightning = new Animation<>(0.012f, lite_frames);


        Sprite[] fire_frames = new Sprite[51];
        for (int i = 0; i < fire_frames.length; i++) {
            fire_frames[i] = fire.createSprite("overheat" + (i+1));
        }
        overheat_anim_fire = new Animation<>(0.015f, fire_frames);

        Sprite[] frost_frames = new Sprite[51];

        for (int i = 0; i < frost_frames.length ; i++) {
            frost_frames[i] = frost.createSprite("overheat" + (i + 1));
        }
        overheat_anim_frost = new Animation<>(0.015f, frost_frames);


        Sprite[] mini_fire_frames = new Sprite[60];
        TextureAtlas miniAtlas5 = assetManager.get(minifireball_atlas_path_fire, TextureAtlas.class);
        for (int i = 0; i < mini_fire_frames.length; i++) {
            mini_fire_frames[i] = miniAtlas5.createSprite("mini" + (i+1));
        }
        minifireball_anim_fire = new Animation<>(0.015f, mini_fire_frames);

        Sprite[] mini_frost_frames = new Sprite[60];
        TextureAtlas miniAtlas4 = assetManager.get(minifireball_atlas_path_frost, TextureAtlas.class);
        for (int i = 0; i < mini_frost_frames.length; i++) {
            mini_frost_frames[i] = miniAtlas4.createSprite("mini" + (i+1));
        }
        minifireball_anim_frost = new Animation<>(0.015f, mini_frost_frames);

    }

    public static void loadAtlas() {
        assetManager.load(overheat_atlas_path_fire, TextureAtlas.class);
        assetManager.load(overheat_atlas_path_lightning, TextureAtlas.class);
        assetManager.load(overheat_atlas_path_frost, TextureAtlas.class);
        assetManager.load(minifireball_atlas_path_fire, TextureAtlas.class);
        assetManager.load(minifireball_atlas_path_frost, TextureAtlas.class);

    }
}
