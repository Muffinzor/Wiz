package wizardo.game.Screens.MainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Screens.MainMenu.Controls.ControllerListener_MAINMENU;
import wizardo.game.Screens.MainMenu.Controls.KeyboardMouseListener_MAINMENU;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Audio.Music.MusicPlayer;
import wizardo.game.Wizardo;

public class MainMenuScreen extends BaseScreen {
    private String musicFilePath = "MainMenuScreen/Abbesses.mp3";
    private Skin mainMenuSkin = new Skin(Gdx.files.internal("MainMenuScreen/MainMenuSkin/MainMenuSkin.json"));

    private Sprite background;
    private final Sprite title;

    public MenuTable menuTable;

    public MainMenuScreen(Wizardo game) {
        super(game);

        stage = new Stage(new ScreenViewport());

        background = new Sprite(new Texture("MainMenuScreen/MainBackground.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        title = new Sprite(new Texture("MainMenuScreen/Title.png"));
        title.setSize(600 * screenRatio, 150 * screenRatio);
        title.setPosition(Gdx.graphics.getWidth()/2f - title.getWidth()/2f, 600 * screenRatio);

        menuTable = new MenuTable(stage, mainMenuSkin, buttons, game);
        mouseAdapter = new KeyboardMouseListener_MAINMENU(this);
        controllerAdapter = new ControllerListener_MAINMENU(this);

    }

    @Override
    public void show() {
        setInputs();
        //MusicPlayer.getMusicPlayer().playMusic(musicFilePath, true);
    }

    private void setInputs() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("Cursors/Menu_Cursor.png"));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(customCursor);
        pixmap.dispose();

        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(mouseAdapter);
        for (Controller controller : Controllers.getControllers()) {
            controller.addListener(controllerAdapter);
        }
    }

    @Override
    public void render(float delta) {
        globalCD -= delta;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        background.draw(batch);
        title.draw(batch);
        batch.end();

        stage.act(delta);
        stage.draw();

    }


    @Override
    public void hide() {
        removeInputs();
        MusicPlayer.getMusicPlayer().stopMusic();
    }

    @Override
    public void pause() {
        MusicPlayer.getMusicPlayer().pauseMusic();
    }

    @Override
    public void resume() {
        MusicPlayer.getMusicPlayer().resumeMusic();
    }

    @Override
    public void dispose() {
        removeInputs();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);

        menuTable.dispose();
        menuTable = new MenuTable(stage, mainMenuSkin, buttons, game);
    }

}
