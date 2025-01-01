package wizardo.game.Resources.MonsterResources.MonsterProjectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class MonsterExplosions {

    public static Animation<Sprite> distortion_explosion_red;
    public static String distortion_explosion_red_path = "MonsterProjectiles/Distortion_Explosion/DistortionExplosion.atlas";



    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(distortion_explosion_red_path, TextureAtlas.class);
        Sprite[] explo = new Sprite[45];
        for (int i = 0; i < explo.length; i++) {
            explo[i] = atlas.createSprite("distortion" + (i+1));
        }
        distortion_explosion_red = new Animation<>(0.02f, explo);

    }

    public static void loadAtlas() {
        assetManager.load(distortion_explosion_red_path, TextureAtlas.class);
    }
}
