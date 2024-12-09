package wizardo.game.Screens.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Display.MenuTable;
import wizardo.game.Resources.CharacterScreenResources;
import wizardo.game.Resources.Spell_Icons;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.Character.BookTable.EquippedTable;
import wizardo.game.Screens.Character.BookTable.SpellIcon_Button;
import wizardo.game.Screens.Character.MasteryTable.MasteryTable;
import wizardo.game.Wizardo;

import static wizardo.game.Resources.Skins.bookTableSkin;
import static wizardo.game.Resources.Skins.masteryTableSkin;
import static wizardo.game.Wizardo.player;

public class CharacterScreen extends BaseScreen {

    float stateTime;

    public MasteryTable mastery_table;
    public EquippedTable equippedSpells_table;
    public Table knownSpells_table;
    public Table equipment_table;
    public Table inventory_table;

    public Button selectedButton;
    public MenuTable activeTable;

    private Sprite background;

    public CharacterScreen(Wizardo game) {
        super(game);

        stage = new Stage(new ScreenViewport(uiCamera));

        background = new Sprite(new Texture("Screens/CharacterScreen/Background.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        mastery_table = new MasteryTable(stage, masteryTableSkin, game, this);
        mastery_table.updateSelectedButton();
        activeTable = mastery_table;

        equippedSpells_table = new EquippedTable(stage, bookTableSkin, game, this);


    }

    @Override
    public void render(float delta) {
        stateTime += delta;
        globalCD -= delta;

        batch.begin();
        background.draw(batch);
        batch.end();

        stage.act(delta);
        stage.draw();

        if(controllerActive) {
            drawSelectedButton();
        }

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
        mastery_table = new MasteryTable(stage, masteryTableSkin, game, this);
        mastery_table.updateSelectedButton();
        activeTable = mastery_table;
    }

    public void drawSelectedButton() {
        // Get the sprite
        Sprite frame = getSprite();
        frame.set(CharacterScreenResources.selected_button_anim.getKeyFrame(stateTime, true));

        // Retrieve the button's position relative to its table (local coordinates)
        Vector2 buttonLocalPos = new Vector2(selectedButton.getX(), selectedButton.getY());

        // Convert from local coordinates to stage coordinates
        Vector2 tableLocalPos = new Vector2(selectedButton.getParent().getX(), selectedButton.getParent().getY());

        float screenButton_X = tableLocalPos.x + buttonLocalPos.x;
        float screenButton_Y = tableLocalPos.y + buttonLocalPos.y;

        screenButton_X += selectedButton.getWidth()/2;
        screenButton_Y += selectedButton.getHeight()/2;

        frame.setScale(Gdx.graphics.getWidth()/1920f);

        // Position the sprite over the button (at the correct screen coordinates)
        frame.setCenter(screenButton_X, screenButton_Y);

        // Draw the sprite using the existing SpriteBatch
        SpriteBatch batch = new SpriteBatch();
        batch.setProjectionMatrix(stage.getCamera().combined);  // Ensure it's using the correct camera
        batch.begin();
        frame.draw(batch);
        batch.end();
    }
}
