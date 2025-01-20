package wizardo.game.Screens.CharacterScreen.Controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import wizardo.game.Display.MenuTable;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;
import wizardo.game.Screens.Popups.AreYouSureScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;

public class ControllerListener_CHARACTERSCREEN extends ControllerAdapter {

    CharacterScreen screen;

    public MenuTable activeTable;

    public ControllerListener_CHARACTERSCREEN(CharacterScreen screen) {
        this.screen = screen;
        activeTable = screen.mastery_table;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisIndex, float value) {
        activeTable = screen.activeTable;

        if(Math.abs(value) < 0.5f) {
            value = 0;
        } else if (!controllerActive) {
            controllerActive = true;
            screen.hideCursor();
        }

        if (axisIndex == 1 || axisIndex == 3) {

            if (value > 0.5f && screen.globalCD <= 0) {

                screen.globalCD = 0.3f;
                activeTable.navigateUp();
                return true;

            } else if (value < -0.5f && screen.globalCD <= 0) {

                screen.globalCD = 0.3f;
                activeTable.navigateDown();
                return true;

            }

        } else if (axisIndex == 0 || axisIndex == 2){

            if (value > 0.5f && screen.globalCD <= 0) {

                screen.globalCD = 0.3f;
                activeTable.navigateRight();
                return true;

            } else if (value < -0.5f && screen.globalCD <= 0) {

                screen.globalCD = 0.3f;
                activeTable.navigateLeft();
                return true;

            }
        }
        return true;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonIndex) {
        System.out.println("CHARACTER SCREEN : Button " + buttonIndex + " pressed");
        if (!controllerActive) {
            controllerActive = true;
            screen.hideCursor();
        }

        activeTable = screen.activeTable;

        screen.globalCD = 0.3f;

        switch (buttonIndex) {
            case 11: //D-pad DOWN
                activeTable.navigateDown();
                return true;
            case 12: //D-pad UP
                activeTable.navigateUp();
                return true;
            case 13: //D-pad LEFT
                activeTable.navigateLeft();
                return true;
            case 14: //D-pad RIGHT
                activeTable.navigateRight();
                return true;
            case 1: // B or Circle
                screen.game.setPreviousScreen();
                return true;
            case 2: // Square
                if(screen.activeTable == screen.inventory_table) {
                    int x = screen.inventory_table.x_pos;
                    int y = screen.inventory_table.y_pos;
                    Equipment piece = screen.inventory_table.buttonsMatrix[x][y].piece;
                    if(piece != null) {
                        screen.selectedEquipmentPiece = piece;
                        screen.game.addNewScreen(new AreYouSureScreen(screen.game, "This will destroy the item"));
                    }
                }
                return true;
        }

        if (buttonIndex == 0) {
            activeTable.pressSelectedButton();
            return true;
        }

        return false;
    }

}
