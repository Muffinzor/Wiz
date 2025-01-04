package wizardo.game.Maps.DecorObjects;

import com.badlogic.gdx.graphics.Texture;
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

    public static Sprite stoneCircle = new Sprite(new Texture("Maps/Decor/PortalCircle.png"));

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
                triggerBody.setActive(false);
            }
        }

        stateTime += delta;
        drawStoneCircle();
        drawSprite();

    }

    @Override
    public void dispose() {
        if(initialized) {
            light.dimKill(0.5f);
            world.destroyBody(triggerBody);
            initialized = false;
        }
    }

    public void handleCollision() {
        chunk.screen.game.addNewScreen(new BattleSelectionScreen(chunk.screen.game));
    }

    @Override
    public void trigger() {

    }

    public void drawSprite() {
        Sprite frame = getSprite(chunk.screen);
        frame.set(GeneralDecorResources.blue_portal_anim.getKeyFrame(stateTime, true));
        frame.setAlpha(0.8f);
        frame.setCenter(triggerBody.getPosition().x * PPM, triggerBody.getPosition().y * PPM + 20);
        chunk.screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
    }

    public void drawStoneCircle() {
        Sprite frame = getSprite(chunk.screen);
        frame.set(stoneCircle);
        frame.setCenter(triggerBody.getPosition().x * PPM, triggerBody.getPosition().y * PPM - 5);
        chunk.screen.displayManager.spriteRenderer.under_sprites.add(frame);
    }

    public void createBody() {
        triggerBody = MapUtils.createEventTriggerBody(chunk, object, 15);
        triggerBody.setUserData(this);
    }

    public void createLight() {
        light = chunk.screen.lightManager.pool.getLight();
        light.setLight(0,0,0.4f,0.8f,125, triggerBody.getPosition());
        chunk.screen.lightManager.addLight(light);
    }
}
