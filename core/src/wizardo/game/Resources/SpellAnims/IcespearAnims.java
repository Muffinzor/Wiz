package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class IcespearAnims {

    public static Animation<Sprite> icespear_anim_frost;
    public static Animation<Sprite> icespear_hit_anim_frost;
    public static String icespear_atlas_path_frost = "Spells/Icespear/Icespear_Frost.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(icespear_atlas_path_frost, TextureAtlas.class);

        Sprite[] spear_frames = new Sprite[8];
        for (int i = 0; i < spear_frames.length; i++) {
            spear_frames[i] = atlas.createSprite("spear" + (i+1));
        }
        icespear_anim_frost = new Animation<>(0.1f, spear_frames);

        Sprite[] hit_frames = new Sprite[16];
        for (int i = 0; i < hit_frames.length; i++) {
            hit_frames[i] = atlas.createSprite("hit" + (i+1));
        }
        icespear_hit_anim_frost = new Animation<>(0.015f, hit_frames);
    }

    public static void loadAtlas() {
        assetManager.load(icespear_atlas_path_frost, TextureAtlas.class);
    }
}
