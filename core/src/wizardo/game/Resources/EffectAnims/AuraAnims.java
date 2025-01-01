package wizardo.game.Resources.EffectAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class AuraAnims {

    public static Animation<Sprite> vogon_aura;
    public static String vogon_aura_path = "Spells/GearSpells/VogonAura/VogonAura.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(vogon_aura_path, TextureAtlas.class);

        Sprite[] frames = new Sprite[244];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("aura" + (i+1));
        }
        vogon_aura = new Animation<>(0.04f, frames);

    }

    public static void loadAtlas() {
        assetManager.load(vogon_aura_path, TextureAtlas.class);
    }
}
