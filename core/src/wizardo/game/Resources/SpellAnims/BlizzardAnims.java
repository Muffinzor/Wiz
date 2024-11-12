package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class BlizzardAnims {

    public static Animation<Sprite> blizzard_hit_anim;
    public static String blizzard_hit_anim_path = "Spells/Blizzard/blizzard_hit.atlas";

    public static Animation<Sprite> blizzard_hit_anim_arcane;
    public static String blizzard_hit_anim_path_arcane = "Spells/Blizzard/blizzard_hit_arcane.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(blizzard_hit_anim_path, TextureAtlas.class);

        Sprite[] blizz_hit = new Sprite[16];
        for (int i = 0; i < blizz_hit.length; i++) {
            blizz_hit[i] = atlas.createSprite("blizz_hit" + (i+1));
        }
        blizzard_hit_anim = new Animation<>(0.02f, blizz_hit);

        TextureAtlas atlas_arcane = assetManager.get(blizzard_hit_anim_path_arcane, TextureAtlas.class);

        Sprite[] blizz_hit_arcane = new Sprite[16];
        for (int i = 0; i < blizz_hit_arcane.length; i++) {
            blizz_hit_arcane[i] = atlas_arcane.createSprite("blizz_hit" + (i+1));
        }
        blizzard_hit_anim_arcane = new Animation<>(0.02f, blizz_hit_arcane);


    }

    public static void loadAtlas() {
        assetManager.load(blizzard_hit_anim_path, TextureAtlas.class);
        assetManager.load(blizzard_hit_anim_path_arcane, TextureAtlas.class);
    }
}
