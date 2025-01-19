package wizardo.game.Screens.EscapeMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

public class EscapeScreen extends BaseScreen {

    public EscapeMenuTable menuTable;

    private Skin mainMenuSkin = new Skin(Gdx.files.internal("Screens/MainMenuScreen/MainMenuSkin/MainMenuSkin.json"));

    public EscapeScreen(Wizardo game) {
        super(game);

        stage = new Stage(new ScreenViewport(uiCamera));
        menuTable = new EscapeMenuTable(stage, mainMenuSkin, game);

    }

    @Override
    public void render(float delta) {
        globalCD -= delta;

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        paused = true;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        menuTable.dispose();
        menuTable = new EscapeMenuTable(stage, mainMenuSkin, game);

        stage.getViewport().update(width, height, true);

    }
}
