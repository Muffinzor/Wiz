package wizardo.game.Screens.LevelUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Resources.Skins;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;


public class LevelUpScreen extends BaseScreen {

    public Skin skin = Skins.levelUpSkin;

    public LevelUpTable levelUpTable;
    boolean initialized;

    public LevelUpScreen(Wizardo game) {
        super(game);

        stage = new Stage(new ScreenViewport(uiCamera));

        batch.setProjectionMatrix(uiCamera.combined);
        levelUpTable = new LevelUpTable(this, stage, skin, game);
    }

    @Override
    public void render(float delta) {
        stateTime += delta;
        globalCD -= delta;

        stage.act(delta);
        stage.draw();

        levelUpTable.draw(delta);
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.clear();
    }

    public void adjustFontSize() {
        float scale = Math.max(Gdx.graphics.getWidth() / 1920f, 0.75f);
        BitmapFont font = skin.getFont("SimpleFont26");
        font.getData().setScale(scale);
        for (TextButton.TextButtonStyle style : skin.getAll(TextButton.TextButtonStyle.class).values()) {
            if (style.font == font) {
                style.font.getData().setScale(scale);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        stage.getViewport().update(width, height, true);
        adjustFontSize();
        levelUpTable.resize();

        batch.setProjectionMatrix(uiCamera.combined);

    }
}
