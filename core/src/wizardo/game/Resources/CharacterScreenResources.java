package wizardo.game.Resources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class CharacterScreenResources {

    public static Animation<Sprite> selected_button_anim;

    public static String selected_button_path = "Screens/CharacterScreen/Selected_Button.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(selected_button_path, TextureAtlas.class);

        Sprite[] frames = new Sprite[6];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("selected" + (i+1));
        }
        selected_button_anim = new Animation<>(0.1f, frames);

    }

    public static void loadAtlas() {
        assetManager.load(selected_button_path, TextureAtlas.class);
    }
}
