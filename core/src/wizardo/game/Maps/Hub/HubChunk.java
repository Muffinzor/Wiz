package wizardo.game.Maps.Hub;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.DecorObjects.StandingTorchObject;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.ForestDecor.Tree;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.DecorObjects.PortalObject;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Wizardo.world;

public class HubChunk extends MapChunk {

    public HubChunk(String pathToFile, float x, float y, Wizardo game, BaseScreen screen) {
        super(pathToFile, x, y, game, screen);
    }

    @Override
    public void render(float delta) {

        if(layerObjects.isEmpty()) {
            initialize();
            createBodies();
        }

        for(EnvironmentObject object : layerObjects) {
            object.update(delta);
        }

        mapRenderer.setView(screen.mainCamera);
        mapRenderer.render();
    }

    @Override
    public void disposeBodies() {
        for (Body body : bodies) {
            world.destroyBody(body);
        }
        bodies.clear();
    }

    public void createBodies() {
        MapObjects obstacles = map.getLayers().get("ObstacleBodies").getObjects();

        for(MapObject object : obstacles) {
            if(object.getName().equals("Obstacle")) {
                MapUtils.createRectangleObstacleBody(this, (RectangleMapObject) object);
            }
        }
    }

    public void initialize() {
        MapObjects obstacles = map.getLayers().get("ObstacleBodies").getObjects();
        for(MapObject object : obstacles) {
            if("Portal".equals(object.getName())) {
                PortalObject portal = new PortalObject(this, object, true);
                layerObjects.add(portal);
            }
            if(object.getName().equals("Tree")) {
                Tree tree = new Tree(this, object);
                layerObjects.add(tree);
            }
        }
    }
}
