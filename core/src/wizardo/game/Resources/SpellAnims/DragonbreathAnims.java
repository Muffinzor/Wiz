package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class DragonbreathAnims {

    public static Animation<Sprite> dragonbreath_anim_fire;
    public static Animation<Sprite> dragonbreath_anim_frost;

    public static String dragonbreath_atlas_path_fire = "Spells/Dragonbreath/Dragonbreath_Fire.atlas";
    public static String dragonbreath_atlas_path_frost = "Spells/Dragonbreath/Dragonbreath_Frost.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(dragonbreath_atlas_path_fire, TextureAtlas.class);

        Sprite[] frames = new Sprite[120];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("dragonbreath" + (i+1));
        }
        dragonbreath_anim_fire = new Animation<>(0.012f, frames);

        TextureAtlas atlas_frost = assetManager.get(dragonbreath_atlas_path_frost, TextureAtlas.class);

        Sprite[] frames_frost = new Sprite[120];
        for (int i = 0; i < frames_frost.length; i++) {
            frames_frost[i] = atlas_frost.createSprite("dragonbreath" + (i+1));
        }
        dragonbreath_anim_frost = new Animation<>(0.012f, frames_frost);

    }

    public static void loadAtlas() {
        assetManager.load(dragonbreath_atlas_path_fire, TextureAtlas.class);
        assetManager.load(dragonbreath_atlas_path_frost, TextureAtlas.class);
    }
}
