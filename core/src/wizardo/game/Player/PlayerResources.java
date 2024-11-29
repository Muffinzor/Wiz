package wizardo.game.Player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class PlayerResources {

    public static Animation<Sprite> playerWalk;
    static {
        TextureAtlas atlas = new TextureAtlas("Player/Player_Atlas.atlas");
        Sprite[] frames = new Sprite[4];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("walk" + i);
        }
        playerWalk = new Animation<>(0.2f, frames);
    }
}
