package wizardo.game.Display.Text;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayDeque;

public class FloatingDamagePool {

    private final ArrayDeque<FloatingDamage> array;

    public FloatingDamagePool() {
        array = new ArrayDeque<>();
    }

    public FloatingDamage getDmgText() {
        if(array.isEmpty()) {
            return new FloatingDamage();
        } else {
            return array.removeLast();
        }
    }

    public void poolDmgText(FloatingDamage text) {
        text.text = "";
        text.y_increase = 0;
        array.add(text);
    }
}
