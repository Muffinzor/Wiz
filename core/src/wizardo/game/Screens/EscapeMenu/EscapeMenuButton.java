package wizardo.game.Screens.EscapeMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class EscapeMenuButton extends TextButton {

    public EscapeMenuButton(String text, Skin skin) {
        super(text, skin);
        TextButtonStyle style = skin.get("WoodButton", TextButtonStyle.class);
        setStyle(style);
        setStyle(adjustedSize());
    }

    public TextButtonStyle adjustedSize() {

        float Xscale = Gdx.graphics.getWidth() / 1920f;
        float Yscale = Gdx.graphics.getHeight() / 1080f;

        TextButtonStyle style = getStyle();
        TextButtonStyle newStyle = new TextButtonStyle();
        newStyle.up = new TextureRegionDrawable(((TextureRegionDrawable) style.up).getRegion());
        newStyle.checked = new TextureRegionDrawable(((TextureRegionDrawable) style.checked).getRegion());
        newStyle.over = new TextureRegionDrawable(((TextureRegionDrawable) style.over).getRegion());
        newStyle.font = style.font;
        newStyle.fontColor = style.fontColor;

        float GEARBOX_WIDTH = Xscale * 500;
        float GEARBOX_HEIGHT = Yscale * 120;

        newStyle.up.setMinWidth(GEARBOX_WIDTH);
        newStyle.up.setMinHeight(GEARBOX_HEIGHT);

        newStyle.checked.setMinWidth(GEARBOX_WIDTH);
        newStyle.checked.setMinHeight(GEARBOX_HEIGHT);

        newStyle.over.setMinWidth(GEARBOX_WIDTH);
        newStyle.over.setMinHeight(GEARBOX_HEIGHT);

        return newStyle;
    }
}
