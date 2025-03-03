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
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Resources.EffectAnims.GearFlareAnims;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;
import wizardo.game.Screens.CharacterScreen.EquipmentTable.GearPanel;
import wizardo.game.Screens.CharacterScreen.EquipmentTable.MenuButton;
import wizardo.game.Screens.Popups.AreYouSureScreen;
import wizardo.game.Screens.Shop.Anims.Gear_Anim;

import static wizardo.game.Resources.ScreenResources.CharacterScreenResources.*;
import static wizardo.game.Resources.ScreenResources.CharacterScreenResources.redQuality;
import static wizardo.game.Resources.Skins.inventorySkin;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Wizardo.player;

public class ShopButton_Equipment extends ImageButton implements MenuButton {

    boolean hovered;

    public Animation<Sprite> flareAnim;
    float random;

    ShopScreen screen;
    public Equipment piece;

    Sprite sprite;
    Sprite spriteOver;
    float spriteRatio = 1f;

    public ShopButton_Equipment(Skin skin, ShopScreen screen, Equipment piece) {
        super(skin, "shop_gear");
        this.screen = screen;
        this.piece = piece;

        random = (float) Math.random();



        if(piece != null) {
            setup();
            addClickListener();
        } else {
            setDisabled(true);
        }
        adjustSize();
    }

    public void setup() {
        sprite = piece.sprite;
        spriteOver = piece.spriteOver;
        flareAnim = GearFlareAnims.getFlareAnim(piece.quality);
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

        if(piece != null && !isDisabled()) {

            SpriteBatch batch = screen.batch;
            batch.begin();
            drawQuality(batch);
            drawItemGlow(batch);

            Sprite frame = screen.getSprite();
            if(hovered) {
                frame.set(spriteOver);
            } else {
                frame.set(sprite);
            }

            frame.setScale(piece.displayScale * xRatio * spriteRatio);
            frame.setCenter(getCenterPoint().x, getCenterPoint().y);

            frame.draw(batch);
            batch.end();
        }
    }

    public void drawItemGlow(SpriteBatch batch) {
        Sprite frame = screen.getSprite();
        frame.set(flareAnim.getKeyFrame(screen.stateTime + random,true));
        frame.setCenter(getCenterPoint().x, getCenterPoint().y);
        if(piece.quality.equals(ItemUtils.EquipQuality.LEGENDARY)) {
            frame.setScale(1.3f);
        } else if(piece.quality.equals(ItemUtils.EquipQuality.NORMAL)) {
            frame.setScale(0.8f);
        }
        frame.draw(batch);
    }

    public void drawQuality(SpriteBatch batch) {
        if(!isDisabled()) {
            Sprite frame = screen.getSprite();
            frame.set(getQualitySprite());
            frame.setScale(1.06f);
            frame.setCenter(getCenterPoint().x, getCenterPoint().y);
            frame.draw(batch);
        }
    }

    public Sprite getQualitySprite() {
        Sprite sprite = null;
        switch(piece.quality) {
            case NORMAL -> sprite = greenQuality;
            case RARE -> sprite = blueQuality;
            case EPIC -> sprite = purpleQuality;
            case LEGENDARY -> sprite = redQuality;
        }
        return sprite;
    }

    public void handleClick() {

        if(!isDisabled() && piece != null && player.inventory.gold >= piece.getPrice()) {
            piece.pickup();
            player.inventory.gold -= piece.getPrice();
            setDisabled(true);

            int index = screen.shop.getGearList().indexOf(piece);
            screen.shop.getGearList().set(index, null);
            screen.gearTable.adjustLabels();
            screen.anims.add(new Gear_Anim(screen, this));

            BattleScreen battlescreen = (BattleScreen) screen.game.getPreviousScreen();
            battlescreen.battleUI.updateGoldPanel();

            BottomText text = new BottomText();
            text.setAll("-" + piece.price, new Vector2(0,0), inventorySkin.getFont("Gear_Text"), Color.YELLOW);
            battlescreen.displayManager.textManager.addGoldText(text);
        }

    }


    public void addClickListener() {
        MenuButton button = this;
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
                screen.panelStage.clear();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(!isDisabled()) {
                    hovered = true;
                    screen.activePanel = new GearPanel(screen.panelStage, piece, false, button);
                    spriteRatio = 1.2f;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(!isDisabled()) {
                    hovered = false;
                    screen.activePanel.dispose();
                    spriteRatio = 1f;
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
