package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class AcolyteAnims {

    public static Animation<Sprite> acolyte_spawn_blue;
    public static Animation<Sprite> acolyte_walk_blue;
    public static Animation<Sprite> acolyte_death_blue;

    public static Animation<Sprite> acolyte_spawn_purple;
    public static Animation<Sprite> acolyte_walk_purple;
    public static Animation<Sprite> acolyte_death_purple;

    public static String acolyte_atlas_path = "Monsters/Acolyte/AcolyteBlue.atlas";
    public static String acolyte_atlas_purple_path = "Monsters/Acolyte/AcolytePurple.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(acolyte_atlas_path, TextureAtlas.class);
        TextureAtlas atlas_purple = assetManager.get(acolyte_atlas_purple_path, TextureAtlas.class);

        Sprite[] spawn_frames_purple = new Sprite[10];
        int index1 = 0;
        for (int i = 9; i >= 0; i--) {
            spawn_frames_purple[index1] = atlas_purple.createSprite("death" + (i));
            index1++;
        }
        acolyte_spawn_purple = new Animation<>(0.2f, spawn_frames_purple);

        Sprite[] walk_frames_purple = new Sprite[4];
        for (int i = 0; i < walk_frames_purple.length; i++) {
            walk_frames_purple[i] = atlas_purple.createSprite("walk" + (i));
        }
        acolyte_walk_purple = new Animation<>(0.25f, walk_frames_purple);


        Sprite[] death_frames_purple = new Sprite[10];
        for (int i = 0; i < death_frames_purple.length; i++) {
            death_frames_purple[i] = atlas_purple.createSprite("death" + (i));
        }
        acolyte_death_purple = new Animation<>(0.2f, death_frames_purple);



        Sprite[] spawn_frames = new Sprite[10];
        int index = 0;
        for (int i = 9; i >= 0; i--) {
            spawn_frames[index] = atlas.createSprite("death" + (i));
            index++;
        }
        acolyte_spawn_blue = new Animation<>(0.2f, spawn_frames);

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
        assetManager.load(acolyte_atlas_purple_path, TextureAtlas.class);
    }

}
