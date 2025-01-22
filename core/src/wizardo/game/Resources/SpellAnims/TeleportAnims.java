package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class TeleportAnims {

    public static Animation<Sprite> teleport_anim;

    public static String teleport_path = "Spells/GearSpells/TeleportMonster/Teleport.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(teleport_path, TextureAtlas.class);

        Sprite[] frames = new Sprite[36];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("teleport" + (i+1));
        }
        teleport_anim = new Animation<>(0.016f, frames);

    }

    public static void loadAtlas() {
        assetManager.load(teleport_path, TextureAtlas.class);
    }
}
