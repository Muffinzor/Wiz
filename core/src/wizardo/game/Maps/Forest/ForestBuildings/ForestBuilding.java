package wizardo.game.Maps.Forest.ForestBuildings;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.Buildings.Building;
import wizardo.game.Maps.Buildings.Rectangle_Building;
import wizardo.game.Maps.DecorObjects.WallTorchObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.Shop.MapShop;
import wizardo.game.Resources.DecorResources.ForestDecorResources;

import static wizardo.game.Utils.Constants.PPM;

public class ForestBuilding extends Building {

    public ForestBuilding(MapChunk chunk, MapObject mapObject) {
        super(chunk, mapObject);
        pickSprite();
        createTorch();
        createShop();
    }

    public void pickSprite() {
        flipped = MathUtils.randomBoolean();
        switch(name) {
            case "Building1_s" -> sprite = ForestDecorResources.rectangle_ruins1_stone;
            case "Building1_g" -> sprite = ForestDecorResources.rectangle_ruins1_grass;
            case "Building2_s" -> sprite = ForestDecorResources.rectangle_ruins2_stone;
            case "Building2_g" -> sprite = ForestDecorResources.rectangle_ruins2_grass;
            case "PillarH_s" -> {
                switch(MathUtils.random(1,2)) {
                    case 1 -> sprite = ForestDecorResources.pillar_h1_stone;
                    case 2 -> sprite = ForestDecorResources.pillar_h2_stone;
                }
            }
            case "PillarH_g" -> {
                switch(MathUtils.random(1,2)) {
                    case 1 -> sprite = ForestDecorResources.pillar_h1_grass;
                    case 2 -> sprite = ForestDecorResources.pillar_h2_grass;
                }
            }
            case "Small_pillar" -> {
                switch(MathUtils.random(1,3)) {
                    case 1 -> sprite = ForestDecorResources.small_pillar1;
                    case 2 -> sprite = ForestDecorResources.small_pillar2;
                    case 3 -> sprite = ForestDecorResources.small_pillar3;
                }
            }
        }
    }

    public void createTorch() {
        if(name.equals("PillarH_s") || name.equals("PillarH_g")) {
            WallTorchObject torch = new WallTorchObject(chunk, object, new Vector2(x + width/2/PPM, y + height/PPM));
            chunk.layerObjects.add(torch);
        }
        if(name.equals("Building2_s") || name.equals("Building2_g")) {
            WallTorchObject torch = new WallTorchObject(chunk, object, new Vector2(x + width/2/PPM, y + height/2/PPM));
            chunk.layerObjects.add(torch);
        }
    }

    public void createShop() {
        if((name.equals("Building1_s") || name.equals("Building1_g")) && chunk.canHaveShop) {
            MapShop shop = new MapShop(chunk, object);
            chunk.layerObjects.add(shop);
        }
    }

    @Override
    public void dispose() {

    }
}
