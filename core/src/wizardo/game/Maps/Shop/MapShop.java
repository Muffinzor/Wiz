package wizardo.game.Maps.Shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Maps.TriggerObject;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Shop.ShopScreen;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Display.DisplayUtils.getSprite;
import static wizardo.game.Utils.Constants.PPM;

public class MapShop extends TriggerObject {

    MapShopInventory inventory;

    public BattleScreen screen;

    public MapShop(MapChunk chunk, MapObject object) {
        super(chunk, object);
        this.screen = (BattleScreen) chunk.screen;
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createTriggerBody();
        }

        drawFrame();
        stateTime += Gdx.graphics.getDeltaTime();
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
        if(inventory == null) {
            createInventory();
        }
        chunk.game.addNewScreen(new ShopScreen(chunk.game, this));
    }

    public void createInventory() {
        inventory = new MapShopInventory(this);
        inventory.createInventory();
    }

    public ArrayList<Equipment> getGearList() {
        return inventory.gear;
    }
    public ArrayList<SpellUtils.Spell_Name> getScrollMasteries() {
        return inventory.scrolls;
    }
    public Boolean[] getScrollSolds() {
        return inventory.scroll_solds;
    }
    public Boolean[] getReagentSolds() {
        return inventory.reagent_solds;
    }
}
