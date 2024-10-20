package wizardo.game.Screens.MainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Screens.MainMenu.Controls.ControllerListener_MAINMENU;
import wizardo.game.Controls.KeyboardMouseListener_TABLEMENU;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Audio.Music.MusicPlayer;
import wizardo.game.Wizardo;

public class MainMenuScreen extends BaseScreen {
    private String musicFilePath = "Screens/MainMenuScreen/Abbesses.mp3";
    private Skin mainMenuSkin = new Skin(Gdx.files.internal("Screens/MainMenuScreen/MainMenuSkin/MainMenuSkin.json"));

    private Sprite background;
    private final Sprite title;

    public MainMenuTable menuTable;

    public MainMenuScreen(Wizardo game) {
        super(game);

        stage = new Stage(new ScreenViewport(uiCamera));

        background = new Sprite(new Texture("Screens/MainMenuScreen/MainBackground.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        title = new Sprite(new Texture("Screens/MainMenuScreen/Title.png"));
        title.setSize(600 * screenRatio, 150 * screenRatio);
        title.setPosition(Gdx.graphics.getWidth()/2f - title.getWidth()/2f, 600 * screenRatio);

        menuTable = new MainMenuTable(stage, mainMenuSkin, game);
        inputProcessor = new KeyboardMouseListener_TABLEMENU(this);
        controllerAdapter = new ControllerListener_MAINMENU(this);
    }

    @Override
    public void show() {
        super.show();
        //MusicPlayer.getMusicPlayer().playMusic(musicFilePath, true);
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
        menuTable = new MainMenuTable(stage, mainMenuSkin, game);
    }

}
