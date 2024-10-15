package wizardo.game.Maps.Dungeon;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import wizardo.game.Maps.LayerObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

public class DungeonChunk extends MapChunk {

    public DungeonChunk(String pathToFile, float x, float y, Wizardo game, BaseScreen screen) {
        super(pathToFile, x, y, game, screen);
    }

    @Override
    public void render(float delta) {
        if(bodies.isEmpty()) {
            initialize();
        }

        for(LayerObject object : layerObjects) {
            object.update(delta);
        }

        screen.mainCamera.translate(-x_pos, -y_pos);
        screen.mainCamera.update();

        mapRenderer.setView(screen.mainCamera);
        mapRenderer.render();

        screen.mainCamera.translate(x_pos, y_pos);
        screen.mainCamera.update();
    }

    @Override
    public void createBodies() {
        MapObjects obstacles = map.getLayers().get("ObstacleBodies").getObjects();
        for(MapObject object : obstacles) {
            if("Test_Body".equals(object.getName())) {
                MapUtils.createRectangleObstacleBody(this, (RectangleMapObject) object);
            }
        }
    }

    public void initialize() {
        createBodies();
    }
}
