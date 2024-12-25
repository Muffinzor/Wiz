package wizardo.game.Screens.Battle.Controls;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import wizardo.game.Player.Pawn;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Character.CharacterScreen;
import wizardo.game.Screens.DevScreen.Cheat_Screen;
import wizardo.game.Screens.EscapeMenu.EscapeScreen;
import wizardo.game.Screens.LevelUp.LevelUpScreen;

import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Wizardo.player;

public class KeyboardMouseListener_BATTLE implements InputProcessor {

    boolean A_pressed;
    boolean S_pressed;
    boolean D_pressed;
    boolean W_pressed;

    Pawn pawn;
    BattleScreen screen;

    public KeyboardMouseListener_BATTLE(BattleScreen screen) {
        this.screen = screen;
        this.pawn = player.pawn;
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

        if (keycode == Input.Keys.C) {
            CharacterScreen char_screen = new CharacterScreen(screen.game);
            screen.game.addNewScreen(char_screen);
        }

        if (keycode == Input.Keys.ESCAPE) {
            screen.game.addNewScreen(new EscapeScreen(screen.game));
        }

        if (keycode == Input.Keys.SPACE) {
            screen.game.addNewScreen(new LevelUpScreen(screen.game));
        }

        if (keycode == Input.Keys.F1) {
            screen.game.addNewScreen(new Cheat_Screen(screen.game));
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
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
