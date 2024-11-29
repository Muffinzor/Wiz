package wizardo.game.Screens.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.MainMenu.MainMenuTable;
import wizardo.game.Wizardo;

import static wizardo.game.Resources.Skins.masteryTableSkin;

public class CharacterScreen extends BaseScreen {

    public MasteryTable mastery_table;
    public Table equippedSpells_table;
    public Table knownSpells_table;
    public Table equipment_table;
    public Table inventory_table;

    private Sprite background;

    public CharacterScreen(Wizardo game) {
        super(game);
        stage = new Stage(new ScreenViewport(uiCamera));

        background = new Sprite(new Texture("Screens/CharacterScreen/Background.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        mastery_table = new MasteryTable(stage, masteryTableSkin, game);
    }

    @Override
    public void render(float delta) {

        batch.begin();
        background.draw(batch);
        batch.end();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        mastery_table.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        stage.getViewport().update(width, height, true);
        mastery_table.dispose();
        mastery_table = new MasteryTable(stage, masteryTableSkin, game);
    }
}
