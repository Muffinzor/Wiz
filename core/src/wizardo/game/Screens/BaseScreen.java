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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import wizardo.game.Controls.ControllerListener_TABLEMENU;
import wizardo.game.Display.DisplayManager;
import wizardo.game.Display.MenuTable;
import wizardo.game.Lighting.LightManager;
import wizardo.game.Monsters.MonsterManager;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.Controls.ControllerListener_BATTLE;
import wizardo.game.Screens.Battle.Controls.KeyboardMouseListener_BATTLE;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;
import wizardo.game.Screens.CharacterScreen.Controls.ControllerListener_CHARACTERSCREEN;
import wizardo.game.Screens.CharacterScreen.Controls.KeyboardMouseListener_CHARACTERSCREEN;
import wizardo.game.Screens.DevScreen.Cheat_Screen;
import wizardo.game.Screens.DevScreen.KeyboardMouseListener_CHEATMENU;
import wizardo.game.Screens.EscapeMenu.Controls.ControllerListener_ESCAPE;
import wizardo.game.Screens.EscapeMenu.Controls.KeyboardMouseListener_ESCAPE;
import wizardo.game.Screens.EscapeMenu.EscapeScreen;
import wizardo.game.Screens.Hub.BattleSelection.BattleSelectionScreen;
import wizardo.game.Screens.Hub.Controls.ControllerListener_HUB;
import wizardo.game.Screens.Hub.Controls.KeyboardMouseListener_HUB;
import wizardo.game.Screens.Hub.HubScreen;
import wizardo.game.Screens.LevelUp.Controls.ControllerListener_LEVELUP;
import wizardo.game.Screens.LevelUp.Controls.KeyboardMouseListener_LEVELUP;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.MainMenu.Controls.ControllerListener_MAINMENU;
import wizardo.game.Controls.KeyboardMouseListener_TABLEMENU;
import wizardo.game.Screens.MainMenu.MainMenuScreen;
import wizardo.game.Screens.Popups.AreYouSureScreen;
import wizardo.game.Screens.Settings.SettingsScreen;
import wizardo.game.Screens.Shop.Controls.ControllerListener_MAPSHOP;
import wizardo.game.Screens.Shop.Controls.KeyboardMouseListener_MAPSHOP;
import wizardo.game.Screens.Shop.ShopScreen;
import wizardo.game.Spells.SpellManager;
import wizardo.game.Wizardo;

public abstract class BaseScreen implements Screen {

    public static InputMultiplexer inputMultiplexer = new InputMultiplexer();
    public static boolean controllerActive;
    public static boolean mouseInvisible;


    public static float xRatio;
    public static float yRatio;

    public Wizardo game;
    public SpriteBatch batch;
    public RayHandler rayHandler;

    public boolean paused;
    public float globalCD = 0;
    public boolean choiceConfirmed; //for popup screen

    protected ControllerAdapter controllerAdapter;
    protected InputProcessor inputProcessor;

    public DisplayManager displayManager;
    public LightManager lightManager;
    public SpellManager spellManager;
    public MonsterManager monsterManager;

    public OrthographicCamera mainCamera;
    public OrthographicCamera uiCamera;

    public Stage stage;
    public MenuTable menuTable;
    protected String cursorTexturePath;

    public BaseScreen(Wizardo game) {
        xRatio = Gdx.graphics.getWidth()/1920f;
        yRatio = Gdx.graphics.getHeight()/1080f;

        this.game = game;
        mainCamera = game.mainCamera;
        uiCamera = game.uiCamera;
        this.batch = new SpriteBatch();

        displayManager = new DisplayManager(this);
        lightManager = new LightManager(this);

        cursorTexturePath = "Cursors/Menu_Cursor.png";
        Gdx.input.setInputProcessor(inputMultiplexer);
        globalCD = 0;

    }

    public abstract void render(float delta);

    public abstract void dispose();

    @Override
    public void resize(int width, int height) {
        xRatio = width/1920f;
        yRatio = height/1080f;
        mainCamera.viewportWidth = width;
        mainCamera.viewportHeight = height;
        mainCamera.update();
        uiCamera.viewportWidth = width;
        uiCamera.viewportHeight = height;
        uiCamera.update();
        uiCamera.setToOrtho(false, width, height);
    }


    public void hideCursor() {

        mouseInvisible = true;
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        Cursor invisibleCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(invisibleCursor);
        pixmap.dispose();

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
        Cursor customCursor;
        if(this instanceof BattleScreen) {
            int hotspotX = pixmap.getWidth() / 2;
            int hotspotY = pixmap.getHeight() / 2;
            customCursor = Gdx.graphics.newCursor(pixmap, hotspotX, hotspotY);
        } else {
            customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        }
        Gdx.graphics.setCursor(customCursor);
        pixmap.dispose();
    }

    @Override
    public void show() {
        setCursorTexture();
        if(controllerActive) {
            hideCursor();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {

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

        if(this instanceof SettingsScreen || this instanceof AreYouSureScreen) {
            inputProcessor = new KeyboardMouseListener_TABLEMENU(this);
            controllerAdapter = new ControllerListener_TABLEMENU(this);
        }

        if(this instanceof EscapeScreen) {
            inputProcessor = new KeyboardMouseListener_ESCAPE( (EscapeScreen) this);
            controllerAdapter = new ControllerListener_ESCAPE( (EscapeScreen) this);
        }

        if(this instanceof BattleScreen) {
            inputProcessor = new KeyboardMouseListener_BATTLE( (BattleScreen) this);
            controllerAdapter = new ControllerListener_BATTLE( (BattleScreen) this);
        }

        if(this instanceof CharacterScreen) {
            inputProcessor = new KeyboardMouseListener_CHARACTERSCREEN( (CharacterScreen) this);
            controllerAdapter = new ControllerListener_CHARACTERSCREEN( (CharacterScreen) this);
        }

        if(this instanceof LevelUpScreen) {
            inputProcessor = new KeyboardMouseListener_LEVELUP( (LevelUpScreen) this);
            controllerAdapter = new ControllerListener_LEVELUP( (LevelUpScreen) this);
        }

        if(this instanceof ShopScreen) {
            inputProcessor = new KeyboardMouseListener_MAPSHOP( (ShopScreen) this);
            controllerAdapter = new ControllerListener_MAPSHOP((ShopScreen) this);
        }

        if(this instanceof Cheat_Screen) {
            inputProcessor = new KeyboardMouseListener_CHEATMENU(this);
            controllerAdapter = new ControllerListener_TABLEMENU(this);
        }

        for (Controller controller : Controllers.getControllers()) {
            controller.addListener(controllerAdapter);
            break;
        }

        inputMultiplexer.addProcessor(inputProcessor);
    }

    public void removeInputs() {
        for (Controller controller : Controllers.getControllers()) {
            controller.removeListener(controllerAdapter);
        }
    }

    public Sprite getSprite() {
        return displayManager.spriteRenderer.pool.getSprite();
    }

    /**
     * adds the sprite to the sorted sprites list to be rendered
     * @param sprite
     */
    public void addSortedSprite(Sprite sprite) {
        displayManager.spriteRenderer.regular_sorted_sprites.add(sprite);
    }

    public void addOverSprite(Sprite sprite) {
        displayManager.spriteRenderer.over_sprites.add(sprite);
    }

    public void addPostLightningSprite(Sprite sprite) {
        displayManager.spriteRenderer.post_lightning_sprites.add(sprite);
    }



    /**
     * adds the sprite to the under-everything-else sprites list to be rendered
     * @param sprite
     */
    public void addUnderSprite(Sprite sprite) {
        displayManager.spriteRenderer.under_sprites.add(sprite);
    }

    /**
     * adds frame to the sorting map, it will be sorted according to its center position instead of bottom-left coordinate
     * @param frame frame to be sorted from center coordinate
     * @param y_pos the center y position of the frame
     */
    public void centerSort(Sprite frame, float y_pos) {
        displayManager.spriteRenderer.spritePositionMap.put(frame, y_pos);
    }



}
