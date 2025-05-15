package wizardo.game.Screens.NpcScreens.WitchScreen;

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
import wizardo.game.Screens.Hub.HubScreen;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Resources.Skins.shopSkin;

public class WitchScreen extends BaseScreen {

    ArrayList<Screen_Anim> anims;

    float stateTime;
    public Button selectedButton;

    HubScreen hubScreen;


    public WitchScreen(Wizardo game, HubScreen hubScreen) {
        super(game);
        this.hubScreen = hubScreen;
        anims = new ArrayList<>();
        stage = new Stage(new ScreenViewport(uiCamera));
    }

    @Override
    public void render(float delta) {
        stateTime += delta;
        globalCD -= delta;

        stage.act(delta);
        stage.draw();

        if(controllerActive) {
            drawSelectedButton();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.setViewport(new ScreenViewport(uiCamera));
        stage.getViewport().update(width, height, true);
        batch.setProjectionMatrix(uiCamera.combined);
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

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
