package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class SkeletonAnims {

    public static Animation<Sprite> skelly_walk_T1;
    public static Animation<Sprite> skelly_death_T1;
    public static Animation<Sprite> skelly_shatter;
    public static String skelly_atlas_path = "Monsters/Skelly/Skelly.atlas";

    public static Animation<Sprite> skelly_walk_T2;
    public static Animation<Sprite> skelly_death_T2;
    public static String skellyT2_atlas_path = "Monsters/Skelly_T2/Skelly_T2.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(skelly_atlas_path, TextureAtlas.class);
        TextureAtlas atlas2 = assetManager.get(skellyT2_atlas_path, TextureAtlas.class);

        Sprite[] shatter_frames = new Sprite[9];
        for (int i = 0; i < shatter_frames.length; i++) {
            shatter_frames[i] = atlas.createSprite("shatter" + (i+1));
        }
        skelly_shatter = new Animation<>(0.07f, shatter_frames);

        Sprite[] walk_frames = new Sprite[4];
        for (int i = 0; i < walk_frames.length; i++) {
            walk_frames[i] = atlas.createSprite("walk" + (i+1));
        }
        skelly_walk_T1 = new Animation<>(0.1f, walk_frames);

        Sprite[] death_frames = new Sprite[12];
        for (int i = 0; i < death_frames.length; i++) {
            death_frames[i] = atlas.createSprite("death" + (i+1));
        }
        skelly_death_T1 = new Animation<>(0.1f, death_frames);

        Sprite[] walk_frames2 = new Sprite[4];
        for (int i = 0; i < walk_frames2.length; i++) {
            walk_frames2[i] = atlas2.createSprite("walk" + (i+1));
        }
        skelly_walk_T2 = new Animation<>(0.1f, walk_frames2);

        Sprite[] death_frames2 = new Sprite[12];
        for (int i = 0; i < death_frames2.length; i++) {
            death_frames2[i] = atlas2.createSprite("death" + (i+1));
        }
        skelly_death_T2 = new Animation<>(0.1f, death_frames2);

    }

    public static void loadAtlas() {
        assetManager.load(skelly_atlas_path, TextureAtlas.class);
        assetManager.load(skellyT2_atlas_path, TextureAtlas.class);
    }
}
