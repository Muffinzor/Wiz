package wizardo.game.Utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

import java.sql.Array;
import java.util.ArrayList;

import static wizardo.game.Utils.Contacts.Masks.OBSTACLE;
import static wizardo.game.Wizardo.world;

public class Methods {

    public static boolean isPositionOverlappingWithObstacle(Vector2 position) {

        ArrayList<Body> foundBodies = new ArrayList<>();

        float lowerX = position.x - 1;
        float lowerY = position.y - 1;
        float upperX = position.x + 1;
        float upperY = position.y + 1;


        world.QueryAABB(fixture -> {

            if ((fixture.getFilterData().categoryBits & OBSTACLE) != 0) {
                foundBodies.add(fixture.getBody());
            }
            return true;
        }, lowerX, lowerY, upperX, upperY);

        return !foundBodies.isEmpty(); // Return true if any OBSTACLE body is found
    }
}
