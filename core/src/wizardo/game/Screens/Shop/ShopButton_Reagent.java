package wizardo.game.Screens.Shop;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.CharacterScreen.EquipmentTable.MenuButton;
import wizardo.game.Screens.Shop.Anims.Reagent_Anim;

import static wizardo.game.Resources.ScreenResources.ShopScreenResources.*;
import static wizardo.game.Resources.Skins.inventorySkin;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Wizardo.player;

public class ShopButton_Reagent extends ImageButton implements MenuButton {

    public boolean hovered;

    ShopScreen screen;

    Animation<Sprite> normal_anim;
    Animation<Sprite> hover_anim;

    public int index;
    int price;

    public ShopButton_Reagent(Skin skin, ShopScreen screen, int index) {
        super(skin, "shop_reagent_normal");
        this.screen = screen;
        this.index = index;

        if(!screen.shop.getReagentSolds()[index]) {
            addClickListener();
        } else {
            setDisabled(true);
        }
        setup();
        adjustSize();
    }

    public void setup() {
        switch(index) {
            case 0 -> {
                normal_anim = gold_reagent_anim;
                hover_anim = gold_reagent_hover_anim;
                price = 500;
            }
            case 1 -> {
                ImageButton.ImageButtonStyle style = inventorySkin.get("shop_reagent_triple", ImageButtonStyle.class);
                setStyle(style);
                normal_anim = triple_reagent_anim;
                hover_anim = triple_reagent_hover_anim;
                price = 2000;
            }
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

        float WIDTH = xRatio * ogWidth * 1.4f;
        float HEIGHT = xRatio * ogHeigth * 1.4f;


        newStyle.imageUp.setMinWidth(WIDTH);
        newStyle.imageUp.setMinHeight(HEIGHT);

        newStyle.imageOver.setMinWidth(WIDTH);
        newStyle.imageOver.setMinHeight(HEIGHT);

        newStyle.imageDisabled.setMinWidth(WIDTH);
        newStyle.imageDisabled.setMinHeight(HEIGHT);

        setStyle(newStyle);

    }

    public void drawGem() {
        if(!isDisabled()) {
            SpriteBatch batch = screen.batch;
            batch.begin();

            Sprite frame = screen.getSprite();
            if(hovered) {
                frame.set(hover_anim.getKeyFrame(screen.stateTime, true));
            } else {
                frame.set(normal_anim.getKeyFrame(screen.stateTime, true));
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
            setDisabled(true);
            screen.shop.getReagentSolds()[index] = true;

            screen.anims.add(new Reagent_Anim(screen, this));

            if(index == 0) {
                player.inventory.dual_reagents ++;
            } else {
                player.inventory.triple_reagents ++;
            }

            BattleScreen battlescreen = (BattleScreen) screen.game.getPreviousScreen();
            battlescreen.battleUI.updatePanels();

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
