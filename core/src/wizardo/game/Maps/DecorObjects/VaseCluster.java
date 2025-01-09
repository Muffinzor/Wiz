package wizardo.game.Maps.DecorObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;

import java.util.ArrayList;
import java.util.Random;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Utils.Methods.isCircleOverlappingWithObstacle;
import static wizardo.game.Utils.Methods.isPositionOverlappingWithObstacle;
import static wizardo.game.Wizardo.player;

public class VaseCluster extends EnvironmentObject {

    int quantity;
    float clusterRadius;
    Vector2 centerPoint;

    float minimumSpread;  //the minimum distance between each body


    ArrayList<Vector2> positions;

    public VaseCluster(MapChunk chunk, MapObject object) {
        super(chunk, object);

        x = object.getProperties().get("x", Float.class) + chunk.x_pos;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos;
        x = x/PPM + (object.getProperties().get("width", Float.class)/2f)/PPM;
        y = y/PPM + (object.getProperties().get("height", Float.class)/2f)/PPM;

        centerPoint = new Vector2(x,y);
        float tiledRadius = object.getProperties().get("width", Float.class) / 2f;
        clusterRadius = tiledRadius / PPM;


        quantity = 10;
        minimumSpread = 16f/PPM;
    }

    @Override
    public void update(float delta) {
        positions = getPositions();

        for(Vector2 position : positions) {
            VaseObject vase = new VaseObject(chunk, object, position);
            chunk.addLayerObject(vase);
        }
        toBeRemoved = true;
    }

    @Override
    public void dispose() {

    }

    public ArrayList<Vector2> getPositions() {

        ArrayList<Vector2> positions = new ArrayList<>();
        Random random = new Random();
        int attemptsLeft = 10; // Prevents infinite loop or excessive overhead
        quantity = (int) (clusterRadius * 10);

        while (positions.size() < quantity && attemptsLeft > 0) {
            double r = Math.sqrt(random.nextDouble()) * clusterRadius;
            double theta = random.nextDouble() * 2 * Math.PI;

            float x = (float) (r * Math.cos(theta)) + centerPoint.x;
            float y = (float) (r * Math.sin(theta)) + centerPoint.y;

            boolean isValid = true;
            for (Vector2 p : positions) {
                if (p.dst(x, y) < minimumSpread) {
                    isValid = false;
                    break;
                }
            }

            if (isValid && centerPoint.dst(x, y) <= clusterRadius - minimumSpread) {
                if(!isCircleOverlappingWithObstacle(new Vector2(x,y), 10)) {
                    positions.add(new Vector2(x, y));
                } else {
                    attemptsLeft--;
                }
            } else {
                attemptsLeft--;
            }
        }

        return positions;
    }
}
