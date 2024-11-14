package wizardo.game.Maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import wizardo.game.Maps.MapGeneration.MapChunk;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Utils.Contacts.Masks.*;
import static wizardo.game.Wizardo.world;

public class MapUtils {

    /**
     *
     * @param chunk
     * @param object
     * @param radius
     * @return
     */
    public static Body createCircleDecorBody(MapChunk chunk, MapObject object, float radius) {
        Body body;
        BodyDef def = new BodyDef();

        float x = object.getProperties().get("x", Float.class) + chunk.x_pos;
        float y = object.getProperties().get("y", Float.class) + chunk.y_pos;

        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(x/PPM, y/PPM);
        def.fixedRotation = true;
        body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = DECOR;
        fixtureDef.filter.maskBits = DECOR_MASK;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    /**
     * Creates an impassable body to the shape of the Object passed in argument,
     * adds it to the chunk's list of bodies to keep its reference
     * @param chunk the tilemap chunk where the object is found
     * @param object the RectangleMapObject
     */
    public static void createRectangleObstacleBody(MapChunk chunk, RectangleMapObject object) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        Rectangle rectangle = object.getRectangle();
        float width = rectangle.width;
        float height = rectangle.height;
        float x = rectangle.x + chunk.x_pos + width / 2;
        float y = rectangle.y + chunk.y_pos + height / 2; //center position
        bodyDef.position.set(x / PPM, y / PPM);
        Body body = world.createBody(bodyDef);
        body.setUserData("Obstacle");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = OBSTACLE;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);
        shape.dispose();
        chunk.bodies.add(body);
    }

    /**
     * Non-collision body to trigger events when the pawn moves over it
     * @param chunk this chunk, used for position
     * @param object the object from the tilemap, which the position will be taken from
     * @param radius radius of the trigger zone, from object center
     * @return a body with the TRIGGER mask
     */
    public static Body createEventTriggerBody(MapChunk chunk, MapObject object, float radius) {
        Body body;
        BodyDef def = new BodyDef();

        float x = object.getProperties().get("x", Float.class) + chunk.x_pos;
        float y = object.getProperties().get("y", Float.class) + chunk.y_pos;

        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(x/PPM, y/PPM);
        def.fixedRotation = true;
        body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = TRIGGER;
        fixtureDef.filter.maskBits = EVENT_MASK;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }
}
