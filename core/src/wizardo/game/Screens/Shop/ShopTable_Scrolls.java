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

public class ShopTable_Scrolls extends MenuTable {

    ShopScreen screen;

    public int x_pos = 0;
    public int y_pos = 0;

    ArrayList<ShopButton_Scroll> scrollButtons;
    ArrayList<Label> spellPrices;
    ArrayList<Label> spellNames;
    Container<Table> container;

    public ShopTable_Scrolls(Stage stage, Skin skin, Wizardo game, ShopScreen screen) {
        super(stage, skin, game);
        this.screen = screen;
        scrollButtons = new ArrayList<>();
        spellPrices = new ArrayList<>();
        spellNames = new ArrayList<>();

        container = new Container<>();
        stage.addActor(container);
        container.setActor(table);

        setupContainer();
        createButtons();
        adjustPrices();
        resizeLabels();
    }

    public void createButtons() {
        for (int i = 0; i < 4; i++) {
            Label price = new Label(getPriceString(i), inventorySkin, "Price_Etiquette");
            price.setAlignment(Align.center);
            spellPrices.add(price);
            table.add(price).padBottom(-10);
        }
        table.row();
        for (int i = 0; i < 4; i++) {
            ShopButton_Scroll buttonScroll = new ShopButton_Scroll(inventorySkin, screen, screen.shop.getScrollMasteries().get(i), i);
            table.add(buttonScroll).padLeft(10).padRight(10);
            scrollButtons.add(buttonScroll);
        }
        table.row();
        for (int i = 0; i < 4; i++) {
            Label name = new Label(getSpellString(screen.shop.getScrollMasteries().get(i)), inventorySkin, "SpellName_Etiquette");
            name.setAlignment(Align.center);
            spellNames.add(name);
            table.add(name).padTop(-25);
        }
    }

    public String getPriceString(int index) {
        String price = "";
        if(screen.shop.getScrollSolds()[index]) {
            return "sold";
        }
        switch(screen.shop.getScrollMasteries().get(index)) {
            case FROSTBOLT, FLAMEJET, CHARGEDBOLT, MISSILES -> price = "500g";
            case ICESPEAR, FIREBALL, CHAIN, BEAM -> price = "650g";
            case FROZENORB, OVERHEAT, THUNDERSTORM, RIFTS ->  price = "800g";
        }
        return price;
    }

    public void adjustPrices() {
        for (int i = 0; i < 4; i++) {
            if(screen.shop.getScrollSolds()[i]) {
                spellPrices.get(i).setText("sold");
                scrollButtons.get(i).setDisabled(true);
            }
        }
    }

    public void drawItems() {
        for(ShopButton_Scroll button : scrollButtons) {
            button.drawItem();
        }
    }

    public void setupContainer() {

        Sprite background = new Sprite(new Texture("Screens/Shop/ScrollPanelBackground.png"));
        background.setSize(background.getWidth() * xRatio, background.getHeight() * yRatio);
        SpriteDrawable backgroundDrawable = new SpriteDrawable(background);

        container.setBackground(backgroundDrawable);
        container.setActor(table);
        container.align(Align.center);

        container.pack();

        container.setPosition(Gdx.graphics.getWidth()/2 - container.getWidth()/2, 270 * yRatio);

        container.validate();
    }

    public void updateButtons() {
        screen.selectedButton = scrollButtons.get(x_pos);
        for(ShopButton_Scroll button : scrollButtons) {
            button.hovered = false;
        }
        scrollButtons.get(x_pos).hovered = true;
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
        scrollButtons.get(x_pos).handleClick();
        updateButtons();
    }

    @Override
    public void resize() {
        resizeLabels();
        resizeButtons();
        resizeContainer();
    }

    public void resizeLabels() {
        for (Label reagentPrice : spellPrices) {
            Label.LabelStyle originalStyle = inventorySkin.get("Price_Etiquette", Label.LabelStyle.class);
            Label.LabelStyle newStyle = new Label.LabelStyle(originalStyle);
            newStyle.background = new TextureRegionDrawable(((TextureRegionDrawable) originalStyle.background));

            newStyle.background.setMinWidth(originalStyle.background.getMinWidth() * xRatio);
            newStyle.background.setMinHeight(originalStyle.background.getMinHeight() * yRatio);

            reagentPrice.setStyle(newStyle);
            reagentPrice.setFontScale(Math.max(xRatio, 0.8f));
        }
        for (Label reagentPrice : spellNames) {
            Label.LabelStyle originalStyle = inventorySkin.get("SpellName_Etiquette", Label.LabelStyle.class);
            Label.LabelStyle newStyle = new Label.LabelStyle(originalStyle);
            newStyle.background = new TextureRegionDrawable(((TextureRegionDrawable) originalStyle.background));

            newStyle.background.setMinWidth(originalStyle.background.getMinWidth() * xRatio);
            newStyle.background.setMinHeight(originalStyle.background.getMinHeight() * yRatio);

            reagentPrice.setStyle(newStyle);
            reagentPrice.setFontScale(Math.max(xRatio, 0.8f));
        }
    }

    public void resizeContainer() {
        Sprite background = new Sprite(new Texture("Screens/Shop/ScrollPanelBackground.png"));
        background.setSize(background.getWidth() * xRatio, background.getHeight() * yRatio);
        container.setBackground(new SpriteDrawable(background));
        container.pack();
        container.setPosition(Gdx.graphics.getWidth()/2 - container.getWidth()/2, 270 * yRatio);

    }

    public void resizeButtons() {
        for(ShopButton_Scroll button : scrollButtons) {
            button.adjustSize();
        }
    }

}
