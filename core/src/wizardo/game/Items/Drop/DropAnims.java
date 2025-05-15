package wizardo.game.Items.Drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static wizardo.game.Wizardo.assetManager;

public class DropAnims {

    public static Animation<Sprite> dualGem_anim;
    public static Animation<Sprite> tripleGem_anim;
    public static String gem_atlas_path = "Items/Drops/MixingReagent/Gems.atlas";
    public static Sprite dualGem_sprite;
    public static Sprite tripleGem_sprite;

    public static void loadAnimations() {

        TextureAtlas gem_atlas = assetManager.get(gem_atlas_path, TextureAtlas.class);
        Sprite[] dual_frames = new Sprite[36];
        for (int i = 0; i < dual_frames.length; i++) {
            dual_frames[i] = gem_atlas.createSprite("gold" + (i + 1));
        }
        dualGem_anim = new Animation<>(0.03f, dual_frames);

        Sprite[] triple_frames = new Sprite[36];
        for (int i = 0; i < triple_frames.length; i++) {
            triple_frames[i] = gem_atlas.createSprite("astral" + (i+1));
        }
        tripleGem_anim = new Animation<>(0.03f, triple_frames);

        dualGem_sprite = new Sprite();
        dualGem_sprite.set(dual_frames[0]);
        tripleGem_sprite = new Sprite();
        tripleGem_sprite.set(triple_frames[0]);
    }

    public static void loadAtlas() {
        assetManager.load(gem_atlas_path, TextureAtlas.class);
    }
}
