package wizardo.game.Maps.Buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;

import static wizardo.game.Utils.Constants.PPM;

public abstract class Building extends EnvironmentObject {

    Sprite sprite;

    int x;
    int y;
    float width;
    float height;

    Sprite decoSprite1;
    Vector2 decoPosition1;
    Sprite decoSprite2;
    Vector2 decoPosition2;
    int alignement;   // 1 = left, 2 = center, 3 = right

    public Building(MapChunk chunk, MapObject mapObject) {
        super(chunk, mapObject);
        width = mapObject.getProperties().get("width", Float.class);
        height = mapObject.getProperties().get("height", Float.class);
        x = (int) (mapObject.getProperties().get("x", Float.class) + chunk.x_pos);
        y = (int) (mapObject.getProperties().get("y", Float.class) + chunk.y_pos);
        x = x/PPM;
        y = y/PPM;
    }

    public void setup() {

    }

    public void update(float delta) {
        stateTime += delta;
        drawFrame();
    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(sprite);
        frame.setPosition(x * PPM, y * PPM);
        chunk.screen.addSortedSprite(frame);
        chunk.screen.centerSort(frame, y * PPM + 30);

        drawDeco();
    }

    public void drawDeco() {
        if(decoSprite1 != null) {
            Sprite frame = chunk.screen.getSprite();
            frame.set(decoSprite1);
            frame.setCenter(decoPosition1.x * PPM, decoPosition1.y * PPM);
            chunk.screen.addSortedSprite(frame);
            chunk.screen.centerSort(frame, decoPosition1.y * PPM - 25);
        }
        if(decoSprite2 != null) {
            Sprite frame = chunk.screen.getSprite();
            frame.set(decoSprite2);
            frame.setCenter(decoPosition2.x * PPM, decoPosition2.y * PPM);
            chunk.screen.addSortedSprite(frame);
            chunk.screen.centerSort(frame, decoPosition2.y * PPM - 25);
        }
    }
}
