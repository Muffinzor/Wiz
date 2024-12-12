package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class FrostNovaAnims {

    public static Animation<Sprite> frostnova_anim;

    public static String frostnova_anim_path = "Spells/Frostnova/Frostnova.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(frostnova_anim_path, TextureAtlas.class);

        Sprite[] frames = new Sprite[87];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("frostnova" + (i+1));
        }
        frostnova_anim = new Animation<>(0.0125f, frames);

    }

    public static void loadAtlas() {
        assetManager.load(frostnova_anim_path, TextureAtlas.class);
    }


}
