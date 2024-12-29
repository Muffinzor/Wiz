package wizardo.game.Resources.DecorResources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class GeneralDecorResources {

    public static Animation<Sprite> blue_portal_anim;
    public static String blue_portal_anim_path = "Maps/Decor/Portal.atlas";

    public static Animation<Sprite> woodenChest_anim;
    public static Animation<Sprite> metalChest_anim;
    public static Animation<Sprite> redChest_anim;
    public static Animation<Sprite> purpleChest_anim;
    public static Animation<Sprite> stoneChest_anim;
    public static Animation<Sprite> goldChest_anim;
    public static String chest_atlas_path = "Maps/Decor/Chests.atlas";

    public static void loadAnimations() {
        loadChestAnims();

        TextureAtlas atlas = assetManager.get(blue_portal_anim_path, TextureAtlas.class);
        Sprite[] frames = new Sprite[10];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("portal" + (i + 1));
        }
        blue_portal_anim = new Animation<>(0.1f, frames);
    }
    public static void loadChestAnims() {
        TextureAtlas atlas = assetManager.get(chest_atlas_path, TextureAtlas.class);

        Sprite[] frames1 = new Sprite[4];
        for (int i = 0; i < frames1.length; i++) {
            frames1[i] = atlas.createSprite("wood" + (i + 1));
        }
        woodenChest_anim = new Animation<>(0.1f, frames1);

        Sprite[] frames2 = new Sprite[4];
        for (int i = 0; i < frames2.length; i++) {
            frames2[i] = atlas.createSprite("silver" + (i + 1));
        }
        metalChest_anim = new Animation<>(0.1f, frames2);

        Sprite[] frames3 = new Sprite[4];
        for (int i = 0; i < frames3.length; i++) {
            frames3[i] = atlas.createSprite("red" + (i + 1));
        }
        redChest_anim = new Animation<>(0.1f, frames3);

        Sprite[] frames4 = new Sprite[4];
        for (int i = 0; i < frames4.length; i++) {
            frames4[i] = atlas.createSprite("purple" + (i + 1));
        }
        purpleChest_anim = new Animation<>(0.1f, frames4);

        Sprite[] frames5 = new Sprite[4];
        for (int i = 0; i < frames5.length; i++) {
            frames5[i] = atlas.createSprite("stone" + (i + 1));
        }
        stoneChest_anim = new Animation<>(0.1f, frames5);

        Sprite[] frames6 = new Sprite[4];
        for (int i = 0; i < frames6.length; i++) {
            frames6[i] = atlas.createSprite("gold" + (i + 1));
        }
        goldChest_anim = new Animation<>(0.1f, frames6);
    }

    public static void loadAtlas() {
        assetManager.load(blue_portal_anim_path, TextureAtlas.class);
        assetManager.load(chest_atlas_path, TextureAtlas.class);
    }
}
