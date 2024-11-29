package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class AcolyteAnims {

    public static Animation<Sprite> acolyte_walk_blue;
    public static Animation<Sprite> acolyte_death_blue;

    public static String acolyte_atlas_path = "Monsters/Acolyte/Acolyte.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(acolyte_atlas_path, TextureAtlas.class);

        Sprite[] walk_frames = new Sprite[4];
        for (int i = 0; i < walk_frames.length; i++) {
            walk_frames[i] = atlas.createSprite("walk" + (i));
        }
        acolyte_walk_blue = new Animation<>(0.25f, walk_frames);


        Sprite[] death_frames = new Sprite[10];
        for (int i = 0; i < death_frames.length; i++) {
            death_frames[i] = atlas.createSprite("death" + (i));
        }
        acolyte_death_blue = new Animation<>(0.2f, death_frames);

    }

    public static void loadAtlas() {
        assetManager.load(acolyte_atlas_path, TextureAtlas.class);
    }

}
