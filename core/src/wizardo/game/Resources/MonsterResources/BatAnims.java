package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class BatAnims {

    public static Animation<Sprite> bat_walk;
    public static Animation<Sprite> bat_death;
    public static Animation<Sprite> bat_shatter;
    public static String bat_atlas_path = "Monsters/Bat/Bat.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(bat_atlas_path, TextureAtlas.class);

        Sprite[] walk_frames = new Sprite[4];
        for (int i = 0; i < walk_frames.length; i++) {
            walk_frames[i] = atlas.createSprite("fly" + (i+1));
        }
        bat_walk = new Animation<>(0.1f, walk_frames);

        Sprite[] death_frames = new Sprite[7];
        for (int i = 0; i < death_frames.length; i++) {
            death_frames[i] = atlas.createSprite("death" + (i+1));
        }
        bat_death = new Animation<>(0.08f, death_frames);

        Sprite[] shatter_frames = new Sprite[7];
        for (int i = 0; i < shatter_frames.length; i++) {
            shatter_frames[i] = atlas.createSprite("shatter" + (i+1));
        }
        bat_shatter = new Animation<>(0.08f, shatter_frames);
    }

    public static void loadAtlas() {
        assetManager.load(bat_atlas_path, TextureAtlas.class);
    }
}
