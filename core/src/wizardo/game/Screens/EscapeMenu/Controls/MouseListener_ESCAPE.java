package wizardo.game.Screens.EscapeMenu.Controls;

import com.badlogic.gdx.InputAdapter;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.EscapeMenu.EscapeScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Screens.BaseScreen.inputMultiplexer;

public class MouseListener_ESCAPE extends InputAdapter {

    EscapeScreen screen;

    public MouseListener_ESCAPE(EscapeScreen screen) {

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
