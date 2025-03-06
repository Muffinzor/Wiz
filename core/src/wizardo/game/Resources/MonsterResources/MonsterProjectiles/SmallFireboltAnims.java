package wizardo.game.Resources.MonsterResources.MonsterProjectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class SmallFireboltAnims {

    public static Animation<Sprite> small_firebolt_anim;
    public static String small_firebolt_path = "MonsterProjectiles/Small_Firebolt/FireProjectile.atlas";


    public static void loadAnimations() {

        TextureAtlas bolt_frames = assetManager.get(small_firebolt_path, TextureAtlas.class);
        Sprite[] bolt = new Sprite[20];
        for (int i = 0; i < bolt.length; i++) {
            bolt[i] = bolt_frames.createSprite("projectile" + (i+1));
        }
        small_firebolt_anim = new Animation<>(0.015f, bolt);

    }

    public static void loadAtlas() {
        assetManager.load(small_firebolt_path, TextureAtlas.class);
    }

}
