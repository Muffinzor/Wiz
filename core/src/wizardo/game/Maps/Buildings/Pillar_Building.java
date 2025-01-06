package wizardo.game.Maps.Buildings;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.DecorObjects.WallTorchObject;
import wizardo.game.Maps.Dungeon.DecorRenderer;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;

import static wizardo.game.Utils.Constants.PPM;

public class Pillar_Building extends Building{

    public Pillar_Building(MapChunk chunk, MapObject mapObject) {
        super(chunk, mapObject);
        setup();
    }

    public void setup() {
        int random = MathUtils.random(1,3);
        switch (random) {
            case 1 -> sprite = DungeonDecorResources.pillar_sprite;
            case 2 -> sprite = DungeonDecorResources.pillar_sprite;
            case 3 -> sprite = DungeonDecorResources.pillar_sprite;
        }

        if(Math.random() >= 0.8f) {
            decoSprite1 = DungeonDecorResources.getRandomFlag();
            decoPosition1 = new Vector2(x + width/2/PPM, y  + height/2/PPM + 0.4f);
        } else {
            WallTorchObject torch = new WallTorchObject(chunk, object, new Vector2(x + width/2/PPM, y  + height/2/PPM + 1.2f));
            chunk.layerObjects.add(torch);
        }
    }

    @Override
    public void dispose() {

    }
}
