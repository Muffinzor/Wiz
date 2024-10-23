package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class FlamejetAnims {

    public static Animation<Sprite> flamejet_fire_anim;
    public static Animation<Sprite> flamejet_fire_anim2;
    public static String flamejet_fire_atlas_path = "Spells/Flamejet/fire1.atlas";
    public static String flamejet_fire_atlas_path2 = "Spells/Flamejet/fire2.atlas";

    public static void loadAnimations() {

        TextureAtlas fire_atlas = assetManager.get(flamejet_fire_atlas_path, TextureAtlas.class);
        TextureAtlas fire_atlas2 = assetManager.get(flamejet_fire_atlas_path2, TextureAtlas.class);

        Sprite[] fire_frames = new Sprite[61];
        for (int i = 0; i < fire_frames.length; i++) {
            fire_frames[i] = fire_atlas.createSprite("firespark" + (i+1));
        }
        flamejet_fire_anim = new Animation<>(0.012f, fire_frames);

        Sprite[] fire_frames2 = new Sprite[56];
        for (int i = 0; i < fire_frames2.length; i++) {
            fire_frames2[i] = fire_atlas2.createSprite("firespark" + (i+1));
        }
        flamejet_fire_anim2 = new Animation<>(0.012f, fire_frames2);

    }

    public static void loadAtlas() {
        assetManager.load(flamejet_fire_atlas_path, TextureAtlas.class);
        assetManager.load(flamejet_fire_atlas_path2, TextureAtlas.class);
    }
}
