package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class OrcShamanAnims {

    public static Animation<Sprite> orcShaman_walk;
    public static Animation<Sprite> orcShaman_death;
    public static Animation<Sprite> orcShaman_stop;
    public static String orcMinion_atlas_path = "Monsters/OrcShaman/OrcShaman.atlas";

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(orcMinion_atlas_path, TextureAtlas.class);

        Sprite[] stop_frames = new Sprite[4];
        for (int i = 0; i < stop_frames.length; i++) {
            stop_frames[i] = atlas.createSprite("idle" + (i+1));
        }
        orcShaman_stop = new Animation<>(0.15f, stop_frames);

        Sprite[] walk_frames = new Sprite[4];
        for (int i = 0; i < walk_frames.length; i++) {
            walk_frames[i] = atlas.createSprite("walk" + (i+1));
        }
        orcShaman_walk = new Animation<>(0.15f, walk_frames);

        Sprite[] death_frames = new Sprite[7];
        for (int i = 0; i < death_frames.length; i++) {
            death_frames[i] = atlas.createSprite("death" + (i+1));
        }
        orcShaman_death = new Animation<>(0.12f, death_frames);

    }

    public static void loadAtlas() {
        assetManager.load(orcMinion_atlas_path, TextureAtlas.class);
    }
}
