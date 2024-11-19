package wizardo.game.Resources.SpellAnims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class LightningHandsAnims {

    public static Animation<Sprite> lightning_branch_anim;
    public static String lightning_branch_path = "Spells/LightningHands/lightning_branch.atlas";

    public static void loadAnimations() {

        TextureAtlas atlas = assetManager.get(lightning_branch_path, TextureAtlas.class);

        Sprite[] lightning = new Sprite[19];
        for (int i = 0; i < lightning.length; i++) {
            lightning[i] = atlas.createSprite("long" + (i+1));
        }
        lightning_branch_anim = new Animation<>(0.03f, lightning);

    }

    public static void loadAtlas() {
        assetManager.load(lightning_branch_path, TextureAtlas.class);
    }

}
