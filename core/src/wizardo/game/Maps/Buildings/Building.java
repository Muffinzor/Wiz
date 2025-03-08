package wizardo.game.Maps.Buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;

import java.util.ArrayList;

import static wizardo.game.Utils.Constants.PPM;

public abstract class Building extends EnvironmentObject {

    public Sprite sprite;
    public boolean flipped;
    public String name;

    public boolean isShop;

    public float x;
    public float y;
    public float width;
    public float height;

    public ArrayList<Sprite> decoration_sprites;
    public ArrayList<Vector2> deco_positions;
    public ArrayList<Boolean> deco_flipped;
    public ArrayList<Float> deco_centerY;

    public Sprite decoSprite1;
    public Vector2 decoPosition1;
    public Sprite decoSprite2;
    public Vector2 decoPosition2;
    int alignement;   // 1 = left, 2 = center, 3 = right

    public Building(MapChunk chunk, MapObject mapObject) {
        super(chunk, mapObject);
        width = mapObject.getProperties().get("width", Float.class);
        height = mapObject.getProperties().get("height", Float.class);
        x = (mapObject.getProperties().get("x", Float.class) + chunk.x_pos);
        y = (mapObject.getProperties().get("y", Float.class) + chunk.y_pos);
        x = x/PPM;
        y = y/PPM;
        name = mapObject.getName();

        decoration_sprites = new ArrayList<>();
        deco_positions = new ArrayList<>();
        deco_flipped = new ArrayList<>();
        deco_centerY = new ArrayList<>();
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
        frame.flip(flipped, false);
        chunk.screen.addSortedSprite(frame);
        chunk.screen.centerSort(frame, y * PPM + height/5);

        drawGoodDeco();
    }

    public void drawGoodDeco() {
        for (int i = 0; i < decoration_sprites.size(); i++) {
            Sprite frame = chunk.screen.getSprite();
            frame.set(decoration_sprites.get(i));
            frame.setPosition(deco_positions.get(i).x, deco_positions.get(i).y);
            frame.flip(deco_flipped.get(i), false);
            chunk.screen.addSortedSprite(frame);
            chunk.screen.centerSort(frame, deco_positions.get(i).y - deco_centerY.get(i));
        }
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
