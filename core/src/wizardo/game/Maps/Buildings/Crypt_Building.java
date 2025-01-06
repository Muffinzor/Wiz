package wizardo.game.Maps.Buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;
import wizardo.game.Screens.BaseScreen;

public class Crypt_Building extends Building {

    public Crypt_Building(MapChunk chunk, MapObject mapObject) {
        super(chunk, mapObject);
        setup();
    }

    public void setup() {
        int random = MathUtils.random(1,3);
        switch (random) {
            case 1 -> sprite = DungeonDecorResources.crypt_sprite1;
            case 2 -> sprite = DungeonDecorResources.crypt_sprite2;
            case 3 -> sprite = DungeonDecorResources.crypt_sprite3;
        }
    }

    @Override
    public void dispose() {

    }
}
