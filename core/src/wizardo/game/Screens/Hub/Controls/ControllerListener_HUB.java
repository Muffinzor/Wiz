package wizardo.game.Screens.Hub.Controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import wizardo.game.Player.Pawn;
import wizardo.game.Screens.EscapeMenu.EscapeScreen;
import wizardo.game.Screens.Hub.HubScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;

public class ControllerListener_HUB extends ControllerAdapter {

    Pawn pawn;
    HubScreen screen;

    public ControllerListener_HUB(HubScreen screen) {
        this.pawn = screen.playerPawn;
        this.screen = screen;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisIndex, float value) {

        switch(axisIndex) {
            case 0, 1 -> {

            }
        }



        if(Math.abs(value) < 0.1f) {
            value = 0;
        } else if (!controllerActive) {
            controllerActive = true;
            screen.hideCursor();
        }

        if (axisIndex == 0) {
            pawn.moveX(value);
        } else if (axisIndex == 1) {
            pawn.moveY(value);
        } else if (axisIndex == 2) {
            pawn.aimX(value);
        } else if (axisIndex == 3) {
            pawn.aimY(-value);
            
        }

        // Left joystick: Axis 0 = left/right, Axis 1 = up/down
        // Right joystick: Axis 2 = aim left/right, Axis 3 = aim up/down


        return true;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonIndex) {
        System.out.println("HUB SCREEN : Button " + buttonIndex + " pressed");
        screen.globalCD = 0.3f;

        switch (buttonIndex) {
            case 6: //Options - PS4
                screen.game.addNewScreen(new EscapeScreen(screen.game));
                return true;
        }

        if (buttonIndex == 0) {
            return true;
        }
        return false;
    }
}
