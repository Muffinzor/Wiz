package wizardo.game.Maps.Dungeon.DungeonBuildings;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Maps.Buildings.Building;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.Shop.MapShop;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;

public class DungeonBuilding extends Building {

    DungeonBuilding_Decoration decoration;

    public DungeonBuilding(MapChunk chunk, MapObject mapObject, boolean isShop) {
        super(chunk, mapObject);
        pickSprite();
        this.isShop = isShop;
        createShop();
        if(!name.equals("Shop")) {
            decoration = new DungeonBuilding_Decoration(this);
        }

    }

    public void pickSprite() {
        flipped = MathUtils.randomBoolean();
        switch(name) {
            case "Building1" -> building1_sprite();
            case "Building2" -> sprite = DungeonDecorResources.squareBuilding_sprite1;
            case "Crypt" -> crypt_sprite();
            case "Pillar" -> sprite = DungeonDecorResources.pillar_sprite;
            case "Shop" -> {
                if(chunk.canHaveShop) sprite = DungeonDecorResources.rectangleBuilding_shop;
                else building1_sprite();
            }
        }
    }

    public void createShop() {
        if(isShop) {
            MapShop shop = new MapShop(chunk, object);
            chunk.shop = shop;
            chunk.layerObjects.add(shop);
        }
    }

    @Override
    public void dispose() {

    }

    public void crypt_sprite() {
        int random = MathUtils.random(1,3);
        switch (random) {
            case 1 -> sprite = DungeonDecorResources.crypt_sprite1;
            case 2 -> sprite = DungeonDecorResources.crypt_sprite2;
            case 3 -> sprite = DungeonDecorResources.crypt_sprite3;
        }
    }

    public void building1_sprite() {
        if(isShop) {
            sprite = DungeonDecorResources.rectangleBuilding_shop;
            return;
        }
        int random = MathUtils.random(1, 6);
        switch (random) {
            case 1 -> sprite = DungeonDecorResources.rectangleBuilding_sprite1;
            case 2 -> sprite = DungeonDecorResources.rectangleBuilding_sprite2;
            case 3 -> sprite = DungeonDecorResources.rectangleBuilding_sprite3;
            case 4 -> sprite = DungeonDecorResources.rectangleBuilding_sprite4;
            case 5 -> sprite = DungeonDecorResources.rectangleBuilding_sprite5;
            case 6 -> sprite = DungeonDecorResources.rectangleBuilding_sprite6;
        }
    }
}
