package wizardo.game.Maps.Dungeon;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.LayerObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class DungeonChunk extends MapChunk {

    public DungeonChunk(String pathToFile, float x, float y, Wizardo game, BattleScreen screen) {
        super(pathToFile, x, y, game, screen);
        chunkCenter = new Vector2(x + CHUNK_SIZE / 2f, y + CHUNK_SIZE / 2f);
    }

    @Override
    public void render(float delta) {
        float distance = player.pawn.getPosition().scl(PPM).dst(chunkCenter);

        if(distance < 3000) {

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

        } else {
            disposeBodies();
        }

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

    @Override
    public void disposeBodies() {
        for (Body body : bodies) {
            world.destroyBody(body);
        }
        bodies.clear();
    }

    public void initialize() {
        createBodies();
    }
}
