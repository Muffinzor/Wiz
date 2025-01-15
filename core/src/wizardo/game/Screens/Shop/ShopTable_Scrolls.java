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
            case FROSTBOLT, FLAMEJET, CHARGEDBOLTS, MISSILES -> price = "500g";
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
        background.setScale(xRatio, yRatio);
        SpriteDrawable backgroundDrawable = new SpriteDrawable(background);

        container.setBackground(backgroundDrawable);
        container.setActor(table);
        container.align(Align.center);

        container.pack();

        container.setPosition(Gdx.graphics.getWidth()/2 - container.getWidth()/2, 270);

        container.validate();
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
