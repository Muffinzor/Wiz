package wizardo.game.Screens.Shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Maps.Shop.MapShop;
import wizardo.game.Resources.ScreenResources.CharacterScreenResources;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.CharacterScreen.Anims.Screen_Anim;
import wizardo.game.Screens.CharacterScreen.EquipmentTable.GearPanel;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Resources.Skins.shopSkin;

public class ShopScreen extends BaseScreen {

    ArrayList<Screen_Anim> anims;

    float stateTime;
    Stage panelStage;
    GearPanel activePanel;

    MapShop shop;
    public ShopTable_Gear gearTable;
    public ShopTable_Scrolls scrollTable;

    Button selectedButton;
    BattleScreen battle;

    public ShopScreen(Wizardo game, MapShop shop) {
        super(game);
        this.shop = shop;
        anims = new ArrayList<>();

        battle = (BattleScreen) game.currentScreen;
        stage = new Stage(new ScreenViewport(uiCamera));
        panelStage = new Stage(new ScreenViewport(uiCamera));

        gearTable = new ShopTable_Gear(stage, shopSkin, game, this);
        scrollTable = new ShopTable_Scrolls(stage, shopSkin, game, this);
    }

    @Override
    public void render(float delta) {
        stateTime += delta;
        globalCD -= delta;

        stage.act(delta);
        stage.draw();

        gearTable.drawItems();
        scrollTable.drawItems();

        panelStage.act(delta);
        panelStage.draw();

        if(controllerActive) {
            drawSelectedButton();
        }

        drawPurchaseAnims(delta);
        battle.displayManager.textManager.updateGoldTexts(delta);
        battle.displayManager.textManager.updateBottomTexts(delta);

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public void drawPurchaseAnims(float delta) {
        batch.begin();
        for(Screen_Anim anim : anims) {
            anim.draw(batch);
            anim.stateTime += delta;
        }
        anims.removeIf(Screen_Anim::isFinished);
        batch.end();
    }

    public void drawSelectedButton() {
        Sprite frame = getSprite();
        frame.set(CharacterScreenResources.selected_button_anim.getKeyFrame(stateTime, true));

        Vector2 buttonLocalPos = new Vector2(selectedButton.getX(), selectedButton.getY());

        Vector2 tableLocalPos = new Vector2(selectedButton.getParent().getX(), selectedButton.getParent().getY());

        float screenButton_X = tableLocalPos.x + buttonLocalPos.x;
        float screenButton_Y = tableLocalPos.y + buttonLocalPos.y;

        screenButton_X += selectedButton.getWidth()/2;
        screenButton_Y += selectedButton.getHeight()/2;

        frame.setScale(Gdx.graphics.getWidth()/1920f);
        frame.setCenter(screenButton_X, screenButton_Y);

        batch.begin();
        frame.draw(batch);
        batch.end();
    }
}
