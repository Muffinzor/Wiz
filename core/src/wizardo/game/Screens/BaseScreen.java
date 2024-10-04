package wizardo.game.Screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Wizardo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class BaseScreen extends ScreenAdapter {

    public Wizardo game;

    public BaseScreen(Wizardo game) {
        this.game = game;

    }

    public abstract void render(float delta);

    public abstract void dispose();

    @Override
    public void resize(int width, int height) {

    }

    public void show() {

    }

    public void hide() {

    }


}
