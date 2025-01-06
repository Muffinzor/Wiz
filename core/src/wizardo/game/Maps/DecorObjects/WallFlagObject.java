package wizardo.game.Maps.DecorObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;

import static wizardo.game.Resources.DecorResources.DungeonDecorResources.*;
import static wizardo.game.Utils.Constants.PPM;

public class WallFlagObject extends EnvironmentObject {


    Sprite flag1;
    Sprite flag2;
    Vector2 position1;
    Vector2 position2;
    int configuration;  //1 = left, 2 = right, 3 = both

    boolean pillar;

    public WallFlagObject(MapChunk chunk, MapObject object) {
        super(chunk, object);
        if(object.getName().equals("Pillar") || object.getName().equals("Building2")) {
            pillar = true;
        }
    }

    public void setup() {
        if(pillar) {
            setCenterPosition();
        } else {
            setBuildingPosition();
            configuration = MathUtils.random(1,3);
        }
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            setup();
            initialized = true;
            pickAnim();
        }

        if(pillar) {
            drawPillarFrame();
        } else {
            drawBuildingFrames();
        }

    }

    public void setCenterPosition() {
        Float width = object.getProperties().get("width", Float.class);
        x = object.getProperties().get("x", Float.class) + chunk.x_pos + width/2;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos + 42;
        x = x/PPM;
        y = y/PPM;

        if(object.getName().equals("Building2")) {
            y = y - 0.2f;
        }


        position1 = new Vector2(x, y);
    }

    public void setBuildingPosition() {
        float x1;
        float x2;
        Float width = object.getProperties().get("width", Float.class);
        x = object.getProperties().get("x", Float.class) + chunk.x_pos;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos + 30;
        x1 = x + width/3f - 25;
        x2 = x + (width/3f) * 2 +25;

        x1 = x1/PPM;
        x2 = x2/PPM;
        y = y/PPM;

        position1 = new Vector2(x1, y);
        position2 = new Vector2(x2, y);
    }

    public void setPosition() {
        Float width = object.getProperties().get("width", Float.class);
        Float height = object.getProperties().get("height", Float.class);
        x = object.getProperties().get("x", Float.class) + chunk.x_pos + width/2;
        y = object.getProperties().get("y", Float.class) + chunk.y_pos + height/2;
        x = x/PPM;
        y = y/PPM;
    }

    public void pickAnim() {
        int random = MathUtils.random(1,4);
        switch(random) {
            case 1 -> flag1 = redFlag;
            case 2 -> flag1 = blueFlag;
            case 3 -> flag1 = goldenFlag;
            case 4 -> flag1 = whiteFlag;
            case 5 -> flag1 = blackFlag;
            case 6 -> flag1 = greenFlag;
            case 7 -> flag1 = purpleFlag;
        }

        int random2 = MathUtils.random(1,4);
        switch(random2) {
            case 1 -> flag2 = redFlag;
            case 2 -> flag2 = blueFlag;
            case 3 -> flag2 = goldenFlag;
            case 4 -> flag2 = whiteFlag;
            case 5 -> flag2 = blackFlag;
            case 6 -> flag2 = greenFlag;
            case 7 -> flag2 = purpleFlag;
        }
    }

    public void drawPillarFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(flag1);
        frame.setCenter(position1.x * PPM, position1.y * PPM);
        chunk.screen.addSortedSprite(frame);
    }

    public void drawBuildingFrames() {
        if(configuration == 1 || configuration == 3) {
            Sprite frame = chunk.screen.getSprite();
            frame.set(flag1);
            frame.setCenter(position1.x * PPM, position1.y * PPM);
            chunk.screen.addSortedSprite(frame);
        }
        if(configuration == 2 || configuration == 3) {
            Sprite frame = chunk.screen.getSprite();
            frame.set(flag2);
            frame.setCenter(position2.x * PPM, position2.y * PPM);
            chunk.screen.addSortedSprite(frame);
        }
    }

    @Override
    public void dispose() {

    }
}
