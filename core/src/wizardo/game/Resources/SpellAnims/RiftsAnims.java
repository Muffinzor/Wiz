package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class RiftsAnims {

    public static Animation<Sprite> rift_zone_anim_arcane;
    public static Animation<Sprite> rift_explosion_anim_arcane;
    public static String rift_zone_atlas_path_arcane = "Spells/Rifts/Arcane/zone_arcane.atlas";
    public static String rift_explosion_atlas_path_arcane = "Spells/Rifts/Arcane/explosion_arcane.atlas";

    public static void loadAnimations() {
        TextureAtlas arc_projectile_atlas = assetManager.get(rift_zone_atlas_path_arcane, TextureAtlas.class);
        TextureAtlas arc_explosion_atlas = assetManager.get(rift_explosion_atlas_path_arcane, TextureAtlas.class);

        Sprite[] arc_zone = new Sprite[108];
        for (int i = 0; i < arc_zone.length; i++) {
            arc_zone[i] = arc_projectile_atlas.createSprite("rift" + (i+1));
        }
        rift_zone_anim_arcane = new Animation<>(0.025f, arc_zone);

        Sprite[] arc_explosion = new Sprite[54];
        for (int i = 0; i < arc_explosion.length; i++) {
            arc_explosion[i] = arc_explosion_atlas.createSprite("arcaneexplosion" + (i+1));
        }
        rift_explosion_anim_arcane = new Animation<>(0.02f, arc_explosion);

    }

    public static void loadAtlas() {
        assetManager.load(rift_zone_atlas_path_arcane, TextureAtlas.class);
        assetManager.load(rift_explosion_atlas_path_arcane, TextureAtlas.class);
    }
}
