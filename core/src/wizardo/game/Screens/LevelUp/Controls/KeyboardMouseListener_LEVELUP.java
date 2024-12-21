package wizardo.game.Screens.LevelUp.Controls;

import com.badlogic.gdx.InputAdapter;
import wizardo.game.Screens.EscapeMenu.EscapeScreen;
import wizardo.game.Screens.LevelUp.LevelUpScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Screens.BaseScreen.inputMultiplexer;

public class KeyboardMouseListener_LEVELUP extends InputAdapter {

    LevelUpScreen screen;

    public KeyboardMouseListener_LEVELUP(LevelUpScreen screen) {

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
