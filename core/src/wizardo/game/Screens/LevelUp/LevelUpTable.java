package wizardo.game.Screens.LevelUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import wizardo.game.Display.MenuTable;
import wizardo.game.Player.Levels.Choices.*;
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
            button.drawPanel(delta);
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

        for (int i = 0; i < 2; i++) {
            PanelButton panel;
            switch(MathUtils.random(1,3)) {
                case 2 -> panel = player.levelUpManager.get_random_t2(screen, choices);
                case 3 -> panel = player.levelUpManager.get_random_t3(screen, choices);
                default -> panel = player.levelUpManager.get_random_t1(screen, choices);
            }
            table.add(panel).padLeft(15).padRight(15);
            buttons.add(panel);
            buttonsMatrix[i] = panel;
        }
        PanelButton panel = new H_Energyrain(screen);
        table.add(panel).padLeft(15).padRight(15);
        buttons.add(panel);
        buttonsMatrix[2] = panel;

        PanelButton panel2 = new H_Dragonbreath(screen);
        table.add(panel2).padLeft(15).padRight(15);
        buttons.add(panel2);
        buttonsMatrix[3] = panel2;
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
