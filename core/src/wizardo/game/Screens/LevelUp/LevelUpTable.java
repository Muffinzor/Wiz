package wizardo.game.Screens.LevelUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import wizardo.game.Display.MenuTable;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Player.Levels.LevelUpUtils;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Wizardo.player;

public class LevelUpTable extends MenuTable {

    Skin skin;
    Stage stage;
    PanelButton[] buttonsMatrix;

    LevelUpScreen screen;

    int x_pos = 1;


    public LevelUpTable(LevelUpScreen screen, Stage stage, Skin skin, Wizardo game) {
        super(stage, skin, game);
        this.skin = skin;
        this.stage = stage;
        this.screen = screen;
        this.buttonsMatrix = new PanelButton[player.levelup_choices];

        createTable();
        createPanels();
        if(controllerActive) {
            updatePanels();
        }

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
        ArrayList<LevelUpEnums.LevelUps> choices = new ArrayList<>();

        for (int i = 0; i < player.levelup_choices; i++) {
            LevelUpEnums.LevelUpQuality quality = LevelUpUtils.getRandomQuality();
            LevelUpEnums.LevelUps type = LevelUpUtils.getPossibleChoice(quality, choices);
            choices.add(type);
            PanelButton panel = new PanelButton(screen, type, quality);
            table.add(panel).padLeft(15).padRight(15);
            buttons.add(panel);
            buttonsMatrix[i] = panel;
        }

    }

    public void updatePanels() {
        for (int i = 0; i < player.levelup_choices; i++) {
            buttonsMatrix[i].selected = (i == x_pos);
            buttonsMatrix[i].adjustSize();
            buttonsMatrix[i].updateFontColor();
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
        x_pos --;
        if(x_pos < 0) {
            x_pos = player.levelup_choices - 1;
        }
        updatePanels();
    }

    @Override
    public void navigateRight() {
        x_pos ++;
        if(x_pos > player.levelup_choices - 1) {
            x_pos = 0;
        }
        updatePanels();
    }

    @Override
    public void pressSelectedButton() {
        buttonsMatrix[x_pos].handleClick();
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
