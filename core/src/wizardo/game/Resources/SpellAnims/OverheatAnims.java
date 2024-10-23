package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class OverheatAnims {


    public static Animation<Sprite> overheat_anim_fire;
    public static String overheat_atlas_path_fire = "Spells/Overheat/fire.atlas";

    public static void loadAnimations() {

        TextureAtlas fire = assetManager.get(overheat_atlas_path_fire, TextureAtlas.class);

        Sprite[] fire_frames = new Sprite[51];
        for (int i = 0; i < fire_frames.length; i++) {
            fire_frames[i] = fire.createSprite("overheat" + (i+1));
        }
        overheat_anim_fire = new Animation<>(0.015f, fire_frames);

    }

    public static void loadAtlas() {
        assetManager.load(overheat_atlas_path_fire, TextureAtlas.class);
    }
}
