package wizardo.game.Screens.Battle.Controls;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import wizardo.game.Player.Pawn;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;
import wizardo.game.Screens.DevScreen.Cheat_Screen;
import wizardo.game.Screens.EscapeMenu.EscapeScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Wizardo.player;

public class ControllerListener_BATTLE extends ControllerAdapter {


    Pawn pawn;
    BattleScreen screen;

    public float last_x_value = 0;
    public float last_y_value = 0;

    float magnitude;

    public ControllerListener_BATTLE(BattleScreen screen) {
        this.pawn = player.pawn;
        this.screen = screen;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisIndex, float value) {
        if(!controllerActive && value > 0.1f) {
            controllerActive = true;
            screen.hideCursor();
        }

        switch(axisIndex) {
            case 0, 1 -> {
                if (Math.abs(value) < 0.1f) {
                    value = 0;
                }
            }
            case 2, 3 -> {

                if(axisIndex == 2) {
                    last_x_value = Math.abs(value);
                } else {
                    last_y_value = Math.abs(value);
                }
                magnitude = last_y_value + last_x_value;

                if (magnitude < 0.05f) {
                    value = 0;
                }
            }

        }



        if (axisIndex == 0) {
            pawn.moveX(value);
        } else if (axisIndex == 1) {
            pawn.moveY(value);
        } else if (axisIndex == 2) {
            if(magnitude > 0.5f)
                pawn.aimX(value);
        } else if (axisIndex == 3) {
            if(magnitude > 0.5f)
                pawn.aimY(-value);
        }

        // Left joystick: Axis 0 = left/right, Axis 1 = up/down
        // Right joystick: Axis 2 = aim left/right, Axis 3 = aim up/down


        return true;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonIndex) {
        System.out.println("BATTLE SCREEN : Button " + buttonIndex + " pressed");
        screen.globalCD = 0.3f;

        switch (buttonIndex) {
            case 6: // Options - PS4
                screen.game.addNewScreen(new EscapeScreen(screen.game));
                return true;

            case 3: // Y or Triangle
                CharacterScreen char_screen = new CharacterScreen(screen.game);
                screen.game.addNewScreen(char_screen);
                return true;

            case 9: // Left Bumper
                Cheat_Screen cheats = new Cheat_Screen(screen.game);
                screen.game.addNewScreen(cheats);
                return true;

            case 0: // A or X
                if (player.nearbyTriggerObject != null) {
                    player.nearbyTriggerObject.trigger();
                }
        }



        if (buttonIndex == 0) {
            return true;
        }
        return false;
    }
}
