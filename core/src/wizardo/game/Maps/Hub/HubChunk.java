package wizardo.game.Maps.Hub;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.Forest.ForestDecor.Tree;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.DecorObjects.PortalObject;
import wizardo.game.Maps.MapUtils;
import wizardo.game.NPC.Witch_NPC;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.Hub.HubDecor.Cauldron;
import wizardo.game.Screens.Hub.HubScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Wizardo.world;

public class HubChunk extends MapChunk {

    HubScreen hubScreen;

    public HubChunk(String pathToFile, float x, float y, Wizardo game, BaseScreen screen) {
        super(pathToFile, x, y, game, screen);
        hubScreen = (HubScreen) screen;
    }

    @Override
    public void render(float delta) {

        if(layerObjects.isEmpty()) {
            initialize();
            createBodies();
            createDecor();
            createNPCs();
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

    public void createNPCs() {
        MapObjects NPCs = map.getLayers().get("NPCs").getObjects();
        for (MapObject npc : NPCs) {
            if(npc.getName().equals("Witch")) {
                Witch_NPC witch = new Witch_NPC(this, npc);
                hubScreen.npc_list.add(witch);
            }
        }
    }

    public void createDecor() {
        MapObjects decor = map.getLayers().get("DecorBodies").getObjects();
        for (MapObject object : decor) {
            if(object.getName().equals("Cauldron")) {
                Cauldron cauldron = new Cauldron(this, object);
                layerObjects.add(cauldron);
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
