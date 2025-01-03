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

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Wizardo.player;

public class EquipmentTable extends MenuTable {

    public Table topRow;
    public Table midRow;
    public Table bottomRow;

    public EquipmentButton[][] buttonsMatrix;
    public CharacterScreen screen;

    public EquipmentTable(Stage stage, Skin skin, Wizardo game, CharacterScreen screen) {
        super(stage, skin, game);
        this.screen = screen;

        createButtons();
        adjustSize();

        stage.addActor(table);
    }

    public void update() {
        for(Button button : buttons) {
            EquipmentButton equipButton = (EquipmentButton) button;
            equipButton.drawItem();
        }
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
        EquipmentButton button2 = new EquipmentButton(skin, screen, player.inventory.equippedAmulet);
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
        EquipmentButton button4 = new EquipmentButton(skin, screen, player.inventory.equippedRobes);
        EquipmentButton button5 = new EquipmentButton(skin, screen, player.inventory.equippedStaff);
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
        EquipmentButton button7 = new EquipmentButton(skin, screen, player.inventory.equippedStone);
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
        createButtons();
        adjustSize();
    }




}
