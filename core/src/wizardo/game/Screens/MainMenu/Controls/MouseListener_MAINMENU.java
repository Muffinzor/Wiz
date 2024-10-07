package wizardo.game.Screens.MainMenu.Controls;

import com.badlogic.gdx.InputAdapter;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.MainMenu.MainMenuScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Screens.BaseScreen.inputMultiplexer;

public class MouseListener_MAINMENU extends InputAdapter {


    MainMenuScreen screen;

    public MouseListener_MAINMENU(MainMenuScreen screen) {

        this.screen = screen;
        inputMultiplexer.addProcessor(this.screen.stage);

    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        if (controllerActive) {
            controllerActive = false;
            screen.showCursor();
        }

        return true;
    }

}
