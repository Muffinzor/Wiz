package wizardo.game.Maps;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Map {

    protected TiledMap tileMap;
    protected OrthogonalTiledMapRenderer mapRenderer;

    /**
     * loads the tiled map into the Map's MapRenderer
     * @param pathToFile path to .tmx file from assets directory
     */
    public void loadTiledMap(String pathToFile) {
        TmxMapLoader loader = new TmxMapLoader();
        tileMap = loader.load(pathToFile);
        mapRenderer = new OrthogonalTiledMapRenderer(tileMap);
    }

}
