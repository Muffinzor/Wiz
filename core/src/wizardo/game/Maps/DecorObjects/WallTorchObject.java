package wizardo.game.Maps.DecorObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Resources.DecorResources.GeneralDecorResources;

import static wizardo.game.Utils.Constants.PPM;

public class WallTorchObject extends EnvironmentObject {

    Animation<Sprite> anim;

    RoundLight light;
    float red;
    float green;
    float blue;
    boolean flipX;

    Vector2 position;

    public WallTorchObject(MapChunk chunk, MapObject object, Vector2 position) {
        super(chunk, object);
        this.position = position;
        stateTime = (float) Math.random();
        flipX = MathUtils.randomBoolean();
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            createLight();
            initialized = true;
        }

        drawFrame();
        stateTime += delta;

    }

    public void pickAnim() {
        switch(chunk.biome) {
            case "Dungeon" -> {
                anim = GeneralDecorResources.walltorch_anim_green;
                green = 0.6f;
                blue = 0.45f;
                red = 0.1f;
            }
            case "Forest" -> {
                anim = GeneralDecorResources.walltorch_anim;
                red = 0.75f;
                green = 0.3f;
            }
        }
    }

    public void createLight() {
        light = chunk.screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, 0.9f, 75, new Vector2(position.x, position.y + 0.2f));
        chunk.screen.lightManager.addLight(light);
    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(position.x * PPM, position.y * PPM);
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
