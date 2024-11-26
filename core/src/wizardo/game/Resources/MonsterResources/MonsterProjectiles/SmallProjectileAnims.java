package wizardo.game.Resources.MonsterResources.MonsterProjectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class SmallProjectileAnims {

    public static Sprite small_green =  new Sprite(new Texture("MonsterProjectiles/Small_Proj/small_green.png"));

    public static Animation<Sprite> green_hit_anim;
    public static String green_hit_path = "MonsterProjectiles/Small_Proj/green_hit.atlas";


    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(green_hit_path, TextureAtlas.class);

        Sprite[] green_hit = new Sprite[60];
        for (int i = 0; i < green_hit.length; i++) {
            green_hit[i] = atlas.createSprite("green" + (i+1));
        }
        green_hit_anim = new Animation<>(0.015f, green_hit);

    }

    public static void loadAtlas() {
        assetManager.load(green_hit_path, TextureAtlas.class);
    }

}
