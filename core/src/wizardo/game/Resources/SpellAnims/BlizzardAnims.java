package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.assetManager;

public class BlizzardAnims {

    public static Animation<Sprite> blizzard_hit_anim1;
    public static Animation<Sprite> blizzard_hit_anim2;
    public static Animation<Sprite> blizzard_hit_anim3;
    public static String blizzard_hit_anim_path = "Spells/Blizzard/Blizzard_Hit.atlas";

    public static Animation<Sprite> blizzard_hit_anim_arcane;
    public static String blizzard_hit_anim_path_arcane = "Spells/Blizzard/blizzard_hit_arcane.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(blizzard_hit_anim_path, TextureAtlas.class);

        Sprite[] blizz_hit1 = new Sprite[45];
        for (int i = 0; i < blizz_hit1.length; i++) {
            blizz_hit1[i] = atlas.createSprite("hitONE" + (i+1));
        }
        blizzard_hit_anim1 = new Animation<>(0.02f, blizz_hit1);

        Sprite[] blizz_hit2 = new Sprite[45];
        for (int i = 0; i < blizz_hit2.length; i++) {
            blizz_hit2[i] = atlas.createSprite("hitTWO" + (i+1));
        }
        blizzard_hit_anim2 = new Animation<>(0.02f, blizz_hit2);

        Sprite[] blizz_hit3 = new Sprite[45];
        for (int i = 0; i < blizz_hit3.length; i++) {
            blizz_hit3[i] = atlas.createSprite("hitTHREE" + (i+1));
        }
        blizzard_hit_anim3 = new Animation<>(0.02f, blizz_hit3);



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

    public static Animation<Sprite> getHitAnim(SpellUtils.Spell_Element anim_element) {
        Animation<Sprite> anim = null;
        switch (anim_element) {
            case FROST -> {
                int random = MathUtils.random(1,3);
                switch (random) {
                    case 1 -> anim = blizzard_hit_anim1;
                    case 2 -> anim = blizzard_hit_anim2;
                    case 3 -> anim = blizzard_hit_anim3;
                }
            }
        }
        return anim;
    }
}
