package wizardo.game.Screens.CharacterScreen.Controls;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Screens.BaseScreen.inputMultiplexer;

public class KeyboardMouseListener_CHARACTERSCREEN extends InputAdapter {

    CharacterScreen screen;

    public KeyboardMouseListener_CHARACTERSCREEN(CharacterScreen screen) {

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

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.C || keycode == Input.Keys.ESCAPE) {
            screen.game.setPreviousScreen();
        }

        return true;
    }
}
