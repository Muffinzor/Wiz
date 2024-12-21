package wizardo.game.Resources.ScreenResources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class LevelUpResources {

    public static Sprite regular_panel = new Sprite(new Texture("Screens/LevelUp/PanelFrame/regular.png"));

    public static Animation<Sprite> selected_panel_anim;
    public static String selected_panel_path = "Screens/LevelUp/PanelFrame/Selected_Panel.atlas";

    public static Sprite green_gem = new Sprite(new Texture("Screens/LevelUp/Green.png"));
    public static Sprite blue_gem = new Sprite(new Texture("Screens/LevelUp/Blue.png"));
    public static Sprite purple_gem = new Sprite(new Texture("Screens/LevelUp/Purple.png"));
    public static Sprite orange_gem = new Sprite(new Texture("Screens/LevelUp/Orange.png"));


    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(selected_panel_path, TextureAtlas.class);
        Sprite[] frames = new Sprite[36];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("selected" + (i+1));
        }
        selected_panel_anim = new Animation<>(0.05f, frames);


    }

    public static void loadAtlas() {
        assetManager.load(selected_panel_path, TextureAtlas.class);
    }
}
