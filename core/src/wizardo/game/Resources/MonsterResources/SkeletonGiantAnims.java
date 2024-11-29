package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class SkeletonGiantAnims {

    public static Animation<Sprite> skellygiant_walk;
    public static Animation<Sprite> skellygiant_death;
    public static String skellyGiant_atlas_path = "Monsters/SkellyGiant/SkellyGiant.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(skellyGiant_atlas_path, TextureAtlas.class);

        Sprite[] walk_frames = new Sprite[4];
        for (int i = 0; i < walk_frames.length; i++) {
            walk_frames[i] = atlas.createSprite("walk" + (i));
        }
        skellygiant_walk = new Animation<>(0.15f, walk_frames);


        Sprite[] death_frames = new Sprite[13];
        for (int i = 0; i < death_frames.length; i++) {
            death_frames[i] = atlas.createSprite("death" + (i));
        }
        skellygiant_death = new Animation<>(0.1f, death_frames);


    }

    public static void loadAtlas() {
        assetManager.load(skellyGiant_atlas_path, TextureAtlas.class);
    }
}
