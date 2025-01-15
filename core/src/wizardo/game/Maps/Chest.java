package wizardo.game.Maps;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Resources.DecorResources.GeneralDecorResources;
import wizardo.game.Screens.Battle.BattleScreen;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

/**
 * tier -2 in constructor to randomly select from Metal tier and above
 */
public class Chest extends TriggerObject {

    int tier;
    boolean opened;
    public Body body;
    RoundLight light;

    Sprite sprite;
    Animation<Sprite> openAnim;

    BattleScreen screen;
    public ChestLoot loot;

    boolean fromEllipseObject;  //if doodad rolled a chest
    Vector2 ellipseCenter;

    public void findTier() {
        if(tier < 0) {

            float goldChance = 0.95f - (player.stats.luck/100f) * 0.05f;
            float stoneChance = 0.85f - (player.stats.luck/100f) * 0.05f;
            float redChance = 0.7f - (player.stats.luck/100f) * 0.075f;
            float metalChance = 0.45f - (player.stats.luck/100f) * 0.1f;
            if(tier == -2) {
                metalChance = 0;
            }

            double roll = Math.random();
            if(roll >= goldChance) {
                tier = 4;
            } else if(roll >= stoneChance) {
                tier = 3;
            } else if(roll >= redChance) {
                tier = 2;
            } else if(roll >= metalChance) {
                tier = 1;
            } else {
                tier = 0;
            }
        }


    }
    public void setup() {
        switch(tier) {
            case 0 -> {
                sprite = GeneralDecorResources.woodenChest_anim.getKeyFrame(0);
                openAnim = GeneralDecorResources.woodenChest_anim;
            }
            case 1 -> {
                sprite = GeneralDecorResources.metalChest_anim.getKeyFrame(0);
                openAnim = GeneralDecorResources.metalChest_anim;
            }
            case 2 -> {
                sprite = GeneralDecorResources.redChest_anim.getKeyFrame(0);
                openAnim = GeneralDecorResources.redChest_anim;
            }
            case 3 -> {
                sprite = GeneralDecorResources.stoneChest_anim.getKeyFrame(0);
                openAnim = GeneralDecorResources.stoneChest_anim;
            }
            case 4 -> {
                sprite = GeneralDecorResources.goldChest_anim.getKeyFrame(0);
                openAnim = GeneralDecorResources.goldChest_anim;
            }
        }
        if(object instanceof EllipseMapObject) {
            float width = object.getProperties().get("width", Float.class);
            float height = object.getProperties().get("height", Float.class);
            float x = object.getProperties().get("x", Float.class) + chunk.x_pos + width/2;
            float y = object.getProperties().get("y", Float.class) + chunk.y_pos + height/2;

            ellipseCenter = new Vector2(x/PPM, y/PPM);

            fromEllipseObject = true;
        }
    }

    public Chest(MapChunk chunk, MapObject object, int tier) {
        super(chunk, object);
        this.tier = tier;
        this.screen = (BattleScreen) chunk.screen;
        findTier();
        setup();
        loot = new ChestLoot(this);
    }

    public void trigger() {
        stateTime = 0;
        opened = true;
        light.dimKill(0.01f);
        player.nearbyTriggerObject = null;
        world.destroyBody(triggerBody);
        screen.dropManager.dropChestLoot(this);
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            if(!opened) {
                createTriggerBody();
                createLight();
            }

        }

        drawFrame();
        stateTime += delta;
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        if(!opened) {
            frame.set(sprite);
        } else {
            frame.set(openAnim.getKeyFrame(stateTime, false));
        }
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        body = MapUtils.createCircleDecorBody_FromTiledMap(chunk, object, 8, false, true);
        if(fromEllipseObject) {
            body.setTransform(ellipseCenter, 0);
        }
        body.setUserData(this);
    }

    public void createTriggerBody() {
        triggerBody = MapUtils.createEventTriggerBody(chunk, object, 60);
        if(fromEllipseObject) {
            triggerBody.setTransform(ellipseCenter, 0);
        }
        triggerBody.setUserData(this);
    }

    public void createLight() {
        light = chunk.screen.lightManager.pool.getLight();
        light.setLight(0,0,0f,0.8f,50, triggerBody.getPosition());
        chunk.screen.lightManager.addLight(light);
    }


    @Override
    public void dispose() {
        if(initialized) {
            initialized = false;
            world.destroyBody(body);
            if (!opened) {
                world.destroyBody(triggerBody);
                light.dimKill(0.5f);
            }
        }
    }

    @Override
    public void handleCollision() {

    }
}
