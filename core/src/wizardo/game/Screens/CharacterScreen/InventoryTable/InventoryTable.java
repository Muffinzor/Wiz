package wizardo.game.Screens.CharacterScreen.InventoryTable;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;
import wizardo.game.Screens.CharacterScreen.EquipmentTable.GearPanel;
import wizardo.game.Wizardo;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Wizardo.player;

public class InventoryTable extends MenuTable {

    public InventoryButton[][] buttonsMatrix;
    public CharacterScreen screen;

    public int x_pos;
    public int y_pos;

    public InventoryTable(Stage stage, Skin skin, Wizardo game, CharacterScreen screen) {
        super(stage, skin, game);
        this.screen = screen;
        buttonsMatrix = new InventoryButton[5][3];
        stage.addActor(table);
        adjustTable();
        createButtons();
    }

    public void update() {
        for(Button button : buttons) {
            InventoryButton inventoryButton = (InventoryButton) button;
            inventoryButton.drawItem();
        }
    }

    public void adjustTable() {

        float width = 480 * xRatio;
        float height = 246 * yRatio;

        int x_pos = Math.round(60 * xRatio);
        int y_pos = Math.round(80 * yRatio);

        table.setPosition(x_pos, y_pos);
        table.setWidth(width);
        table.setHeight(height);

        createButtons();

    }

    public void createButtons() {
        for (int i = 0; i < 5; i++) {
            InventoryButton button = new InventoryButton(skin, screen, player.inventory.holdingBox[i]);
            table.add(button);
            buttons.add(button);
            buttonsMatrix[i][0] = button;
        }
        table.row();
        for (int i = 5; i < 10; i++) {
            InventoryButton button = new InventoryButton(skin, screen, player.inventory.holdingBox[i]);
            table.add(button);
            buttons.add(button);
            buttonsMatrix[i-5][1] = button;
        }
        table.row();
        for (int i = 10; i < 15; i++) {
            InventoryButton button = new InventoryButton(skin, screen, player.inventory.holdingBox[i]);
            table.add(button);
            buttons.add(button);
            buttonsMatrix[i-10][2] = button;
        }
    }

    public void updateSelectedButton() {
        screen.selectedButton = buttonsMatrix[x_pos][y_pos];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                buttonsMatrix[i][j].hovered = (i == x_pos && j == y_pos);
                if(i == x_pos && j == y_pos) {
                    buttonsMatrix[i][j].spriteRatio = 0.8f;
                } else {
                    buttonsMatrix[i][j].spriteRatio = 0.7f;
                }
            }
        }

        if(screen.activePanel != null) {
            screen.activePanel.dispose();
        }

        if(buttonsMatrix[x_pos][y_pos].piece != null) {
            screen.activePanel = new GearPanel(screen.panelStage, buttonsMatrix[x_pos][y_pos].piece, false, buttonsMatrix[x_pos][y_pos]);
        }
    }

    public void exitTable() {
        if(screen.activePanel != null) {
            screen.activePanel.dispose();
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                buttonsMatrix[i][j].hovered = false;
                buttonsMatrix[i][j].spriteRatio = 0.7f;
            }
        }
    }

    @Override
    public void navigateDown() {
        y_pos --;
        if(y_pos < 0) {
            screen.activeTable = screen.equipment_table;
            if(x_pos < 2) {
                screen.selectedButton = screen.equipment_table.buttonsMatrix[0][2];
                screen.equipment_table.x_pos = 0;
            } else {
                screen.selectedButton = screen.equipment_table.buttonsMatrix[1][2];
                screen.equipment_table.x_pos = 1;
            }
            screen.equipment_table.y_pos = 2;
            exitTable();
            if(screen.equipment_table.buttonsMatrix[screen.equipment_table.x_pos][screen.equipment_table.y_pos].piece != null) {
                screen.activePanel = new GearPanel(screen.panelStage,
                        screen.equipment_table.buttonsMatrix[screen.equipment_table.x_pos][screen.equipment_table.y_pos].piece, false,
                        screen.equipment_table.buttonsMatrix[screen.equipment_table.x_pos][screen.equipment_table.y_pos]);
            }
        } else {
            updateSelectedButton();
        }

    }


    @Override
    public void navigateUp() {
        y_pos ++;
        if(y_pos > 2) {
            y_pos = 2;
        }
        updateSelectedButton();


    }

    @Override
    public void navigateLeft() {
        x_pos --;
        if(x_pos < 0) {
            x_pos = 0;
        }
        updateSelectedButton();
    }

    @Override
    public void navigateRight() {
        x_pos ++;
        if(x_pos > 4) {
            if(screen.equippedSpells_table.buttonsMatrix[0][0] != null) {
                screen.activeTable = screen.equippedSpells_table;
                screen.selectedButton = screen.equippedSpells_table.buttonsMatrix[0][0];
                screen.equippedSpells_table.x_pos = 0;
                screen.equippedSpells_table.y_pos = 0;
            } else if(screen.knownSpells_table.buttonsMatrix[0][0] != null) {
                screen.activeTable = screen.knownSpells_table;
                screen.selectedButton = screen.knownSpells_table.buttonsMatrix[0][0];
                screen.knownSpells_table.x_pos = 0;
                screen.knownSpells_table.y_pos = 0;
            } else {
                screen.activeTable = screen.mastery_table.mixingTable;
                screen.selectedButton = screen.mastery_table.mixingTable.buttons.getFirst();
                screen.mastery_table.mixingTable.x_pos = 0;
            }

            exitTable();
        } else {
            updateSelectedButton();
        }

    }

    @Override
    public void pressSelectedButton() {
        buttonsMatrix[x_pos][y_pos].handleClick();
    }

    @Override
    public void resize() {
        table.clear();
        buttons.clear();
        adjustTable();
    }
}
