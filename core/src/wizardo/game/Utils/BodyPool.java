package wizardo.game.Utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayDeque;

public class BodyPool {


    private final ArrayDeque<Body> array;

    public BodyPool() {
        array = new ArrayDeque<>();
    }

    public Body getBody(Vector2 position) {
        if(array.isEmpty()) {
            return BodyFactory.monsterBody(position, 10);
        } else {
            Body body = array.removeLast();
            body.setTransform(position,0);
            return body;
        }
    }

    public void poolBody(Body body) {
        body.setActive(false);
        body.setTransform(-1000,-1000,0);
        array.add(body);
    }

    public int getSize() {
        return array.size();
    }
}
