package wizardo.game.Screens.Shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import wizardo.game.Display.MenuTable;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Resources.Skins.inventorySkin;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;

public class ShopTable_Gear extends MenuTable {

    ShopScreen screen;
    Container<Table> container;

    ArrayList<ShopButton_Equipment> gearButtons;

    Table gear1;
    Table gear2;
    Table gear3;

    ArrayList<Label> prices;

    public ShopTable_Gear(Stage stage, Skin skin, Wizardo game, ShopScreen screen) {
        super(stage, skin, game);
        this.screen = screen;
        gearButtons = new ArrayList<>();
        prices = new ArrayList<>();

        container = new Container<>();
        stage.addActor(container);
        container.setActor(table);
        setupContainer();

        createTables();
        createButtons();

    }

    public void createTables() {
        gear1 = new Table();
        gear2 = new Table();
        gear3 = new Table();
        table.add(gear1).expandX();
        table.add(gear2).expandX();
        table.add(gear3).expandX();
    }

    public void createButtons() {
        ShopButton_Equipment gearButton1 = new ShopButton_Equipment(inventorySkin, screen, screen.shop.getGearList().get(0));
        ShopButton_Equipment gearButton2 = new ShopButton_Equipment(inventorySkin, screen, screen.shop.getGearList().get(1));
        ShopButton_Equipment gearButton3 = new ShopButton_Equipment(inventorySkin, screen, screen.shop.getGearList().get(2));

        Label price1 = new Label(getPriceString(gearButton1.piece), inventorySkin, "Price_Etiquette");
        price1.setAlignment(Align.center);
        prices.add(price1);
        Label price2 = new Label(getPriceString(gearButton2.piece), inventorySkin, "Price_Etiquette");
        price2.setAlignment(Align.center);
        prices.add(price2);
        Label price3 = new Label(getPriceString(gearButton3.piece), inventorySkin, "Price_Etiquette");
        price3.setAlignment(Align.center);
        prices.add(price3);

        gear1.add(price1).row();
        gear1.add(gearButton1);
        gear1.pad(10);

        gear2.add(price2).row();
        gear2.add(gearButton2);
        gear2.pad(10);

        gear3.add(price3).row();
        gear3.add(gearButton3);
        gear3.pad(10);

        gearButtons.add(gearButton1);
        gearButtons.add(gearButton2);
        gearButtons.add(gearButton3);
    }

    public void setupContainer() {

        Sprite background = new Sprite(new Texture("Screens/Shop/GearPanelBackground.png"));
        background.setScale(xRatio, yRatio);
        SpriteDrawable backgroundDrawable = new SpriteDrawable(background);

        container.setBackground(backgroundDrawable);
        container.setActor(table);
        container.align(Align.center);

        container.pack();

        container.setPosition(400, 400);

        container.validate();
    }

    public void adjustLabels() {
        for (int i = 0; i < 3; i++) {
            if(screen.shop.getGearList().get(i) == null) {
                prices.get(i).setText("sold");
            }
        }
    }

    public void drawItems() {
        for(ShopButton_Equipment button : gearButtons) {
            button.drawItem();
        }
    }

    public String getPriceString(Equipment piece) {
        String s = "";
        if(piece == null) {
            return "sold";
        }
        switch(piece.quality) {
            case NORMAL -> s += 500 + "g";
            case RARE -> s += 1000 + "g";
            case EPIC -> s += 2500 + "g";
            case LEGENDARY -> s += 5000 + "g";
        }
        return s;
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

    }
}
