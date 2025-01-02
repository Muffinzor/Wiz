package wizardo.game.Screens.Shop.Controls;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import wizardo.game.Screens.EscapeMenu.EscapeScreen;
import wizardo.game.Screens.Shop.ShopScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Screens.BaseScreen.inputMultiplexer;

public class KeyboardMouseListener_MAPSHOP extends InputAdapter {

    ShopScreen screen;

    public KeyboardMouseListener_MAPSHOP(ShopScreen screen) {

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

        if (keycode == Input.Keys.ESCAPE) {
            screen.game.setPreviousScreen();
        }

        return true;
    }
}
