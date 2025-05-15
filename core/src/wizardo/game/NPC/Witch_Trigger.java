package wizardo.game.NPC;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Maps.TriggerObject;

public class Witch_Trigger extends TriggerObject {

    Vector2 position;

    public Witch_Trigger(MapChunk chunk, MapObject object) {
        super(chunk, object);
        position = new Vector2(x, y);
        createTriggerBody();
    }

    @Override
    public void update(float delta) {

    }

    public void createTriggerBody() {
        triggerBody = MapUtils.createEventTriggerBodyFromMapObject(chunk, object, 35);
        triggerBody.setUserData(this);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void handleCollision() {

    }

    @Override
    public void trigger() {
        System.out.println("KEK");
    }
}
