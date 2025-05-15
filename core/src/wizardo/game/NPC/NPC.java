package wizardo.game.NPC;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.compression.lzma.Base;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;

public class NPC {

    public Body body;
    public Animation<Sprite> anim;
    public BaseScreen screen;
    public MapChunk chunk;
    public MapObject object;

    public float x;
    public float y;

    public NPC(MapChunk chunk, MapObject object) {
        this.screen = chunk.screen;
        this.chunk = chunk;
        this.object = object;
        Float width = object.getProperties().get("width", Float.class);
        Float height = object.getProperties().get("height", Float.class);
        x = object.getProperties().get("x", Float.class) + chunk.x_pos + width/2;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos + height/2;
        x = x/PPM;
        y = y/PPM;
    }

    public void create_body() {

    }

    public void draw_frame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(screen.stateTime,true));
        frame.setPosition(body.getPosition().x * PPM - frame.getWidth()/2f, body.getPosition().y * PPM - 10);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
    }

}
