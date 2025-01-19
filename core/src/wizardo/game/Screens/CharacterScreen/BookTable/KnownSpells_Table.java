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

public class KnownSpells_Table extends MenuTable {

    CharacterScreen screen;
    Vector2 centerPoint;

    Table firstRowTable;
    Table secondRowTable;

    public SpellIcon_Button[][] buttonsMatrix;
    public int x_pos;
    public int y_pos;

    public KnownSpells_Table(Stage stage, Skin skin, Wizardo game, CharacterScreen screen) {
        super(stage, skin, game);
        this.screen = screen;
        buttonsMatrix = new SpellIcon_Button[2][2];

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
        float width = 210 * xRatio;
        float height = 130 * yRatio;

        int x_pos = Math.round(980 * xRatio);
        int y_pos = Math.round(205 * yRatio);

        table.setPosition(x_pos, y_pos);
        table.setWidth(width);
        table.setHeight(height);
        stage.addActor(table);

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

        if(!player.spellbook.knownSpells.isEmpty()) {
            SpellIcon_Button button = new SpellIcon_Button(player.spellbook.knownSpells.getFirst(), false, screen);
            firstRowTable.add(button);
            buttons.add(button);
            buttonsMatrix[0][0] = button;
        }


        if(player.spellbook.knownSpells.size() > 1) {
            SpellIcon_Button button2 = new SpellIcon_Button(player.spellbook.knownSpells.get(1), false, screen);
            firstRowTable.add(button2);
            buttons.add(button2);
            buttonsMatrix[1][0] = button2;
        }

        if(player.spellbook.knownSpells.size() > 2) {
            table.row();
            SpellIcon_Button button3 = new SpellIcon_Button(player.spellbook.knownSpells.get(2), false, screen);
            secondRowTable.add(button3);
            buttons.add(button3);
            buttonsMatrix[0][1] = button3;
        }

        if(player.spellbook.knownSpells.size() > 3) {
            SpellIcon_Button button4 = new SpellIcon_Button(player.spellbook.knownSpells.get(3), false, screen);
            secondRowTable.add(button4);
            buttons.add(button4);
            buttonsMatrix[1][1] = button4;
        }
    }

    public Vector2 getCenter() {
        return new Vector2(centerPoint);
    }

    public void updateSelectedButton() {
        screen.selectedButton = buttonsMatrix[x_pos][y_pos];
    }

    @Override
    public void navigateDown() {
        y_pos --;
        if(y_pos < 0) {
            y_pos = 0;
        }
        updateSelectedButton();
    }

    @Override
    public void navigateUp() {
        y_pos ++;
        if(buttons.size() < 3) {
            y_pos = 0;
        } else if(buttons.size() < 4) {
            x_pos = 0;
        }
        updateSelectedButton();
    }

    @Override
    public void navigateLeft() {
        x_pos --;
        if(x_pos < 0) {
            screen.activeTable = screen.equippedSpells_table;

            if(player.spellbook.equippedSpells.size() == 1) {
                screen.equippedSpells_table.x_pos = 0;
                screen.equippedSpells_table.y_pos = 0;
                screen.equippedSpells_table.updateSelectedButton();
                return;
            }

            if(player.spellbook.equippedSpells.size() == 2) {
                screen.equippedSpells_table.x_pos = 1;
                screen.equippedSpells_table.y_pos = 0;
                screen.equippedSpells_table.updateSelectedButton();
                return;
            }

            if(player.spellbook.equippedSpells.size() == 3) {
                if(y_pos == 1) {
                    screen.equippedSpells_table.x_pos = 0;
                    screen.equippedSpells_table.y_pos = 1;
                } else {
                    screen.equippedSpells_table.x_pos = 1;
                    screen.equippedSpells_table.y_pos = 0;
                }
                screen.equippedSpells_table.updateSelectedButton();
                return;
            }
        }

        updateSelectedButton();
    }

    @Override
    public void navigateRight() {
        x_pos ++;
        if((x_pos > 0 && player.spellbook.knownSpells.size() == 1) || x_pos > 1) {
            screen.activeTable = screen.mastery_table.mixingTable;
            screen.mastery_table.mixingTable.x_pos = 0;
            screen.mastery_table.mixingTable.updateSelectedButton();
            return;
        }

        if(x_pos == 1 && player.spellbook.knownSpells.size() == 3) {
            y_pos = 0;
        }
        updateSelectedButton();
    }

    @Override
    public void pressSelectedButton() {
        SpellIcon_Button button = buttonsMatrix[x_pos][y_pos];
        button.handleClick();
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
