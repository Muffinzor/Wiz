package wizardo.game.Screens.Hub.Controls;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import wizardo.game.Player.Pawn;
import wizardo.game.Screens.EscapeMenu.EscapeScreen;
import wizardo.game.Screens.Hub.HubScreen;

public class KeyboardListener_HUB implements InputProcessor {

    boolean A_pressed;
    boolean S_pressed;
    boolean D_pressed;
    boolean W_pressed;

    Pawn pawn;
    HubScreen screen;

    public KeyboardListener_HUB(Pawn pawn, HubScreen screen) {
        this.screen = screen;
        this.pawn = pawn;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) {
            pawn.moveX(-1);
            A_pressed = true;
        }

        if (keycode == Input.Keys.W) {
            pawn.moveY(-1);
            W_pressed = true;
        }

        if (keycode == Input.Keys.S) {
            pawn.moveY(1);
            S_pressed = true;
        }

        if (keycode == Input.Keys.D) {
            pawn.moveX(1);
            D_pressed = true;
        }

        if (keycode == Input.Keys.ESCAPE) {
            screen.game.setNewScreen(new EscapeScreen(screen.game));
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A) {
            if(!D_pressed) {
                pawn.moveX(0);
            } else {
                pawn.moveX(1);
            }
            A_pressed = false;
        }

        if (keycode == Input.Keys.W) {
            if(!S_pressed) {
                pawn.moveY(0);
            } else {
                pawn.moveY(1);
            }
            W_pressed = false;

        }

        if (keycode == Input.Keys.S) {
            if(!W_pressed) {
                pawn.moveY(0);
            } else {
                pawn.moveY(-1);
            }
            S_pressed = false;
        }

        if (keycode == Input.Keys.D) {
            if(!A_pressed) {
                pawn.moveX(0);
            } else {
                pawn.moveX(-1);
            }
            D_pressed = false;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
