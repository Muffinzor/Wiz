package wizardo.game.Resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class PlayerResources {

    public static Sprite shieldBar = new Sprite(new Texture("Player/ShieldBar.png"));
    public static Sprite shieldEffect = new Sprite(new Texture("Player/ShieldEffect.png"));

    public static Animation<Sprite> playerWalk;
    static {
        TextureAtlas atlas = new TextureAtlas("Player/Player_thinLine.atlas");
        Sprite[] frames = new Sprite[4];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("walk_thin" + i);
        }
        playerWalk = new Animation<>(0.2f, frames);
    }
}
