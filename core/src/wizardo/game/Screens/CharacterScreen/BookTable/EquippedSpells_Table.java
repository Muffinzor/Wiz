package wizardo.game.Screens.CharacterScreen.BookTable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;
import wizardo.game.Screens.CharacterScreen.EquipmentTable.GearPanel;
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
    public int x_pos = 0;
    public int y_pos = 0;

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

    public void draw() {
        for(Button button : buttons) {
            SpellIcon_Button spell_button = (SpellIcon_Button) button;
            spell_button.drawButton();
        }
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
        secondRow = buttons.size() > 2;
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
            y_pos--;
            if (y_pos < 0) {
                y_pos = 0;
            }
            updateSelectedButton();
        }
    }

    @Override
    public void navigateUp() {
        if(secondRow) {
            y_pos++;
            x_pos = 0;
            if (y_pos > 1) {
                y_pos = 1;
            }
            screen.selectedButton = buttonsMatrix[x_pos][y_pos];
        }
    }

    @Override
    public void navigateLeft() {
        x_pos--;
        if(x_pos < 0) {
            screen.activeTable = screen.inventory_table;
            screen.inventory_table.x_pos = 4;
            screen.inventory_table.y_pos = 0;
            screen.selectedButton = screen.inventory_table.buttonsMatrix[4][0];
            if(screen.inventory_table.buttonsMatrix[4][0].piece != null) {
                screen.activePanel = new GearPanel(screen.panelStage,
                        screen.inventory_table.buttonsMatrix[4][0].piece, false,
                        screen.inventory_table.buttonsMatrix[4][0]);
            }
        } else {
            updateSelectedButton();
        }

    }

    @Override
    public void navigateRight() {
        x_pos++;
        if(player.spellbook.knownSpells.isEmpty()) {
            if (x_pos >= player.spellbook.equippedSpells.size() || x_pos > 1 || y_pos == 1 && x_pos == 1) {
                screen.activeTable = screen.mastery_table.mixingTable;
                screen.mastery_table.mixingTable.x_pos = 0;
                screen.mastery_table.mixingTable.updateSelectedButton();
                return;
            }
        } else if(x_pos > 1 || (x_pos > 0 && player.spellbook.equippedSpells.size() == 1) ||
                (x_pos > 0 && y_pos > 0)) {
            screen.activeTable = screen.knownSpells_table;
            screen.knownSpells_table.x_pos = 0;
            screen.knownSpells_table.y_pos = y_pos;
            if(player.spellbook.knownSpells.size() <= 2) {
                screen.knownSpells_table.y_pos = 0;
            }
            screen.knownSpells_table.updateSelectedButton();
            return;
        }
        updateSelectedButton();
    }

    public void updateSelectedButton() {
        screen.selectedButton = buttonsMatrix[x_pos][y_pos];
    }

    @Override
    public void pressSelectedButton() {
        SpellIcon_Button button = buttonsMatrix[x_pos][y_pos];
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
