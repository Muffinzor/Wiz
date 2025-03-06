package wizardo.game.Maps.Forest;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.Dungeon.DecorRenderer;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.Forest.ForestBuildings.ForestBuilding;
import wizardo.game.Maps.Forest.ForestBuildings.ForestFountain;
import wizardo.game.Maps.Forest.ForestBuildings.ForestStone;
import wizardo.game.Maps.ForestDecor.GrassCluster;
import wizardo.game.Maps.ForestDecor.Tree;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.Shop.MapShop;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class ForestChunk extends MapChunk {

    DecorRenderer decorRenderer;

    boolean alreadySeen;

    public ForestChunk(String pathToFile, float x, float y, Wizardo game, BattleScreen screen) {
        super(pathToFile, x, y, game, screen);
        chunkCenter = new Vector2(x + CHUNK_SIZE / 2f, y + CHUNK_SIZE / 2f);
        decorRenderer = new DecorRenderer(this);
        biome = "Forest";
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

    public void createDecor() {

        MapObjects decor = map.getLayers().get("GrassBodies").getObjects();
        for (MapObject object : decor) {
            if(object.getName().equals(("GrassCluster"))) {
                GrassCluster cluster = new GrassCluster(this, object);
                layerObjects.add(cluster);
            }
        }

        decor = map.getLayers().get("DecorBodies").getObjects();
        for (MapObject object : decor) {
            if(object.getName().equals("Tree") && Math.random() > 0.33f) {
                Tree tree = new Tree(this, object);
                layerObjects.add(tree);
            }
        }

        decor = map.getLayers().get("ObstacleBodies").getObjects();
        for (MapObject object : decor) {

            if(object.getName().equals("Building1_g") ||
                    object.getName().equals("Building1_s") ||
                    object.getName().equals("Building2_g") ||
                    object.getName().equals("Building2_s") ||
                    object.getName().equals("PillarH_g") ||
                    object.getName().equals("PillarH_s") ||
                    object.getName().equals("PillarV_g") ||
                    object.getName().equals("PillarV_s") ||
                    object.getName().equals("Small_pillar")) {

                ForestBuilding building = new ForestBuilding(this, object);
                layerObjects.add(building);
            }

            if(object.getName().equals("Stone1")) {
                ForestStone stone = new ForestStone(this, object);
                layerObjects.add(stone);
            }

            if(object.getName().equals("Fountain")) {
                ForestFountain fountain = new ForestFountain(this, object);
                layerObjects.add(fountain);
            }

            if(object.getName().equals("Shop")) {
                if(canHaveShop) {
                    MapShop shop = new MapShop(this, object);
                    layerObjects.add(shop);
                }
                //Rectangle_Building building = new Rectangle_Building(this, object, true);
                //layerObjects.add(building);
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
