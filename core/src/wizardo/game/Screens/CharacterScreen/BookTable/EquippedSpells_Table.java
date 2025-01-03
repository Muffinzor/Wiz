package wizardo.game.Screens.CharacterScreen.BookTable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Wizardo.player;

public class EquippedSpells_Table extends MenuTable {

    CharacterScreen screen;
    public Vector2 centerPoint;

    boolean secondRow;

    Table firstRowTable;
    Table secondRowTable;

    public SpellIcon_Button[][] buttonsMatrix = new SpellIcon_Button[2][2];
    public int x_position = 0;
    public int y_position = 0;

    public EquippedSpells_Table(Stage stage, Skin skin, Wizardo game, CharacterScreen screen) {
        super(stage, skin, game);
        this.screen = screen;

        firstRowTable = new Table();
        secondRowTable = new Table();
        table.add(firstRowTable).row();
        table.add(secondRowTable);
        table.top();
        createButtons();
        adjustButtons();
        adjustSize();
        stage.addActor(table);

    }

    public void adjustSize() {
        float width = 230 * xRatio;
        float height = 130 * yRatio;
        int x_pos = Math.round(700 * xRatio);
        int y_pos = Math.round(230 * yRatio);

        table.setPosition(x_pos, y_pos);
        table.setWidth(width);
        table.setHeight(height);

        centerPoint = new Vector2(x_pos + width/2, y_pos + height/2);

        adjustButtons();

    }


    public void adjustButtons() {
        for(Button button : buttons) {
            SpellIcon_Button butt = (SpellIcon_Button) button;
            butt.adjustSize();
        }
    }

    public void updateSpells() {
        for(Button button : buttons) {
            button.remove();
        }
        buttonsMatrix = new SpellIcon_Button[2][2];
        buttons.clear();
        createButtons();
        adjustButtons();
    }

    public void createButtons() {
        if(!player.spellbook.equippedSpells.isEmpty()) {
            SpellIcon_Button button = new SpellIcon_Button(player.spellbook.equippedSpells.get(0), true, screen);
            firstRowTable.add(button);
            buttons.add(button);
            buttonsMatrix[0][0] = button;
        }

        if(player.spellbook.equippedSpells.size() > 1) {
            SpellIcon_Button button2 = new SpellIcon_Button(player.spellbook.equippedSpells.get(1), true, screen);
            firstRowTable.add(button2);
            buttons.add(button2);
            buttonsMatrix[1][0] = button2;
        }

        if(player.spellbook.equippedSpells.size() > 2) {
            SpellIcon_Button button3 = new SpellIcon_Button(player.spellbook.equippedSpells.get(2), true, screen);
            secondRowTable.add(button3);
            buttons.add(button3);
            buttonsMatrix[0][1] = button3;
        }
    }

    @Override
    public void navigateDown() {
        if(secondRow) {
            y_position--;
            if (y_position < 0) {
                y_position = 0;
            }
            updateSelectedButton();
        }
    }

    @Override
    public void navigateUp() {
        if(secondRow) {
            y_position++;
            x_position = 0;
            if (y_position > 1) {
                y_position = 1;
            }
            screen.selectedButton = buttonsMatrix[x_position][y_position];
        }
    }

    @Override
    public void navigateLeft() {
        x_position --;
        if(x_position < 0) {
            x_position = 0;
        }
        updateSelectedButton();
    }

    @Override
    public void navigateRight() {
        x_position ++;
        if(player.spellbook.knownSpells.isEmpty()) {
            if (x_position >= player.spellbook.equippedSpells.size() || x_position > 1 || y_position == 1 && x_position == 1) {
                screen.activeTable = screen.mastery_table.mixingTable;
                screen.mastery_table.mixingTable.x_pos = 0;
                screen.mastery_table.mixingTable.updateSelectedButton();
                return;
            }
        } else if(x_position > 1 || (x_position > 0 && player.spellbook.equippedSpells.size() == 1) ||
                (x_position > 0 && y_position > 0)) {
            screen.activeTable = screen.knownSpells_table;
            screen.knownSpells_table.x_pos = 0;
            screen.knownSpells_table.y_pos = y_position;
            if(player.spellbook.knownSpells.size() <= 2) {
                screen.knownSpells_table.y_pos = 0;
            }
            screen.knownSpells_table.updateSelectedButton();
            return;
        }
        updateSelectedButton();
    }

    public void updateSelectedButton() {
        screen.selectedButton = buttonsMatrix[x_position][y_position];
    }

    @Override
    public void pressSelectedButton() {
        SpellIcon_Button button = buttonsMatrix[x_position][y_position];
        button.handleClick();
    }

    public Vector2 getCenter() {
        return new Vector2(centerPoint);
    }

    @Override
    public void resize() {
        adjustSize();
    }

    public void dispose() {
        table.clear();
        table.remove();
    }
}
