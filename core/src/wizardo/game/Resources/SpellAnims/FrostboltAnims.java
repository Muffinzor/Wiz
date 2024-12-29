package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.assetManager;

public class FrostboltAnims {

    public static String frostbolt_projectile_frost_path = "Spells/Frostbolt/Frostbolt_Projectile.atlas";
    public static Animation<Sprite> frostbolt_projectile_anim_frost;
    public static Animation<Sprite> frostbolt_explosion_anim_frost;
    public static Animation<Sprite> frostbolt_explosion_anim_frost2;
    public static String frostbolt_atlas_path_frost = "Spells/Frostbolt/Frost_Explosion.atlas";

    public static String frostbolt_projectile_lightning_path = "Spells/Frostbolt/LightningBolt.atlas";
    public static Animation<Sprite> frostbolt_projectile_anim_lightning;
    public static Animation<Sprite> frostbolt_explosion_anim_lightning;
    public static Animation<Sprite> frostbolt_explosion_anim_lightning2;
    public static String frostbolt_atlas_path_lightning = "Spells/Frostbolt/FrostLite_Explosion.atlas";


    public static Animation<Sprite> frostbolt_explosion_anim_fire1;
    public static Animation<Sprite> frostbolt_explosion_anim_fire2;
    public static String frostbolt_atlas_path_fire1 = "Spells/Frostbolt/fire_explosion1.atlas";
    public static String frostbolt_atlas_path_fire2 = "Spells/Frostbolt/fire_explosion2.atlas";


    public static void loadAnimations() {
        TextureAtlas proj_atlas = assetManager.get(frostbolt_projectile_frost_path, TextureAtlas.class);
        TextureAtlas atlas = assetManager.get(frostbolt_atlas_path_frost, TextureAtlas.class);
        TextureAtlas lightning_atlas = assetManager.get(frostbolt_atlas_path_lightning, TextureAtlas.class);
        TextureAtlas liteproj_atlas = assetManager.get(frostbolt_projectile_lightning_path, TextureAtlas.class);
        TextureAtlas fire_atlas1 = assetManager.get(frostbolt_atlas_path_fire1, TextureAtlas.class);
        TextureAtlas fire_atlas2 = assetManager.get(frostbolt_atlas_path_fire2, TextureAtlas.class);

        Sprite[] bolt_frames = new Sprite[20];
        for (int i = 0; i < bolt_frames.length; i++) {
            bolt_frames[i] = proj_atlas.createSprite("projectile" + (i+1));
        }
        frostbolt_projectile_anim_frost = new Animation<>(0.03f, bolt_frames);


        /** FROST **/
        Sprite[] explosion_frames = new Sprite[60];
        for (int i = 0; i < explosion_frames.length; i++) {
            explosion_frames[i] = atlas.createSprite("explosionONE" + (i+1));
        }
        frostbolt_explosion_anim_frost = new Animation<>(0.02f, explosion_frames);
        Sprite[] explosion_frames2 = new Sprite[60];
        for (int i = 0; i < explosion_frames2.length; i++) {
            explosion_frames2[i] = atlas.createSprite("explosionTWO" + (i+1));
        }
        frostbolt_explosion_anim_frost2 = new Animation<>(0.02f, explosion_frames2);


        /** LIGHTNING **/
        Sprite[] litebolt_frames = new Sprite[120];
        for (int i = 0; i < litebolt_frames.length; i++) {
            litebolt_frames[i] = liteproj_atlas.createSprite("bolt" + (i+1));
        }
        frostbolt_projectile_anim_lightning = new Animation<>(0.02f, litebolt_frames);

        Sprite[] explosion_frames_lightning = new Sprite[60];
        for (int i = 0; i < explosion_frames_lightning.length; i++) {
            explosion_frames_lightning[i] = lightning_atlas.createSprite("explosionONE" + (i+1));
        }
        frostbolt_explosion_anim_lightning = new Animation<>(0.02f, explosion_frames_lightning);
        Sprite[] explosion_frames_lightning2 = new Sprite[60];
        for (int i = 0; i < explosion_frames_lightning2.length; i++) {
            explosion_frames_lightning2[i] = lightning_atlas.createSprite("explosionTWO" + (i+1));
        }
        frostbolt_explosion_anim_lightning2 = new Animation<>(0.02f, explosion_frames_lightning2);


        Sprite[] fire_frames1 = new Sprite[63];
        for (int i = 0; i < fire_frames1.length; i++) {
            fire_frames1[i] = fire_atlas1.createSprite("FrostExplosionOne" +(i+1));
        }
        frostbolt_explosion_anim_fire1 = new Animation<>(0.015f, fire_frames1);

        Sprite[] fire_frames2 = new Sprite[63];
        for (int i = 0; i < fire_frames2.length; i++) {
            fire_frames2[i] = fire_atlas2.createSprite("FrostExplosionTwo" +(i+1));
        }
        frostbolt_explosion_anim_fire2 = new Animation<>(0.015f, fire_frames2);


    }
    public static void loadAtlas() {
        assetManager.load(frostbolt_projectile_frost_path, TextureAtlas.class);
        assetManager.load(frostbolt_atlas_path_frost, TextureAtlas.class);
        assetManager.load(frostbolt_atlas_path_lightning, TextureAtlas.class);
        assetManager.load(frostbolt_atlas_path_fire1, TextureAtlas.class);
        assetManager.load(frostbolt_atlas_path_fire2, TextureAtlas.class);
        assetManager.load(frostbolt_projectile_lightning_path, TextureAtlas.class);

    }

    public static Animation<Sprite> getAnim(SpellUtils.Spell_Element anim_element) {
        switch(anim_element) {
            case FROST -> {
                if(MathUtils.randomBoolean()) {
                    return frostbolt_explosion_anim_frost;
                } else {
                    return frostbolt_explosion_anim_frost2;
                }
            }
            case COLDLITE -> {
                if(MathUtils.randomBoolean()) {
                    return frostbolt_explosion_anim_lightning;
                } else {
                    return frostbolt_explosion_anim_lightning;
                }
            }
        }
        return null;
    }
}
