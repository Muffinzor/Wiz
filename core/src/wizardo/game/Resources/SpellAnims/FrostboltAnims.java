package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class FrostboltAnims {

    public static Animation<Sprite> frostbolt_anim_frost;
    public static Animation<Sprite> frostbolt_explosion_anim_frost;
    public static String frostbolt_atlas_path_frost = "Spells/Frostbolt/Frost/Frostbolt_Frost.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(frostbolt_atlas_path_frost, TextureAtlas.class);

        Sprite[] bolt_frames = new Sprite[12];
        for (int i = 0; i < bolt_frames.length; i++) {
            bolt_frames[i] = atlas.createSprite("bolt" + (i+1));
        }
        frostbolt_anim_frost = new Animation<>(0.1f, bolt_frames);

        Sprite[] explosion_frames = new Sprite[16];
        for (int i = 0; i < explosion_frames.length; i++) {
            explosion_frames[i] = atlas.createSprite("explosion" + (i+1));
        }
        frostbolt_explosion_anim_frost = new Animation<>(0.03f, explosion_frames);

    }

    public static void loadAtlas() {
        assetManager.load(frostbolt_atlas_path_frost, TextureAtlas.class);
    }
}
