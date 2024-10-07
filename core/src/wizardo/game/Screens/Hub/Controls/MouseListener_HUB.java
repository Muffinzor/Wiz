
package wizardo.game.Screens.Hub.Controls;

import com.badlogic.gdx.InputAdapter;

import wizardo.game.Screens.BaseScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;

public class MouseListener_HUB extends InputAdapter {

    BaseScreen screen;

    public MouseListener_HUB(BaseScreen screen) {

        this.screen = screen;

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

