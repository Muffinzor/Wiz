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
    public static String frozenorb_atlas_path_frost = "Spells/Frozenorb/Frozen_Orb.atlas";
    public static String frozenorb_atlas_path_fire = "Spells/Frozenorb/FireOrb.atlas";
    public static String frozenorb_atlas_path_arcane = "Spells/Frozenorb/Arcane_Orb.atlas";
    public static String frozenorb_atlas_path_lightning = "Spells/Frozenorb/lightning_orb.atlas";

    public static Animation<Sprite> orbit_anim;

    public static void loadAnimations() {
        TextureAtlas frost_atlas = assetManager.get(frozenorb_atlas_path_frost, TextureAtlas.class);
        TextureAtlas fire_atlas = assetManager.get(frozenorb_atlas_path_fire, TextureAtlas.class);
        TextureAtlas arcane_atlas = assetManager.get(frozenorb_atlas_path_arcane, TextureAtlas.class);
        TextureAtlas lightning_atlas = assetManager.get(frozenorb_atlas_path_lightning, TextureAtlas.class);

        Sprite[] frost_frames = new Sprite[203];
        for (int i = 0; i < frost_frames.length; i++) {
            frost_frames[i] = frost_atlas.createSprite("frozen_orb" + (i + 1));
        }
        frozenorb_anim_frost = new Animation<>(0.032f, frost_frames);

        Sprite[] fire_frames = new Sprite[203];
        for (int i = 0; i < fire_frames.length; i++) {
            fire_frames[i] = fire_atlas.createSprite("frozen_orb" + (i + 1));
        }
        frozenorb_anim_fire = new Animation<>(0.032f, fire_frames);

        Sprite[] arcane_frames = new Sprite[203];
        for (int i = 0; i < arcane_frames.length; i++) {
            arcane_frames[i] = arcane_atlas.createSprite("frozen_orb" + (i + 1));
        }
        frozenorb_anim_arcane = new Animation<>(0.03f, arcane_frames);

        Sprite[] lightning_frames = new Sprite[8];
        for (int i = 0; i < lightning_frames.length; i++) {
            lightning_frames[i] = lightning_atlas.createSprite("lightning" + (i + 1));
        }
        frozenorb_anim_lightning = new Animation<>(0.03f, lightning_frames);

        Sprite[] orbit_frames = new Sprite[22];
        for (int i = 35; i < orbit_frames.length + 35; i++) {
            orbit_frames[i - 35] = frost_atlas.createSprite("frozen_orb" + (i + 1));
        }
        orbit_anim = new Animation<>(0.032f, orbit_frames);

    }

    public static void loadAtlas() {
        assetManager.load(frozenorb_atlas_path_frost, TextureAtlas.class);
        assetManager.load(frozenorb_atlas_path_fire, TextureAtlas.class);
        assetManager.load(frozenorb_atlas_path_arcane, TextureAtlas.class);
        assetManager.load(frozenorb_atlas_path_lightning, TextureAtlas.class);

    }
}
