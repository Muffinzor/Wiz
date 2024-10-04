package wizardo.game.Screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import wizardo.game.Wizardo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class BaseScreen extends ScreenAdapter {

    protected Wizardo game;
    protected SpriteBatch batch;

    public BaseScreen(Wizardo game) {
        this.game = game;
        this.batch = new SpriteBatch();
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
