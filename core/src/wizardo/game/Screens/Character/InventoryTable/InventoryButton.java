package wizardo.game.Screens.Character.InventoryTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Screens.Character.CharacterScreen;
import wizardo.game.Screens.Character.EquipmentTable.GearPanel;
import wizardo.game.Screens.Character.EquipmentTable.MenuButton;

import static wizardo.game.Resources.ScreenResources.CharacterScreenResources.*;
import static wizardo.game.Resources.ScreenResources.CharacterScreenResources.redQuality;

public class InventoryButton extends ImageButton implements MenuButton {

    boolean hovered;

    CharacterScreen screen;
    Equipment piece;

    Sprite sprite;
    Sprite spriteOver;
    float spriteRatio = 0.7f;

    public InventoryButton(Skin skin, CharacterScreen screen, Equipment piece) {
        super(skin, "inventory");
        this.screen = screen;
        this.piece = piece;

        if(piece != null) {
            setup();
            addClickListener();
        }
    }

    public void setup() {
        sprite = piece.sprite;
        spriteOver = piece.spriteOver;
    }



    public void drawItem() {
        if(piece != null) {
            SpriteBatch batch = screen.batch;
            batch.begin();
            drawQuality(batch);

            Sprite frame = screen.getSprite();
            if(hovered) {
                frame.set(spriteOver);
            } else {
                frame.set(sprite);
            }

            frame.setScale(piece.displayScale * spriteRatio);
            frame.rotate(piece.displayRotation);
            frame.setCenter(getCenterPoint().x, getCenterPoint().y);

            frame.draw(batch);
            batch.end();
        }
    }
    public void drawQuality(SpriteBatch batch) {
        Sprite frame = screen.getSprite();
        frame.set(getQualitySprite());
        frame.setScale(0.8f);
        frame.setCenter(getCenterPoint().x, getCenterPoint().y);
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

    public void handleClick() {
        if(piece != null) {
            piece.equip();
            screen.inventory_table.resize();
            screen.equipment_table.resize();
            screen.mastery_table.updateChanges();
            screen.equippedSpells_table.resize();
            screen.knownSpells_table.resize();
        }

    }

    public void addClickListener() {
        MenuButton button = this;
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleClick();
                screen.panelStage.clear();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hovered = true;
                GearPanel panel = new GearPanel(screen.panelStage, piece, false, button);
                screen.activePanel = panel;
                spriteRatio = 0.8f;
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hovered = false;
                screen.activePanel.dispose();
                spriteRatio = 0.7f;
            }
        });
    }


    @Override
    public Vector2 getCenterPoint() {
        float x;
        float y;
        x = getX();
        x += getParent().getX();

        y = getY();
        y += getParent().getY();

        x += getWidth()/2f;
        y += getHeight()/2f;

        return new Vector2(x, y);
    }
}
