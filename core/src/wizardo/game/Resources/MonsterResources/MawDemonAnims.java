package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class MawDemonAnims {

    public static Animation<Sprite> mawDemon_spawn;
    public static Animation<Sprite> mawDemon_walk;
    public static Animation<Sprite> mawDemon_death;
    public static Animation<Sprite> mawDemon_skullFlame;

    public static String maw_demon_path = "Monsters/MawDemon/MawDemon.atlas";
    public static String skull_path = "Monsters/MawDemon/SkullFlame.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(maw_demon_path, TextureAtlas.class);
        TextureAtlas skull_atlas = assetManager.get(skull_path, TextureAtlas.class);

        Sprite[] walk_frames = new Sprite[4];
        for (int i = 0; i < walk_frames.length; i++) {
            walk_frames[i] = atlas.createSprite("walk" + (i+1));
        }
        mawDemon_walk = new Animation<>(0.15f, walk_frames);

        Sprite[] death_frames = new Sprite[11];
        for (int i = 0; i < death_frames.length; i++) {
            death_frames[i] = atlas.createSprite("death" + (i+1));
        }
        mawDemon_death = new Animation<>(0.1f, death_frames);

        Sprite[] skull_frames = new Sprite[58];
        for (int i = 0; i < skull_frames.length; i++) {
            skull_frames[i] = skull_atlas.createSprite("skull" + (i+1));
        }
        mawDemon_skullFlame = new Animation<>(0.02f, skull_frames);

    }

    public static void loadAtlas() {
        assetManager.load(maw_demon_path, TextureAtlas.class);
        assetManager.load(skull_path, TextureAtlas.class);
    }

}
