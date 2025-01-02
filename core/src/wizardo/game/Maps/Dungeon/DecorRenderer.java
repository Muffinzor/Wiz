package wizardo.game.Maps.Dungeon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;

import static wizardo.game.Display.DisplayUtils.getSprite;

public class DecorRenderer {

    MapChunk chunk;
    TiledMap map;

    float stateTime;

    public DecorRenderer(MapChunk chunk) {
        this.chunk = chunk;
        this.map = chunk.map;
    }

    public void drawObstacles(float delta) {
        stateTime += delta;

        for (MapObject object : map.getLayers().get("ObstacleBodies").getObjects()) {

            Sprite frame = getSprite(chunk.screen);

            switch(object.getName()) {
                case "Pillar" -> {
                    frame.set(DungeonDecorResources.pillar_sprite);
                    RectangleMapObject rectObject = (RectangleMapObject) object;
                    float x = rectObject.getRectangle().x + chunk.x_pos;
                    float y = rectObject.getRectangle().y + chunk.y_pos;
                    frame.setPosition(x, y);
                    chunk.screen.addSortedSprite(frame);
                    chunk.screen.centerSort(frame, y + 30);
                }
                case "Building1", "BuildingShop" -> {
                    frame.set(DungeonDecorResources.building1_sprite);
                    RectangleMapObject rectObject = (RectangleMapObject) object;
                    float x = rectObject.getRectangle().x + chunk.x_pos;
                    float y = rectObject.getRectangle().y + chunk.y_pos;
                    frame.setPosition(x, y);
                    chunk.screen.addSortedSprite(frame);
                    chunk.screen.centerSort(frame, y + 30);
                }
                case "Building2" -> {
                    frame.set(DungeonDecorResources.building2_sprite);
                    RectangleMapObject rectObject = (RectangleMapObject) object;
                    float x = rectObject.getRectangle().x + chunk.x_pos;
                    float y = rectObject.getRectangle().y + chunk.y_pos;
                    frame.setPosition(x, y);
                    chunk.screen.addSortedSprite(frame);
                    chunk.screen.centerSort(frame, y + 30);
                }
            }

        }

    }




}
