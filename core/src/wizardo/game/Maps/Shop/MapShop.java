package wizardo.game.Maps.Shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
import static wizardo.game.Wizardo.player;

public class MapShop extends TriggerObject {

    MapShopInventory inventory;
    public BattleScreen screen;

    boolean displayDirectionHelp;
    Sprite border_sprite = new Sprite(new Texture("Maps/direction_indicator.png"));
    Sprite shop_sprite = new Sprite(new Texture("Maps/shop_indicator.png"));

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
        triggerBody = MapUtils.createEventTriggerBodyFromMapObject(chunk, object, 75);
        triggerBody.setUserData(this);
        Vector2 CurrentPosition = triggerBody.getPosition();
        triggerBody.setTransform(CurrentPosition.x + 128f/PPM, CurrentPosition.y + 30f/PPM, 0);
    }

    public void drawFrame() {
        Sprite frame2 = getSprite(chunk.screen);
        frame2.set(DungeonDecorResources.dungeonshop_anim.getKeyFrame(stateTime, true));
        frame2.setCenter(triggerBody.getPosition().x * PPM, triggerBody.getPosition().y * PPM);
        chunk.screen.addSortedSprite(frame2);
        chunk.screen.centerSort(frame2, triggerBody.getPosition().y * PPM - 20);
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

    public void display_direction() {
        check_distance_to_player();
        give_direction_to_player();
    }

    public void check_distance_to_player() {
        float dst = triggerBody.getPosition().dst(player.pawn.getPosition());
        displayDirectionHelp = dst > 30 && dst < 4000f/PPM;
    }

    public void give_direction_to_player() {
        if(displayDirectionHelp) {

            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();
            Vector2 direction = new Vector2(triggerBody.getPosition()).sub(player.pawn.getPosition()).nor(); // Step 1 & 2
            float angle = direction.angleDeg() + 270;

            // Find the maximum scaling factor to keep the sprite at the screen edge
            float padding = 40;
            float scaleX = (screenWidth / 2 - padding) / Math.abs(direction.x);
            float scaleY = (screenHeight / 2 - padding) / Math.abs(direction.y);
            float scale = Math.min(scaleX, scaleY); // Pick the smallest scale to stay within screen bounds

            // Position relative to screen center

            Vector2 screenEdgePos = direction.scl(scale);
            Vector2 finalPosition = new Vector2(screenEdgePos.x + screenWidth / 2, screenEdgePos.y + screenHeight / 2);

            Sprite border_frame = screen.getSprite();
            border_frame.set(border_sprite);
            border_frame.setRotation(angle);
            border_frame.setCenter(finalPosition.x, finalPosition.y);
            screen.displayManager.spriteRenderer.ui_sprites.add(border_frame);

            Sprite npc_frame = screen.getSprite();
            npc_frame.set(shop_sprite);
            npc_frame.setCenter(finalPosition.x, finalPosition.y);
            screen.displayManager.spriteRenderer.ui_sprites.add(npc_frame);

        }
    }
}
