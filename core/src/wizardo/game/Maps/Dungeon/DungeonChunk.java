package wizardo.game.Maps.Dungeon;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.DecorObjects.*;
import wizardo.game.Maps.Dungeon.DungeonBuildings.DungeonBuilding;
import wizardo.game.Maps.Dungeon.DungeonBuildings.Statue_Building;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.Shop.MapShop;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class DungeonChunk extends MapChunk {

    boolean alreadySeen;

    public DungeonChunk(String pathToFile, float x, float y, Wizardo game, BattleScreen screen) {
        super(pathToFile, x, y, game, screen);
        chunkCenter = new Vector2(x + CHUNK_SIZE / 2f, y + CHUNK_SIZE / 2f);
        biome = "Dungeon";
    }

    @Override
    public void render(float delta) {
        float distance = player.pawn.getPosition().scl(PPM).dst(chunkCenter);

        if(shop != null) {
            shop.display_direction();
        }

        if(distance < 4500) {

            if(bodies.isEmpty() && delta > 0) {
                initialize();
            }

            for(EnvironmentObject object : layerObjects) {
                object.update(delta);
            }
            layerObjects.removeIf(object -> object.toBeRemoved);

            screen.mainCamera.translate(-x_pos, -y_pos);
            screen.mainCamera.update();

            mapRenderer.setView(screen.mainCamera);
            mapRenderer.render();

            screen.mainCamera.translate(x_pos, y_pos);
            screen.mainCamera.update();

            mergeLists();

        } else {
            disposeBodies();
            disposeDecor();
        }

    }

    public void createDecor() {
        MapObjects decor = map.getLayers().get("DecorBodies").getObjects();
        for (MapObject object : decor) {
            if(object.getName().equals("Portal")) {
                PortalObject portal = new PortalObject(this, object, false);
                layerObjects.add(portal);
            }

            if(object.getName().equals(("VaseCluster")) && Math.random() >= 0.6) {
                VaseCluster cluster = new VaseCluster(this, object);
                layerObjects.add(cluster);
            }

            if(object.getName().equals("StandingTorch") && Math.random() >= 0.5) {
                StandingTorchObject torch = new StandingTorchObject(this, object);
                layerObjects.add(torch);
            }
        }

        decor = map.getLayers().get("ObstacleBodies").getObjects();
        for (MapObject object : decor) {

            if(object.getName().equals("Pillar") ||
                    object.getName().equals("Crypt") ||
                    object.getName().equals("Building2") ||
                    object.getName().equals("Building1")) {
                DungeonBuilding building = new DungeonBuilding(this, object, false);
                layerObjects.add(building);
            }

            if(object.getName().equals("Statue")) {
                Statue_Building statue = new Statue_Building(this, object);
                layerObjects.add(statue);
            }

            if(object.getName().equals("Shop")) {
                if(canHaveShop) {
                    MapShop shop = new MapShop(this, object);
                    layerObjects.add(shop);
                    DungeonBuilding building = new DungeonBuilding(this, object, true);
                    layerObjects.add(building);
                } else {
                    DungeonBuilding building = new DungeonBuilding(this, object, false);
                    layerObjects.add(building);
                }

            }
            if(object.getName().equals("FirePillar")) {
                FirePillarObject pillar = new FirePillarObject(this, object);
                layerObjects.add(pillar);
            }
        }
    }


    @Override
    public void disposeBodies() {
        for (Body body : bodies) {
            world.destroyBody(body);
        }
        bodies.clear();
    }
    public void disposeDecor() {
        for (EnvironmentObject object : layerObjects) {
            object.dispose();
        }
    }

    public void initialize() {
        createBodies();
        if(!alreadySeen) {
            createChests();
            createDecor();
            alreadySeen = true;
        }
    }
}
