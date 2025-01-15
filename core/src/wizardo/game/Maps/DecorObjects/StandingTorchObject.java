package wizardo.game.Maps.DecorObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class StandingTorchObject extends EnvironmentObject {

    Body body;
    RoundLight light = null;
    Animation<Sprite> anim;
    boolean destroyed;

    public StandingTorchObject(MapChunk chunk, MapObject object) {
        super(chunk, object);
        Float width = object.getProperties().get("width", Float.class);
        Float height = object.getProperties().get("height", Float.class);
        x = object.getProperties().get("x", Float.class) + chunk.x_pos + width/2;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos + height/2;
        x = x/PPM;
        y = y/PPM;

        anim = DungeonDecorResources.standingTorch;
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

        if(collided && !destroyed) {
            pickAnim();
            destroyed = true;
            stateTime = 0;
            body.setActive(false);
        }

        if(destroyed && light != null) {
            light.dimKill(0.08f);
            light = null;
        }

    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, !destroyed));
        frame.setCenter(body.getPosition().x * PPM + 2, body.getPosition().y * PPM + frame.getHeight()/2);
        chunk.screen.addSortedSprite(frame);
    }

    public void createBody() {
        body = MapUtils.createCircleDecorBody_FromTiledMap(chunk, object, 8, false, false);
        body.setUserData(this);
        if(destroyed) {
            body.setActive(false);
        }
    }
    public void createLight() {
        if(!destroyed) {
            Vector2 lightPosition = new Vector2(body.getPosition().x + 2f / PPM, body.getPosition().y + 64f / PPM);
            light = chunk.screen.lightManager.pool.getLight();
            light.setLight(0.75f, 0.35f, 0, 0.75f, 55, lightPosition);
            chunk.screen.lightManager.addLight(light);
        }
    }

    public void handleCollision() {
        if(body.isActive()) {
            destroyed = true;
            stateTime = 0;
            body.setActive(false);
        }
    }

    public void pickAnim() {
        int random = MathUtils.random(1,2);
        switch (random) {
            case 1 -> anim = DungeonDecorResources.standingbreak1;
            case 2 -> anim = DungeonDecorResources.standingbreak2;
        }
    }

    @Override
    public void dispose() {
        if(initialized) {
            world.destroyBody(body);
            if(light != null) {
                light.dimKill(0.5f);
                light = null;
            }
            body = null;
            initialized = false;
        }
    }
}
