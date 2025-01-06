package wizardo.game.Maps.DecorObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;

import static wizardo.game.Utils.Constants.PPM;

public class FirePillarObject extends EnvironmentObject {

    RoundLight light;
    Animation<Sprite> anim;

    public FirePillarObject(MapChunk chunk, MapObject object) {
        super(chunk, object);
        Float width = object.getProperties().get("width", Float.class);
        Float height = object.getProperties().get("height", Float.class);
        x = object.getProperties().get("x", Float.class) + chunk.x_pos + width/2;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos + height/2;
        x = x/PPM;
        y = y/PPM;

        anim = DungeonDecorResources.firePillar;
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createLight();
        }

        drawFrame();
        stateTime+= delta;

    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(x * PPM, y * PPM + frame.getHeight()/2 - 12);
        chunk.screen.addSortedSprite(frame);
    }

    public void createLight() {
        Vector2 lightPosition = new Vector2(x + 0.1f, y + 2.4f);
        light = chunk.screen.lightManager.pool.getLight();
        light.setLight(0.025f, 0.25f, 0.17f, 0.95f, 120, lightPosition);
        chunk.screen.lightManager.addLight(light);
    }



    @Override
    public void dispose() {
        if(initialized) {
            if(light != null) {
                light.dimKill(0.5f);
                light = null;
            }
            initialized = false;
        }
    }
}
