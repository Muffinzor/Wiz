package wizardo.game.Screens.EscapeMenu.Controls;

import com.badlogic.gdx.InputAdapter;
import wizardo.game.Screens.EscapeMenu.EscapeScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Screens.BaseScreen.inputMultiplexer;

public class KeyboardMouseListener_ESCAPE extends InputAdapter {

    EscapeScreen screen;

    public KeyboardMouseListener_ESCAPE(EscapeScreen screen) {

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
