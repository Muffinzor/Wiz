package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.assetManager;

public class ExplosionAnims_Energy {

    public static Animation<Sprite> energyExplosion_anim_purple;
    public static Animation<Sprite> energyExplosion_anim_purple2;
    public static String atlas_path_purple = "Spells/Explosions/EnergyExplosions/EnergyExplosion_Purple.atlas";

    public static Animation<Sprite> energyExplosion_anim_blue;
    public static Animation<Sprite> energyExplosion_anim_blue2;
    public static String atlas_path_blue = "Spells/Explosions/EnergyExplosions/EnergyExplosion_Blue.atlas";

    public static Animation<Sprite> energyExplosion_anim_orange;
    public static Animation<Sprite> energyExplosion_anim_orange2;
    public static String atlas_path_orange = "Spells/Explosions/EnergyExplosions/EnergyExplosion_Orange.atlas";

    public static Animation<Sprite> energyExplosion_anim_yellow;
    public static Animation<Sprite> energyExplosion_anim_yellow2;
    public static String atlas_path_yellow = "Spells/Explosions/EnergyExplosions/EnergyExplosion_Yellow.atlas";


    public static void loadAnimations() {
        float frameDuration = 0.022f;

        /** ARCANE **/
        TextureAtlas arcane = assetManager.get(atlas_path_purple, TextureAtlas.class);
        Sprite[] arc_frames1 = new Sprite[60];
        for (int i = 0; i < arc_frames1.length; i++) {
            arc_frames1[i] = arcane.createSprite("explosionONE" + (i+1));
        }
        energyExplosion_anim_purple = new Animation<>(frameDuration, arc_frames1);


        /** FROST **/
        TextureAtlas frost = assetManager.get(atlas_path_blue, TextureAtlas.class);
        Sprite[] frost_frames1 = new Sprite[60];
        for (int i = 0; i < frost_frames1.length; i++) {
            frost_frames1[i] = frost.createSprite("explosionONE" + (i+1));
        }
        energyExplosion_anim_blue = new Animation<>(frameDuration, frost_frames1);

        Sprite[] frost_frames2 = new Sprite[60];
        for (int i = 0; i < frost_frames2.length; i++) {
            frost_frames2[i] = frost.createSprite("explosionTWO" + (i+1));
        }
        energyExplosion_anim_blue2 = new Animation<>(frameDuration, frost_frames2);


        /** FIRE **/
        TextureAtlas fire = assetManager.get(atlas_path_orange, TextureAtlas.class);
        Sprite[] fire_frames1 = new Sprite[60];
        for (int i = 0; i < fire_frames1.length; i++) {
            fire_frames1[i] = fire.createSprite("explosionONE" + (i+1));
        }
        energyExplosion_anim_orange = new Animation<>(frameDuration, fire_frames1);

        Sprite[] fire_frames2 = new Sprite[60];
        for (int i = 0; i < fire_frames2.length; i++) {
            fire_frames2[i] = fire.createSprite("explosionTWO" + (i+1));
        }
        energyExplosion_anim_orange2 = new Animation<>(frameDuration, fire_frames2);

        /** LIGHTNING **/
        TextureAtlas lightning = assetManager.get(atlas_path_yellow, TextureAtlas.class);
        Sprite[] lite_frames1 = new Sprite[60];
        for (int i = 0; i < lite_frames1.length; i++) {
            lite_frames1[i] = lightning.createSprite("explosionONE" + (i+1));
        }
        energyExplosion_anim_yellow = new Animation<>(frameDuration, lite_frames1);

        Sprite[] lite_frames2 = new Sprite[60];
        for (int i = 0; i < lite_frames2.length; i++) {
            lite_frames2[i] = lightning.createSprite("explosionTWO" + (i+1));
        }
        energyExplosion_anim_yellow2 = new Animation<>(frameDuration, lite_frames2);




    }

    public static void loadAtlas() {
        assetManager.load(atlas_path_purple, TextureAtlas.class);
        assetManager.load(atlas_path_blue, TextureAtlas.class);
        assetManager.load(atlas_path_orange, TextureAtlas.class);
        assetManager.load(atlas_path_yellow, TextureAtlas.class);
    }

    public static Animation<Sprite> getExplosionAnim(SpellUtils.Spell_Element element) {
        switch(element) {
            case ARCANE -> {
                if(MathUtils.randomBoolean()) {
                    return energyExplosion_anim_purple;
                } else {
                    return energyExplosion_anim_purple;
                }
            }
            case FROST -> {
                if(MathUtils.randomBoolean()) {
                    return energyExplosion_anim_blue;
                } else {
                    return energyExplosion_anim_blue2;
                }
            }
            case FIRE -> {
                if(MathUtils.randomBoolean()) {
                    return energyExplosion_anim_orange;
                } else {
                    return energyExplosion_anim_orange2;
                }
            }
            case LIGHTNING -> {
                if(MathUtils.randomBoolean()) {
                    return energyExplosion_anim_yellow;
                } else {
                    return energyExplosion_anim_yellow2;
                }
            }

        }
        return null;
    }
}
