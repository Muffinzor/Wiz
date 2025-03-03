package wizardo.game.Screens.Shop;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import wizardo.game.Display.Text.BottomText;
import wizardo.game.Items.Drop.ScrollDrop;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;
import wizardo.game.Screens.CharacterScreen.EquipmentTable.GearPanel;
import wizardo.game.Screens.CharacterScreen.EquipmentTable.MenuButton;
import wizardo.game.Screens.Popups.AreYouSureScreen;
import wizardo.game.Screens.Shop.Anims.Scroll_Anim;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Player.Levels.StatsBuffer.apply_Scroll;
import static wizardo.game.Resources.ScreenResources.CharacterScreenResources.*;
import static wizardo.game.Resources.ScreenResources.CharacterScreenResources.redQuality;
import static wizardo.game.Resources.Skins.inventorySkin;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Spells.SpellUtils.whatElementIsThis;
import static wizardo.game.Wizardo.player;

public class ShopButton_Scroll extends ImageButton implements MenuButton {

    public boolean hovered;

    ShopScreen screen;

    Sprite sprite;
    Sprite spriteOver;

    public SpellUtils.Spell_Name mastery;
    int index;
    int price;

    public ShopButton_Scroll(Skin skin, ShopScreen screen, SpellUtils.Spell_Name mastery, int index) {
        super(skin, "shop_gear");
        this.screen = screen;
        this.mastery = mastery;
        this.index = index;
        adjustElementStyle();

        if(!screen.shop.getScrollSolds()[index]) {
            setup();
            addClickListener();
        } else {
            setDisabled(true);
        }
        adjustSize();
    }

    public void adjustElementStyle() {
        switch(whatElementIsThis(mastery)) {
            case FROST -> {
                ImageButtonStyle style = inventorySkin.get("frostscroll", ImageButtonStyle.class);
                setStyle(style);
            }
            case FIRE -> {
                ImageButtonStyle style = inventorySkin.get("firescroll", ImageButtonStyle.class);
                setStyle(style);
            }
            case LIGHTNING -> {
                ImageButtonStyle style = inventorySkin.get("lightningscroll", ImageButtonStyle.class);
                setStyle(style);
            }
            case ARCANE -> {
                ImageButtonStyle style = inventorySkin.get("arcanescroll", ImageButtonStyle.class);
                setStyle(style);
            }
        }
    }

    public void setup() {
        switch(mastery) {
            case FROSTBOLT, ICESPEAR, FROZENORB -> {
                sprite = ScrollDrop.getScrollSprite(SpellUtils.Spell_Element.FROST);
                spriteOver = new Sprite(new Texture("Items/Drops/Scrolls/frostScroll_Over.png"));
            }
            case FLAMEJET, FIREBALL, OVERHEAT -> {
                sprite = ScrollDrop.getScrollSprite(SpellUtils.Spell_Element.FIRE);
                spriteOver = new Sprite(new Texture("Items/Drops/Scrolls/fireScroll_Over.png"));
            }
            case CHARGEDBOLTS, CHAIN, THUNDERSTORM -> {
                sprite = ScrollDrop.getScrollSprite(SpellUtils.Spell_Element.LIGHTNING);
                spriteOver = new Sprite(new Texture("Items/Drops/Scrolls/lightningScroll_Over.png"));
            }
            case MISSILES, BEAM, RIFTS -> {
                sprite = ScrollDrop.getScrollSprite(SpellUtils.Spell_Element.ARCANE);
                spriteOver = new Sprite(new Texture("Items/Drops/Scrolls/arcaneScroll_Over.png"));
            }
        }
        switch(mastery) {
            case FROSTBOLT, FLAMEJET, CHARGEDBOLTS, MISSILES -> price = 500;
            case ICESPEAR, FIREBALL, CHAIN, BEAM -> price = 650;
            case FROZENORB, OVERHEAT, THUNDERSTORM, RIFTS -> price = 800;
        }
    }

    public void adjustSize() {

        ImageButton.ImageButtonStyle style = this.getStyle();
        ImageButton.ImageButtonStyle newStyle = new ImageButton.ImageButtonStyle();
        newStyle.imageUp = new TextureRegionDrawable(((TextureRegionDrawable) style.imageUp).getRegion());
        newStyle.imageOver = new TextureRegionDrawable(((TextureRegionDrawable) style.imageOver).getRegion());
        newStyle.imageDisabled = new TextureRegionDrawable(((TextureRegionDrawable) style.imageDisabled).getRegion());

        float ogWidth = newStyle.imageUp.getMinWidth();
        float ogHeigth = newStyle.imageUp.getMinHeight();

        float WIDTH = xRatio * ogWidth * 0.7f;
        float HEIGHT = xRatio * ogHeigth * 0.7f;


        newStyle.imageUp.setMinWidth(WIDTH);
        newStyle.imageUp.setMinHeight(HEIGHT);

        newStyle.imageOver.setMinWidth(WIDTH);
        newStyle.imageOver.setMinHeight(HEIGHT);

        newStyle.imageDisabled.setMinWidth(WIDTH);
        newStyle.imageDisabled.setMinHeight(HEIGHT);

        setStyle(newStyle);

    }

    public void drawItem() {
        if(!isDisabled()) {
            SpriteBatch batch = screen.batch;
            batch.begin();

            Sprite frame = screen.getSprite();
            if(hovered) {
                frame.set(spriteOver);
            } else {
                frame.set(sprite);
            }

            frame.setScale(xRatio, yRatio);
            frame.setCenter(getCenterPoint().x, getCenterPoint().y);

            frame.draw(batch);
            batch.end();
        }
    }



    public void handleClick() {

        if(!isDisabled() && player.inventory.gold >= price) {
            player.inventory.gold -= price;
            screen.shop.getScrollSolds()[index] = true;
            screen.scrollTable.adjustPrices();
            screen.anims.add(new Scroll_Anim(screen, this));
            apply_Scroll(mastery);

            BattleScreen battlescreen = (BattleScreen) screen.game.getPreviousScreen();
            battlescreen.battleUI.updateGoldPanel();

            BottomText text = new BottomText();
            text.setAll("-" + price, new Vector2(0,0), inventorySkin.getFont("Gear_Text"), Color.YELLOW);
            battlescreen.displayManager.textManager.addGoldText(text);
        }

    }


    public void addClickListener() {
        this.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Input.Buttons.RIGHT) {
                    return true;
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (event.getButton() == Input.Buttons.LEFT) {
                    handleClick();
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(!isDisabled()) {
                    hovered = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(!isDisabled()) {
                    hovered = false;
                }
            }
        });
    }

    @Override
    public Vector2 getCenterPoint() {
        float x;
        float y;

        x = getX();
        x += getParent().getX();
        x += getParent().getParent().getX();
        x += getParent().getParent().getParent().getX();

        y = getY();
        y += getParent().getY();
        y += getParent().getParent().getY();
        y += getParent().getParent().getParent().getY();

        x += getWidth()/2f;
        y += getHeight()/2f;

        return new Vector2(x, y);
    }
}
