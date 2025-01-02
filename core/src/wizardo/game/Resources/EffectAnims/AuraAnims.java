package wizardo.game.Resources.EffectAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class AuraAnims {

    public static Animation<Sprite> vogon_aura;
    public static String vogon_aura_path = "Spells/GearSpells/VogonAura/VogonAura.atlas";

    public static Animation<Sprite> flame_aura;
    public static Animation<Sprite> flame_aura2;
    public static String flame_aura_path = "Spells/GearSpells/FireAura/FireAura.atlas";

    public static void loadAnimations() {

        TextureAtlas fireatlas = assetManager.get(flame_aura_path, TextureAtlas.class);
        Sprite[] fireframes = new Sprite[30];
        for (int i = 0; i < 30; i++) {
            fireframes[i] = fireatlas.createSprite("flame" + (i+1));
        }
        flame_aura = new Animation<>(0.02f, fireframes);
        Sprite[] fireframes2 = new Sprite[30];
        for (int i = 0; i < 30; i++) {
            fireframes2[i] = fireatlas.createSprite("flame" + (i+31));
        }
        flame_aura2 = new Animation<>(0.02f, fireframes2);

        TextureAtlas atlas = assetManager.get(vogon_aura_path, TextureAtlas.class);
        Sprite[] frames = new Sprite[244];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("aura" + (i+1));
        }
        vogon_aura = new Animation<>(0.04f, frames);

    }

    public static void loadAtlas() {
        assetManager.load(vogon_aura_path, TextureAtlas.class);
        assetManager.load(flame_aura_path, TextureAtlas.class);
    }
}
