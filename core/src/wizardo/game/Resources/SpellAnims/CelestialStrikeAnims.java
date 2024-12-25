package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class CelestialStrikeAnims {


    public static Animation<Sprite> celestial_strike_anim;
    public static Animation<Sprite> celestial_strike_explosion_anim;

    public static String celestial_strike_anim_path = "Spells/CelestialStrike/celestial_strike.atlas";
    public static String celestial_strike_explosion_path = "Spells/CelestialStrike/CelestialStrike_Explosion.atlas";

    public static void loadAnimations() {

        TextureAtlas strike_atlas = assetManager.get(celestial_strike_anim_path, TextureAtlas.class);
        Sprite[] strike_frames = new Sprite[90];
        for (int i = 0; i < strike_frames.length; i++) {
            strike_frames[i] = strike_atlas.createSprite("thunder" + (i+1));
        }
        celestial_strike_anim = new Animation<>(0.015f, strike_frames);

        TextureAtlas explosion_atlas = assetManager.get(celestial_strike_explosion_path, TextureAtlas.class);
        Sprite[] explosion_frames = new Sprite[60];
        for (int i = 0; i < explosion_frames.length; i++) {
            explosion_frames[i] = explosion_atlas.createSprite("explosion" + (i+1));
        }
        celestial_strike_explosion_anim = new Animation<>(0.015f, explosion_frames);


    }

    public static void loadAtlas() {
        assetManager.load(celestial_strike_anim_path, TextureAtlas.class);
        assetManager.load(celestial_strike_explosion_path, TextureAtlas.class);
    }
}
