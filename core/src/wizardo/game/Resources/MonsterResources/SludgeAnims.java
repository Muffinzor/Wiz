package wizardo.game.Resources.MonsterResources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class SludgeAnims {

    public static Animation<Sprite> sludge_green_move;
    public static Animation<Sprite> sludge_green_death;
    public static String sludge_green_path = "Monsters/Sludge/GreenSludge.atlas";
    public static String sludge_mini_green_path = "Monsters/Sludge/GreenSludgeMini.atlas";
    public static Animation<Sprite> sludge_green_move_mini;
    public static Animation<Sprite> sludge_green_death_mini;

    public static void loadAnimations() {
        TextureAtlas atlas = assetManager.get(sludge_green_path, TextureAtlas.class);
        TextureAtlas atlas_mini = assetManager.get(sludge_mini_green_path, TextureAtlas.class);

        Sprite[] move_frames_mini = new Sprite[6];
        for (int i = 0; i < move_frames_mini.length; i++) {
            move_frames_mini[i] = atlas_mini.createSprite("sludge" + (i));
        }
        sludge_green_move_mini = new Animation<>(0.2f, move_frames_mini);

        Sprite[] death_frames_mini = new Sprite[7];
        for (int i = 0; i < death_frames_mini.length; i++) {
            death_frames_mini[i] = atlas_mini.createSprite("death" + (i));
        }
        sludge_green_death_mini = new Animation<>(0.2f, death_frames_mini);

        Sprite[] move_frames = new Sprite[6];
        for (int i = 0; i < move_frames.length; i++) {
            move_frames[i] = atlas.createSprite("sludge" + (i));
        }
        sludge_green_move = new Animation<>(0.2f, move_frames);

        Sprite[] death_frames = new Sprite[7];
        for (int i = 0; i < death_frames.length; i++) {
            death_frames[i] = atlas.createSprite("death" + (i));
        }
        sludge_green_death = new Animation<>(0.2f, death_frames);
    }

    public static void loadAtlas() {
        assetManager.load(sludge_green_path, TextureAtlas.class);
        assetManager.load(sludge_mini_green_path, TextureAtlas.class);
    }
}
