package wizardo.game.Controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import wizardo.game.Screens.BaseScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;

public class PlayerControllerListener_TEMPLATE extends ControllerAdapter {

    BaseScreen screen;

    public PlayerControllerListener_TEMPLATE(BaseScreen screen) {

        this.screen = screen;

    }

    @Override
    public boolean axisMoved(Controller controller, int axisIndex, float value) {

        if(Math.abs(value) < 0.4f) {
            value = 0;
        } else if (!controllerActive) {
            controllerActive = true;
            screen.hideCursor();
        }

        return true;
    }
}
