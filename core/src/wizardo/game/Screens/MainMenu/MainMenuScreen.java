package wizardo.game.Screens.MainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import wizardo.game.Screens.MainMenu.Controls.ControllerListener_MAINMENU;
import wizardo.game.Screens.MainMenu.Controls.KeyboardMouseListener_MAINMENU;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Audio.Music.MusicPlayer;
import wizardo.game.Wizardo;

public class MainMenuScreen extends BaseScreen {
    private String musicFilePath = "MainMenuScreen/Abbesses.mp3";
    private Skin mainMenuSkin = new Skin(Gdx.files.internal("MainMenuScreen/MainMenuSkin/MainMenuSkin.json"));

    private final Sprite background;
    private final Sprite title;

    public MenuTable menuTable;

    public MainMenuScreen(Wizardo game) {
        super(game);
        System.out.println("LOL");

        background = new Sprite(new Texture("MainMenuScreen/MainBackground.png"));
        title = new Sprite(new Texture("MainMenuScreen/Title.png"));

        stage = new Stage();
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

    }

}
