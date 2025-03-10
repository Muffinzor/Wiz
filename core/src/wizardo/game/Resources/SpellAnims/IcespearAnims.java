package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class IcespearAnims {

    public static Animation<Sprite> icespear_anim_frost;
    public static Animation<Sprite> icespear_hit_anim_frost;
    public static String icespear_atlas_path_frost = "Spells/Icespear/Icespear_Frost.atlas";

    public static Animation<Sprite> icespear_anim_fire;
    public static Animation<Sprite> icespear_hit_anim_fire;
    public static String icespear_atlas_path_fire = "Spells/Icespear/Icespear_Fire.atlas";


    public static Animation<Sprite> icespear_anim_arcane;
    public static Animation<Sprite> icespear_hit_anim_arcane;
    public static String icespear_atlas_path_arcane = "Spells/Icespear/Icespear_Arcane.atlas";


    public static Animation<Sprite> icespear_anim_lightning;
    public static Animation<Sprite> icespear_hit_anim_lightning;
    public static String icespear_atlas_path_lightning = "Spells/Icespear/Icespear_Lightning.atlas";

    public static Animation<Sprite> icespear_anim_coldlite;
    public static Animation<Sprite> icespear_hit_anim_coldlite;
    public static String icespear_atlas_path_coldlite = "Spells/Icespear/Icespear_Coldlite.atlas";

    public static Animation<Sprite> icespear_break;
    public static String icespear_break_path = "Spells/Icespear/icespear_break.atlas";

    public static void loadAnimations() {
        TextureAtlas IceSpearHit = assetManager.get(icespear_break_path, TextureAtlas.class);

        Sprite[] IceSpearHitFrames = new Sprite[54];
        for (int i = 0; i < IceSpearHitFrames.length; i++) {
            IceSpearHitFrames[i] = IceSpearHit.createSprite("icespearHit" + (i+1));
            IceSpearHitFrames[i].setScale(0.15f);
        }
        icespear_break = new Animation<>(0.008f, IceSpearHitFrames);

        TextureAtlas coldlite_atlas = assetManager.get(icespear_atlas_path_coldlite, TextureAtlas.class);

        Sprite[] coldlite_frames = new Sprite[30];
        for (int i = 0; i < coldlite_frames.length; i++) {
            coldlite_frames[i] = coldlite_atlas.createSprite("spear" + (i+1));
        }
        icespear_anim_coldlite = new Animation<>(0.1f, coldlite_frames);

        Sprite[] coldlitehit_frames = new Sprite[16];
        for (int i = 0; i < coldlitehit_frames.length; i++) {
            coldlitehit_frames[i] = coldlite_atlas.createSprite("hit" + (i+1));
        }
        icespear_hit_anim_coldlite = new Animation<>(0.015f, coldlitehit_frames);

        TextureAtlas lite_atlas = assetManager.get(icespear_atlas_path_lightning, TextureAtlas.class);

        Sprite[] lite_frames = new Sprite[30];
        for (int i = 0; i < lite_frames.length; i++) {
            lite_frames[i] = lite_atlas.createSprite("spear" + (i+1));
        }
        icespear_anim_lightning = new Animation<>(0.1f, lite_frames);

        Sprite[] litehit_frames = new Sprite[16];
        for (int i = 0; i < litehit_frames.length; i++) {
            litehit_frames[i] = lite_atlas.createSprite("hit" + (i+1));
        }
        icespear_hit_anim_lightning = new Animation<>(0.015f, litehit_frames);


        TextureAtlas atlas = assetManager.get(icespear_atlas_path_frost, TextureAtlas.class);

        Sprite[] spear_frames = new Sprite[30];
        for (int i = 0; i < spear_frames.length; i++) {
            spear_frames[i] = atlas.createSprite("spear" + (i+1));
        }
        icespear_anim_frost = new Animation<>(0.015f, spear_frames);

        Sprite[] hit_frames = new Sprite[16];
        for (int i = 0; i < hit_frames.length; i++) {
            hit_frames[i] = atlas.createSprite("hit" + (i+1));
        }
        icespear_hit_anim_frost = new Animation<>(0.015f, hit_frames);



        TextureAtlas fire_atlas = assetManager.get(icespear_atlas_path_fire, TextureAtlas.class);

        Sprite[] fire_frames = new Sprite[30];
        for (int i = 0; i < fire_frames.length; i++) {
            fire_frames[i] = fire_atlas.createSprite("spear" + (i+1));
        }
        icespear_anim_fire = new Animation<>(0.015f, fire_frames);

        Sprite[] firehit_frames = new Sprite[16];
        for (int i = 0; i < firehit_frames.length; i++) {
            firehit_frames[i] = fire_atlas.createSprite("hit" + (i+1));
        }
        icespear_hit_anim_fire = new Animation<>(0.015f, firehit_frames);



        TextureAtlas arcane_atlas = assetManager.get(icespear_atlas_path_arcane, TextureAtlas.class);

        Sprite[] arcane_frames = new Sprite[30];
        for (int i = 0; i < arcane_frames.length; i++) {
            arcane_frames[i] = arcane_atlas.createSprite("spear" + (i+1));
        }
        icespear_anim_arcane = new Animation<>(0.015f, arcane_frames);

        Sprite[] arcanehit_frames = new Sprite[16];
        for (int i = 0; i < arcanehit_frames.length; i++) {
            arcanehit_frames[i] = arcane_atlas.createSprite("hit" + (i+1));
        }
        icespear_hit_anim_arcane = new Animation<>(0.015f, arcanehit_frames);
    }

    public static void loadAtlas() {
        assetManager.load(icespear_atlas_path_frost, TextureAtlas.class);
        assetManager.load(icespear_atlas_path_fire, TextureAtlas.class);
        assetManager.load(icespear_atlas_path_arcane, TextureAtlas.class);
        assetManager.load(icespear_atlas_path_lightning, TextureAtlas.class);
        assetManager.load(icespear_break_path, TextureAtlas.class);
        assetManager.load(icespear_atlas_path_coldlite, TextureAtlas.class);
    }
}
