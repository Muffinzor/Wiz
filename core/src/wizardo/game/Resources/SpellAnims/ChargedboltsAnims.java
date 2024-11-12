package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class ChargedboltsAnims {

    public static Animation<Sprite> chargedbolt_lightning_anim;
    public static String chargedbolt_atlas_path_lightning = "Spells/ChargedBolts/lightning.atlas";

    public static Animation<Sprite> chargedbolt_frost_anim;
    public static String chargedbolt_atlas_path_frost = "Spells/ChargedBolts/frost.atlas";

    public static Animation<Sprite> chargedbolt_arcane_anim;
    public static String chargedbolt_atlas_path_arcane = "Spells/ChargedBolts/arcane.atlas";

    public static void loadAnimations() {

        TextureAtlas lightning_atlas = assetManager.get(chargedbolt_atlas_path_lightning, TextureAtlas.class);

        Sprite[] lightning_frames = new Sprite[21];
        for (int i = 0; i < lightning_frames.length; i++) {
            lightning_frames[i] = lightning_atlas.createSprite("charged_bolt" + (i+1));
        }
        chargedbolt_lightning_anim = new Animation<>(0.03f, lightning_frames);

        TextureAtlas frost_atlas = assetManager.get(chargedbolt_atlas_path_frost, TextureAtlas.class);

        Sprite[] frost_frames = new Sprite[21];
        for (int i = 0; i < frost_frames.length; i++) {
            frost_frames[i] = frost_atlas.createSprite("charged_bolt" + (i+1));
        }
        chargedbolt_frost_anim = new Animation<>(0.03f, frost_frames);

        TextureAtlas arcane_atlas = assetManager.get(chargedbolt_atlas_path_arcane, TextureAtlas.class);

        Sprite[] arcane_frames = new Sprite[21];
        for (int i = 0; i < arcane_frames.length; i++) {
            arcane_frames[i] = arcane_atlas.createSprite("charged_bolt" + (i+1));
        }
        chargedbolt_arcane_anim = new Animation<>(0.03f, arcane_frames);

    }

    public static void loadAtlas() {
        assetManager.load(chargedbolt_atlas_path_lightning, TextureAtlas.class);
        assetManager.load(chargedbolt_atlas_path_frost, TextureAtlas.class);
        assetManager.load(chargedbolt_atlas_path_arcane, TextureAtlas.class);
    }
}
