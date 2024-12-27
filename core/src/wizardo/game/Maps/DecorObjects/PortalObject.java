package wizardo.game.Maps.DecorObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Maps.TriggerObject;
import wizardo.game.Resources.DecorResources.GeneralDecorResources;
import wizardo.game.Screens.Hub.BattleSelection.BattleSelectionScreen;

import static wizardo.game.Display.DisplayUtils.getSprite;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class PortalObject extends TriggerObject {

    public RoundLight light;
    boolean active;

    public PortalObject(MapChunk chunk, MapObject object, boolean active) {
        super(chunk, object);
        this.active = active;
    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            createLight();
            if(!active) {
                body.setActive(false);
            }
        }

        stateTime += delta;
        drawSprite();

    }

    @Override
    public void dispose() {
        if(initialized) {
            light.dimKill(0.5f);
            world.destroyBody(body);
            initialized = false;
        }
    }

    public void handleCollision() {
        chunk.screen.game.addNewScreen(new BattleSelectionScreen(chunk.screen.game));
    }

    public void drawSprite() {
        Sprite frame = getSprite(chunk.screen);
        frame.set(GeneralDecorResources.blue_portal_anim.getKeyFrame(stateTime, true));
        frame.setAlpha(0.8f);
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM + 20);
        chunk.screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
    }

    public void createBody() {
        body = MapUtils.createEventTriggerBody(chunk, object, 15);
        body.setUserData(this);
    }

    public void createLight() {
        light = new RoundLight(chunk.screen);
        light.setLight(0,0,0.4f,0.8f,125, body.getPosition());
        chunk.screen.lightManager.addLight(light);
    }
}
