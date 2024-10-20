package wizardo.game.Screens.Popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

public class AreYouSureScreen extends BaseScreen {

    public float globalCD = 0;
    public YesOrNoTable menuTable;

    private Skin mainMenuSkin = new Skin(Gdx.files.internal("MainMenuScreen/MainMenuSkin/MainMenuSkin.json"));

    public AreYouSureScreen(Wizardo game) {
        super(game);

        stage = new Stage(new ScreenViewport(uiCamera));
        menuTable = new YesOrNoTable(stage, mainMenuSkin, game);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
