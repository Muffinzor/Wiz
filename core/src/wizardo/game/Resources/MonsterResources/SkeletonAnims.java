package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class SkeletonAnims {

    public static Animation<Sprite> skelly_walk_T1;
    public static Animation<Sprite> skelly_death_T1;
    public static String skelly_atlas_path = "Monsters/Skelly/Skelly.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(skelly_atlas_path, TextureAtlas.class);

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

    }

    public static void loadAtlas() {
        assetManager.load(skelly_atlas_path, TextureAtlas.class);
    }
}
