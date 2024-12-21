package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class ArcaneMissileAnims {

    public static Animation<Sprite> arcanemissile_anim_arcane;
    public static Animation<Sprite> arcanemissile_anim_fire;

    public static String arcaneMissile_atlas_path_arcane = "Spells/ArcaneMissile/arcane_missile.atlas";
    public static String arcaneMissile_atlas_path_fire = "Spells/ArcaneMissile/arcane_missile_fire.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas_fire = assetManager.get(arcaneMissile_atlas_path_fire, TextureAtlas.class);

        Sprite[] fire_frames = new Sprite[8];
        for (int i = 0; i < fire_frames.length; i++) {
            fire_frames[i] = atlas_fire.createSprite("missile" + (i+1));
        }
        arcanemissile_anim_fire = new Animation<>(0.03f, fire_frames);


        TextureAtlas atlas = assetManager.get(arcaneMissile_atlas_path_arcane, TextureAtlas.class);

        Sprite[] missile_frames = new Sprite[8];
        for (int i = 0; i < missile_frames.length; i++) {
            missile_frames[i] = atlas.createSprite("missile" + (i+1));
        }
        arcanemissile_anim_arcane = new Animation<>(0.03f, missile_frames);


    }

    public static void loadAtlas() {
        assetManager.load(arcaneMissile_atlas_path_arcane, TextureAtlas.class);
        assetManager.load(arcaneMissile_atlas_path_fire, TextureAtlas.class);
    }
}
