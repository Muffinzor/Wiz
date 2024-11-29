package wizardo.game.Utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Utils.Contacts.Masks.*;
import static wizardo.game.Wizardo.world;

public class BodyFactory {

    public static Body playerBody (Vector2 position) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        def.position.set(position.x, position.y);

        Body body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(12f / PPM);

        FixtureDef fixt = new FixtureDef();
        fixt.filter.categoryBits = PAWN;
        fixt.shape = shape;
        fixt.density = 1;
        fixt.friction = 0;
        fixt.restitution = 0;
        fixt.isSensor = false;

        body.createFixture(fixt);
        shape.dispose();

        MassData data = new MassData();
        data.mass = 1;
        body.setMassData(data);

        return body;

    }
    public static Body monsterBody(Vector2 position, float radius) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(position.x, position.y);
        def.fixedRotation = true;

        Body body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = MONSTER;
        fixtureDef.filter.maskBits = MONSTER_MASK;
        fixtureDef.isSensor = false;
        fixtureDef.friction = 0f;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }
    public static Body spellProjectileCircleBody(Vector2 position, float radius, boolean isSensor) {
        Body body;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(position.x, position.y);
        def.fixedRotation = true;
        body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = isSensor;

        fixtureDef.filter.categoryBits = SPELL;
        fixtureDef.filter.maskBits = SPELL_MASK;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }
    public static Body spellProjectileDiamondBody(Vector2 position, float width, float height, float angle, boolean sensor) {

        Body body;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;


        def.position.set(position.x, position.y);
        def.angle = angle * MathUtils.degRad; // Convert angle to radians if it's in degrees
        def.fixedRotation = true;
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();

        // Define the vertices of the diamond shape
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(0, height / 2 / PPM); // Top
        vertices[1] = new Vector2(-width / 2 / PPM, 0); // Left
        vertices[2] = new Vector2(0, -height / 2 / PPM); // Bottom
        vertices[3] = new Vector2(width / 2 / PPM, 0); // Right

        shape.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = SPELL;
        fixtureDef.filter.maskBits = SPELL_MASK;

        fixtureDef.isSensor = sensor;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }
    public static Body spellRectangleBody(Vector2 position, float width, float height, float angle, boolean sensor) {

        Body body;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.StaticBody;

        def.position.set(position.x, position.y);
        def.angle = angle * MathUtils.degRad;
        def.fixedRotation = true;
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = SPELL;
        fixtureDef.filter.maskBits = SPELL_MASK;

        fixtureDef.isSensor = sensor;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }
    public static Body spellExplosionBody(Vector2 position, float radius) {
        Body body;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.StaticBody;

        def.position.set(position.x, position.y);
        def.fixedRotation = true;
        body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        fixtureDef.filter.categoryBits = SPELL;
        fixtureDef.filter.maskBits = SPELL_MASK;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    public static Body monsterProjectileBody (Vector2 position, float radius) {

        Body body;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(position.x, position.y);
        def.fixedRotation = true;
        body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = MONSTER_PROJECTILE;
        fixtureDef.filter.maskBits = MONSTER_PROJECTILE_MASK;

        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }
}
