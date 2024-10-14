package wizardo.game.Utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Utils.Contacts.Masks.PAWN;

public class BodyFactory {

    public static Body playerBody (World world, Vector2 position) {

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        def.position.set(position.x / PPM, position.y / PPM);

        Body body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(8.0f / PPM);

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
}
