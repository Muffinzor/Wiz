package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.assetManager;

public class FireballAnims {

    public static Animation<Sprite> fireball_anim_fire;
    public static Animation<Sprite> fireball_explosion_anim_fire;
    public static Animation<Sprite> fireball_explosion_anim_fire2;
    public static String fireball_fire_atlas_path = "Spells/Fireball/Fireball_Fire.atlas";
    public static String fireball_fireExplosion_atlas_path = "Spells/Fireball/Explosion_Fire.atlas";

    public static Animation<Sprite> fireball_anim_frost;
    public static Animation<Sprite> fireball_explosion_anim_frost;
    public static String fireball_frost_atlas_path = "Spells/Fireball/Fireball_Frost.atlas";
    public static String fireball_frostExplosion_atlas_path = "Spells/Fireball/ToonFrost_Explosion.atlas";

    public static Animation<Sprite> fireball_anim_lightning;
    public static Animation<Sprite> fireball_explosion_anim_lightning;
    public static Animation<Sprite> fireball_explosion_anim_lightning2;
    public static String fireball_lightning_atlas_path = "Spells/Fireball/Fireball_Lightning.atlas";
    public static String fireball_lightningExplosion_atlas_path = "Spells/Fireball/ToonLightning_Explosion.atlas";

    public static Animation<Sprite> fireball_anim_arcane;
    public static Animation<Sprite> fireball_explosion_anim_arcane;
    public static String fireball_arcane_atlas_path = "Spells/Fireball/Fireball_Arcane.atlas";
    public static String fireball_arcaneExplosion_atlas_path = "Spells/Fireball/ToonArcane_Explosion.atlas";


    public static Animation<Sprite> fireball_explosion_anim_firelite;
    public static Animation<Sprite> fireball_explosion_anim_firelite2;
    public static String fireball_fireliteExplosion_atlas_path = "Spells/Fireball/ToonFirelite_Explosion.atlas";

    public static void loadAnimations() {
        /** FIRELITE **/
        TextureAtlas firelite_explosion_atlas = assetManager.get(fireball_fireliteExplosion_atlas_path, TextureAtlas.class);

        Sprite[] fireliteExplosion_frames = new Sprite[60];
        for (int i = 0; i < fireliteExplosion_frames.length; i++) {
            fireliteExplosion_frames[i] = firelite_explosion_atlas.createSprite("explosionONE" + (i+1));
        }
        fireball_explosion_anim_firelite = new Animation<>(0.022f, fireliteExplosion_frames);
        Sprite[] fireliteExplosion_frames2 = new Sprite[60];
        for (int i = 0; i < fireliteExplosion_frames2.length; i++) {
            fireliteExplosion_frames2[i] = firelite_explosion_atlas.createSprite("explosionTWO" + (i+1));
        }
        fireball_explosion_anim_firelite2 = new Animation<>(0.022f, fireliteExplosion_frames2);

        /** ARCANE */
        TextureAtlas arc_proj_atlas = assetManager.get(fireball_arcane_atlas_path, TextureAtlas.class);
        TextureAtlas arc_explosion_atlas = assetManager.get(fireball_arcaneExplosion_atlas_path, TextureAtlas.class);

        Sprite[] arcProj_frames = new Sprite[60];
        for (int i = 0; i < arcProj_frames.length; i++) {
            arcProj_frames[i] = arc_proj_atlas.createSprite("fireball" + (i+1));
        }
        fireball_anim_arcane = new Animation<>(0.03f, arcProj_frames);

        Sprite[] arcExplosion_frames = new Sprite[45];
        for (int i = 0; i < arcExplosion_frames.length; i++) {
            arcExplosion_frames[i] = arc_explosion_atlas.createSprite("explosion" + (i+1));
        }
        fireball_explosion_anim_arcane = new Animation<>(0.02f, arcExplosion_frames);


        /** LIGHTNING **/
        TextureAtlas lite_proj_atlas = assetManager.get(fireball_lightning_atlas_path, TextureAtlas.class);
        TextureAtlas lite_explosion_atlas = assetManager.get(fireball_lightningExplosion_atlas_path, TextureAtlas.class);
        Sprite[] liteProj_frames = new Sprite[60];
        for (int i = 0; i < liteProj_frames.length; i++) {
            liteProj_frames[i] = lite_proj_atlas.createSprite("fireball" + (i+1));
        }
        fireball_anim_lightning = new Animation<>(0.03f, liteProj_frames);

        Sprite[] liteExplosion_frames = new Sprite[60];
        for (int i = 0; i < liteExplosion_frames.length; i++) {
            liteExplosion_frames[i] = lite_explosion_atlas.createSprite("explosionONE" + (i+1));
        }
        fireball_explosion_anim_lightning = new Animation<>(0.022f, liteExplosion_frames);
        Sprite[] liteExplosion_frames2 = new Sprite[60];
        for (int i = 0; i < liteExplosion_frames2.length; i++) {
            liteExplosion_frames2[i] = lite_explosion_atlas.createSprite("explosionTWO" + (i+1));
        }
        fireball_explosion_anim_lightning2 = new Animation<>(0.022f, liteExplosion_frames2);

        /** FIRE **/
        TextureAtlas projectile_atlas = assetManager.get(fireball_fire_atlas_path, TextureAtlas.class);
        TextureAtlas fire_atlas = assetManager.get(fireball_fireExplosion_atlas_path, TextureAtlas.class);
        Sprite[] fireProj_frames = new Sprite[60];
        for (int i = 0; i < fireProj_frames.length; i++) {
            fireProj_frames[i] = projectile_atlas.createSprite("fireball" + (i+1));
        }
        fireball_anim_fire = new Animation<>(0.03f, fireProj_frames);

        Sprite[] fireExplosion_frames = new Sprite[60];
        for (int i = 0; i < fireExplosion_frames.length; i++) {
            fireExplosion_frames[i] = fire_atlas.createSprite("explosionONE" + (i+1));
        }
        fireball_explosion_anim_fire = new Animation<>(0.02f, fireExplosion_frames);
        Sprite[] fireExplosion_frames2 = new Sprite[60];
        for (int i = 0; i < fireExplosion_frames2.length; i++) {
            fireExplosion_frames2[i] = fire_atlas.createSprite("explosionTWO" + (i+1));
        }
        fireball_explosion_anim_fire2 = new Animation<>(0.02f, fireExplosion_frames2);


        TextureAtlas frost_projectile_atlas = assetManager.get(fireball_frost_atlas_path, TextureAtlas.class);
        TextureAtlas frost_atlas = assetManager.get(fireball_frostExplosion_atlas_path, TextureAtlas.class);

        Sprite[] frostProj_frames = new Sprite[60];
        for (int i = 0; i < frostProj_frames.length; i++) {
            frostProj_frames[i] = frost_projectile_atlas.createSprite("fireball" + (i+1));
        }
        fireball_anim_frost = new Animation<>(0.02f, frostProj_frames);

        Sprite[] frostExplosion_frames = new Sprite[60];
        for (int i = 0; i < frostExplosion_frames.length; i++) {
            frostExplosion_frames[i] = frost_atlas.createSprite("explosion" + (i+1));
        }
        fireball_explosion_anim_frost = new Animation<>(0.02f, frostExplosion_frames);

    }

    public static void loadAtlas() {
        assetManager.load(fireball_fire_atlas_path, TextureAtlas.class);
        assetManager.load(fireball_fireExplosion_atlas_path, TextureAtlas.class);

        assetManager.load(fireball_frost_atlas_path, TextureAtlas.class);
        assetManager.load(fireball_frostExplosion_atlas_path, TextureAtlas.class);

        assetManager.load(fireball_lightning_atlas_path, TextureAtlas.class);
        assetManager.load(fireball_lightningExplosion_atlas_path, TextureAtlas.class);

        assetManager.load(fireball_arcane_atlas_path, TextureAtlas.class);
        assetManager.load(fireball_arcaneExplosion_atlas_path, TextureAtlas.class);

        assetManager.load(fireball_fireliteExplosion_atlas_path, TextureAtlas.class);
    }

    public static Animation<Sprite> getAnim(SpellUtils.Spell_Element anim_element) {
        switch(anim_element) {
            case LIGHTNING -> {
                if(MathUtils.randomBoolean()) {
                    return fireball_explosion_anim_lightning;
                } else {
                    return fireball_explosion_anim_lightning2;
                }
            }
            case FIRE -> {
                if(MathUtils.randomBoolean()) {
                    return fireball_explosion_anim_fire;
                } else {
                    return fireball_explosion_anim_fire2;
                }
            }
            case FIRELITE -> {
                if(MathUtils.randomBoolean()) {
                    return fireball_explosion_anim_firelite;
                } else {
                    return fireball_explosion_anim_firelite2;
                }
            }
            case FROST -> {
                return fireball_explosion_anim_frost;
            }
            case ARCANE -> {
                return fireball_explosion_anim_arcane;
            }
        }

        return null;
    }

}
