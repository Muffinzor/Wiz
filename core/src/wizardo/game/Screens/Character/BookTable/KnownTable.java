package wizardo.game.Screens.Character.BookTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.Character.CharacterScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Wizardo.player;

public class KnownTable extends MenuTable {

    CharacterScreen screen;
    Vector2 centerPoint;

    SpellIcon_Button[][] buttonsMatrix;
    public int x_pos;
    public int y_pos;

    public KnownTable(Stage stage, Skin skin, Wizardo game, CharacterScreen screen) {
        super(stage, skin, game);
        this.screen = screen;

        resize();
    }

    public void createTable() {
        float xRatio = Gdx.graphics.getWidth() / 1920f;
        float yRatio = Gdx.graphics.getHeight() / 1080f;

        float width = 210 * xRatio;
        float height = 260 * yRatio;

        int x_pos = Math.round(980 * xRatio);
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

        if(!player.spellbook.knownSpells.isEmpty()) {
            SpellIcon_Button button = new SpellIcon_Button(player.spellbook.knownSpells.getFirst(), false, screen);
            table.add(button);

            buttonsMatrix[0][0] = button;
        }


        if(player.spellbook.knownSpells.size() > 1) {
            SpellIcon_Button button2 = new SpellIcon_Button(player.spellbook.knownSpells.get(1), false, screen);
            table.add(button2);

            buttonsMatrix[1][0] = button2;
        }

        if(player.spellbook.knownSpells.size() > 2) {
            table.row();
            SpellIcon_Button button3 = new SpellIcon_Button(player.spellbook.knownSpells.get(2), false, screen);
            table.add(button3);

            buttonsMatrix[0][1] = button3;
        }

        if(player.spellbook.knownSpells.size() > 3) {
            SpellIcon_Button button4 = new SpellIcon_Button(player.spellbook.knownSpells.get(3), false, screen);
            table.add(button4);

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
        if(y_pos > 1) {
            y_pos = 1;
        }
        updateSelectedButton();
    }

    @Override
    public void navigateLeft() {
        x_pos --;
        if(x_pos < 0) {
            screen.activeTable = screen.equippedSpells_table;

            if(player.spellbook.equippedSpells.size() == 1) {
                screen.equippedSpells_table.x_position = 0;
                screen.equippedSpells_table.y_position = 0;
                screen.equippedSpells_table.updateSelectedButton();
                return;
            }

            if(player.spellbook.equippedSpells.size() == 2) {
                screen.equippedSpells_table.x_position = 1;
                screen.equippedSpells_table.y_position = 0;
                screen.equippedSpells_table.updateSelectedButton();
                return;
            }

            if(player.spellbook.equippedSpells.size() == 3) {
                if(y_pos == 1) {
                    screen.equippedSpells_table.x_position = 0;
                    screen.equippedSpells_table.y_position = 1;
                } else {
                    screen.equippedSpells_table.x_position = 1;
                    screen.equippedSpells_table.y_position = 0;
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
        System.out.println("click handled");
    }

    @Override
    public void resize() {
        boolean active = screen.activeTable.equals(this);
        table.clear();
        table.remove();

        buttonsMatrix = new SpellIcon_Button[2][2];
        createTable();
        createButtons();
        if(active) {
            updateSelectedButton();
        }
        screen.selectedSpell_Button = null;
        screen.mastery_table.mixingTable.updateButtons();
    }

    public void dispose() {
        table.clear();
        table.remove();
    }
}
