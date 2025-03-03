package wizardo.game.Resources.ScreenResources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class ShopScreenResources {

    public static Animation<Sprite> gold_reagent_anim;
    public static Animation<Sprite> gold_reagent_hover_anim;

    public static Animation<Sprite> triple_reagent_anim;
    public static Animation<Sprite> triple_reagent_hover_anim;
    public static String normal_reagents_path = "Screens/Shop/Gems.atlas";
    public static String hover_reagents_hover_path = "Screens/Shop/GemsOver.atlas";



    public static void loadAnimations() {

        TextureAtlas normal_atlas = assetManager.get(normal_reagents_path, TextureAtlas.class);
        TextureAtlas hover_atlas = assetManager.get(hover_reagents_hover_path, TextureAtlas.class);

        Sprite[] gold_normal_frames = new Sprite[36];
        for (int i = 0; i < gold_normal_frames.length; i++) {
            gold_normal_frames[i] = normal_atlas.createSprite("gold" + (i + 1));
        }
        gold_reagent_anim = new Animation<>(0.06f, gold_normal_frames);

        Sprite[] gold_hover_frames = new Sprite[36];
        for (int i = 0; i < gold_hover_frames.length; i++) {
            gold_hover_frames[i] = hover_atlas.createSprite("gold" + (i + 1));
        }
        gold_reagent_hover_anim = new Animation<>(0.06f, gold_hover_frames);

        Sprite[] triple_normal_frames = new Sprite[36];
        for (int i = 0; i < triple_normal_frames.length; i++) {
            triple_normal_frames[i] = normal_atlas.createSprite("astral" + (i + 1));
        }
        triple_reagent_anim = new Animation<>(0.06f, triple_normal_frames);

        Sprite[] triple_hover_frames = new Sprite[36];
        for (int i = 0; i < triple_hover_frames.length; i++) {
            triple_hover_frames[i] = hover_atlas.createSprite("astral" + (i + 1));
        }
        triple_reagent_hover_anim = new Animation<>(0.06f, triple_hover_frames);
    }

    public static void loadAtlas() {
        assetManager.load(normal_reagents_path, TextureAtlas.class);
        assetManager.load(hover_reagents_hover_path, TextureAtlas.class);
    }
}
