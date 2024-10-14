package wizardo.game.Resources.DecorResources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class GeneralDecorResources {

    public static Animation<Sprite> blue_portal_anim;
    public static String blue_portal_anim_path = "Maps/Decor/Portal.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(blue_portal_anim_path, TextureAtlas.class);

        Sprite[] frames = new Sprite[10];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("portal" + (i + 1));
        }
        blue_portal_anim = new Animation<>(0.1f, frames);
    }

    public static void loadAtlas() {
        assetManager.load(blue_portal_anim_path, TextureAtlas.class);
    }
}
