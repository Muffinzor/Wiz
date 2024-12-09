package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class FireballAnims {

    public static Animation<Sprite> fireball_anim_fire;
    public static Animation<Sprite> fireball_explosion_anim_fire;
    public static String fireball_fire_atlas_path = "Spells/Fireball/Fireball_Projectile.atlas";
    public static String fireball_fireExplosion_atlas_path = "Spells/Fireball/fire_explosion.atlas";

    public static Animation<Sprite> fireball_anim_frost;
    public static Animation<Sprite> fireball_explosion_anim_frost;
    public static String fireball_frost_atlas_path = "Spells/Fireball/frost_projectile.atlas";
    public static String fireball_frostExplosion_atlas_path = "Spells/Fireball/frost_explosion.atlas";

    public static Animation<Sprite> fireball_anim_lightning;
    public static Animation<Sprite> fireball_explosion_anim_lightning;
    public static String fireball_lightning_atlas_path = "Spells/Fireball/lightning_projectile.atlas";
    public static String fireball_lightningExplosion_atlas_path = "Spells/Fireball/lightning_explosion.atlas";

    public static Animation<Sprite> fireball_anim_arcane;
    public static Animation<Sprite> fireball_explosion_anim_arcane;
    public static String fireball_arcane_atlas_path = "Spells/Fireball/arcane_projectile.atlas";
    public static String fireball_arcaneExplosion_atlas_path = "Spells/Fireball/arcane_explosion.atlas";

    public static void loadAnimations() {
        TextureAtlas arc_proj_atlas = assetManager.get(fireball_arcane_atlas_path, TextureAtlas.class);
        TextureAtlas arc_explosion_atlas = assetManager.get(fireball_arcaneExplosion_atlas_path, TextureAtlas.class);

        Sprite[] arcProj_frames = new Sprite[6];
        for (int i = 0; i < arcProj_frames.length; i++) {
            arcProj_frames[i] = arc_proj_atlas.createSprite("Fireball" + (i+1));
        }
        fireball_anim_arcane = new Animation<>(0.1f, arcProj_frames);

        Sprite[] arcExplosion_frames = new Sprite[62];
        for (int i = 0; i < arcExplosion_frames.length; i++) {
            arcExplosion_frames[i] = arc_explosion_atlas.createSprite("explosion" + (i+1));
        }
        fireball_explosion_anim_arcane = new Animation<>(0.015f, arcExplosion_frames);


        TextureAtlas lite_proj_atlas = assetManager.get(fireball_lightning_atlas_path, TextureAtlas.class);
        TextureAtlas lite_explosion_atlas = assetManager.get(fireball_lightningExplosion_atlas_path, TextureAtlas.class);

        Sprite[] liteProj_frames = new Sprite[6];
        for (int i = 0; i < liteProj_frames.length; i++) {
            liteProj_frames[i] = lite_proj_atlas.createSprite("Fireball" + (i+1));
        }
        fireball_anim_lightning = new Animation<>(0.1f, liteProj_frames);

        Sprite[] liteExplosion_frames = new Sprite[63];
        for (int i = 0; i < liteExplosion_frames.length; i++) {
            liteExplosion_frames[i] = lite_explosion_atlas.createSprite("fireexplosion" + (i+1));
        }
        fireball_explosion_anim_lightning = new Animation<>(0.015f, liteExplosion_frames);



        TextureAtlas projectile_atlas = assetManager.get(fireball_fire_atlas_path, TextureAtlas.class);
        TextureAtlas fire_atlas = assetManager.get(fireball_fireExplosion_atlas_path, TextureAtlas.class);

        Sprite[] fireProj_frames = new Sprite[6];
        for (int i = 0; i < fireProj_frames.length; i++) {
            fireProj_frames[i] = projectile_atlas.createSprite("fire" + (i+1));
        }
        fireball_anim_fire = new Animation<>(0.1f, fireProj_frames);

        Sprite[] fireExplosion_frames = new Sprite[62];
        for (int i = 0; i < fireExplosion_frames.length; i++) {
            fireExplosion_frames[i] = fire_atlas.createSprite("explosion" + (i+1));
        }
        fireball_explosion_anim_fire = new Animation<>(0.015f, fireExplosion_frames);


        TextureAtlas frost_projectile_atlas = assetManager.get(fireball_frost_atlas_path, TextureAtlas.class);
        TextureAtlas frost_atlas = assetManager.get(fireball_frostExplosion_atlas_path, TextureAtlas.class);

        Sprite[] frostProj_frames = new Sprite[6];
        for (int i = 0; i < frostProj_frames.length; i++) {
            frostProj_frames[i] = frost_projectile_atlas.createSprite("Fireball" + (i+1));
        }
        fireball_anim_frost = new Animation<>(0.1f, frostProj_frames);

        Sprite[] frostExplosion_frames = new Sprite[63];
        for (int i = 0; i < frostExplosion_frames.length; i++) {
            frostExplosion_frames[i] = frost_atlas.createSprite("explosion" + (i+1));
        }
        fireball_explosion_anim_frost = new Animation<>(0.012f, frostExplosion_frames);

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
    }

}
