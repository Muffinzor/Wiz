package wizardo.game.Screens.MainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Sounds.Music.MusicPlayer;
import wizardo.game.Wizardo;

public class MainMenuScreen extends BaseScreen {

    private String musicFilePath = "MainMenuScreen/Abbesses.mp3";

    private final Sprite background;
    private final Sprite title;

    public MainMenuScreen(Wizardo game) {
        super(game);

        background = new Sprite(new Texture("MainMenuScreen/MainBackground.png"));
        title = new Sprite(new Texture("MainMenuScreen/Title.png"));

    }

    @Override
    public void show() {
        MusicPlayer.getMusicPlayer().playMusic(musicFilePath, true);
    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {

        batch.begin();
        background.draw(batch);
        title.draw(batch);
        batch.end();

        handleInputs();

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private void handleInputs() {

    }
}
