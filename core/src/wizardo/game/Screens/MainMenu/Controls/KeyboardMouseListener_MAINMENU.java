package wizardo.game.Screens.MainMenu.Controls;

import com.badlogic.gdx.InputAdapter;
import wizardo.game.Audio.Sounds.SoundPlayer;
import wizardo.game.Screens.MainMenu.MainMenuScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Screens.BaseScreen.inputMultiplexer;

public class KeyboardMouseListener_MAINMENU extends InputAdapter {


    MainMenuScreen screen;

    public KeyboardMouseListener_MAINMENU(MainMenuScreen screen) {

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
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        SoundPlayer.getSoundPlayer().playSound("Sounds/click_1.mp3", 1);
        return true;
    }

}
