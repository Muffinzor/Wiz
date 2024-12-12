package wizardo.game.Resources.ScreenResources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class CharacterScreenResources {

    public static Sprite circle_selected = new Sprite(new Texture("Screens/CharacterScreen/Circle_Selected.png"));

    public static Animation<Sprite> selected_button_anim;
    public static String selected_button_path = "Screens/CharacterScreen/Selected_Button.atlas";

    public static Animation<Sprite> arcane_anim;
    public static String arcane_path = "Screens/CharacterScreen/SpellCreation/ArcaneAnim.atlas";

    public static Animation<Sprite> fire_anim;
    public static String fire_path = "Screens/CharacterScreen/SpellCreation/FireAnim.atlas";

    public static Animation<Sprite> frost_anim;
    public static String frost_path = "Screens/CharacterScreen/SpellCreation/FrostAnim.atlas";

    public static Animation<Sprite> lightning_anim;
    public static String lightning_path = "Screens/CharacterScreen/SpellCreation/LightningAnim.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(selected_button_path, TextureAtlas.class);
        Sprite[] frames = new Sprite[6];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("selected" + (i+1));
        }
        selected_button_anim = new Animation<>(0.1f, frames);

        TextureAtlas arcane_atlas = assetManager.get(arcane_path, TextureAtlas.class);
        Sprite[] arcane_frames = new Sprite[16];
        for (int i = 0; i < arcane_frames.length; i++) {
            arcane_frames[i] = arcane_atlas.createSprite("arcane" + (i));
        }
        arcane_anim = new Animation<>(0.025f, arcane_frames);

        TextureAtlas fire_atlas = assetManager.get(fire_path, TextureAtlas.class);
        Sprite[] fire_frames = new Sprite[15];
        for (int i = 0; i < fire_frames.length; i++) {
            fire_frames[i] = fire_atlas.createSprite("fire" + (i + 1));
        }
        fire_anim = new Animation<>(0.025f, fire_frames);

        TextureAtlas frost_atlas = assetManager.get(frost_path, TextureAtlas.class);
        Sprite[] frost_frames = new Sprite[15];
        for (int i = 0; i < frost_frames.length; i++) {
            frost_frames[i] = frost_atlas.createSprite("frost" + (i + 1));
        }
        frost_anim = new Animation<>(0.025f, frost_frames);

        TextureAtlas lightning_atlas = assetManager.get(lightning_path, TextureAtlas.class);
        Sprite[] lightning_frames = new Sprite[15];
        for (int i = 0; i < lightning_frames.length; i++) {
            lightning_frames[i] = lightning_atlas.createSprite("lightning" + (i + 1));
        }
        lightning_anim = new Animation<>(0.025f, lightning_frames);

    }

    public static void loadAtlas() {
        assetManager.load(selected_button_path, TextureAtlas.class);
        assetManager.load(arcane_path, TextureAtlas.class);
        assetManager.load(fire_path, TextureAtlas.class);
        assetManager.load(frost_path, TextureAtlas.class);
        assetManager.load(lightning_path, TextureAtlas.class);
    }
}
