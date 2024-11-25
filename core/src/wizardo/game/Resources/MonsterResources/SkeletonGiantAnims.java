package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class SkeletonGiantAnims {

    public static Animation<Sprite> skellyGiant_walk_T1;
    public static Animation<Sprite> skellyGiant_death_T1;
    public static String skellyGiant_atlas_path = "Monsters/SkellyGiant/GiantSkelly_T0.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(skellyGiant_atlas_path, TextureAtlas.class);

        Sprite[] walk_frames = new Sprite[4];
        for (int i = 0; i < walk_frames.length; i++) {
            walk_frames[i] = atlas.createSprite("walk" + (i+1));
        }
        skellyGiant_walk_T1 = new Animation<>(0.15f, walk_frames);

        Sprite[] death_frames = new Sprite[9];
        for (int i = 0; i < death_frames.length; i++) {
            death_frames[i] = atlas.createSprite("death" + (i+1));
        }
        skellyGiant_death_T1 = new Animation<>(0.1f, death_frames);

    }

    public static void loadAtlas() {
        assetManager.load(skellyGiant_atlas_path, TextureAtlas.class);
    }
}
