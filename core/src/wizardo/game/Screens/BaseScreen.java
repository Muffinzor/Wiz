/**
 * Where the logic of every game state happens
 * Every BaseScreen inheritor has its own Control Listeners, Camera, DisplayManager, SpriteRenderer
 */



package wizardo.game.Screens;

import box2dLight.RayHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import wizardo.game.Controls.ControllerListener_TABLEMENU;
import wizardo.game.Display.DisplayManager;
import wizardo.game.Display.MenuTable;
import wizardo.game.Lighting.LightManager;
import wizardo.game.Screens.EscapeMenu.Controls.ControllerListener_ESCAPE;
import wizardo.game.Screens.EscapeMenu.Controls.KeyboardMouseListener_ESCAPE;
import wizardo.game.Screens.EscapeMenu.EscapeScreen;
import wizardo.game.Screens.Hub.BattleSelection.BattleSelectionScreen;
import wizardo.game.Screens.Hub.Controls.ControllerListener_HUB;
import wizardo.game.Screens.Hub.Controls.KeyboardMouseListener_HUB;
import wizardo.game.Screens.Hub.HubScreen;
import wizardo.game.Screens.MainMenu.Controls.ControllerListener_MAINMENU;
import wizardo.game.Controls.KeyboardMouseListener_TABLEMENU;
import wizardo.game.Screens.MainMenu.MainMenuScreen;
import wizardo.game.Wizardo;

import java.util.ArrayList;

public abstract class BaseScreen implements Screen {

    public static InputMultiplexer inputMultiplexer = new InputMultiplexer();
    public static boolean controllerActive;
    public static boolean mouseInvisible;
    public static float screenRatio;

    public Wizardo game;
    protected SpriteBatch batch;
    public RayHandler rayHandler;

    public boolean paused;
    public float globalCD = 0;

    protected ControllerAdapter controllerAdapter;
    protected InputProcessor inputProcessor;

    public DisplayManager displayManager;
    public LightManager lightManager;
    public ArrayList<Animation> animations;
    public OrthographicCamera mainCamera;
    public OrthographicCamera uiCamera;

    public Stage stage;
    public MenuTable menuTable;
    protected ArrayList<Button> buttons;
    protected String cursorTexturePath;


    public BaseScreen(Wizardo game) {

        this.game = game;
        mainCamera = game.mainCamera;
        uiCamera = game.uiCamera;
        this.batch = new SpriteBatch();
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
        mainCamera.viewportWidth = width;
        mainCamera.viewportHeight = height;
        mainCamera.update();
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

            Pixmap pixmap = new Pixmap(Gdx.files.internal(cursorTexturePath));
            Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
            Gdx.graphics.setCursor(customCursor);
            pixmap.dispose();

        }

    }

    public void setCursorTexture() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(cursorTexturePath));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(customCursor);
        pixmap.dispose();
    }

    @Override
    public void show() {
        setInputs();
        setCursorTexture();
    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {
        removeInputs();
    }

    /**
     * Reinitializes multiplexer with the current screen inputs
     */
    public void setInputs() {
        inputMultiplexer.clear();

        if(this instanceof HubScreen) {
            inputProcessor = new KeyboardMouseListener_HUB( (HubScreen) this);
            controllerAdapter = new ControllerListener_HUB( (HubScreen) this);
        }

        if(this instanceof MainMenuScreen) {
            inputProcessor = new KeyboardMouseListener_TABLEMENU(this);
            controllerAdapter = new ControllerListener_MAINMENU( (MainMenuScreen) this);
        }

        if(this instanceof BattleSelectionScreen) {
            inputProcessor = new KeyboardMouseListener_TABLEMENU(this);
            controllerAdapter = new ControllerListener_TABLEMENU(this);
        }

        if(this instanceof EscapeScreen) {
            inputProcessor = new KeyboardMouseListener_ESCAPE( (EscapeScreen) this);
            controllerAdapter = new ControllerListener_ESCAPE( (EscapeScreen) this);
        }

        for (Controller controller : Controllers.getControllers()) {
            controller.addListener(controllerAdapter);
        }
        inputMultiplexer.addProcessor(inputProcessor);
    }



}
