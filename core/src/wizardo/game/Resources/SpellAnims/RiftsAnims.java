package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.assetManager;

public class RiftsAnims {

    public static Animation<Sprite> rift_zone_anim_arcane;
    public static Animation<Sprite> rift_explosion_anim_arcane;
    public static Animation<Sprite> rift_explosion_anim_arcane2;
    public static String rift_zone_atlas_path_arcane = "Spells/Rifts/Arcane/zone_arcane.atlas";
    public static String rift_explosion_atlas_path_arcane = "Spells/Rifts/Arcane/RiftExplosion_Arcane.atlas";

    public static Animation<Sprite> rift_zone_anim_frost;
    public static Animation<Sprite> rift_explosion_anim_frost;
    public static Animation<Sprite> rift_explosion_anim_frost2;
    public static String rift_zone_atlas_path_frost = "Spells/Rifts/Frost/zone_frost.atlas";
    public static String rift_explosion_atlas_path_frost = "Spells/Rifts/Frost/RiftExplosion_Frost.atlas";

    public static Animation<Sprite> rift_zone_anim_lightning;
    public static Animation<Sprite> rift_explosion_anim_lite;
    public static Animation<Sprite> rift_explosion_anim_lite2;
    public static String rift_zone_atlas_path_lightning = "Spells/Rifts/Lightning/zone_lightning.atlas";
    public static String rift_explosion_atlas_path_lightning = "Spells/Rifts/Lightning/RiftExplosion_Lightning.atlas";

    public static Animation<Sprite> rift_zone_anim_fire;
    public static Animation<Sprite> rift_explosion_anim_fire;
    public static Animation<Sprite> rift_explosion_anim_fire2;
    public static String rift_zone_atlas_path_fire = "Spells/Rifts/Fire/zone_fire.atlas";
    public static String rift_explosion_atlas_path_fire = "Spells/Rifts/Fire/RiftExplosion_Fire.atlas";

    public static void loadAnimations() {

        /** FIRE **/
        TextureAtlas fire_projectile_atlas = assetManager.get(rift_zone_atlas_path_fire, TextureAtlas.class);
        TextureAtlas fire_explosion_atlas = assetManager.get(rift_explosion_atlas_path_fire, TextureAtlas.class);

        Sprite[] fire_zone = new Sprite[108];
        for (int i = 0; i < fire_zone.length; i++) {
            fire_zone[i] = fire_projectile_atlas.createSprite("rift" + (i+1));
        }
        rift_zone_anim_fire = new Animation<>(0.025f, fire_zone);

        Sprite[] fire_explosion = new Sprite[64];
        for (int i = 0; i < fire_explosion.length; i++) {
            fire_explosion[i] = fire_explosion_atlas.createSprite("riftONE" + (i+1));
        }
        rift_explosion_anim_fire = new Animation<>(0.02f, fire_explosion);
        Sprite[] fire_explosion2 = new Sprite[64];
        for (int i = 0; i < fire_explosion2.length; i++) {
            fire_explosion2[i] = fire_explosion_atlas.createSprite("riftTWO" + (i+1));
        }
        rift_explosion_anim_fire2 = new Animation<>(0.02f, fire_explosion2);

        /** LIGHTNING **/
        TextureAtlas lite_projectile_atlas = assetManager.get(rift_zone_atlas_path_lightning, TextureAtlas.class);
        TextureAtlas lite_explosion_atlas = assetManager.get(rift_explosion_atlas_path_lightning, TextureAtlas.class);
        Sprite[] lite_zone = new Sprite[108];
        for (int i = 0; i < lite_zone.length; i++) {
            lite_zone[i] = lite_projectile_atlas.createSprite("rift" + (i+1));
        }
        rift_zone_anim_lightning = new Animation<>(0.025f, lite_zone);

        Sprite[] lite_explosion = new Sprite[64];
        for (int i = 0; i < lite_explosion.length; i++) {
            lite_explosion[i] = lite_explosion_atlas.createSprite("riftONE" + (i+1));
        }
        rift_explosion_anim_lite = new Animation<>(0.02f, lite_explosion);
        Sprite[] lite_explosion2 = new Sprite[64];
        for (int i = 0; i < lite_explosion2.length; i++) {
            lite_explosion2[i] = lite_explosion_atlas.createSprite("riftTWO" + (i+1));
        }
        rift_explosion_anim_lite2 = new Animation<>(0.02f, lite_explosion2);


        /** ARCANE **/
        TextureAtlas arc_projectile_atlas = assetManager.get(rift_zone_atlas_path_arcane, TextureAtlas.class);
        TextureAtlas arc_explosion_atlas = assetManager.get(rift_explosion_atlas_path_arcane, TextureAtlas.class);

        Sprite[] arc_zone = new Sprite[108];
        for (int i = 0; i < arc_zone.length; i++) {
            arc_zone[i] = arc_projectile_atlas.createSprite("rift" + (i+1));
        }
        rift_zone_anim_arcane = new Animation<>(0.025f, arc_zone);
        Sprite[] arc_explosion = new Sprite[64];
        for (int i = 0; i < arc_explosion.length; i++) {
            arc_explosion[i] = arc_explosion_atlas.createSprite("riftTWO" + (i+1));
        }
        rift_explosion_anim_arcane = new Animation<>(0.02f, arc_explosion);
        Sprite[] arc_explosion2 = new Sprite[64];
        for (int i = 0; i < arc_explosion2.length; i++) {
            arc_explosion2[i] = arc_explosion_atlas.createSprite("riftONE" + (i+1));
        }
        rift_explosion_anim_arcane2 = new Animation<>(0.02f, arc_explosion2);


        TextureAtlas frost_projectile_atlas = assetManager.get(rift_zone_atlas_path_frost, TextureAtlas.class);
        TextureAtlas frost_explosion_atlas = assetManager.get(rift_explosion_atlas_path_frost, TextureAtlas.class);

        Sprite[] frost_zone = new Sprite[108];
        for (int i = 0; i < frost_zone.length; i++) {
            frost_zone[i] = frost_projectile_atlas.createSprite("rift" + (i+1));
        }
        rift_zone_anim_frost = new Animation<>(0.025f, frost_zone);

        Sprite[] frost_explosion = new Sprite[64];
        for (int i = 0; i < frost_explosion.length; i++) {
            frost_explosion[i] = frost_explosion_atlas.createSprite("riftONE" + (i+1));
        }
        rift_explosion_anim_frost = new Animation<>(0.02f, frost_explosion);
        Sprite[] frost_explosion2 = new Sprite[64];
        for (int i = 0; i < frost_explosion2.length; i++) {
            frost_explosion2[i] = frost_explosion_atlas.createSprite("riftTWO" + (i+1));
        }
        rift_explosion_anim_frost2 = new Animation<>(0.02f, frost_explosion2);

    }

    public static void loadAtlas() {
        assetManager.load(rift_zone_atlas_path_arcane, TextureAtlas.class);
        assetManager.load(rift_explosion_atlas_path_arcane, TextureAtlas.class);

        assetManager.load(rift_zone_atlas_path_frost, TextureAtlas.class);
        assetManager.load(rift_explosion_atlas_path_frost, TextureAtlas.class);

        assetManager.load(rift_zone_atlas_path_lightning, TextureAtlas.class);
        assetManager.load(rift_explosion_atlas_path_lightning, TextureAtlas.class);

        assetManager.load(rift_zone_atlas_path_fire, TextureAtlas.class);
        assetManager.load(rift_explosion_atlas_path_fire, TextureAtlas.class);
    }

    public static Animation<Sprite> getExplosionAnim(SpellUtils.Spell_Element anim_element) {
        Animation<Sprite> anim = null;
        switch(anim_element) {
            case ARCANE -> {
                if (MathUtils.randomBoolean()) {
                    anim = rift_explosion_anim_arcane;
                } else {
                    anim = rift_explosion_anim_arcane2;
                }
            }
            case FROST -> {
                if (MathUtils.randomBoolean()) {
                    anim = rift_explosion_anim_frost;
                } else {
                    anim = rift_explosion_anim_frost2;
                }
            }
            case FIRE -> {
                if (MathUtils.randomBoolean()) {
                    anim = rift_explosion_anim_fire;
                } else {
                    anim = rift_explosion_anim_fire2;
                }
            }
            case LIGHTNING -> {
                if (MathUtils.randomBoolean()) {
                    anim = rift_explosion_anim_lite;
                } else {
                    anim = rift_explosion_anim_lite2;
                }
            }
        }
        return anim;
    }
}
