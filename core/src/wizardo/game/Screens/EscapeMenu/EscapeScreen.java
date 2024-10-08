package wizardo.game.Screens.EscapeMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.EscapeMenu.Controls.ControllerListener_ESCAPE;
import wizardo.game.Screens.EscapeMenu.Controls.KeyboardMouseListener_ESCAPE;
import wizardo.game.Wizardo;

public class EscapeScreen extends BaseScreen {

    public float globalCD = 0;
    public EscapeMenuTable menuTable;

    private Skin mainMenuSkin = new Skin(Gdx.files.internal("MainMenuScreen/MainMenuSkin/MainMenuSkin.json"));

    public EscapeScreen(Wizardo game) {
        super(game);

        stage = new Stage();
        menuTable = new EscapeMenuTable(stage, mainMenuSkin, buttons, game);

    }

    private void setInputs() {
        mouseAdapter = new KeyboardMouseListener_ESCAPE(this);
        controllerAdapter = new ControllerListener_ESCAPE(this);

        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(mouseAdapter);
        for (Controller controller : Controllers.getControllers()) {
            controller.addListener(controllerAdapter);
        }
    }

    @Override
    public void render(float delta) {
        globalCD -= delta;

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        setInputs();
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
}
