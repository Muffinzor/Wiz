package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.assetManager;

public class ExplosionAnims_Elemental {

    public static Animation<Sprite> frostnova_anim;
    public static String frostnova_path = "Spells/Explosions/ElementalExplosions/FrostNova_Explosion.atlas";

    public static Animation<Sprite> arcaneExplosion_anim;
    public static String arcane_path = "Spells/Explosions/ElementalExplosions/Arcane_Explosion.atlas";


    public static void loadAnimations() {
        float frameDuration = 0.022f;


        /** FROST **/
        TextureAtlas frost = assetManager.get(frostnova_path, TextureAtlas.class);
        Sprite[] frost_frames1 = new Sprite[80];
        for (int i = 0; i < frost_frames1.length; i++) {
            frost_frames1[i] = frost.createSprite("explosion" + (i+1));
        }
        frostnova_anim = new Animation<>(frameDuration, frost_frames1);

        /** ARCANE **/
        TextureAtlas arcane = assetManager.get(arcane_path, TextureAtlas.class);
        Sprite[] arc_frames1 = new Sprite[60];
        for (int i = 0; i < arc_frames1.length; i++) {
            arc_frames1[i] = arcane.createSprite("explosion" + (i+1));
        }
        arcaneExplosion_anim = new Animation<>(frameDuration, arc_frames1);


    }

    public static void loadAtlas() {
        assetManager.load(frostnova_path, TextureAtlas.class);
        assetManager.load(arcane_path, TextureAtlas.class);

    }

    public static Animation<Sprite> getExplosionAnim(SpellUtils.Spell_Element element) {
        Animation<Sprite> anim = null;
        switch(element) {
            case FROST -> anim = frostnova_anim;
            case ARCANE -> anim = arcaneExplosion_anim;
            case FIRE -> anim = OverheatAnims.overheat_anim_fire;
        }
        return anim;
    }
}
