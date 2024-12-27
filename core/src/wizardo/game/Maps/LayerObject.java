package wizardo.game.Maps;

import com.badlogic.gdx.maps.MapObject;
import wizardo.game.Maps.MapGeneration.MapChunk;

public abstract class LayerObject {

    public float x;
    public float y;

    public boolean initialized;
    public boolean collided;
    public MapChunk chunk;
    public MapObject object;
    public float stateTime;

    public LayerObject(MapChunk chunk, MapObject object) {
        this.chunk = chunk;
        this.object = object;
    }

    public abstract void update(float delta);

    public abstract void dispose();

    public void handleCollision() {

    }
}
