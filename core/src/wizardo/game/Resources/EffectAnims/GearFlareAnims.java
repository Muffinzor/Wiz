package wizardo.game.Resources.EffectAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class GearFlareAnims {

    public static Animation<Sprite> white_flare;
    public static String white_path = "Items/GearEffect/Gear_Flare_White.atlas";

    public static Animation<Sprite> red_flare;
    public static String red_path = "Items/GearEffect/Gear_Flare_Extra.atlas";

    public static Animation<Sprite> purple_flare;
    public static String purple_path = "Items/GearEffect/Gear_Flare_Purple.atlas";

    public static Animation<Sprite> blue_flare;
    public static String blue_path = "Items/GearEffect/Gear_Flare_Blue.atlas";

    public static Animation<Sprite> green_flare;
    public static String green_path = "Items/GearEffect/Gear_Flare_Green.atlas";

    public static Animation<Sprite> gear_pop;
    public static String gear_pop_path = "Items/GearEffect/GearPop.atlas";

    public static void loadAnimations() {

        TextureAtlas pop_atlas = assetManager.get(gear_pop_path, TextureAtlas.class);
        Sprite[] pop_frames = new Sprite[44];
        for (int i = 0; i < pop_frames.length; i++) {
            pop_frames[i] = pop_atlas.createSprite("pop" + (i+1));
        }
        gear_pop = new Animation<>(0.012f, pop_frames);

        TextureAtlas white_atlas = assetManager.get(white_path, TextureAtlas.class);
        Sprite[] white_frames = new Sprite[90];
        for (int i = 0; i < white_frames.length; i++) {
            white_frames[i] = white_atlas.createSprite("flareRED" + (i+1));
        }
        white_flare = new Animation<>(0.01f, white_frames);

        TextureAtlas atlas = assetManager.get(red_path, TextureAtlas.class);
        Sprite[] frames = new Sprite[90];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("flareRED" + (i+1));
        }
        red_flare = new Animation<>(0.01f, frames);

        TextureAtlas atlas2 = assetManager.get(purple_path, TextureAtlas.class);
        Sprite[] frames2 = new Sprite[90];
        for (int i = 0; i < frames2.length; i++) {
            frames2[i] = atlas2.createSprite("flareRED" + (i+1));
        }
        purple_flare = new Animation<>(0.01f, frames2);

        TextureAtlas atlas3 = assetManager.get(blue_path, TextureAtlas.class);
        Sprite[] frames3 = new Sprite[90];
        for (int i = 0; i < frames3.length; i++) {
            frames3[i] = atlas3.createSprite("flareRED" + (i+1));
        }
        blue_flare = new Animation<>(0.01f, frames3);

        TextureAtlas atlas4 = assetManager.get(green_path, TextureAtlas.class);
        Sprite[] frames4 = new Sprite[90];
        for (int i = 0; i < frames4.length; i++) {
            frames4[i] = atlas4.createSprite("flareRED" + (i+1));
        }
        green_flare = new Animation<>(0.01f, frames4);

    }

    public static void loadAtlas() {
        assetManager.load(red_path, TextureAtlas.class);
        assetManager.load(purple_path, TextureAtlas.class);
        assetManager.load(blue_path, TextureAtlas.class);
        assetManager.load(green_path, TextureAtlas.class);
        assetManager.load(gear_pop_path, TextureAtlas.class);
        assetManager.load(white_path, TextureAtlas.class);
    }
}
