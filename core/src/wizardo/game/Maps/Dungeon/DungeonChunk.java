package wizardo.game.Maps.Dungeon;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.DecorObjects.*;
import wizardo.game.Maps.LayerObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
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
            decorRenderer.drawObstacles();

            screen.mainCamera.translate(x_pos, y_pos);
            screen.mainCamera.update();

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
                VaseObject vase = new VaseObject(this, object);
                layerObjects.add(vase);
            }

            if(object.getName().equals("StandingTorch")) {
                StandingTorchObject torch = new StandingTorchObject(this, object);
                layerObjects.add(torch);
            }
        }

        decor = map.getLayers().get("ObstacleBodies").getObjects();
        for (MapObject object : decor) {

            if(object.getName().equals("Pillar")) {
                if(Math.random() >= 0.4f) {
                    PillarTorchObject torch = new PillarTorchObject(this, object);
                    layerObjects.add(torch);
                } else if(Math.random() > 0.8f) {
                    WallFlagObject flag = new WallFlagObject(this, object);
                    layerObjects.add(flag);
                }
            }

            if(object.getName().equals("Building1")) {
                if(Math.random() >= 0.4f) {
                    PillarTorchObject torch = new PillarTorchObject(this, object);
                    layerObjects.add(torch);
                }
                if(Math.random() >= 0.7f) {
                    WallFlagObject flag = new WallFlagObject(this, object);
                    layerObjects.add(flag);
                }
            }

            if(object.getName().equals("Building2")) {
                if(Math.random() >= 0.4f) {
                    PillarTorchObject torch = new PillarTorchObject(this, object);
                    layerObjects.add(torch);
                } else if(Math.random() >= 0.7f) {
                    WallFlagObject flag = new WallFlagObject(this, object);
                    layerObjects.add(flag);
                }
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
        for (LayerObject object : layerObjects) {
            object.dispose();
        }
    }

    public void initialize() {
        createBodies();
        if(!alreadySeen) {
            createDecor();
            alreadySeen = true;
        }

    }
}
