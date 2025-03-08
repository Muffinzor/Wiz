package wizardo.game.Maps.Dungeon.DungeonBuildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.Buildings.Building;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;

import static wizardo.game.Utils.Constants.PPM;

public class Statue_Building extends Building {

    public Statue_Building(MapChunk chunk, MapObject object) {
        super(chunk, object);
        randomSprite();
    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(sprite);
        frame.setCenter(x * PPM + frame.getWidth()/2 - width + 5, y * PPM + frame.getHeight()/2 + height/2);
        chunk.screen.addSortedSprite(frame);
        chunk.screen.centerSort(frame, y * PPM + 30);

        drawDeco();
    }

    @Override
    public void dispose() {

    }

    public void randomSprite() {
        int random = MathUtils.random(1,3);
        switch (random) {
            case 1 -> sprite = DungeonDecorResources.goldStatue1;
            case 2 -> sprite = DungeonDecorResources.goldStatue2;
            case 3 -> sprite = DungeonDecorResources.goldStatue3;
        }
    }

}
