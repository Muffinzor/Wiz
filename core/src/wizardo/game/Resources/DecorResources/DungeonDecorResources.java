package wizardo.game.Resources.DecorResources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class DungeonDecorResources {

    public static Sprite pillar_sprite = new Sprite(new Texture("Maps/Decor/Dungeon/pillar.png"));
    public static Sprite building1_sprite = new Sprite(new Texture("Maps/Decor/Dungeon/building1.png"));
    public static Sprite building2_sprite = new Sprite(new Texture("Maps/Decor/Dungeon/building2.png"));

    public static Animation<Sprite> dungeonshop_anim;
    public static String shopPath = "Maps/Decor/Dungeon/Shop.atlas";
    public static void loadShopAnimations() {
        TextureAtlas atlas = assetManager.get(shopPath, TextureAtlas.class);
        Sprite[] frames1 = new Sprite[4];
        for (int i = 0; i < frames1.length; i++) {
            frames1[i] = atlas.createSprite("dungeonshop" + (i+1));
        }
        dungeonshop_anim = new Animation<>(0.12f, frames1);
    }

    /**
     * VASES
     */
    public static String vasePath = "Maps/Decor/Dungeon/Vase/Vases.atlas";
    public static Sprite vase1;
    public static Sprite vase2;
    public static Sprite vase3;
    public static Animation<Sprite> break1;
    public static Animation<Sprite> break2;
    public static Animation<Sprite> break3;
    public static void loadVaseAnimations() {
        TextureAtlas atlas = assetManager.get(vasePath, TextureAtlas.class);

        Sprite[] frames1 = new Sprite[7];
        for (int i = 0; i < frames1.length; i++) {
            frames1[i] = atlas.createSprite("vaseONEbreak" + (i+1));
        }
        break1 = new Animation<>(0.06f, frames1);
        Sprite[] frames2 = new Sprite[7];
        for (int i = 0; i < frames2.length; i++) {
            frames2[i] = atlas.createSprite("vaseTWObreak" + (i+1));
        }
        break2 = new Animation<>(0.06f, frames2);
        Sprite[] frames3 = new Sprite[7];
        for (int i = 0; i < frames3.length; i++) {
            frames3[i] = atlas.createSprite("vaseTHREEbreak" + (i+1));
        }
        break3 = new Animation<>(0.06f, frames3);

        vase1 = atlas.createSprite("VaseONE");
        vase2 = atlas.createSprite("VaseTWO");
        vase3 = atlas.createSprite("VaseTHREE");
    }

    /**
     * STANDING TORCH
     */
    public static String standingTorchPath = "Maps/Decor/Dungeon/StandingTorch/StandingTorch.atlas";
    public static Animation<Sprite> standingTorch;
    public static Animation<Sprite> standingbreak1;
    public static Animation<Sprite> standingbreak2;
    public static void loadStandingTorchAnimations() {
        TextureAtlas atlas = assetManager.get(standingTorchPath, TextureAtlas.class);

        Sprite[] frames1 = new Sprite[3];
        for (int i = 0; i < frames1.length; i++) {
            frames1[i] = atlas.createSprite("standingtorch" + (i+1));
        }
        standingTorch = new Animation<>(0.06f, frames1);
        Sprite[] frames2 = new Sprite[10];
        for (int i = 0; i < frames2.length; i++) {
            frames2[i] = atlas.createSprite("breakONE" + (i+1));
        }
        standingbreak1 = new Animation<>(0.06f, frames2);
        Sprite[] frames3 = new Sprite[12];
        for (int i = 0; i < frames3.length; i++) {
            frames3[i] = atlas.createSprite("breakTWO" + (i+1));
        }
        standingbreak2 = new Animation<>(0.06f, frames3);
    }



    public static void loadAnimations() {
        loadVaseAnimations();
        loadStandingTorchAnimations();
        loadShopAnimations();
    }
    public static void loadAtlas() {
        assetManager.load(vasePath, TextureAtlas.class);
        assetManager.load(shopPath, TextureAtlas.class);
        assetManager.load(standingTorchPath, TextureAtlas.class);
    }
}
