package wizardo.game.Resources.DecorResources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class ForestDecorResources {

    // Trees
    public static Sprite green_tree_1 = new Sprite(new Texture("Maps/Decor/Forest/green_tree_1.png"));
    public static Sprite green_tree_2 = new Sprite(new Texture("Maps/Decor/Forest/green_tree_2.png"));
    public static Sprite green_tree_dark_1 = new Sprite(new Texture("Maps/Decor/Forest/green_tree_dark_1.png"));
    public static Sprite green_tree_dark_2 = new Sprite(new Texture("Maps/Decor/Forest/green_tree_dark_2.png"));
    public static Sprite green_tree_shadow_1 = new Sprite(new Texture("Maps/Decor/Forest/green_tree_1_shadow.png"));
    public static Sprite green_tree_shadow_2 = new Sprite(new Texture("Maps/Decor/Forest/green_tree_2_shadow.png"));

    // Grass
    public static String grass_path = "Maps/Decor/Forest/Grass.atlas";
    public static Sprite[] grass = new Sprite[8];

    // Buildings
    public static Sprite rectangle_ruins1_stone = new Sprite(new Texture("Maps/Decor/Forest/Buildings/building1_s.png"));
    public static Sprite rectangle_ruins1_grass = new Sprite(new Texture("Maps/Decor/Forest/Buildings/building1_g.png"));
    public static Sprite rectangle_ruins2_stone = new Sprite(new Texture("Maps/Decor/Forest/Buildings/building2_s2.png"));
    public static Sprite rectangle_ruins2_grass = new Sprite(new Texture("Maps/Decor/Forest/Buildings/building2_g.png"));
    public static Sprite pillar_h1_grass = new Sprite(new Texture("Maps/Decor/Forest/Buildings/pillar_h1_g.png"));
    public static Sprite pillar_h1_stone = new Sprite(new Texture("Maps/Decor/Forest/Buildings/pillar_h1_s.png"));
    public static Sprite pillar_h2_grass = new Sprite(new Texture("Maps/Decor/Forest/Buildings/pillar_h2_g.png"));
    public static Sprite pillar_h2_stone = new Sprite(new Texture("Maps/Decor/Forest/Buildings/pillar_h2_s.png"));

    public static Sprite small_pillar1 = new Sprite(new Texture("Maps/Decor/Forest/Buildings/small_pillar1.png"));
    public static Sprite small_pillar2 = new Sprite(new Texture("Maps/Decor/Forest/Buildings/small_pillar2.png"));
    public static Sprite small_pillar3 = new Sprite(new Texture("Maps/Decor/Forest/Buildings/small_pillar3.png"));

    // Stones
    public static Sprite stone_1 = new Sprite(new Texture("Maps/Decor/Forest/Stones/stone_1.png"));
    public static Sprite stone_2 = new Sprite(new Texture("Maps/Decor/Forest/Stones/stone_2.png"));
    public static Sprite stone_3 = new Sprite(new Texture("Maps/Decor/Forest/Stones/stone_3.png"));

    // Fountain
    public static Animation<Sprite> fountain_anim;
    public static String fountain_path = "Maps/Decor/Forest/Fountain/Fountain.atlas";


    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(grass_path, TextureAtlas.class);
        for (int i = 0; i < grass.length; i++) {
            grass[i] = atlas.createSprite("grass" + (i+1));
        }

        TextureAtlas fountain_atlas = assetManager.get(fountain_path, TextureAtlas.class);
        Sprite[] fountain_frames = new Sprite[8];
        for (int i = 0; i < fountain_frames.length; i++) {
            fountain_frames[i] = fountain_atlas.createSprite("fountain" + (i+1));
        }
        fountain_anim = new Animation<>(0.1f, fountain_frames);
    }

    public static void loadAtlas() {
        assetManager.load(grass_path, TextureAtlas.class);
        assetManager.load(fountain_path, TextureAtlas.class);
    }

}
