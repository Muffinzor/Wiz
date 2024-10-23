package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class BlizzardAnims {

    public static Animation<Sprite> blizzard_hit_anim;

    public static String blizzard_hit_anim_path = "Spells/Blizzard/blizzard_hit.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(blizzard_hit_anim_path, TextureAtlas.class);

        Sprite[] blizz_hit = new Sprite[16];
        for (int i = 0; i < blizz_hit.length; i++) {
            blizz_hit[i] = atlas.createSprite("blizz_hit" + (i+1));
        }
        blizzard_hit_anim = new Animation<>(0.02f, blizz_hit);


    }

    public static void loadAtlas() {
        assetManager.load(blizzard_hit_anim_path, TextureAtlas.class);
    }
}
