package wizardo.game.Maps.Forest.ForestBuildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.Buildings.Building;
import wizardo.game.Maps.DecorObjects.WallTorchObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Resources.DecorResources.ForestDecorResources;

import static wizardo.game.Utils.Constants.PPM;

public class ForestStone extends Building {

    public ForestStone(MapChunk chunk, MapObject mapObject) {
        super(chunk, mapObject);
        pickSprite();
        createTorch();
    }

    public void pickSprite() {
        flipped = MathUtils.randomBoolean();
        int random = MathUtils.random(1,3);
        switch(random) {
            case 1 -> sprite = ForestDecorResources.stone_1;
            case 2 -> sprite = ForestDecorResources.stone_2;
            case 3 -> sprite = ForestDecorResources.stone_3;

        }
    }

    public void createTorch() {
        if(Math.random() > 0.33f) {
            WallTorchObject torch = new WallTorchObject(chunk, object, new Vector2(x + width/2/PPM, y + height * 2/PPM));
            chunk.layerObjects.add(torch);
        }
    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(sprite);
        frame.setPosition(x * PPM, y * PPM);
        frame.flip(flipped, false);
        chunk.screen.addSortedSprite(frame);
        chunk.screen.centerSort(frame, y * PPM + height/3);
    }

    @Override
    public void dispose() {

    }
}
