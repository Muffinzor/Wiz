package wizardo.game.Maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class TriggerObject extends LayerObject{

    public Body body;
    public float stateTime;
    
    public TriggerObject(MapChunk chunk, MapObject object) {
        super(chunk, object);
        stateTime = 0;
    }

    public abstract void handleCollision();
}
