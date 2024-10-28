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

    public static void loadAnimations() {
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
        fireball_explosion_anim_frost = new Animation<>(0.015f, frostExplosion_frames);

    }

    public static void loadAtlas() {
        assetManager.load(fireball_fire_atlas_path, TextureAtlas.class);
        assetManager.load(fireball_fireExplosion_atlas_path, TextureAtlas.class);

        assetManager.load(fireball_frost_atlas_path, TextureAtlas.class);
        assetManager.load(fireball_frostExplosion_atlas_path, TextureAtlas.class);
    }

}
