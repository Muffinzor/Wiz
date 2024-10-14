package wizardo.game.Maps.Hub;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.LayerObject;
import wizardo.game.Maps.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Maps.PortalObject;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

public class HubChunk extends MapChunk {

    public HubChunk(String pathToFile, float x, float y, Wizardo game, BaseScreen screen) {
        super(pathToFile, x, y, game, screen);
    }

    @Override
    public void render(float delta) {

        if(layerObjects.isEmpty()) {
            initialize();
        }

        for(LayerObject object : layerObjects) {
            object.update(delta);
        }

        mapRenderer.setView(screen.mainCamera);
        mapRenderer.render();
    }

    public void initialize() {
        MapObjects obstacles = map.getLayers().get("ObstacleBodies").getObjects();
        for(MapObject object : obstacles) {
            if("Test_Body".equals(object.getName())) {
                PortalObject portal = new PortalObject(this, object);
                layerObjects.add(portal);
            }
        }
    }
}
