package wizardo.game.Screens.Shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import wizardo.game.Display.MenuTable;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Resources.Skins.inventorySkin;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Spells.SpellUtils.getSpellString;

public class ShopTable_Reagents extends MenuTable {

    ShopScreen screen;

    public int x_pos = 0;
    public int y_pos = 0;

    ArrayList<ShopButton_Reagent> reagentButtons;
    ArrayList<Label> reagentPrices;
    Container<Table> container;

    public ShopTable_Reagents(Stage stage, Skin skin, Wizardo game, ShopScreen screen) {
        super(stage, skin, game);
        this.screen = screen;
        reagentButtons = new ArrayList<>();
        reagentPrices = new ArrayList<>();

        container = new Container<>();
        stage.addActor(container);
        container.setActor(table);

        setupContainer();
        createButtons();
        adjustPrices();
        resizeLabels();
    }

    public void createButtons() {

        Label price = new Label(getPriceString(0), inventorySkin, "Price_Etiquette");
        price.setAlignment(Align.center);
        table.add(price).padBottom(-10);
        reagentPrices.add(price);

        table.row();

        ShopButton_Reagent normal_reagent_button = new ShopButton_Reagent(inventorySkin, screen, 0);
        table.add(normal_reagent_button).padLeft(10).padRight(10);
        reagentButtons.add(normal_reagent_button);

        table.row();

        Label price2 = new Label(getPriceString(1), inventorySkin, "Price_Etiquette");
        price2.setAlignment(Align.center);
        table.add(price2).padBottom(-10);
        reagentPrices.add(price2);

        table.row();

        ShopButton_Reagent triple_reagent_button = new ShopButton_Reagent(inventorySkin, screen, 1);
        table.add(triple_reagent_button).padLeft(10).padRight(10);
        reagentButtons.add(triple_reagent_button);
    }

    public String getPriceString(int index) {
        String price = "";
        if(screen.shop.getReagentSolds()[index]) {
            return "sold";
        }
        switch(index) {
            case 0 -> price = "500g";
            case 1 -> price = "2000g";
        }
        return price;
    }

    public void adjustPrices() {
        for (int i = 0; i < 2; i++) {
            if(screen.shop.getReagentSolds()[i]) {
                reagentPrices.get(i).setText("sold");
                reagentButtons.get(i).setDisabled(true);
            }
        }
    }

    public void drawGems() {
        for(ShopButton_Reagent button : reagentButtons) {
            button.drawGem();
        }
    }

    public void setupContainer() {

        Sprite background = new Sprite(new Texture("Screens/Shop/ReagentPanelBackground.png"));
        background.setSize(background.getWidth() * xRatio, background.getHeight() * yRatio);
        SpriteDrawable backgroundDrawable = new SpriteDrawable(background);

        container.setBackground(backgroundDrawable);
        container.setActor(table);
        container.align(Align.center);

        container.pack();

        container.setPosition(Gdx.graphics.getWidth()/2 + 450 * xRatio, 630 * yRatio);

        container.validate();
        container.setDebug(true);
    }

    public void updateButtons() {
        screen.selectedButton = reagentButtons.get(x_pos);
        for(ShopButton_Reagent button : reagentButtons) {
            button.hovered = false;
        }
        reagentButtons.get(x_pos).hovered = true;
    }

    @Override
    public void navigateDown() {
        screen.menuTable = screen.gearTable;
        screen.gearTable.x_pos = x_pos;
        screen.gearTable.updateButtons();
    }

    @Override
    public void navigateUp() {
        screen.menuTable = screen.gearTable;
        screen.gearTable.x_pos = x_pos;
        screen.gearTable.updateButtons();
    }

    @Override
    public void navigateLeft() {
        x_pos--;
        if(x_pos < 0) {
            x_pos = 3;
        }
        updateButtons();
    }

    @Override
    public void navigateRight() {
        x_pos++;
        if(x_pos > 3) {
            x_pos = 0;
        }
        updateButtons();
    }

    @Override
    public void pressSelectedButton() {
        reagentButtons.get(y_pos).handleClick();
        updateButtons();
    }

    @Override
    public void resize() {
        resizeLabels();
        resizeButtons();
        resizeContainer();
    }

    public void resizeLabels() {
        for (Label reagentPrice : reagentPrices) {
            Label.LabelStyle originalStyle = inventorySkin.get("Price_Etiquette", Label.LabelStyle.class);
            Label.LabelStyle newStyle = new Label.LabelStyle(originalStyle);
            newStyle.background = new TextureRegionDrawable(((TextureRegionDrawable) originalStyle.background));

            newStyle.background.setMinWidth(originalStyle.background.getMinWidth() * xRatio);
            newStyle.background.setMinHeight(originalStyle.background.getMinHeight() * yRatio);

            reagentPrice.setStyle(newStyle);
            reagentPrice.setFontScale(Math.max(xRatio, 0.8f));
        }
    }

    public void resizeContainer() {
        Sprite background = new Sprite(new Texture("Screens/Shop/ReagentPanelBackground.png"));
        background.setSize(background.getWidth() * xRatio, background.getHeight() * yRatio);
        container.setBackground(new SpriteDrawable(background));
        container.pack();
        container.setPosition(Gdx.graphics.getWidth()/2 + 450 * xRatio, 630 * yRatio);
    }

    public void resizeButtons() {
        for(ShopButton_Reagent button : reagentButtons) {
            button.adjustSize();
        }
    }
}
