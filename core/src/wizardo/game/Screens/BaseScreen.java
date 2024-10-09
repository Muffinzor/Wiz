package wizardo.game.Screens;

import box2dLight.RayHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import wizardo.game.Display.SpriteRenderer;
import wizardo.game.Wizardo;

import java.util.ArrayList;

public abstract class BaseScreen implements Screen {

    public static InputMultiplexer inputMultiplexer = new InputMultiplexer();
    public static boolean controllerActive;
    public static boolean mouseInvisible;
    public static float screenRatio;

    public Wizardo game;
    protected SpriteBatch batch;

    public boolean paused;
    public float globalCD = 0;

    protected ControllerAdapter controllerAdapter;
    protected InputAdapter mouseAdapter;
    protected InputProcessor keyboardProcessor;

    public SpriteRenderer spriteRenderer;
    public RayHandler rayHandler;
    public ArrayList<Animation> animations;
    public Stage stage;
    protected ArrayList<Button> buttons;
    protected String cursorTexturePath;

    protected OrthographicCamera camera;

    public BaseScreen(Wizardo game) {

        this.game = game;
        this.batch = new SpriteBatch();
        spriteRenderer = new SpriteRenderer();
        animations = new ArrayList<>();
        buttons = new ArrayList<>();

        cursorTexturePath = "Cursors/Menu_Cursor.png";
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    public abstract void render(float delta);

    public abstract void dispose();

    @Override
    public void resize(int width, int height) {
        screenRatio = width/1920f;
    }

    public void show() {

    }

    public void hide() {

    }

    public void removeInputs() {
        if(controllerAdapter != null) {
            for (Controller controller : Controllers.getControllers()) {
                controller.removeListener(controllerAdapter);
            }
        }
    }


    public void hideCursor() {

        if(!mouseInvisible) {

            mouseInvisible = true;
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            Cursor invisibleCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
            Gdx.graphics.setCursor(invisibleCursor);
            pixmap.dispose();

        }
    }

    public void showCursor() {

        if(mouseInvisible) {

            mouseInvisible = false;

            Pixmap pixmap = new Pixmap(Gdx.files.internal("Cursors/Menu_Cursor.png"));
            Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
            Gdx.graphics.setCursor(customCursor);
            pixmap.dispose();

        }

    }


}
