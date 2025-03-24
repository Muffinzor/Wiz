package wizardo.game.Screens.LevelUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import wizardo.game.Display.MenuTable;
import wizardo.game.Player.Levels.Choices.*;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Player.Levels.LevelUpEnums.LevelUps.*;
import static wizardo.game.Player.Levels.LevelUpEnums.LevelUps.LIGHTNING;
import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Wizardo.player;

public class LevelUpTable extends MenuTable {

    Skin skin;
    Stage stage;
    PanelButton[] buttonsMatrix;

    public LevelUpScreen screen;

    int x_pos = 1;

    public ArrayList<LevelUpEnums.LevelUps> elements_used;


    public LevelUpTable(LevelUpScreen screen, Stage stage, Skin skin, Wizardo game) {
        super(stage, skin, game);
        this.skin = skin;
        this.stage = stage;
        this.screen = screen;
        this.buttonsMatrix = new PanelButton[player.levelup_choices];
        elements_used = new ArrayList<>();

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
        ArrayList<LevelUpEnums.LevelUps> choices = player.levelUpManager.get_selection(screen);
        for (int i = 0; i < choices.size(); i++) {
            PanelButton panel = build_panel(choices.get(i));
            table.add(panel).padLeft(15).padRight(15);
            buttons.add(panel);
            buttonsMatrix[i] = panel;
        }
    }

    public PanelButton build_panel(LevelUpEnums.LevelUps type) {
        PanelButton panel = null;
        switch(type) {
            case FROSTBOLT -> panel = new M_Frostbolt(screen);
            case ICESPEAR -> panel = new M_Icespear(screen);
            case FROZENORB -> panel = new M_Frozenorb(screen);
            case FLAMEJET -> panel = new M_Flamejet(screen);
            case FIREBALL -> panel = new M_Fireball(screen);
            case OVERHEAT -> panel = new M_Overheat(screen);
            case CHARGEDBOLT -> panel = new M_Chargedbolt(screen);
            case CHAIN -> panel = new M_Chainlightning(screen);
            case THUNDERSTORM -> panel = new M_Thunderstorm(screen);
            case MISSILES -> panel = new M_Arcanemissiles(screen);
            case BEAM -> panel = new M_Energybeam(screen);
            case RIFTS -> panel = new M_Rifts(screen);

            case DRAGONBREATH -> panel = new H_Dragonbreath(screen);
            case BLIZZARD -> panel = new H_Blizzard(screen);
            case CELESTIALSTRIKE -> panel = new H_Celestialstrike(screen);
            case JUDGEMENT -> panel = new H_Judgement(screen);
            case ENERGYRAIN -> panel = new H_Energyrain(screen);

            case LUCK -> panel = new B_Luck(screen);
            case REGEN -> panel = new B_Regen(screen);
            case MAGNET -> panel = new B_Magnet(screen);
            case EXPERIENCE -> panel = new B_Experience(screen);
            case SHIELD -> panel = new B_Shield(screen);
            case DEFENSE -> panel = new B_Defense(screen);

            case ELEMENT -> panel = new B_Element(screen, get_random_unique_element());
        }
        return panel;
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

    public LevelUpEnums.LevelUps get_random_unique_element() {
        LevelUpEnums.LevelUps element = null;
        while(element == null || elements_used.contains(element)) {
            int random = MathUtils.random(1,4);
            switch(random) {
                case 1 -> element = FIRE;
                case 2 -> element = FROST;
                case 3 -> element = ARCANE;
                case 4 -> element = LIGHTNING;
            }
        }
        elements_used.add(element);
        return element;
    }
}
