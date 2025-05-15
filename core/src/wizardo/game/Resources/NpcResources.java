package wizardo.game.Resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class NpcResources {

    public static Animation<Sprite> witch_anim;

    static {
        TextureAtlas atlas = new TextureAtlas("NPCs/Witch.atlas");
        Sprite[] witch_frames = new Sprite[4];
        for (int i = 0; i < witch_frames.length; i++) {
            witch_frames[i] = atlas.createSprite("witch" + (i+1));
        }
        witch_anim = new Animation<>(0.25f, witch_frames);
    }
}
