package wizardo.game.Maps.MapGeneration;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.LayerObject;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

import java.util.ArrayList;

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
    public ArrayList<LayerObject> layerObjects;

    public MapChunk(String pathToFile, float x, float y, Wizardo game, BaseScreen screen) {
        this.game = game;
        this.screen = screen;
        this.map = new TmxMapLoader().load(pathToFile);
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
        x_pos = x;
        y_pos = y;
        bodies = new ArrayList<>();
        layerObjects = new ArrayList<>();
    }

    public abstract void render(float delta);

    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        layerObjects.clear();
    }

    public abstract void createBodies();

    public abstract void disposeBodies();
}
