package wizardo.game.Screens.CharacterScreen.InventoryTable;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;
import wizardo.game.Screens.CharacterScreen.EquipmentTable.EquipmentButton;
import wizardo.game.Wizardo;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Wizardo.player;

public class InventoryTable extends MenuTable {

    public EquipmentButton[][] buttonsMatrix;
    public CharacterScreen screen;

    public InventoryTable(Stage stage, Skin skin, Wizardo game, CharacterScreen screen) {
        super(stage, skin, game);
        this.screen = screen;
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
        }
        table.row();
        for (int i = 5; i < 10; i++) {
            InventoryButton button = new InventoryButton(skin, screen, player.inventory.holdingBox[i]);
            table.add(button);
            buttons.add(button);
        }
        table.row();
        for (int i = 10; i < 15; i++) {
            InventoryButton button = new InventoryButton(skin, screen, player.inventory.holdingBox[i]);
            table.add(button);
            buttons.add(button);
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

    }

    @Override
    public void navigateRight() {

    }

    @Override
    public void pressSelectedButton() {

    }

    @Override
    public void resize() {
        table.clear();
        buttons.clear();
        adjustTable();
    }
}
