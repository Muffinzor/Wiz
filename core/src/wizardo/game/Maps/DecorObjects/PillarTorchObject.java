package wizardo.game.Maps.DecorObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Maps.LayerObject;
import wizardo.game.Maps.MapGeneration.MapChunk;

import static wizardo.game.Utils.Constants.PPM;

public class PillarTorchObject extends LayerObject {

    public static Animation<Sprite> walltorch_anim;
    static {
        Sprite[] frames = new Sprite[19];
        TextureAtlas atlas = new TextureAtlas("Maps/Decor/Dungeon/WallTorch/torch.atlas");
        for (int i = 0; i < frames.length; i++) {
            frames[i] = atlas.createSprite("torch" + (i+1));
        }
        walltorch_anim = new Animation<>(0.1f, frames);
    }

    RoundLight light;
    boolean flipX;

    public PillarTorchObject(MapChunk chunk, MapObject object) {
        super(chunk, object);
        float width = object.getProperties().get("width", Float.class);
        x = object.getProperties().get("x", Float.class) + chunk.x_pos + width/2;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos + 45;
        x = x/PPM;
        y = y/PPM;
        stateTime = (float) Math.random();
        flipX = MathUtils.randomBoolean();

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
        light.setLight(0.7f, 0.3f, 0, 0.9f, 75, new Vector2(x,y + 0.25f));
        chunk.screen.lightManager.addLight(light);
    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(walltorch_anim.getKeyFrame(stateTime, true));
        frame.setCenter(x * PPM, y * PPM);
        frame.setScale(0.35f);
        frame.flip(flipX, false);
        chunk.screen.addSortedSprite(frame);
    }

    @Override
    public void dispose() {
        if(initialized) {
            light.dimKill(0.5f);
            initialized = false;
        }
    }
}
