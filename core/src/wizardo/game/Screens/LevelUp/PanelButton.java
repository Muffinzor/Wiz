package wizardo.game.Screens.LevelUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import wizardo.game.Screens.LevelUp.LevelUpUtils.*;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Resources.Skins.masteryTableSkin;
import static wizardo.game.Screens.BaseScreen.screenRatio;

public class PanelButton extends TextButton {

    float stateTime;

    Skin skin;
    Sprite qualityGem;
    TextButtonStyle style;
    LevelUpScreen screen;
    Stage stage;
    LevelUpTable table;

    public LevelUps type;
    public LevelUpQuality quality;
    public boolean selected;



    public PanelButton(LevelUpScreen screen, LevelUps type, LevelUpQuality quality) {
        super("", screen.skin);
        this.screen = screen;
        this.stage = screen.stage;
        this.table = screen.table;
        this.skin = screen.skin;
        this.type = type;
        this.quality = quality;

        pickStyle();
        adjustSize();
        setText(TextGenerator.getPanelText(type, quality));

        addClickListener();
    }

    public void pickStyle() {
        TextButton.TextButtonStyle style;
        style = skin.get(type.toString().toLowerCase(), TextButton.TextButtonStyle.class);
        this.style = style;

        switch(quality) {
            case NORMAL -> qualityGem = LevelUpResources.green_gem;
            case RARE -> qualityGem = LevelUpResources.blue_gem;
            case EPIC -> qualityGem = LevelUpResources.purple_gem;
            case LEGENDARY -> qualityGem = LevelUpResources.orange_gem;
        }
    }


    public void drawLiteralFrame(float delta) {
        stateTime += delta;

        Sprite frame = screen.getSprite();
        if(selected) {
            frame.set(LevelUpResources.selected_panel_anim.getKeyFrame(stateTime, true));
        } else {
            frame.set(LevelUpResources.regular_panel);
        }
        frame.setScale(Gdx.graphics.getWidth()/1920f, Gdx.graphics.getHeight()/1080f);
        frame.setCenter(getCenter().x, getCenter().y);

        SpriteBatch batch = screen.batch;
        batch.begin();
        frame.draw(batch);
        batch.end();
    }
    public void drawQualityGem() {
        Sprite frame = screen.getSprite();
        frame.set(qualityGem);
        float x_ratio = Gdx.graphics.getWidth()/1920f;
        float y_ratio = Gdx.graphics.getHeight()/1080f;
        frame.setScale(x_ratio, y_ratio);
        frame.setCenter(getCenter().x, getCenter().y - getHeight()/2 + 26 * y_ratio);

        SpriteBatch batch = screen.batch;
        batch.begin();
        frame.draw(batch);
        batch.end();

    }

    public void addClickListener() {

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.game.setPreviousScreen();
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
        float xRatio = Gdx.graphics.getWidth() / 1920f;
        float yRatio = Gdx.graphics.getHeight() / 1080f;

        TextButton.TextButtonStyle style = this.style;
        TextButton.TextButtonStyle newStyle = new TextButton.TextButtonStyle();
        newStyle.up = new TextureRegionDrawable(((TextureRegionDrawable) style.up).getRegion());
        newStyle.font = skin.getFont("SimpleFont26");

        float WIDTH = xRatio * 399;
        float HEIGHT = yRatio * 692;

        newStyle.up.setMinWidth(WIDTH);
        newStyle.up.setMinHeight(HEIGHT);

        Label label = getLabel();
        label.setWrap(true);
        label.setAlignment(Align.center);
        getLabelCell().pad(10 * xRatio);

        setStyle(newStyle);
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
        updatedStyle.up.setMinWidth(getWidth());
        updatedStyle.up.setMinHeight(getHeight());
        setStyle(updatedStyle);
        this.style = updatedStyle;
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
