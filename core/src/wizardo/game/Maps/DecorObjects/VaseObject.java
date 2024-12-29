package wizardo.game.Maps.DecorObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.LayerObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class VaseObject extends LayerObject {

    Body body;
    Sprite sprite;
    Animation<Sprite> anim;
    boolean destroyed;

    public VaseObject(MapChunk chunk, MapObject object) {
        super(chunk, object);
        Float width = object.getProperties().get("width", Float.class);
        Float height = object.getProperties().get("height", Float.class);
        x = object.getProperties().get("x", Float.class) + chunk.x_pos + width/2;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos + height/2;
        x = x/PPM;
        y = y/PPM;
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            pickAnim();
            createBody();
        }

        drawFrame();
        stateTime+= delta;

        if(collided && !destroyed) {
            destroyed = true;
            stateTime = 0;
            body.setActive(false);
        }

    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        if(!destroyed) {
            frame.set(sprite);
        } else {
            frame.set(anim.getKeyFrame(stateTime, false));
        }
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM + 4);
        chunk.screen.addSortedSprite(frame);
        chunk.screen.centerSort(frame, body.getPosition().y * PPM);
    }

    public void createBody() {
        body = MapUtils.createCircleDecorBody_FromTiledMap(chunk, object, 8, false, false);
        body.setUserData(this);
        if(destroyed) {
            body.setActive(false);
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
        int random = MathUtils.random(1,3);
        switch (random) {
            case 1 -> {
                sprite = DungeonDecorResources.vase1;
                anim = DungeonDecorResources.break1;
            }
            case 2 -> {
                sprite = DungeonDecorResources.vase2;
                anim = DungeonDecorResources.break2;
            }
            case 3 -> {
                sprite = DungeonDecorResources.vase3;
                anim = DungeonDecorResources.break3;
            }
        }
    }

    @Override
    public void dispose() {
        if(initialized) {
            world.destroyBody(body);
            initialized = false;
        }
    }
}
