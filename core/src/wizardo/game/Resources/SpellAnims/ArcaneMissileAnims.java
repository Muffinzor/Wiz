package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class ArcaneMissileAnims {

    public static Animation<Sprite> arcanemissile_anim_arcane;
    public static Animation<Sprite> arcanemissile_anim_fire;
    public static Animation<Sprite> arcanemissile_anim_frost;

    public static String arcaneMissile_atlas_path_arcane = "Spells/ArcaneMissile/Triangle_Projectile.atlas";
    public static String arcaneMissile_atlas_path_fire = "Spells/ArcaneMissile/Triangle_Fire.atlas";
    public static String arcaneMissile_atlas_path_frost = "Spells/ArcaneMissile/Triangle_Frost.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas_fire = assetManager.get(arcaneMissile_atlas_path_fire, TextureAtlas.class);
        Sprite[] fire_frames = new Sprite[30];
        for (int i = 0; i < fire_frames.length; i++) {
            fire_frames[i] = atlas_fire.createSprite("triangle" + (i+1));
        }
        arcanemissile_anim_fire = new Animation<>(0.03f, fire_frames);


        TextureAtlas frost_atlas = assetManager.get(arcaneMissile_atlas_path_frost, TextureAtlas.class);
        Sprite[] frost_frames = new Sprite[30];
        for (int i = 0; i < frost_frames.length; i++) {
            frost_frames[i] = frost_atlas.createSprite("triangle" + (i+1));
        }
        arcanemissile_anim_frost = new Animation<>(0.03f, frost_frames);

        TextureAtlas atlas = assetManager.get(arcaneMissile_atlas_path_arcane, TextureAtlas.class);
        Sprite[] missile_frames = new Sprite[30];
        for (int i = 0; i < missile_frames.length; i++) {
            missile_frames[i] = atlas.createSprite("triangle" + (i+1));
        }
        arcanemissile_anim_arcane = new Animation<>(0.03f, missile_frames);


    }

    public static void loadAtlas() {
        assetManager.load(arcaneMissile_atlas_path_arcane, TextureAtlas.class);
        assetManager.load(arcaneMissile_atlas_path_fire, TextureAtlas.class);
        assetManager.load(arcaneMissile_atlas_path_frost, TextureAtlas.class);
    }
}
