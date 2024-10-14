package wizardo.game.Screens.LoadingScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Resources.DecorResources.GeneralDecorResources;
import wizardo.game.Resources.SpellAnims.FrostboltAnims;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.MainMenu.MainMenuScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Wizardo.assetManager;

public class LoadingScreen extends BaseScreen {

    private ProgressBar progressBar;
    private boolean assetsFinishedLoading;

    public LoadingScreen(Wizardo game) {
        super(game);

        stage = new Stage(new ScreenViewport());

        Skin skin = new Skin(Gdx.files.internal("MainMenuScreen/MainMenuSkin/MainMenuSkin.json"));

        progressBar = new ProgressBar(0, 1, 0.01f, false, skin);
        progressBar.setSize(300, 40);
        progressBar.setPosition(Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 20);
        stage.addActor(progressBar);

        loadAssets();
    }

    @Override
    public void render(float delta) {

        if (assetManager.update() && !assetsFinishedLoading) {
            loadAnims();
            System.out.println("Loading Complete!");
            assetsFinishedLoading = true;
            game.setOverScreen(new MainMenuScreen(game));
        }

        float progress = assetManager.getProgress();
        progressBar.setValue(progress);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void setInputs() {
        
    }

    public void loadAssets() {
        FrostboltAnims.loadAtlas();
        GeneralDecorResources.loadAtlas();
    }

    public void loadAnims() {
        FrostboltAnims.loadAnimations();
        GeneralDecorResources.loadAnimations();
    }
}
