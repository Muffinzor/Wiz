package wizardo.game.Maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import wizardo.game.Maps.MapGeneration.MapChunk;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Utils.Contacts.Masks.*;
import static wizardo.game.Wizardo.world;

public class MapUtils {

    /** requires world coordinate, not PPM modified */
    public static Body createCircleDecorBody_FromVector(Vector2 position, float radius, boolean isSensor, boolean isStatic) {
        Body body;
        BodyDef def = new BodyDef();

        float x = position.x;
        float y = position.y;

        if(isStatic) {
            def.type = BodyDef.BodyType.StaticBody;
        } else {
            def.type = BodyDef.BodyType.DynamicBody;
        }

        def.position.set(x, y);
        def.fixedRotation = true;
        def.linearDamping = 10000f;
        def.angularDamping = 10000f;
        body = world.createBody(def);

        if(!isStatic) {
            MassData mass = new MassData();
            float newMass = 20000;
            mass.mass = newMass;
            body.setMassData(mass);
        }

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = DECOR;
        fixtureDef.filter.maskBits = DECOR_MASK;
        fixtureDef.isSensor = isSensor;


        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }
    public static Body createCircleDecorBody_FromTiledMap(MapChunk chunk, MapObject object, float radius, boolean isSensor, boolean isStatic) {
        Body body;
        BodyDef def = new BodyDef();

        float x = object.getProperties().get("x", Float.class) + chunk.x_pos;
        float y = object.getProperties().get("y", Float.class) + chunk.y_pos;

        if(isStatic) {
            def.type = BodyDef.BodyType.StaticBody;
        } else {
            def.type = BodyDef.BodyType.DynamicBody;
        }

        def.position.set(x/PPM, y/PPM);
        def.fixedRotation = true;
        def.linearDamping = 10000f;
        def.angularDamping = 10000f;
        body = world.createBody(def);

        if(!isStatic) {
            MassData mass = new MassData();
            float newMass = 20000;
            mass.mass = newMass;
            body.setMassData(mass);
        }

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = DECOR;
        fixtureDef.filter.maskBits = DECOR_MASK;
        fixtureDef.isSensor = isSensor;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }
    public static Body createCircleObstacleBody_FromTiledMap(MapChunk chunk, MapObject object, float radius) {
        Body body;
        BodyDef def = new BodyDef();

        float x = object.getProperties().get("x", Float.class) + chunk.x_pos;
        float y = object.getProperties().get("y", Float.class) + chunk.y_pos;

        def.type = BodyDef.BodyType.StaticBody;

        def.position.set(x/PPM, y/PPM);
        body = world.createBody(def);
        body.setUserData("Obstacle");

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = OBSTACLE;
        fixtureDef.isSensor = false;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }
    public static Body createLowObstacleBody_FromTiledMap(MapChunk chunk, MapObject object, float radius) {
        Body body;
        BodyDef def = new BodyDef();

        float x = object.getProperties().get("x", Float.class) + chunk.x_pos;
        float y = object.getProperties().get("y", Float.class) + chunk.y_pos;

        def.type = BodyDef.BodyType.StaticBody;

        def.position.set(x/PPM, y/PPM);
        def.fixedRotation = true;
        def.linearDamping = 10000f;
        def.angularDamping = 10000f;
        body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = SHORT_OBSTACLE;
        fixtureDef.isSensor = false;

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
        if(width > 0.05f && height > 0.05f) {
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
    }

    /**
     * Creates an impassable body to the shape of the Object passed in argument,
     * adds it to the chunk's list of bodies to keep its reference
     * @param chunk the tilemap chunk where the object is found
     * @param object the RectangleMapObject
     */
    public static void createCircleObstacleBody(MapChunk chunk, RectangleMapObject object, float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        Rectangle rectangle = object.getRectangle();
        float width = rectangle.width;
        float height = rectangle.height;
        float x = rectangle.x + chunk.x_pos + width / 2;
        float y = rectangle.y + chunk.y_pos + height / 2;
        bodyDef.position.set(x / PPM, y / PPM);
        if(width > 0.05f && height > 0.05f) {
            Body body = world.createBody(bodyDef);
            body.setUserData("Obstacle");

            CircleShape shape = new CircleShape();
            shape.setRadius(radius / PPM);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.filter.categoryBits = OBSTACLE;
            fixtureDef.shape = shape;
            fixtureDef.friction = 0;
            fixtureDef.restitution = 0f;

            body.createFixture(fixtureDef);
            shape.dispose();
            chunk.bodies.add(body);
        }
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

    public static Body EllipseObstacleBody(MapChunk chunk, EllipseMapObject ellipseObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Get the ellipse dimensions and position from the EllipseMapObject
        Ellipse ellipse = ellipseObject.getEllipse();
        float x = ellipse.x + chunk.x_pos + ellipse.width / 2; // Box2D uses center position
        float y = ellipse.y + chunk.y_pos + ellipse.height / 2; // Box2D uses center position
        float width = ellipse.width;
        float height = ellipse.height;

        bodyDef.position.set(x / PPM, y / PPM); // Convert to Box2D coordinates

        Body body = world.createBody(bodyDef);

        // Number of vertices per segment (must be between 3 and 8)
        int numVerticesPerSegment = 8;
        // Total number of segments to approximate the ellipse
        int numSegments = 16; // Adjust for more or less accuracy
        float angleStep = (float) (2 * Math.PI / numSegments);
        float radiusX = width / 2 / PPM;
        float radiusY = height / 2 / PPM;

        for (int j = 0; j < numSegments; j++) {
            // Create a polygon shape for this segment
            PolygonShape shape = new PolygonShape();
            Vector2[] vertices = new Vector2[numVerticesPerSegment];
            for (int i = 0; i < numVerticesPerSegment; i++) {
                float angle = (j + i) * angleStep;
                vertices[i] = new Vector2((float) (radiusX * Math.cos(angle)), (float) (radiusY * Math.sin(angle)));
            }
            shape.set(vertices);

            // Create fixture definition
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.filter.categoryBits = OBSTACLE;
            fixtureDef.shape = shape;
            fixtureDef.friction = 0; // Adjust friction as needed
            fixtureDef.restitution = 0f; // Adjust restitution as needed

            body.createFixture(fixtureDef);

            // Clean up
            shape.dispose();
        }

        return body;
    }
}
