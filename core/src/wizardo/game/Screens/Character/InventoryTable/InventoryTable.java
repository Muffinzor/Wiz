package wizardo.game.Screens.Character.InventoryTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.Character.CharacterScreen;
import wizardo.game.Screens.Character.EquipmentTable.EquipmentButton;
import wizardo.game.Wizardo;

import static wizardo.game.Wizardo.player;

public class InventoryTable extends MenuTable {

    public EquipmentButton[][] buttonsMatrix;
    public CharacterScreen screen;

    public InventoryTable(Stage stage, Skin skin, Wizardo game, CharacterScreen screen) {
        super(stage, skin, game);
        this.screen = screen;
        resize();
    }

    public void update() {
        for(Button button : buttons) {
            InventoryButton inventoryButton = (InventoryButton) button;
            inventoryButton.drawItem();
        }
    }

    public void createTable() {

        //adjustFontSize();
        float xRatio = Gdx.graphics.getWidth() / 1920f;
        float yRatio = Gdx.graphics.getHeight() / 1080f;

        float width = 480 * xRatio;
        float height = 246 * yRatio;

        int x_pos = Math.round(60 * xRatio);
        int y_pos = Math.round(80 * yRatio);

        table = new Table();
        table.setPosition(x_pos, y_pos);
        table.setWidth(width);
        table.setHeight(height);
        stage.addActor(table);

        //table.setDebug(true);

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
        table.remove();
        buttons.clear();
        createTable();
    }
}
