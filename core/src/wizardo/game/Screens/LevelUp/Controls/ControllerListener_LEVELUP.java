package wizardo.game.Screens.LevelUp.Controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import wizardo.game.Screens.EscapeMenu.EscapeScreen;
import wizardo.game.Screens.LevelUp.LevelUpScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;

public class ControllerListener_LEVELUP extends ControllerAdapter {

    LevelUpScreen screen;

    public ControllerListener_LEVELUP(LevelUpScreen screen) {
        this.screen = screen;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisIndex, float value) {

        if(Math.abs(value) < 0.5f) {
            value = 0;
        } else if (!controllerActive) {
            controllerActive = true;
            screen.hideCursor();
        }

        if (axisIndex == 1) {

            if (value > 0.5f && screen.globalCD <= 0) {

                screen.globalCD = 0.3f;
                screen.menuTable.navigateUp();
                return true;

            } else if (value < -0.5f && screen.globalCD <= 0) {

                screen.globalCD = 0.3f;
                screen.menuTable.navigateDown();
                return true;

            }
        }
        return true;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonIndex) {
        screen.globalCD = 0.3f;

        switch (buttonIndex) {
            case 11: //D-pad DOWN
                screen.menuTable.navigateDown();
                return true;
            case 12: //D-pad UP
                screen.menuTable.navigateUp();
                return true;
        }

        if (buttonIndex == 0) {
            screen.menuTable.pressSelectedButton();
            return true;
        }
        return false;
    }

}
