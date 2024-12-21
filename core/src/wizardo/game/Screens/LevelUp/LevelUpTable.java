package wizardo.game.Screens.LevelUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import wizardo.game.Display.MenuTable;
import wizardo.game.Wizardo;

public class LevelUpTable extends MenuTable {

    Skin skin;
    Stage stage;
    PanelButton[] buttonsMatrix;

    LevelUpScreen screen;


    public LevelUpTable(LevelUpScreen screen, Stage stage, Skin skin, Wizardo game) {
        super(stage, skin, game);
        this.skin = skin;
        this.stage = stage;
        this.screen = screen;
        this.buttonsMatrix = new PanelButton[3];

        createTable();
        createPanels();
    }

    public void draw(float delta) {
        for(PanelButton button : buttonsMatrix) {
            button.drawLiteralFrame(delta);
            button.drawQualityGem();
        }
    }

    public void createTable() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        int x_pos = 0;
        int y_pos = 0;

        table = new Table();
        table.setPosition(x_pos, y_pos);
        table.setWidth(width);
        table.setHeight(height);
        stage.addActor(table);

    }

    public void createPanels() {
        for (int i = 0; i < buttonsMatrix.length; i++) {
            PanelButton panel = new PanelButton(screen, LevelUpUtils.getRandomLevelUp(), LevelUpUtils.getRandomQuality());
            table.add(panel).padLeft(15).padRight(15);
            buttons.add(panel);
            buttonsMatrix[i] = panel;
        }
    }

    @Override
    public void navigateDown() {

    }

    @Override
    public void navigateUp() {

    }

    @Override
    public void navigateLeft() {

    }

    @Override
    public void navigateRight() {

    }

    @Override
    public void pressSelectedButton() {

    }

    @Override
    public void resize() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        table.setWidth(width);
        table.setHeight(height);

        for (PanelButton panel : buttonsMatrix) {
            panel.adjustSize();
        }
    }
}
