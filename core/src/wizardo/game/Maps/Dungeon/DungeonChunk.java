package wizardo.game.Maps.Dungeon;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.Buildings.*;
import wizardo.game.Maps.Chest;
import wizardo.game.Maps.DecorObjects.*;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Maps.Shop.MapShop;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class DungeonChunk extends MapChunk {

    DecorRenderer decorRenderer;

    boolean alreadySeen;

    public DungeonChunk(String pathToFile, float x, float y, Wizardo game, BattleScreen screen) {
        super(pathToFile, x, y, game, screen);
        chunkCenter = new Vector2(x + CHUNK_SIZE / 2f, y + CHUNK_SIZE / 2f);
        decorRenderer = new DecorRenderer(this);
    }

    @Override
    public void render(float delta) {
        float distance = player.pawn.getPosition().scl(PPM).dst(chunkCenter);

        if(distance < 3000) {

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

    @Override
    public void createBodies() {
        MapObjects obstacles = map.getLayers().get("ObstacleBodies").getObjects();

        for(MapObject object : obstacles) {

            if(object instanceof RectangleMapObject) {
                MapUtils.createRectangleObstacleBody(this, (RectangleMapObject) object);
            }
            if(object instanceof EllipseMapObject) {
                MapUtils.EllipseObstacleBody(this, (EllipseMapObject) object);
            }
        }
    }

    public void createDecor() {
        MapObjects decor = map.getLayers().get("DecorBodies").getObjects();
        for (MapObject object : decor) {
            if(object.getName().equals("Portal")) {
                PortalObject portal = new PortalObject(this, object, false);
                layerObjects.add(portal);
            }

            if(object.getName().equals("Vase") && Math.random() >= 0.75) {
                VaseObject vase = new VaseObject(this, object, null);
                layerObjects.add(vase);
            }

            if(object.getName().equals(("VaseCluster")) && Math.random() >= 0.75) {
                //VaseCluster cluster = new VaseCluster(this, object);
                //layerObjects.add(cluster);
            }

            if(object.getName().equals("StandingTorch") && Math.random() >= 0.5) {
                StandingTorchObject torch = new StandingTorchObject(this, object);
                layerObjects.add(torch);
            }

            if(object.getName().equals("Brazier")) {
                BrazierObject brazier = new BrazierObject(this, object);
                layerObjects.add(brazier);
            }

            if(object.getName().equals("Doodad")) {
                if(Math.random() >= 0.0f) {
                    Chest chest = new Chest(this, object, -1);
                    layerObjects.add(chest);
                } else if(Math.random() >= 0.75f) {
                    DoodadObject doodad = new DoodadObject(this, object);
                    layerObjects.add(doodad);
                }
            }
        }

        decor = map.getLayers().get("ObstacleBodies").getObjects();
        for (MapObject object : decor) {

            if(object.getName().equals("Pillar")) {
                Pillar_Building pillar = new Pillar_Building(this, object);
                layerObjects.add(pillar);
            }
            if(object.getName().equals("Crypt")) {
                Crypt_Building crypt = new Crypt_Building(this, object);
                layerObjects.add(crypt);
            }
            if(object.getName().equals("Building2")) {
                Square_Building building = new Square_Building(this, object);
                layerObjects.add(building);
            }
            if(object.getName().equals("Building1")) {
                Rectangle_Building building = new Rectangle_Building(this, object, false);
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
                    Rectangle_Building building = new Rectangle_Building(this, object, true);
                    layerObjects.add(building);
                } else {
                    Rectangle_Building building = new Rectangle_Building(this, object, false);
                    layerObjects.add(building);
                }

            }

            if(object.getName().equals("FirePillar")) {
                FirePillarObject pillar = new FirePillarObject(this, object);
                layerObjects.add(pillar);
            }

            if(object.getName().equals("Doodad")) {
                DoodadObject doodad = new DoodadObject(this, object);
                layerObjects.add(doodad);
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
