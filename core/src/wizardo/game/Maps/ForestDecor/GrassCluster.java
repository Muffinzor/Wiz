package wizardo.game.Maps.ForestDecor;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.ForestDecor.Grass;
import wizardo.game.Maps.MapGeneration.MapChunk;

import java.util.ArrayList;
import java.util.Random;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Utils.Methods.isCircleOverlappingWithObstacle;
import static wizardo.game.Utils.Methods.isPositionOverlappingWithObstacle;
import static wizardo.game.Wizardo.player;

public class GrassCluster extends EnvironmentObject {

    int quantity;
    float clusterRadius;
    Vector2 centerPoint;

    float minimumSpread;  //the minimum distance between each body


    ArrayList<Vector2> positions;

    public GrassCluster(MapChunk chunk, MapObject object) {
        super(chunk, object);

        x = object.getProperties().get("x", Float.class) + chunk.x_pos;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos;
        x = x/PPM + (object.getProperties().get("width", Float.class)/2f)/PPM;
        y = y/PPM + (object.getProperties().get("height", Float.class)/2f)/PPM;

        centerPoint = new Vector2(x,y);
        float tiledRadius = object.getProperties().get("width", Float.class) / 2f;
        clusterRadius = tiledRadius / PPM;


        quantity = 10;
        minimumSpread = 32f/PPM;
    }

    @Override
    public void update(float delta) {
        if(!toBeRemoved) {
            positions = getPositions();
            for (Vector2 position : positions) {
                Grass grass = new Grass(chunk, object, position);
                chunk.addLayerObject(grass);
            }
            toBeRemoved = true;
        }
    }

    @Override
    public void dispose() {

    }

    public ArrayList<Vector2> getPositions() {

        ArrayList<Vector2> positions = new ArrayList<>();
        Random random = new Random();
        quantity = Math.min(50, (int) (clusterRadius * 2));

        while (positions.size() < quantity) {
            double r = Math.sqrt(random.nextDouble()) * clusterRadius;
            double theta = random.nextDouble() * 2 * Math.PI;

            float x = (float) (r * Math.cos(theta)) + centerPoint.x;
            float y = (float) (r * Math.sin(theta)) + centerPoint.y;

            positions.add(new Vector2(x, y));
        }

        return positions;
    }
}
