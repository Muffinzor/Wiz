package wizardo.game.Screens.CharacterScreen.EquipmentTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Wizardo.player;

public class EquipmentTable extends MenuTable {

    public Table topRow;
    public Table midRow;
    public Table bottomRow;

    public int x_pos;
    public int y_pos;

    public EquipmentButton[][] buttonsMatrix;
    public CharacterScreen screen;

    public EquipmentTable(Stage stage, Skin skin, Wizardo game, CharacterScreen screen) {
        super(stage, skin, game);
        this.screen = screen;
        buttonsMatrix = new EquipmentButton[3][3];

        createButtons();
        adjustSize();

        stage.addActor(table);
    }

    public void update() {
        screen.batch.begin();
        for(Button button : buttons) {
            EquipmentButton equipButton = (EquipmentButton) button;
            equipButton.drawItem();
        }
        screen.batch.end();
    }

    public void adjustSize() {
        float width = 400 * xRatio;
        float height = 465 * yRatio;

        int x_pos = Math.round(100 * xRatio);
        int y_pos = Math.round(445 * yRatio);

        table.setPosition(x_pos, y_pos);
        table.setWidth(width);
        table.setHeight(height);

        for(Button button : buttons) {
            EquipmentButton equipButton = (EquipmentButton) button;
            equipButton.adjustSize();
        }
    }

    public void createButtons() {
        float xPadding = 12f * (Gdx.graphics.getWidth()/1920f);
        float yPadding = 12f * (Gdx.graphics.getWidth()/1920f);

        EquipmentButton button1 = new EquipmentButton(skin, screen, player.inventory.equippedHat);
        buttonsMatrix[0][0] = button1;
        EquipmentButton button2 = new EquipmentButton(skin, screen, player.inventory.equippedAmulet);
        buttonsMatrix[1][0] = button2;
        topRow = new Table();
        Label label1 = new Label("Hat", skin);
        Label label2 = new Label("Amulet", skin);
        topRow.add(label1).padBottom(-yPadding);
        topRow.add(label2).padBottom(-yPadding);
        topRow.row();
        topRow.add(button1).pad(xPadding).expandX();
        topRow.add(button2).pad(xPadding).expandX();

        table.add(topRow);
        table.row();

        EquipmentButton button3 = new EquipmentButton(skin, screen, player.inventory.equippedBook);
        buttonsMatrix[0][1] = button3;
        EquipmentButton button4 = new EquipmentButton(skin, screen, player.inventory.equippedRobes);
        buttonsMatrix[1][1] = button4;
        EquipmentButton button5 = new EquipmentButton(skin, screen, player.inventory.equippedStaff);
        buttonsMatrix[2][1] = button5;
        midRow = new Table();
        Label label3 = new Label("Book", skin);
        Label label4 = new Label("Robe", skin);
        Label label5 = new Label("Staff", skin);
        midRow.add(label3).padBottom(-yPadding);
        midRow.add(label4).padBottom(-yPadding);
        midRow.add(label5).padBottom(-yPadding);
        midRow.row();
        midRow.add(button3).pad(xPadding).expandX();
        midRow.add(button4).pad(xPadding).expandX();
        midRow.add(button5).pad(xPadding).expandX();
        table.add(midRow);
        table.row();

        bottomRow = new Table();
        Label label6 = new Label("Ring", skin);
        Label label7 = new Label("Soul Stone", skin);
        bottomRow.add(label6).padBottom(-yPadding);
        bottomRow.add(label7).padBottom(-yPadding);
        bottomRow.row();
        EquipmentButton button6 = new EquipmentButton(skin, screen, player.inventory.equippedRing);
        buttonsMatrix[0][2] = button6;
        EquipmentButton button7 = new EquipmentButton(skin, screen, player.inventory.equippedStone);
        buttonsMatrix[1][2] = button7;
        bottomRow.add(button6).pad(xPadding).expandX();
        bottomRow.add(button7).pad(xPadding).expandX();
        table.add(bottomRow);

        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
    }

    public void updateSelectedButton() {
        screen.selectedButton = buttonsMatrix[x_pos][y_pos];
        if(screen.activePanel != null) {
            screen.activePanel.dispose();
        }

        ArrayList<EquipmentButton> buttons = new ArrayList<>();
        buttons.add(buttonsMatrix[0][0]);
        buttons.add(buttonsMatrix[1][0]);
        buttons.add(buttonsMatrix[0][1]);
        buttons.add(buttonsMatrix[1][1]);
        buttons.add(buttonsMatrix[2][1]);
        buttons.add(buttonsMatrix[0][2]);
        buttons.add(buttonsMatrix[1][2]);
        for(EquipmentButton button : buttons) {
            button.hovered = false;
        }
        buttonsMatrix[x_pos][y_pos].hovered = true;
        if(buttonsMatrix[x_pos][y_pos].piece != null) {
            screen.activePanel = new GearPanel(screen.panelStage, buttonsMatrix[x_pos][y_pos].piece, true, buttonsMatrix[x_pos][y_pos]);
        }
    }

    @Override
    public void navigateDown() {
        y_pos --;
        if(y_pos == 1) {
            x_pos = 1;
        } else if(x_pos > 1) {
            x_pos = 1;
        }
        if(y_pos < 0) {
            y_pos = 0;
        }
        updateSelectedButton();

    }

    @Override
    public void navigateUp() {
        y_pos ++;
        if(y_pos == 1) {
            x_pos = 1;
        } else if(x_pos > 1) {
            x_pos = 1;
        }
        if(y_pos > 2) {
            screen.activeTable = screen.inventory_table;
            if(x_pos == 0) {
                screen.selectedButton = screen.inventory_table.buttonsMatrix[1][0];
                screen.inventory_table.x_pos = 1;
            } else {
                screen.selectedButton = screen.inventory_table.buttonsMatrix[3][0];
                screen.inventory_table.x_pos = 3;
            }
            screen.inventory_table.y_pos = 0;
            if(screen.activePanel != null) {
                screen.activePanel.dispose();
            }
            if(screen.inventory_table.buttonsMatrix[screen.inventory_table.x_pos][0].piece != null) {
                screen.activePanel = new GearPanel(screen.panelStage,
                        screen.inventory_table.buttonsMatrix[screen.inventory_table.x_pos][0].piece, false,
                        screen.inventory_table.buttonsMatrix[screen.inventory_table.x_pos][0]);
            }
        } else {
            updateSelectedButton();
        }
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
        int max_x;
        if(y_pos == 1) {
            max_x = 2;
        } else {
            max_x = 1;
        }
        if(x_pos > max_x) {
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
            if(screen.activePanel != null) {
                screen.activePanel.dispose();
            }
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
        createButtons();
        adjustSize();
    }




}
