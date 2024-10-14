package wizardo.game.Screens.EscapeMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

public class EscapeScreen extends BaseScreen {

    public float globalCD = 0;
    public EscapeMenuTable menuTable;

    private Skin mainMenuSkin = new Skin(Gdx.files.internal("MainMenuScreen/MainMenuSkin/MainMenuSkin.json"));

    public EscapeScreen(Wizardo game) {
        super(game);

        stage = new Stage(new ScreenViewport(uiCamera));
        menuTable = new EscapeMenuTable(stage, mainMenuSkin, buttons, game);

    }

    @Override
    public void render(float delta) {
        globalCD -= delta;

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        super.show();
        paused = true;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        setInputs();
    }

    @Override
    public void dispose() {
        removeInputs();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        menuTable.dispose();
        menuTable = new EscapeMenuTable(stage, mainMenuSkin, buttons, game);

        stage.getViewport().update(width, height, true);

    }
}
