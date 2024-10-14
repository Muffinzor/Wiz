package wizardo.game.Maps;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.DecorResources.GeneralDecorResources;
import wizardo.game.Screens.Hub.BattleSelection.BattleSelectionScreen;

import static wizardo.game.Display.DisplayUtils.getSprite;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class PortalObject extends TriggerObject {

    public RoundLight light;
    boolean touched;

    public PortalObject(MapChunk chunk, MapObject object) {
        super(chunk, object);
    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            createLight();
        }

        stateTime += delta;
        drawSprite();

    }

    @Override
    public void dispose() {
        light.pointLight.remove();
        light = null;
        world.destroyBody(body);
        body = null;
    }

    public void handleCollision() {
        chunk.screen.game.setOverScreen(new BattleSelectionScreen(chunk.screen.game));
    }

    public void drawSprite() {
        Sprite frame = getSprite(chunk.screen);
        frame.set(GeneralDecorResources.blue_portal_anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        chunk.screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
    }

    public void createBody() {
        body = MapUtils.createEventTriggerBody(chunk, object, 15);
        body.setUserData(this);
    }

    public void createLight() {
        light = new RoundLight(chunk.screen);
        light.setLight(0,0,0.4f,0.8f,125, body.getPosition());
    }
}
