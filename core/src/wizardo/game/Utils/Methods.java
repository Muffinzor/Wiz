package wizardo.game.Utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import java.util.ArrayList;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Utils.Contacts.Masks.DECOR;
import static wizardo.game.Utils.Contacts.Masks.OBSTACLE;
import static wizardo.game.Wizardo.world;

public class Methods {

    public static boolean isCircleOverlappingWithObstacle(Vector2 position, float radius) {

        ArrayList<Body> foundBodies = new ArrayList<>();

        float lowerX = position.x - radius/PPM;
        float lowerY = position.y - radius/PPM;
        float upperX = position.x + radius/PPM;
        float upperY = position.y + radius/PPM;


        world.QueryAABB(fixture -> {

            if ((fixture.getFilterData().categoryBits & OBSTACLE) != 0 || (fixture.getFilterData().categoryBits & DECOR) != 0) {
                foundBodies.add(fixture.getBody());
            }
            return true;
        }, lowerX, lowerY, upperX, upperY);

        return !foundBodies.isEmpty(); // Return true if any OBSTACLE body is found

    }

    public static boolean isPositionOverlappingWithObstacle(Vector2 position) {

        ArrayList<Body> foundBodies = new ArrayList<>();

        float lowerX = position.x - 0.1f;
        float lowerY = position.y - 0.1f;
        float upperX = position.x + 0.1f;
        float upperY = position.y + 0.1f;


        world.QueryAABB(fixture -> {

            if ((fixture.getFilterData().categoryBits & OBSTACLE) != 0) {
                foundBodies.add(fixture.getBody());
            }
            return true;
        }, lowerX, lowerY, upperX, upperY);

        return !foundBodies.isEmpty(); // Return true if any OBSTACLE body is found
    }
}
