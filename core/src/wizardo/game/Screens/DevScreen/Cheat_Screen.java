package wizardo.game.Screens.DevScreen;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Screens.BaseScreen;

import wizardo.game.Wizardo;

import static wizardo.game.Resources.Skins.mainMenuSkin;

public class Cheat_Screen extends BaseScreen {

    //masteryTable table;


    public Cheat_Screen(Wizardo game) {
        super(game);

        stage = new Stage(new ScreenViewport(uiCamera));
        menuTable = new masteryTable(stage, mainMenuSkin, game);

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
