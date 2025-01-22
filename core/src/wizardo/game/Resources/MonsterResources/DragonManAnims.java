package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class DragonManAnims {

    public static Animation<Sprite> dragonman_walk;
    public static String dragonman_atlas_path = "Monsters/DragonMan/DragonMan.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(dragonman_atlas_path, TextureAtlas.class);

        Sprite[] walk_frames = new Sprite[4];
        for (int i = 0; i < walk_frames.length; i++) {
            walk_frames[i] = atlas.createSprite("Walk" + (i+1));
        }
        dragonman_walk = new Animation<>(0.15f, walk_frames);

    }

    public static void loadAtlas() {
        assetManager.load(dragonman_atlas_path, TextureAtlas.class);
    }

}
