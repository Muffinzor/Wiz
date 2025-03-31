package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class ZombieAnims {

    public static Animation<Sprite> zombie_walk;
    public static Animation<Sprite> zombie_death;
    public static String zombie_atlas_path = "Monsters/Zombie/Zombie.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(zombie_atlas_path, TextureAtlas.class);

        Sprite[] walk_frames = new Sprite[4];
        for (int i = 0; i < walk_frames.length; i++) {
            walk_frames[i] = atlas.createSprite("walk" + (i));
        }
        zombie_walk = new Animation<>(0.22f, walk_frames);

        Sprite[] death_frames = new Sprite[12];
        for (int i = 0; i < death_frames.length; i++) {
            death_frames[i] = atlas.createSprite("death" + (i));
        }
        zombie_death = new Animation<>(0.1f, death_frames);
    }

    public static void loadAtlas() {
        assetManager.load(zombie_atlas_path, TextureAtlas.class);
    }
}
