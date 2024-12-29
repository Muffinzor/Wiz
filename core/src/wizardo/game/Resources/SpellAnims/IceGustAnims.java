package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

import static wizardo.game.Wizardo.assetManager;

public class IceGustAnims {

    public static Animation<Sprite> ice_gust1;
    public static Animation<Sprite> ice_gust2;
    public static Animation<Sprite> ice_gust3;
    public static String ice_gust_path = "Spells/GearSpells/SnowGust/SnowGust.atlas";


    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(ice_gust_path, TextureAtlas.class);

        Sprite[] gust_frames1 = new Sprite[60];
        for (int i = 0; i < gust_frames1.length; i++) {
            gust_frames1[i] = atlas.createSprite("gustONE" + (i+1));
        }
        ice_gust1 = new Animation<>(0.02f, gust_frames1);

        Sprite[] gust_frames2 = new Sprite[60];
        for (int i = 0; i < gust_frames2.length; i++) {
            gust_frames2[i] = atlas.createSprite("gustTWO" + (i+1));
        }
        ice_gust2 = new Animation<>(0.02f, gust_frames2);

        Sprite[] gust_frames3 = new Sprite[60];
        for (int i = 0; i < gust_frames3.length; i++) {
            gust_frames3[i] = atlas.createSprite("gustTHREE" + (i+1));
        }
        ice_gust3 = new Animation<>(0.02f, gust_frames3);

    }

    public static void loadAtlas() {
        assetManager.load(ice_gust_path, TextureAtlas.class);
    }

    public static Animation<Sprite> getIceGustAnim() {
        Animation<Sprite> anim = null;

        int random = MathUtils.random(1,3);
        switch (random) {
            case 1 -> anim = ice_gust1;
            case 2 -> anim = ice_gust2;
            case 3 -> anim = ice_gust3;
        }


        return anim;
    }
}
