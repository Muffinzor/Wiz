package wizardo.game.Screens.Character.BookTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.Character.CharacterScreen;
import wizardo.game.Screens.Character.MasteryTable.MasteryButton;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Items.Equipment.Equipment.checkForGearConditionalEffects;
import static wizardo.game.Wizardo.player;

public class EquippedTable extends MenuTable {

    CharacterScreen screen;
    public Vector2 centerPoint;

    boolean secondRow;

    public SpellIcon_Button[][] buttonsMatrix = new SpellIcon_Button[2][2];
    public int x_position = 0;
    public int y_position = 0;

    public EquippedTable(Stage stage, Skin skin, Wizardo game, CharacterScreen screen) {
        super(stage, skin, game);
        this.screen = screen;

        resize();
    }

    public void createTable() {
        float xRatio = Gdx.graphics.getWidth() / 1920f;
        float yRatio = Gdx.graphics.getHeight() / 1080f;

        float width = 230 * xRatio;
        float height = 260 * yRatio;

        int x_pos = Math.round(700 * xRatio);
        int y_pos = Math.round(100 * yRatio);

        table = new Table();
        table.setPosition(x_pos, y_pos);
        table.setWidth(width);
        table.setHeight(height);
        stage.addActor(table);

        //table.setDebug(true);

        centerPoint = new Vector2(x_pos + width/2, y_pos + height/2);
    }

    public void createButtons() {

        if(!player.spellbook.equippedSpells.isEmpty()) {
            SpellIcon_Button button = new SpellIcon_Button(player.spellbook.equippedSpells.get(0), true, screen);
            table.add(button);
            buttonsMatrix[0][0] = button;
        }

        if(player.spellbook.equippedSpells.size() > 1) {
            SpellIcon_Button button2 = new SpellIcon_Button(player.spellbook.equippedSpells.get(1), true, screen);
            table.add(button2);

            buttonsMatrix[1][0] = button2;
        }

        if(player.spellbook.equippedSpells.size() > 2) {
            table.row();
            SpellIcon_Button button3 = new SpellIcon_Button(player.spellbook.equippedSpells.get(2), true, screen);
            table.add(button3).colspan(2).center();

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
        boolean active = screen.activeTable.equals(this);
        table.clear();
        table.remove();

        buttonsMatrix = new SpellIcon_Button[2][2];
        createTable();
        createButtons();
        secondRow = buttonsMatrix[0][1] != null;
        if(active) {
            updateSelectedButton();
        }
        checkForGearConditionalEffects();
        screen.mastery_table.mixingTable.updateButtons();
    }

    public void dispose() {
        table.clear();
        table.remove();
    }
}
