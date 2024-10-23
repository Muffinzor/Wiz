package wizardo.game.Screens.Character.Controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.Character.CharacterScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;

public class ControllerListener_CHARACTERSCREEN extends ControllerAdapter {

    CharacterScreen screen;

    MenuTable activeTable;

    public ControllerListener_CHARACTERSCREEN(CharacterScreen screen) {
        this.screen = screen;
        activeTable = screen.mastery_table;
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
                activeTable.navigateUp();
                return true;

            } else if (value < -0.5f && screen.globalCD <= 0) {

                screen.globalCD = 0.3f;
                activeTable.navigateDown();
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
                activeTable.navigateDown();
                return true;
            case 12: //D-pad UP
                activeTable.navigateUp();
                return true;
        }

        if (buttonIndex == 0) {
            screen.menuTable.pressSelectedButton();
            return true;
        }
        return false;
    }

}
