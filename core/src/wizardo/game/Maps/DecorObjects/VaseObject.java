package wizardo.game.Maps.DecorObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class VaseObject extends EnvironmentObject {

    Body body;
    Sprite sprite;
    Animation<Sprite> anim;
    boolean destroyed;

    Vector2 position;

    public VaseObject(MapChunk chunk, MapObject object, Vector2 position) {
        super(chunk, object);
        body = null;
        if(position != null) {
            this.position = position;
        }
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
        if(body != null) {
            Sprite frame = chunk.screen.getSprite();
            if (!destroyed) {
                frame.set(sprite);
            } else {
                frame.set(anim.getKeyFrame(stateTime, false));
            }
            frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM + 4);
            chunk.screen.addSortedSprite(frame);
            chunk.screen.centerSort(frame, body.getPosition().y * PPM);
        }
    }

    public void createBody() {

        if(position != null && body == null) {
            body = MapUtils.createCircleDecorBody_FromVector(position, 8, false, false);
            body.setUserData(this);
            if(destroyed) {
                body.setActive(false);
            }
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
            body = null;
        }
    }
}
