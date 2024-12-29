package wizardo.game.Display.Text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Screens.BaseScreen;

public class FloatingText {

    public float alpha = 1;
    public float stateTime = 0;
    public BitmapFont font;
    public Color color;
    public String text;
    public Vector2 position;
    public float y_increase;

    public FloatingText() {
        y_increase = 0;
    }

    public void setAll(String text, Vector2 position, BitmapFont font, Color color) {
        alpha = 1;
        this.text = text;
        this.position = new Vector2(position);
        this.font = font;
        this.color = new Color(color);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPosition(Vector2 position) {
        float randomOffsetX = MathUtils.random(-10, 10);
        float randomOffsetY = MathUtils.random(-5, 10);
        this.position = new Vector2 (position.x + randomOffsetX, position.y + randomOffsetY);
    }

    public void setColor(Color color) {
        this.color = new Color(color);
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public void update(float delta) {
        stateTime += delta;
        y_increase += 0.2f;
        if(stateTime >= 0.3f) {
            alpha -= 0.01f;
        }
    }

    public void render(SpriteBatch batch) {
        Color textColor = color;

        textColor.a = alpha;

        font.setColor(textColor);

        float textX = position.x;

        font.draw(batch, text, textX, position.y + y_increase);
    }
}
