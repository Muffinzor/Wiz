package wizardo.game.Screens.LevelUp;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import wizardo.game.Resources.ScreenResources.LevelUpResources;
import wizardo.game.Player.Levels.LevelUpEnums.*;
import wizardo.game.Spells.Spell;

import static wizardo.game.Resources.ScreenResources.LevelUpResources.*;
import static wizardo.game.Resources.ScreenResources.LevelUpResources.orange_gem;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Wizardo.player;

public abstract class PanelButton extends TextButton {

    float stateTime;

    Skin skin;
    public Sprite qualityGem;
    TextButtonStyle style;
    LevelUpScreen screen;
    Stage stage;
    LevelUpTable table;

    public LevelUps type;
    public LevelUpQuality quality;
    public boolean selected;

    public Spell learned_spell;  // if this is a mastery powerup
    public int spell_dmg_buff = 15;
    public float bonus_effect_chance = 0.4f;

    public PanelButton(LevelUpScreen screen) {
        super("", screen.skin);
        this.screen = screen;
        this.stage = screen.stage;
        this.table = screen.levelUpTable;
        this.skin = screen.skin;
    }

    public void setup() {
        pickStyle();
        adjustSize();
        addClickListener();
    }

    public void pickStyle() {
        TextButton.TextButtonStyle style;
        style = skin.get(type.toString().toLowerCase(), TextButton.TextButtonStyle.class);
        this.style = style;
    }

    public void set_gem_sprite() {
        switch(quality) {
            case NORMAL -> qualityGem = green_gem;
            case RARE -> qualityGem = blue_gem;
            case EPIC -> qualityGem = purple_gem;
            case LEGENDARY -> qualityGem = orange_gem;
        }
    }

    public void drawPanel(float delta) {
        drawLiteralFrame(delta);
    }

    public void drawLiteralFrame(float delta) {
        stateTime += delta;

        Sprite frame = screen.getSprite();
        if(selected) {
            frame.set(LevelUpResources.selected_panel_anim.getKeyFrame(stateTime, true));
        } else {
            frame.set(LevelUpResources.regular_panel);
        }
        frame.setScale(xRatio);
        frame.setCenter(getCenter().x, getCenter().y);

        SpriteBatch batch = screen.batch;
        batch.begin();
        frame.draw(batch);
        batch.end();
    }
    public void drawQualityGem() {
        Sprite frame = screen.getSprite();
        frame.set(qualityGem);
        frame.setScale(xRatio);
        frame.setCenter(getCenter().x, getCenter().y - getHeight()/2 + 26 * xRatio);
        SpriteBatch batch = screen.batch;
        batch.begin();
        frame.draw(batch);
        batch.end();
    }

    public void handleClick() {
        apply_stats();
        if(learned_spell != null) {
            equip_spell();
        }
        screen.game.setPreviousScreen();
    }

    public void equip_spell() {
        boolean sameTypeEquipped = false;
        for(Spell equipped : player.spellbook.equippedSpells) {
            if(equipped.toString().equals(this.learned_spell.toString())) {
                sameTypeEquipped = true;
            }
        }
        if(player.spellbook.equippedSpells.size() < 3 && !sameTypeEquipped) {
            player.spellbook.equippedSpells.add(learned_spell);
        }
    }

    public abstract void apply_stats();

    public void addClickListener() {
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleClick();
            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selected = true;
                updateFontColor();
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selected = false;
                updateFontColor();
            }
        });
    }
    public void adjustSize() {
        TextButton.TextButtonStyle style = this.style;
        TextButton.TextButtonStyle newStyle = new TextButton.TextButtonStyle();
        newStyle.up = new TextureRegionDrawable(((TextureRegionDrawable) style.up).getRegion());
        newStyle.font = this.style.font;
        newStyle.fontColor = this.style.fontColor;

        float WIDTH = xRatio * 399;
        float HEIGHT = xRatio * 692;

        newStyle.up.setMinWidth(WIDTH);
        newStyle.up.setMinHeight(HEIGHT);

        Label label = getLabel();
        label.setWrap(true);
        label.setAlignment(Align.center);
        getLabelCell().pad(15 * xRatio);

        setStyle(newStyle);
        this.style = newStyle;
    }
    public void updateFontColor() {
        TextButton.TextButtonStyle updatedStyle = new TextButton.TextButtonStyle();
        updatedStyle.up = style.up;
        updatedStyle.font = style.font;
        if(selected) {
            updatedStyle.fontColor = skin.getColor("Selected_Color");
        } else {
            updatedStyle.fontColor = skin.getColor("White");
        }
        setStyle(updatedStyle);
        this.style = updatedStyle;
        adjustSize();
    }
    public Vector2 getCenter() {
        float table_x = getParent().getX();
        float table_y = getParent().getY();

        float own_x = getX();
        float own_y = getY();

        float actual_x = table_x + own_x + getWidth()/2f;
        float actual_y = table_y + own_y + getHeight()/2f;

        return new Vector2(actual_x, actual_y);
    }

}
