package wizardo.game.Maps.DecorObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Maps.LayerObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class BrazierObject extends LayerObject {

    RoundLight light;
    Animation<Sprite> anim;
    Body body;

    public BrazierObject(MapChunk chunk, MapObject object) {
        super(chunk, object);
        Float width = object.getProperties().get("width", Float.class);
        Float height = object.getProperties().get("height", Float.class);
        x = object.getProperties().get("x", Float.class) + chunk.x_pos + width/2;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos + height/2;
        x = x/PPM;
        y = y/PPM;

        anim = DungeonDecorResources.brazierAnim;
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            createLight();
        }

        drawFrame();
        stateTime+= delta;

    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(x * PPM - 2, y * PPM + 12);
        chunk.screen.addSortedSprite(frame);
        chunk.screen.centerSort(frame, y * PPM -4);
    }

    public void createBody() {
        body = MapUtils.createLowObstacleBody_FromTiledMap(chunk, object, 10);
    }

    public void createLight() {
        Vector2 lightPosition = new Vector2(body.getPosition().x, body.getPosition().y + 1.2f);
        light = chunk.screen.lightManager.pool.getLight();
        light.setLight(0.75f, 0.25f, 0, 0.95f, 80, lightPosition);
        chunk.screen.lightManager.addLight(light);
    }



    @Override
    public void dispose() {
        if(initialized) {
            if(light != null) {
                light.dimKill(0.5f);
                light = null;
            }
            if(body != null) {
                world.destroyBody(body);
                body = null;
            }
            initialized = false;
        }
    }
}
