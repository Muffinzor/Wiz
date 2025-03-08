package wizardo.game.Maps.Forest.ForestDecor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import static wizardo.game.Resources.DecorResources.ForestDecorResources.*;
import static wizardo.game.Utils.Constants.PPM;

public class Grass extends EnvironmentObject {

    Vector2 position;
    Sprite sprite;
    boolean flip;

    public Grass(MapChunk chunk, MapObject object, Vector2 position) {
        super(chunk, object);
        if(position != null) {
            this.position = position;
        }
        setup();
    }

    public void setup() {
        int random = MathUtils.random(0,7);
        sprite = grass[random];
        flip = MathUtils.randomBoolean();
    }

    @Override
    public void update(float delta) {
        drawFrame();
    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(sprite);
        frame.setPosition(position.x * PPM - frame.getWidth()/2, position.y * PPM);
        frame.flip(flip, false);
        chunk.screen.centerSort(frame, position.y * PPM + 15);
        chunk.screen.addSortedSprite(frame);
    }

    @Override
    public void dispose() {

    }
}
