package wizardo.game.Resources.DecorResources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

import static wizardo.game.Wizardo.assetManager;

public class DungeonDecorResources {

    public static Sprite pillar_sprite = new Sprite(new Texture("Maps/Decor/Dungeon/pillar_type2.png"));
    public static Sprite building1_sprite = new Sprite(new Texture("Maps/Decor/Dungeon/building1_type2.png"));

    public static Sprite squareBuilding_sprite1 = new Sprite(new Texture("Maps/Decor/Dungeon/building2.png"));

    public static Sprite crypt_sprite1 = new Sprite(new Texture("Maps/Decor/Dungeon/building2_type1.png"));
    public static Sprite crypt_sprite2 = new Sprite(new Texture("Maps/Decor/Dungeon/building2_type2.png"));
    public static Sprite crypt_sprite3 = new Sprite(new Texture("Maps/Decor/Dungeon/building2_type3.png"));

    public static Sprite goldStatue1 = new Sprite(new Texture("Maps/Decor/Dungeon/Statue1.png"));
    public static Sprite goldStatue2 = new Sprite(new Texture("Maps/Decor/Dungeon/Statue2.png"));
    public static Sprite goldStatue3 = new Sprite(new Texture("Maps/Decor/Dungeon/Statue3.png"));

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

    /**
     * FIRE PILLAR
     */
    public static String firePillarPath = "Maps/Decor/Dungeon/FirePillar/GreenFirePillar.atlas";
    public static Animation<Sprite> firePillar;
    public static void loadFirePillarAnimations() {
        TextureAtlas atlas = assetManager.get(firePillarPath, TextureAtlas.class);
        Sprite[] frames1 = new Sprite[19];
        for (int i = 0; i < frames1.length; i++) {
            frames1[i] = atlas.createSprite("fire" + (i+1));
        }
        firePillar = new Animation<>(0.06f, frames1);
    }

    /**
     * BRAZIER
     */
    public static String brazierPath = "Maps/Decor/Dungeon/Brazier/Brazier.atlas";
    public static Animation<Sprite> brazierAnim;
    public static void loadBrazierAnimations() {
        TextureAtlas atlas = assetManager.get(brazierPath, TextureAtlas.class);
        Sprite[] frames1 = new Sprite[8];
        for (int i = 0; i < frames1.length; i++) {
            frames1[i] = atlas.createSprite("brazier" + (i+1));
        }
        brazierAnim = new Animation<>(0.06f, frames1);
    }


    /**
     * WALL DECORATIONS
     */

    public static Sprite painting1 = new Sprite(new Texture("Maps/Decor/Dungeon/BuildingDeco/painting1.png"));
    public static Sprite painting2 = new Sprite(new Texture("Maps/Decor/Dungeon/BuildingDeco/painting2.png"));
    public static Sprite chains_single = new Sprite(new Texture("Maps/Decor/Dungeon/BuildingDeco/chains_single.png"));
    public static Sprite chains_skelly = new Sprite(new Texture("Maps/Decor/Dungeon/BuildingDeco/chains_skelly.png"));
    public static Sprite chains_double = new Sprite(new Texture("Maps/Decor/Dungeon/BuildingDeco/chains_double.png"));
    public static Sprite gargoyleFace = new Sprite(new Texture("Maps/Decor/Dungeon/BuildingDeco/wall_gargoyle_face.png"));

    public static Sprite redFlag = new Sprite(new Texture("Maps/Decor/Dungeon/Flags/Red.png"));
    public static Sprite blueFlag = new Sprite(new Texture("Maps/Decor/Dungeon/Flags/Blue.png"));
    public static Sprite goldenFlag = new Sprite(new Texture("Maps/Decor/Dungeon/Flags/Golden.png"));
    public static Sprite whiteFlag = new Sprite(new Texture("Maps/Decor/Dungeon/Flags/White.png"));
    public static Sprite blackFlag = new Sprite(new Texture("Maps/Decor/Dungeon/Flags/Black.png"));
    public static Sprite greenFlag = new Sprite(new Texture("Maps/Decor/Dungeon/Flags/Green.png"));
    public static Sprite purpleFlag = new Sprite(new Texture("Maps/Decor/Dungeon/Flags/Purple.png"));

    public static Sprite getRandomFlag() {
        Sprite flag = null;
        int random = MathUtils.random(1,7);
        switch (random) {
            case 1 -> flag = redFlag;
            case 2 -> flag = blueFlag;
            case 3 -> flag = goldenFlag;
            case 4 -> flag = greenFlag;
            case 5 -> flag = whiteFlag;
            case 6 -> flag = blackFlag;
            case 7 -> flag = purpleFlag;
        }
        return flag;
    }

    public static Sprite getWallDeco(boolean singleCenter) {
        Sprite sprite = null;
        int random;
        if(singleCenter) {
            random = MathUtils.random(6,7);
        } else {
            random = MathUtils.random(1,5);
        }
        switch (random) {
            case 1 -> sprite = painting1;
            case 2 -> sprite = painting2;
            case 3 -> sprite = chains_single;
            case 4 -> sprite = chains_double;
            case 5 -> sprite = getRandomFlag();
            case 6 -> sprite = chains_skelly;
            case 7 -> sprite = gargoyleFace;
        }
        return sprite;
    }






    public static void loadAnimations() {
        loadVaseAnimations();
        loadStandingTorchAnimations();
        loadShopAnimations();
        loadFirePillarAnimations();
        loadBrazierAnimations();
    }
    public static void loadAtlas() {
        assetManager.load(vasePath, TextureAtlas.class);
        assetManager.load(shopPath, TextureAtlas.class);
        assetManager.load(standingTorchPath, TextureAtlas.class);
        assetManager.load(firePillarPath, TextureAtlas.class);
        assetManager.load(brazierPath, TextureAtlas.class);
    }
}
