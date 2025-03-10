package wizardo.game.Screens.CharacterScreen.EquipmentTable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;

import static wizardo.game.Resources.ScreenResources.CharacterScreenResources.*;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;

public class EquipmentButton extends ImageButton implements MenuButton {

    boolean hovered;

    CharacterScreen screen;
    public Equipment piece;

    Sprite sprite;
    Sprite spriteOver;

    public EquipmentButton(Skin skin, CharacterScreen screen, Equipment piece) {
        super(skin);
        this.screen = screen;
        this.piece = piece;
        if(piece != null) {
           setup();
           addClickListener();
        }
        adjustSize();
    }

    public void setup() {
        sprite = piece.sprite;
        spriteOver = piece.spriteOver;
    }

    public void adjustSize() {

        ImageButton.ImageButtonStyle style = this.getStyle();
        ImageButton.ImageButtonStyle newStyle = new ImageButton.ImageButtonStyle();
        newStyle.imageUp = new TextureRegionDrawable(((TextureRegionDrawable) style.imageUp).getRegion());
        newStyle.imageOver = new TextureRegionDrawable(((TextureRegionDrawable) style.imageOver).getRegion());

        float WIDTH = xRatio * 115;
        float HEIGHT = yRatio * 110;

        newStyle.imageUp.setMinWidth(WIDTH);
        newStyle.imageUp.setMinHeight(HEIGHT);

        newStyle.imageOver.setMinWidth(WIDTH);
        newStyle.imageOver.setMinHeight(HEIGHT);

        setStyle(newStyle);

    }

    public void drawItem() {
        if(piece != null) {
            SpriteBatch batch = screen.batch;
            drawQuality(batch);

            Sprite frame = screen.getSprite();
            if(hovered) {
                frame.set(spriteOver);
            } else {
                frame.set(sprite);
            }

            frame.setScale(xRatio * piece.displayScale, yRatio * piece.displayScale);
            frame.rotate(piece.displayRotation);
            frame.setCenter(getCenterPoint().x, getCenterPoint().y);

            frame.draw(batch);
        }
    }
    public void drawQuality(SpriteBatch batch) {
        Sprite frame = screen.getSprite();
        frame.set(getQualitySprite());
        frame.setCenter(getCenterPoint().x, getCenterPoint().y);
        frame.setScale(xRatio, yRatio);
        frame.draw(batch);
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

    public void addClickListener() {
        MenuButton button = this;
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleClick();
                screen.activePanel.dispose();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hovered = true;
                if(screen.activePanel != null) {
                    screen.activePanel.dispose();
                }
                screen.activePanel = new GearPanel(screen.panelStage, piece, true, button);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hovered = false;
                screen.activePanel.dispose();
            }
        });
    }

    public void handleClick() {
        screen.selectedSpell_Button = null;
        if(piece != null) {
            piece.storeAfterUnequip();
            screen.inventory_table.resize();
            screen.equipment_table.resize();
            screen.mastery_table.updateChanges();
            screen.equippedSpells_table.resize();
            screen.equippedSpells_table.updateSpells();
            screen.knownSpells_table.resize();
            screen.knownSpells_table.updateSpells();
            screen.stats_Table.createNewPanel();
            screen.selectedButton = screen.equipment_table.buttonsMatrix[screen.equipment_table.x_pos][screen.equipment_table.y_pos];
        }
    }

    @Override
    public Vector2 getCenterPoint() {
        float x;
        float y;
        x = getX();
        x += getParent().getX();
        x += getParent().getParent().getX();

        y = getY();
        y += getParent().getY();
        y += getParent().getParent().getY();

        x += getWidth()/2f;
        y += getHeight()/2f;

        return new Vector2(x, y);
    }
}
