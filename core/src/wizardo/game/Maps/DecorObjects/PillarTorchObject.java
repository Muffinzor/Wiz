package wizardo.game.Maps.DecorObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Maps.LayerObject;
import wizardo.game.Maps.MapGeneration.MapChunk;

import static wizardo.game.Utils.Constants.PPM;

public class PillarTorchObject extends LayerObject {

    public static Animation<Sprite> walltorch_anim;
    static {
        Sprite[] frames = new Sprite[5];
        TextureAtlas atlas = new TextureAtlas("Maps/Decor/Dungeon/WallTorch/torch.atlas");
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("torch" + i);
        }
        walltorch_anim = new Animation<>(0.2f, frames);
    }

    RoundLight light;
    float x;
    float y;


    public PillarTorchObject(MapChunk chunk, MapObject object) {
        super(chunk, object);
        x = object.getProperties().get("x", Float.class) + chunk.x_pos + 32;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos + 42;
        x = x/PPM;
        y = y/PPM;
        stateTime = (float) Math.random();
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            createLight();
            initialized = true;
        }

        drawFrame();
        stateTime += delta;

    }

    public void createLight() {
        light = chunk.screen.lightManager.pool.getLight();
        light.setLight(0.7f, 0.3f, 0, 1, 45, new Vector2(x,y + 0.3f));
        chunk.screen.lightManager.addLight(light);
    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(walltorch_anim.getKeyFrame(stateTime, true));
        frame.setCenter(x * PPM, y * PPM);
        chunk.screen.addSortedSprite(frame);
    }

    @Override
    public void dispose() {
        light.dimKill(0.5f);
    }
}
