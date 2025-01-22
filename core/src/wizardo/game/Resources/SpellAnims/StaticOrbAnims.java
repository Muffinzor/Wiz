package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class StaticOrbAnims {

    public static Animation<Sprite> staticorb_anim_lightning;
    public static String staticorb_path_lightning = "Spells/GearSpells/StaticOrb/StaticOrb_Lightning.atlas";
    public static Animation<Sprite> staticpulse_anim_lightning;
    public static String staticpulse_path_lightning = "Spells/GearSpells/StaticOrb/StaticPulse_Lightning.atlas";

    public static Animation<Sprite> staticorb_anim_frost;
    public static String staticorb_path_frost = "Spells/GearSpells/StaticOrb/StaticOrb_Frost.atlas";
    public static Animation<Sprite> staticpulse_anim_frost;
    public static String staticpulse_path_frost = "Spells/GearSpells/StaticOrb/StaticPulse_Frost.atlas";

    public static Animation<Sprite> staticorb_anim_fire;
    public static String staticorb_path_fire = "Spells/GearSpells/StaticOrb/StaticOrb_Fire.atlas";
    public static Animation<Sprite> staticpulse_anim_fire;
    public static String staticpulse_path_fire = "Spells/GearSpells/StaticOrb/StaticPulse_Fire.atlas";

    public static Animation<Sprite> staticorb_anim_arcane;
    public static String staticorb_path_arcane = "Spells/GearSpells/StaticOrb/StaticOrb_Arcane.atlas";
    public static Animation<Sprite> staticpulse_anim_arcane;
    public static String staticpulse_path_arcane = "Spells/GearSpells/StaticOrb/StaticPulse_Arcane.atlas";



    public static void loadAnimations() {

        TextureAtlas pulse_atlas_fire = assetManager.get(staticpulse_path_fire, TextureAtlas.class);
        Sprite[] pulse_fire = new Sprite[45];
        for (int i = 0; i < pulse_fire.length; i++) {
            pulse_fire[i] = pulse_atlas_fire.createSprite("staticpulse" + (i+1));
        }
        staticpulse_anim_fire = new Animation<>(0.02f, pulse_fire);

        TextureAtlas atlas_fire = assetManager.get(staticorb_path_fire, TextureAtlas.class);
        Sprite[] orb_fire = new Sprite[200];
        for (int i = 0; i < orb_fire.length; i++) {
            orb_fire[i] = atlas_fire.createSprite("liteorb" + (i+1));
        }
        staticorb_anim_fire = new Animation<>(0.02f, orb_fire);

        TextureAtlas pulse_atlas_arcane = assetManager.get(staticpulse_path_arcane, TextureAtlas.class);
        Sprite[] pulse_arcane = new Sprite[45];
        for (int i = 0; i < pulse_arcane.length; i++) {
            pulse_arcane[i] = pulse_atlas_arcane.createSprite("staticpulse" + (i+1));
        }
        staticpulse_anim_arcane = new Animation<>(0.02f, pulse_arcane);

        TextureAtlas atlas_arcane = assetManager.get(staticorb_path_arcane, TextureAtlas.class);
        Sprite[] orb_arcane = new Sprite[200];
        for (int i = 0; i < orb_arcane.length; i++) {
            orb_arcane[i] = atlas_arcane.createSprite("liteorb" + (i+1));
        }
        staticorb_anim_arcane = new Animation<>(0.02f, orb_arcane);

        TextureAtlas pulse_atlas_frost = assetManager.get(staticpulse_path_frost, TextureAtlas.class);
        Sprite[] pulse_frost = new Sprite[45];
        for (int i = 0; i < pulse_frost.length; i++) {
            pulse_frost[i] = pulse_atlas_frost.createSprite("staticpulse" + (i+1));
        }
        staticpulse_anim_frost = new Animation<>(0.02f, pulse_frost);

        TextureAtlas atlas_frost = assetManager.get(staticorb_path_frost, TextureAtlas.class);
        Sprite[] orb_frost = new Sprite[200];
        for (int i = 0; i < orb_frost.length; i++) {
            orb_frost[i] = atlas_frost.createSprite("liteorb" + (i+1));
        }
        staticorb_anim_frost = new Animation<>(0.02f, orb_frost);

        TextureAtlas pulse_atlas_lite = assetManager.get(staticpulse_path_lightning, TextureAtlas.class);
        Sprite[] pulse_lite = new Sprite[45];
        for (int i = 0; i < pulse_lite.length; i++) {
            pulse_lite[i] = pulse_atlas_lite.createSprite("staticpulse" + (i+1));
        }
        staticpulse_anim_lightning = new Animation<>(0.02f, pulse_lite);

        TextureAtlas atlas = assetManager.get(staticorb_path_lightning, TextureAtlas.class);
        Sprite[] orb_lite = new Sprite[200];
        for (int i = 0; i < orb_lite.length; i++) {
            orb_lite[i] = atlas.createSprite("liteorb" + (i+1));
        }
        staticorb_anim_lightning = new Animation<>(0.02f, orb_lite);

    }

    public static void loadAtlas() {
        assetManager.load(staticorb_path_lightning, TextureAtlas.class);
        assetManager.load(staticpulse_path_lightning, TextureAtlas.class);
        assetManager.load(staticorb_path_frost, TextureAtlas.class);
        assetManager.load(staticpulse_path_frost, TextureAtlas.class);

        assetManager.load(staticorb_path_arcane, TextureAtlas.class);
        assetManager.load(staticpulse_path_arcane, TextureAtlas.class);
        assetManager.load(staticorb_path_fire, TextureAtlas.class);
        assetManager.load(staticpulse_path_fire, TextureAtlas.class);
    }
}
