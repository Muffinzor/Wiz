package wizardo.game.Maps.Shop;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Maps.TriggerObject;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;
import wizardo.game.Screens.Character.CharacterScreen;
import wizardo.game.Screens.Popups.AreYouSureScreen;
import wizardo.game.Screens.Shop.ShopScreen;

import static wizardo.game.Display.DisplayUtils.getSprite;
import static wizardo.game.Utils.Constants.PPM;

public class MapShop extends TriggerObject {

    public MapShop(MapChunk chunk, MapObject object) {
        super(chunk, object);
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createTriggerBody();
        }

        drawFrame();
        stateTime += delta;
    }

    public void createTriggerBody() {
        triggerBody = MapUtils.createEventTriggerBody(chunk, object, 75);
        triggerBody.setUserData(this);
        Vector2 CurrentPosition = triggerBody.getPosition();
        triggerBody.setTransform(CurrentPosition.x + 128f/PPM, CurrentPosition.y + 30f/PPM, 0);
    }

    public void drawFrame() {
        Sprite frame2 = getSprite(chunk.screen);
        frame2.set(DungeonDecorResources.dungeonshop_anim.getKeyFrame(stateTime, true));
        frame2.setCenter(triggerBody.getPosition().x * PPM, triggerBody.getPosition().y * PPM);
        chunk.screen.addSortedSprite(frame2);
        chunk.screen.centerSort(frame2, triggerBody.getPosition().y * PPM -1);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void handleCollision() {

    }

    @Override
    public void trigger() {
        chunk.game.addNewScreen(new AreYouSureScreen(chunk.game, "This will destroy the item"));
    }
}
