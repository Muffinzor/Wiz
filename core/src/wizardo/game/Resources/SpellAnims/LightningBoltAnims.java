package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

import static wizardo.game.Wizardo.assetManager;

public class LightningBoltAnims {


    public static Animation<Sprite> lightningbolt_anim;
    public static String lightningbolt_path = "Spells/LightningBolt/LightningBolt.atlas";


    public static Animation<Sprite> lightningboltExplosion_anim1;
    public static Animation<Sprite> lightningboltExplosion_anim2;
    public static Animation<Sprite> lightningboltExplosion_anim3;
    public static String lightningboltExplosion_path = "Spells/LightningBolt/LightningBoltExplosion.atlas";



    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(lightningbolt_path, TextureAtlas.class);
        TextureAtlas explosion_atlas = assetManager.get(lightningboltExplosion_path, TextureAtlas.class);

        Sprite[] litebolt_frames = new Sprite[120];
        for (int i = 0; i < litebolt_frames.length; i++) {
            litebolt_frames[i] = atlas.createSprite("bolt" + (i+1));
        }
        lightningbolt_anim = new Animation<>(0.02f, litebolt_frames);

        Sprite[] explosion1 = new Sprite[39];
        for (int i = 0; i < explosion1.length; i++) {
            explosion1[i] = explosion_atlas.createSprite("bolt" + (i+1));
        }
        lightningboltExplosion_anim1 = new Animation<>(0.016f, explosion1);

        Sprite[] explosion2 = new Sprite[39];
        for (int i = 0; i < explosion2.length; i++) {
            explosion2[i] = explosion_atlas.createSprite("boltTWO" + (i+1));
        }
        lightningboltExplosion_anim2 = new Animation<>(0.016f, explosion2);

        Sprite[] explosion3 = new Sprite[39];
        for (int i = 0; i < explosion3.length; i++) {
            explosion3[i] = explosion_atlas.createSprite("boltTHREE" + (i+1));
        }
        lightningboltExplosion_anim3 = new Animation<>(0.016f, explosion3);

    }

    public static void loadAtlas() {
        assetManager.load(lightningbolt_path, TextureAtlas.class);
        assetManager.load(lightningboltExplosion_path, TextureAtlas.class);
    }

    public static Animation<Sprite> getExplosionAnim() {
        Animation<Sprite> anim = null;
        int random = MathUtils.random(1,3);
        switch (random) {
            case 1 -> anim = lightningboltExplosion_anim1;
            case 2 -> anim = lightningboltExplosion_anim2;
            case 3 -> anim = lightningboltExplosion_anim3;
        }
        return anim;
    }
}
