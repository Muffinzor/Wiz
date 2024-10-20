package wizardo.game.Screens.Settings;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.EscapeMenu.EscapeMenuTable;
import wizardo.game.Wizardo;

import static wizardo.game.Resources.Skins.mainMenuSkin;

public class SettingsScreen extends BaseScreen {

    public SettingsTable settingsTable;

    public SettingsScreen(Wizardo game) {
        super(game);

        stage = new Stage(new ScreenViewport(uiCamera));
        settingsTable = new SettingsTable(stage, mainMenuSkin, game);

    }

    @Override
    public void render(float delta) {
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
