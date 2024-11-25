package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class AcolyteAnims {

    public static Animation<Sprite> acolyte_walk_T1;

    public static String acolyte_atlas_path = "Monsters/Acolyte/Acolyte.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(acolyte_atlas_path, TextureAtlas.class);

        Sprite[] walk_frames = new Sprite[4];
        for (int i = 0; i < walk_frames.length; i++) {
            walk_frames[i] = atlas.createSprite("walk" + (i));
        }
        acolyte_walk_T1 = new Animation<>(0.25f, walk_frames);

    }

    public static void loadAtlas() {
        assetManager.load(acolyte_atlas_path, TextureAtlas.class);
    }

}
