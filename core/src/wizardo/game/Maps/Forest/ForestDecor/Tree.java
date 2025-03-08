package wizardo.game.Maps.Forest.ForestDecor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Resources.DecorResources.ForestDecorResources;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class Tree extends EnvironmentObject {

    Body body;
    Sprite sprite;
    Sprite shadow;
    boolean flip;

    float width = 10;

    public Tree(MapChunk chunk, MapObject object) {
        super(chunk, object);
        Float width = object.getProperties().get("width", Float.class);
        Float height = object.getProperties().get("height", Float.class);
        x = object.getProperties().get("x", Float.class) + chunk.x_pos + width/2;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos + height/2;
        x = x/PPM;
        y = y/PPM;

        setup();

    }

    public void setup() {
        int random = MathUtils.random(1,2);
        switch (random) {
            case 1 -> {
                sprite = ForestDecorResources.green_tree_dark_1;
                shadow = ForestDecorResources.green_tree_shadow_1;
            }
            case 2 -> {
                sprite = ForestDecorResources.green_tree_dark_2;
                shadow = ForestDecorResources.green_tree_shadow_2;
            }
        }
        flip = MathUtils.randomBoolean();

    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
        }

        drawFrame();
        drawShadow();
        stateTime+= delta;

    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(sprite);
        frame.setPosition(body.getPosition().x * PPM - frame.getWidth()/2, body.getPosition().y * PPM - 38);
        frame.flip(flip, false);
        chunk.screen.centerSort(frame, body.getPosition().y * PPM);
        chunk.screen.addSortedSprite(frame);
    }

    public void drawShadow() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(shadow);
        frame.setPosition(body.getPosition().x * PPM - frame.getWidth()/2, body.getPosition().y * PPM - 38);
        frame.flip(flip, false);
        chunk.screen.centerSort(frame, body.getPosition().y * PPM - 50);
        chunk.screen.addSortedSprite(frame);
    }

    public void createBody() {
        body = MapUtils.createCircleObstacleBody_FromTiledMap(chunk, object, width);
        body.setUserData(this);
    }


    @Override
    public void dispose() {
        if(initialized) {
            world.destroyBody(body);
            body = null;
            initialized = false;
        }
    }
}
