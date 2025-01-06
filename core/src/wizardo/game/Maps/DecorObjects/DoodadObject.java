package wizardo.game.Maps.DecorObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class DoodadObject extends EnvironmentObject {

    Sprite sprite;

    Body body;

    public DoodadObject(MapChunk chunk, MapObject object) {
        super(chunk, object);
        x = object.getProperties().get("x", Float.class) + chunk.x_pos;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos;
        x = x/PPM;
        y = y/PPM;

    }

    public void setup() {
        switch(MathUtils.random(0,1)) {
            case 0 -> sprite = new Sprite(new Texture("Maps/Decor/Doodads/Bibli1.png"));
            case 1 -> sprite = new Sprite(new Texture("Maps/Decor/Doodads/Rack1.png"));
        }
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            setup();
            createBody();
            initialized = true;
        }

        drawFrame();
        stateTime+= delta;

    }

    public void createBody() {
        body = MapUtils.EllipseObstacleBody(chunk, (EllipseMapObject) object);

    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(sprite);
        frame.setPosition(x * PPM, y * PPM);
        chunk.screen.addSortedSprite(frame);
        chunk.screen.centerSort(frame, y * PPM - 5);
    }


    @Override
    public void dispose() {
        if(initialized) {
            world.destroyBody(body);
            initialized = false;
        }
    }
}

