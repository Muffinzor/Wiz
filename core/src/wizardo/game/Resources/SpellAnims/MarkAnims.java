package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

import static wizardo.game.Wizardo.assetManager;

public class MarkAnims {

    public static Animation<Sprite> detonate_anim1;
    public static Animation<Sprite> detonate_anim2;
    public static Animation<Sprite> detonate_anim3;
    public static Animation<Sprite> markAnim;
    public static String markExplosion_path = "Spells/GearSpells/Mark/Detonate.atlas";



    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(markExplosion_path, TextureAtlas.class);
        Sprite[] detonate1 = new Sprite[36];
        for (int i = 0; i < detonate1.length; i++) {
            detonate1[i] = atlas.createSprite("detonateONE" + (i+1));
        }
        detonate_anim1 = new Animation<>(0.02f, detonate1);

        Sprite[] mark = new Sprite[4];
        for (int i = 0; i < mark.length; i++) {
            mark[i] = new Sprite(new Texture("Spells/GearSpells/Mark/crosshair" + (i+1) + ".png"));
        }
        markAnim = new Animation<>(0.2f, mark);

    }

    public static void loadAtlas() {
        assetManager.load(markExplosion_path, TextureAtlas.class);
    }

    public static Animation<Sprite> getDetonateAnim() {
        Animation<Sprite> anim = null;
        int random = MathUtils.random(1,3);
        switch (random) {
            case 1 -> anim = detonate_anim1;
            case 2 -> anim = detonate_anim1;
            case 3 -> anim = detonate_anim1;
        }
        return anim;
    }
}
