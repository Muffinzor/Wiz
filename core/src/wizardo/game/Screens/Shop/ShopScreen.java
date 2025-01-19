package wizardo.game.Screens.Shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    public Button selectedButton;
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
        menuTable = scrollTable;
        selectedButton = scrollTable.scrollButtons.getFirst();
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
        if(selectedButton != null) {
            Sprite frame = getSprite();
            frame.set(CharacterScreenResources.selected_button_anim.getKeyFrame(stateTime, true));

            float x = selectedButton.getX();
            float y = selectedButton.getY();

            x += selectedButton.getParent().getX();
            y += selectedButton.getParent().getY();

            if (selectedButton.getParent().getParent() != null) {
                x += selectedButton.getParent().getParent().getX();
                y += selectedButton.getParent().getParent().getY();

                if (selectedButton.getParent().getParent().getParent() != null) {
                    x += selectedButton.getParent().getParent().getParent().getX();
                    y += selectedButton.getParent().getParent().getParent().getY();

                    if (selectedButton.getParent().getParent().getParent().getParent() != null) {
                        x += selectedButton.getParent().getParent().getParent().getParent().getX();
                        y += selectedButton.getParent().getParent().getParent().getParent().getY();
                    }
                }

            }

            x += selectedButton.getWidth() / 2;
            y += selectedButton.getHeight() / 2;

            frame.setScale(xRatio);
            frame.setCenter(x, y);

            batch.begin();
            frame.draw(batch);
            batch.end();
        }
    }
}
