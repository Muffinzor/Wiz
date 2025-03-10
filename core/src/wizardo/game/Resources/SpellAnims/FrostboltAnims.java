package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.assetManager;

public class FrostboltAnims {

    public static String frostbolt_projectile_arcane_path = "Spells/Frostbolt/Frostbolt_Projectile_Arcane.atlas";
    public static Animation<Sprite> frostbolt_projectile_anim_arcane;
    public static Animation<Sprite> frostbolt_explosion_anim_arcane;
    public static Animation<Sprite> frostbolt_explosion_anim_arcane2;
    public static String frostbolt_atlas_path_arcane = "Spells/Frostbolt/arcane_explosion.atlas";

    public static String frostbolt_projectile_frost_path = "Spells/Frostbolt/Frostbolt_Projectile.atlas";
    public static Animation<Sprite> frostbolt_projectile_anim_frost;
    public static Animation<Sprite> frostbolt_explosion_anim_frost;
    public static Animation<Sprite> frostbolt_explosion_anim_frost2;
    public static String frostbolt_atlas_path_frost = "Spells/Frostbolt/Frost_Explosion.atlas";

    public static String frostbolt_projectile_lightning_path = "Spells/Frostbolt/LightningBolt.atlas";
    public static Animation<Sprite> frostbolt_projectile_anim_lightning;
    public static Animation<Sprite> frostbolt_explosion_anim_lightning;
    public static Animation<Sprite> frostbolt_explosion_anim_lightning2;
    public static String frostbolt_atlas_path_lightning = "Spells/Frostbolt/lightning_explosion.atlas";

    public static Animation<Sprite> frostbolt_explosion_anim_coldlite;
    public static Animation<Sprite> frostbolt_explosion_anim_coldlite2;
    public static String frostbolt_atlas_path_coldlite = "Spells/Frostbolt/coldlite_explosion.atlas";

    public static String frostbolt_projectile_fire_path = "Spells/Frostbolt/Frostbolt_Projectile_Fire.atlas";
    public static Animation<Sprite> frostbolt_projectile_anim_fire;


    public static void loadAnimations() {
        TextureAtlas proj_atlas = assetManager.get(frostbolt_projectile_frost_path, TextureAtlas.class);
        TextureAtlas fire_proj_atlas = assetManager.get(frostbolt_projectile_fire_path, TextureAtlas.class);
        TextureAtlas atlas = assetManager.get(frostbolt_atlas_path_frost, TextureAtlas.class);
        TextureAtlas lightning_atlas = assetManager.get(frostbolt_atlas_path_lightning, TextureAtlas.class);
        TextureAtlas liteproj_atlas = assetManager.get(frostbolt_projectile_lightning_path, TextureAtlas.class);
        TextureAtlas coldlite_atlas = assetManager.get(frostbolt_atlas_path_coldlite, TextureAtlas.class);
        TextureAtlas arcane_proj_atlas = assetManager.get(frostbolt_projectile_arcane_path, TextureAtlas.class);
        TextureAtlas arcane_atlas = assetManager.get(frostbolt_atlas_path_arcane, TextureAtlas.class);

        /** ARCANE **/
        Sprite[] arcane_frames = new Sprite[60];
        for (int i = 0; i < arcane_frames.length; i++) {
            arcane_frames[i] = arcane_atlas.createSprite("explosionONE" + (i+1));
        }
        frostbolt_explosion_anim_arcane = new Animation<>(0.02f, arcane_frames);
        Sprite[] arcane_frames2 = new Sprite[60];
        for (int i = 0; i < arcane_frames2.length; i++) {
            arcane_frames2[i] = arcane_atlas.createSprite("explosionTWO" + (i+1));
        }
        frostbolt_explosion_anim_arcane2 = new Animation<>(0.02f, arcane_frames2);
        Sprite[] arc_proj_frames = new Sprite[20];
        for (int i = 0; i < arc_proj_frames.length; i++) {
            arc_proj_frames[i] = arcane_proj_atlas.createSprite("projectile" + (i+1));
        }
        frostbolt_projectile_anim_arcane = new Animation<>(0.03f, arc_proj_frames);

        /** COLDLITE **/
        Sprite[] coldlite_frames = new Sprite[60];
        for (int i = 0; i < coldlite_frames.length; i++) {
            coldlite_frames[i] = coldlite_atlas.createSprite("explosionONE" + (i+1));
        }
        frostbolt_explosion_anim_coldlite = new Animation<>(0.02f, coldlite_frames);
        Sprite[] coldlite_frames2 = new Sprite[60];
        for (int i = 0; i < coldlite_frames2.length; i++) {
            coldlite_frames2[i] = coldlite_atlas.createSprite("explosionTWO" + (i+1));
        }
        frostbolt_explosion_anim_coldlite2 = new Animation<>(0.02f, coldlite_frames2);


        Sprite[] bolt_frames = new Sprite[20];
        for (int i = 0; i < bolt_frames.length; i++) {
            bolt_frames[i] = fire_proj_atlas.createSprite("projectile" + (i+1));
        }
        frostbolt_projectile_anim_fire = new Animation<>(0.03f, bolt_frames);


        /** FROST **/
        Sprite[] firebolt_frames = new Sprite[20];
        for (int i = 0; i < firebolt_frames.length; i++) {
            firebolt_frames[i] = proj_atlas.createSprite("projectile" + (i+1));
        }
        frostbolt_projectile_anim_frost = new Animation<>(0.03f, firebolt_frames);
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

    }

    public static void loadAtlas() {
        assetManager.load(frostbolt_projectile_frost_path, TextureAtlas.class);
        assetManager.load(frostbolt_projectile_fire_path, TextureAtlas.class);
        assetManager.load(frostbolt_atlas_path_frost, TextureAtlas.class);
        assetManager.load(frostbolt_atlas_path_lightning, TextureAtlas.class);
        assetManager.load(frostbolt_projectile_lightning_path, TextureAtlas.class);
        assetManager.load(frostbolt_atlas_path_coldlite, TextureAtlas.class);
        assetManager.load(frostbolt_atlas_path_arcane, TextureAtlas.class);
        assetManager.load(frostbolt_projectile_arcane_path, TextureAtlas.class);
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
            case LIGHTNING -> {
                if(MathUtils.randomBoolean()) {
                    return frostbolt_explosion_anim_lightning;
                } else {
                    return frostbolt_explosion_anim_lightning2;
                }
            }
            case ARCANE -> {
                if(MathUtils.randomBoolean()) {
                    return frostbolt_explosion_anim_arcane;
                } else {
                    return frostbolt_explosion_anim_arcane2;
                }
            }
            case COLDLITE -> {
                if(MathUtils.randomBoolean()) {
                    return frostbolt_explosion_anim_coldlite;
                } else {
                    return frostbolt_explosion_anim_coldlite2;
                }
            }
        }
        return null;
    }
}
