package wizardo.game.Screens.Hub.BattleSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

import java.util.ArrayList;

public class BattleSelectionScreen extends BaseScreen {

    BattleSelectionTable table;

    private Skin mainMenuSkin = new Skin(Gdx.files.internal("MainMenuScreen/MainMenuSkin/MainMenuSkin.json"));

    public BattleSelectionScreen(Wizardo game) {
        super(game);
        stage = new Stage(new ScreenViewport(uiCamera));
        buttons = new ArrayList<>();
        table = new BattleSelectionTable(stage, mainMenuSkin, buttons, game);
    }

    @Override
    public void render(float delta) {
        globalCD -= delta;

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
