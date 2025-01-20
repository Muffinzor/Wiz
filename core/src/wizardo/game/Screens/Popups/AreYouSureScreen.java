package wizardo.game.Screens.Popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Resources.Skins.mainMenuSkin;

public class AreYouSureScreen extends BaseScreen {

    public float globalCD = 0;
    YesOrNoTable table;

    public AreYouSureScreen(Wizardo game, String message) {
        super(game);

        stage = new Stage(new ScreenViewport(uiCamera));
        menuTable = new YesOrNoTable(stage, mainMenuSkin, game, message);
        table = (YesOrNoTable) menuTable;
    }

    @Override
    public void render(float delta) {

        table.updateSelectedButton();

        stateTime += delta;

        stage.act();
        stage.draw();
        if(controllerActive) {
            drawSelectedButton();
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
