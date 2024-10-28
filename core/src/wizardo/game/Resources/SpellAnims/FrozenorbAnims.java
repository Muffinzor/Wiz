package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class FrozenorbAnims {

    public static Animation<Sprite> frozenorb_anim_frost;
    public static Animation<Sprite> frozenorb_anim_fire;
    public static Animation<Sprite> frozenorb_anim_arcane;
    public static Animation<Sprite> frozenorb_anim_lightning;
    public static String frozenorb_atlas_path_frost = "Spells/Frozenorb/frost_orb.atlas";
    public static String frozenorb_atlas_path_fire = "Spells/Frozenorb/fire_orb.atlas";
    public static String frozenorb_atlas_path_arcane = "Spells/Frozenorb/arcane_orb.atlas";
    public static String frozenorb_atlas_path_lightning = "Spells/Frozenorb/lightning_orb.atlas";

    public static void loadAnimations() {
        TextureAtlas frost_atlas = assetManager.get(frozenorb_atlas_path_frost, TextureAtlas.class);
        TextureAtlas fire_atlas = assetManager.get(frozenorb_atlas_path_fire, TextureAtlas.class);
        TextureAtlas arcane_atlas = assetManager.get(frozenorb_atlas_path_arcane, TextureAtlas.class);
        TextureAtlas lightning_atlas = assetManager.get(frozenorb_atlas_path_lightning, TextureAtlas.class);

        Sprite[] frost_frames = new Sprite[4];
        for (int i = 0; i < frost_frames.length; i++) {
            frost_frames[i] = frost_atlas.createSprite("frost" + (i + 1));
        }
        frozenorb_anim_frost = new Animation<>(0.03f, frost_frames);

        Sprite[] fire_frames = new Sprite[4];
        for (int i = 0; i < fire_frames.length; i++) {
            fire_frames[i] = fire_atlas.createSprite("fireorb" + (i + 1));
        }
        frozenorb_anim_fire = new Animation<>(0.03f, fire_frames);

        Sprite[] arcane_frames = new Sprite[4];
        for (int i = 0; i < arcane_frames.length; i++) {
            arcane_frames[i] = arcane_atlas.createSprite("arcane" + (i + 1));
        }
        frozenorb_anim_arcane = new Animation<>(0.03f, arcane_frames);

        Sprite[] lightning_frames = new Sprite[8];
        for (int i = 0; i < lightning_frames.length; i++) {
            lightning_frames[i] = lightning_atlas.createSprite("lightning" + (i + 1));
        }
        frozenorb_anim_lightning = new Animation<>(0.03f, lightning_frames);

    }

    public static void loadAtlas() {
        assetManager.load(frozenorb_atlas_path_frost, TextureAtlas.class);
        assetManager.load(frozenorb_atlas_path_fire, TextureAtlas.class);
        assetManager.load(frozenorb_atlas_path_arcane, TextureAtlas.class);
        assetManager.load(frozenorb_atlas_path_lightning, TextureAtlas.class);

    }
}
