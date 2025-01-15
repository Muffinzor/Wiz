package wizardo.game.Maps.MapGeneration;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.Chest;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.Shop.MapShop;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Wizardo.assetManager;

public abstract class MapChunk {

    public static final int CHUNK_SIZE = 2560;

    public Wizardo game;
    public BaseScreen screen;
    public TiledMap map;
    public OrthogonalTiledMapRenderer mapRenderer;

    public float x_pos;
    public float y_pos;

    public Vector2 chunkCenter;

    public ArrayList<Body> bodies;
    public ArrayList<EnvironmentObject> layerObjects;
    public ArrayList<EnvironmentObject> objectsToAdd;

    public MapShop shop;
    public boolean canHaveShop;

    public String pathToFile;

    public MapChunk(String pathToFile, float x, float y, Wizardo game, BaseScreen screen) {
        this.game = game;
        this.screen = screen;

        //this.map = new TmxMapLoader().load(pathToFile);
        this.map = assetManager.get(pathToFile, TiledMap.class);

        this.pathToFile = pathToFile;
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
        x_pos = x;
        y_pos = y;
        bodies = new ArrayList<>();
        layerObjects = new ArrayList<>();
        objectsToAdd = new ArrayList<>();

    }

    public abstract void render(float delta);

    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        layerObjects.clear();
    }

    public abstract void createBodies();

    public abstract void disposeBodies();

    public void createChests() {
        MapObjects obstacles = map.getLayers().get("LootBodies").getObjects();

        for(MapObject object : obstacles) {

            if(object.getName().equals("Chest") && Math.random() >= 0.7f) {
                Chest chest = new Chest(this, object, -1);
                layerObjects.add(chest);
            }

            if(object.getName().equals("TrueChest")) {
                Chest chest = new Chest(this, object, -2);
                layerObjects.add(chest);
            }

        }
    }

    public void addLayerObject(EnvironmentObject layerObject) {
        objectsToAdd.add(layerObject);
    }

    public void mergeLists() {
        layerObjects.addAll(objectsToAdd);
        objectsToAdd.clear();
    }
}
