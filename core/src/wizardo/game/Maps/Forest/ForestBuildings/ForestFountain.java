package wizardo.game.Maps.Forest.ForestBuildings;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.Buildings.Building;
import wizardo.game.Maps.DecorObjects.WallTorchObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Resources.DecorResources.ForestDecorResources;

import static wizardo.game.Utils.Constants.PPM;

public class ForestFountain extends Building {

    Animation<Sprite> anim;
    Vector2 centerpoint;

    public ForestFountain(MapChunk chunk, MapObject mapObject) {
        super(chunk, mapObject);
        anim = ForestDecorResources.fountain_anim;
    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(x * PPM + 60, y * PPM - 12);
        chunk.screen.addUnderSprite(frame);
    }

    @Override
    public void dispose() {

    }
}
