package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.assetManager;

public class ExplosionAnims_Elemental {

    public static Animation<Sprite> frostnova_anim;
    public static String frostnova_path = "Spells/Explosions/ElementalExplosions/FrostNova_Explosion.atlas";

    public static Animation<Sprite> arcaneExplosion_anim;
    public static String arcane_path = "Spells/Explosions/ElementalExplosions/Arcane_Explosion.atlas";

    public static Animation<Sprite> lightningCloud_anim_lightning;
    public static Animation<Sprite> lightningCloud_anim_lightning2;
    public static Animation<Sprite> lightningCloud_anim_arcane;
    public static Animation<Sprite> lightningCloud_anim_arcane2;
    public static Animation<Sprite> lightningCloud_anim_frost;
    public static Animation<Sprite> lightningCloud_anim_frost2;
    public static Animation<Sprite> lightningCloud_anim_fire;
    public static Animation<Sprite> lightningCloud_anim_fire2;
    public static String lightningCloud_path_lightning = "Spells/Explosions/ElementalExplosions/Lightning_Cloud_Lightning.atlas";
    public static String lightningCloud_path_fire = "Spells/Explosions/ElementalExplosions/Lightning_Cloud_Fire.atlas";
    public static String lightningCloud_path_frost = "Spells/Explosions/ElementalExplosions/Lightning_Cloud_Frost.atlas";
    public static String lightningCloud_path_arcane = "Spells/Explosions/ElementalExplosions/Lightning_Cloud_Arcane.atlas";

    public static void loadAnimations() {
        float frameDuration = 0.022f;

        /** LIGHTNING CLOUD **/
        TextureAtlas lite_cloud = assetManager.get(lightningCloud_path_lightning, TextureAtlas.class);
        Sprite[] lite_frames1 = new Sprite[60];
        for (int i = 0; i < lite_frames1.length; i++) {
            lite_frames1[i] = lite_cloud.createSprite("explosion" + (i+1));
        }
        lightningCloud_anim_lightning = new Animation<>(frameDuration, lite_frames1);
        Sprite[] lite_frames2 = new Sprite[60];
        for (int i = 0; i < lite_frames2.length; i++) {
            lite_frames2[i] = lite_cloud.createSprite("explosionTWO" + (i+1));
        }
        lightningCloud_anim_lightning2 = new Animation<>(frameDuration, lite_frames2);

        TextureAtlas arc_cloud = assetManager.get(lightningCloud_path_arcane, TextureAtlas.class);
        Sprite[] arc_frames1 = new Sprite[60];
        for (int i = 0; i < arc_frames1.length; i++) {
            arc_frames1[i] = arc_cloud.createSprite("explosion" + (i+1));
        }
        lightningCloud_anim_arcane = new Animation<>(frameDuration, arc_frames1);
        Sprite[] arc_frames2 = new Sprite[60];
        for (int i = 0; i < arc_frames2.length; i++) {
            arc_frames2[i] = arc_cloud.createSprite("explosionTWO" + (i+1));
        }
        lightningCloud_anim_arcane2 = new Animation<>(frameDuration, arc_frames2);

        TextureAtlas frost_cloud = assetManager.get(lightningCloud_path_frost, TextureAtlas.class);
        Sprite[] frost_frames1 = new Sprite[60];
        for (int i = 0; i < frost_frames1.length; i++) {
            frost_frames1[i] = frost_cloud.createSprite("explosion" + (i+1));
        }
        lightningCloud_anim_frost = new Animation<>(frameDuration, frost_frames1);
        Sprite[] frost_frames2 = new Sprite[60];
        for (int i = 0; i < frost_frames2.length; i++) {
            frost_frames2[i] = frost_cloud.createSprite("explosionTWO" + (i+1));
        }
        lightningCloud_anim_frost2 = new Animation<>(frameDuration, frost_frames2);

        TextureAtlas fire_cloud = assetManager.get(lightningCloud_path_fire, TextureAtlas.class);
        Sprite[] fire_frames1 = new Sprite[60];
        for (int i = 0; i < fire_frames1.length; i++) {
            fire_frames1[i] = fire_cloud.createSprite("explosion" + (i+1));
        }
        lightningCloud_anim_fire = new Animation<>(frameDuration, fire_frames1);
        Sprite[] fire_frames2 = new Sprite[60];
        for (int i = 0; i < fire_frames2.length; i++) {
            fire_frames2[i] = fire_cloud.createSprite("explosionTWO" + (i+1));
        }
        lightningCloud_anim_fire2 = new Animation<>(frameDuration, fire_frames2);



        /** FROST **/
        TextureAtlas frost = assetManager.get(frostnova_path, TextureAtlas.class);
        Sprite[] frostnova_frames1 = new Sprite[80];
        for (int i = 0; i < frostnova_frames1.length; i++) {
            frostnova_frames1[i] = frost.createSprite("explosion" + (i+1));
        }
        frostnova_anim = new Animation<>(frameDuration, frostnova_frames1);

        /** ARCANE **/
        TextureAtlas arcane = assetManager.get(arcane_path, TextureAtlas.class);
        Sprite[] arcaneNova_frames1 = new Sprite[60];
        for (int i = 0; i < arcaneNova_frames1.length; i++) {
            arcaneNova_frames1[i] = arcane.createSprite("explosion" + (i+1));
        }
        arcaneExplosion_anim = new Animation<>(frameDuration, arcaneNova_frames1);


    }

    public static void loadAtlas() {
        assetManager.load(frostnova_path, TextureAtlas.class);
        assetManager.load(arcane_path, TextureAtlas.class);
        assetManager.load(lightningCloud_path_frost, TextureAtlas.class);
        assetManager.load(lightningCloud_path_fire, TextureAtlas.class);
        assetManager.load(lightningCloud_path_arcane, TextureAtlas.class);
        assetManager.load(lightningCloud_path_lightning, TextureAtlas.class);

    }

    public static Animation<Sprite> getExplosionAnim(SpellUtils.Spell_Element element) {
        Animation<Sprite> anim = null;
        switch(element) {
            case FROST -> anim = frostnova_anim;
            case ARCANE -> anim = arcaneExplosion_anim;
            case FIRE -> anim = OverheatAnims.overheat_anim_fire;
        }
        return anim;
    }
    public static Animation<Sprite> getLightningCloud(SpellUtils.Spell_Element element) {
        switch(element) {
            case LIGHTNING -> {
                if(MathUtils.randomBoolean()) {
                    return lightningCloud_anim_lightning;
                } else {
                    return lightningCloud_anim_lightning2;
                }
            }
            case FROST -> {
                if(MathUtils.randomBoolean()) {
                    return lightningCloud_anim_frost;
                } else {
                    return lightningCloud_anim_frost2;
                }
            }
            case ARCANE -> {
                if(MathUtils.randomBoolean()) {
                    return lightningCloud_anim_arcane;
                } else {
                    return lightningCloud_anim_arcane2;
                }
            }
            case FIRE -> {
                if(MathUtils.randomBoolean()) {
                    return lightningCloud_anim_fire;
                } else {
                    return lightningCloud_anim_fire2;
                }
            }

        }
        return null;
    }
}
