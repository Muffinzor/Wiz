package wizardo.game.Maps;

import com.badlogic.gdx.maps.MapObject;

public abstract class LayerObject {

    public boolean initialized;
    public MapChunk chunk;
    public MapObject object;

    public LayerObject(MapChunk chunk, MapObject object) {
        this.chunk = chunk;
        this.object = object;
    }

    public abstract void update(float delta);

    public abstract void dispose();
}
