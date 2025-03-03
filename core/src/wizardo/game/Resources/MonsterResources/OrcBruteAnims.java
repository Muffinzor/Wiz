package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class OrcBruteAnims {

    public static Animation<Sprite> orcBrute_walk;
    public static Animation<Sprite> orcBrute_death;
    public static String orcBrute_atlas_path = "Monsters/OrcBrute/OrcBrute.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(orcBrute_atlas_path, TextureAtlas.class);

        Sprite[] walk_frames = new Sprite[4];
        for (int i = 0; i < walk_frames.length; i++) {
            walk_frames[i] = atlas.createSprite("walk" + (i+1));
        }
        orcBrute_walk = new Animation<>(0.15f, walk_frames);

        Sprite[] death_frames = new Sprite[8];
        for (int i = 0; i < death_frames.length; i++) {
            death_frames[i] = atlas.createSprite("death" + (i+1));
        }
        orcBrute_death = new Animation<>(0.10f, death_frames);
    }

    public static void loadAtlas() {
        assetManager.load(orcBrute_atlas_path, TextureAtlas.class);
    }
}
