package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class ArcaneMissileAnims {

    public static Animation<Sprite> arcanemissile_arcane_anim;

    public static String arcaneMissile_atlas_path = "Spells/ArcaneMissile/arcane_missile.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(arcaneMissile_atlas_path, TextureAtlas.class);

        Sprite[] missile_frames = new Sprite[8];
        for (int i = 0; i < missile_frames.length; i++) {
            missile_frames[i] = atlas.createSprite("missile" + (i+1));
        }
        arcanemissile_arcane_anim = new Animation<>(0.03f, missile_frames);


    }

    public static void loadAtlas() {
        assetManager.load(arcaneMissile_atlas_path, TextureAtlas.class);
    }
}
