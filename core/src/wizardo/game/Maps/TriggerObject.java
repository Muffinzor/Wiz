package wizardo.game.Maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.MapGeneration.MapChunk;

public abstract class TriggerObject extends LayerObject{

    public Body triggerBody;
    
    public TriggerObject(MapChunk chunk, MapObject object) {
        super(chunk, object);
        stateTime = 0;
    }

    public abstract void handleCollision();

    public void trigger() {

    }
}
