package wizardo.game.Maps.Buildings;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.DecorObjects.WallTorchObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;

import static wizardo.game.Utils.Constants.PPM;

public class Square_Building extends Building {

    public Square_Building(MapChunk chunk, MapObject mapObject) {
        super(chunk, mapObject);
        setup();
    }

    public void setup() {
        int random = MathUtils.random(1,3);
        switch (random) {
            case 1 -> sprite = DungeonDecorResources.squareBuilding_sprite1;
            case 2 -> sprite = DungeonDecorResources.squareBuilding_sprite1;
            case 3 -> sprite = DungeonDecorResources.squareBuilding_sprite1;
        }

        double randomDecos = Math.random();
        if(randomDecos >= 0.8f) {
            decoPosition1 = new Vector2(x + width/3/PPM - 0.1f, y + height/2/PPM - 0.6f);
            decoPosition2 = new Vector2(decoPosition1.x + width/3/PPM + 0.3f, decoPosition1.y);
            decoSprite1 = DungeonDecorResources.getWallDeco(false);
            decoSprite2 = DungeonDecorResources.getWallDeco(false);
            while (decoSprite1 == decoSprite2) {
                decoSprite2 = DungeonDecorResources.getWallDeco(false);
            }
        } else if(randomDecos >= 0.3f) {
            alignement = MathUtils.random(1,5);
            if(alignement == 3) {
                decoSprite1 = DungeonDecorResources.getWallDeco(true);
            } else {
                decoSprite1 = DungeonDecorResources.getWallDeco(false);
            }

            switch(alignement) {
                case 1,2 -> {
                    decoPosition1 = new Vector2(x + width/3/PPM - 0.1f, y  + height/2/PPM - 0.6f);
                    decoPosition2 = new Vector2(decoPosition1.x + width/3/PPM + 0.1f, y  + height/2/PPM - 0.4f);
                    torch(decoPosition2);
                }
                case 3 -> decoPosition1 = new Vector2(x + width/2/PPM, y  + height/2/PPM - 0.6f);

                case 4,5 -> {
                    decoPosition1 = new Vector2(x + ((width/3 * 2)/PPM) + 0.1f, y  + height/2/PPM - 0.6f);
                    decoPosition2 = new Vector2(x + width/3/PPM - 0.1f, y  + height/2/PPM - 0.4f);
                    torch(decoPosition2);
                }
            }
        } else {
            torch(new Vector2(x + width/2/PPM, y  + height/2/PPM - 0.4f));
        }
    }

    public void torch(Vector2 position) {
        WallTorchObject torch = new WallTorchObject(chunk, object, position);
        chunk.layerObjects.add(torch);
    }

    @Override
    public void dispose() {

    }
}
