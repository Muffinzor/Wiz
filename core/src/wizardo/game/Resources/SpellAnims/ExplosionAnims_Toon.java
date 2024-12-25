package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.assetManager;

public class ExplosionsAnims {

    public static Animation<Sprite> explosion1_anim_fire;
    public static Animation<Sprite> explosion2_anim_fire;
    public static String atlas_path_fire = "Spells/Explosions/Explosions_Fire.atlas";
    public static Animation<Sprite> explosion1_anim_frost;
    public static Animation<Sprite> explosion2_anim_frost;
    public static String atlas_path_frost = "Spells/Explosions/Explosions_Frost.atlas";
    public static Animation<Sprite> explosion1_anim_arcane;
    public static Animation<Sprite> explosion2_anim_arcane;
    public static String atlas_path_arcane = "Spells/Explosions/Explosions_Arcane.atlas";
    public static Animation<Sprite> explosion1_anim_lightning;
    public static Animation<Sprite> explosion2_anim_lightning;
    public static String atlas_path_lightning = "Spells/Explosions/Explosions_Lightning.atlas";

    public static void loadAnimations() {

        float frameDuration = 0.012f;

        TextureAtlas fire = assetManager.get(atlas_path_fire, TextureAtlas.class);
        TextureAtlas frost = assetManager.get(atlas_path_frost, TextureAtlas.class);
        TextureAtlas arcane = assetManager.get(atlas_path_arcane, TextureAtlas.class);
        TextureAtlas lightning = assetManager.get(atlas_path_lightning, TextureAtlas.class);

        Sprite[] lite_frames1 = new Sprite[63];
        for (int i = 0; i < lite_frames1.length; i++) {
            lite_frames1[i] = lightning.createSprite("explosion" + (i+1));
        }
        explosion1_anim_lightning = new Animation<>(frameDuration, lite_frames1);

        Sprite[] lite_frames2 = new Sprite[63];
        for (int i = 0; i < lite_frames2.length ; i++) {
            lite_frames2[i] = lightning.createSprite("explosionTWO" + (i + 1));
        }
        explosion2_anim_lightning = new Animation<>(frameDuration, lite_frames2);

        Sprite[] fire_frames1 = new Sprite[63];
        for (int i = 0; i < fire_frames1.length; i++) {
            fire_frames1[i] = fire.createSprite("explosion" + (i+1));
        }
        explosion1_anim_fire = new Animation<>(frameDuration, fire_frames1);

        Sprite[] fire_frames2 = new Sprite[63];

        for (int i = 0; i < fire_frames2.length ; i++) {
            fire_frames2[i] = fire.createSprite("explosionTWO" + (i + 1));
        }
        explosion2_anim_fire = new Animation<>(frameDuration, fire_frames2);


        Sprite[] frost_frames1 = new Sprite[63];
        for (int i = 0; i < frost_frames1.length; i++) {
            frost_frames1[i] = frost.createSprite("explosion" + (i+1));
        }
        explosion1_anim_frost = new Animation<>(frameDuration, frost_frames1);

        Sprite[] frost_frames2 = new Sprite[63];

        for (int i = 0; i < frost_frames2.length ; i++) {
            frost_frames2[i] = frost.createSprite("explosionTWO" + (i + 1));
        }
        explosion2_anim_frost = new Animation<>(frameDuration, frost_frames2);


        Sprite[] arcane_frames1 = new Sprite[63];
        for (int i = 0; i < arcane_frames1.length; i++) {
            arcane_frames1[i] = arcane.createSprite("explosion" + (i+1));
        }
        explosion1_anim_arcane = new Animation<>(frameDuration, arcane_frames1);

        Sprite[] arcane_frames2 = new Sprite[63];
        for (int i = 0; i < arcane_frames2.length ; i++) {
            arcane_frames2[i] = arcane.createSprite("explosionTWO" + (i + 1));
        }
        explosion2_anim_arcane = new Animation<>(frameDuration, arcane_frames2);

    }

    public static void loadAtlas() {
        assetManager.load(atlas_path_fire, TextureAtlas.class);
        assetManager.load(atlas_path_frost, TextureAtlas.class);
        assetManager.load(atlas_path_arcane, TextureAtlas.class);
        assetManager.load(atlas_path_lightning, TextureAtlas.class);
    }

    public static Animation<Sprite> getExplosionAnim(SpellUtils.Spell_Element element) {
        switch(element) {
            case ARCANE -> {
                if(MathUtils.randomBoolean()) {
                    return explosion1_anim_arcane;
                } else {
                    return explosion2_anim_arcane;
                }
            }
            case FROST -> {
                if(MathUtils.randomBoolean()) {
                    return explosion1_anim_frost;
                } else {
                    return explosion2_anim_frost;
                }
            }
            case FIRE -> {
                if(MathUtils.randomBoolean()) {
                    return explosion1_anim_fire;
                } else {
                    return explosion2_anim_fire;
                }
            }
            case LIGHTNING -> {
                if(MathUtils.randomBoolean()) {
                    return explosion1_anim_lightning;
                } else {
                    return explosion2_anim_lightning;
                }
            }
        }
        return null;
    }
}
