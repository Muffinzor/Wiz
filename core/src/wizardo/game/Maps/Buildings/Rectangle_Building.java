package wizardo.game.Maps.Buildings;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;


public class Rectangle_Building extends Building {

    public Rectangle_Building(MapChunk chunk, MapObject mapObject, boolean isShop) {
        super(chunk, mapObject);
        setup(isShop);
    }

    public void setup(boolean isShop) {
        if(isShop) {
            sprite = DungeonDecorResources.rectangleBuilding_shop;
        } else {
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

    @Override
    public void dispose() {

    }
}
