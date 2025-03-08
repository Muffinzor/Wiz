package wizardo.game.Maps.Dungeon.DungeonBuildings;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.DecorObjects.WallTorchObject;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;

import java.util.ArrayList;

import static wizardo.game.Maps.Forest.ForestBuildings.ForestBuilding_Decoration.Deco_Type.BUSH;
import static wizardo.game.Resources.DecorResources.ForestDecorResources.*;
import static wizardo.game.Utils.Constants.PPM;

public class DungeonBuilding_Decoration {

    DungeonBuilding building;
    String name;

    public DungeonBuilding_Decoration(DungeonBuilding building) {
        this.building = building;
        this.name = building.name;
        do_the_thing();
    }

    public void do_the_thing() {
        switch(name) {
            case "Pillar" -> pillar_decoration();
            case "Building2" -> square_decoration();
        }
    }

    public void pillar_decoration() {
        if(Math.random() > 0.8f) {
            building.decoration_sprites.add(DungeonDecorResources.getRandomFlag());
            building.deco_positions.add(get_flag_position(0));
            building.deco_flipped.add(MathUtils.randomBoolean());
            building.deco_centerY.add(25f);
        } else {
            WallTorchObject torch = new WallTorchObject(building.chunk, building.object,
                    new Vector2(building.x + building.width/2/PPM, building.y + building.height/2/PPM + 1.2f));
            building.chunk.layerObjects.add(torch);
        }
    }

    public void square_decoration() {
        int quantity = MathUtils.random(1,2);
        switch(quantity) {
            case 1 -> {
                // Single Large Deco
                if(Math.random() > 0.75f) {
                    building.decoration_sprites.add(DungeonDecorResources.getWallDeco(true));
                    building.deco_positions.add(get_centered_position());
                } else {
                    building.decoration_sprites.add(DungeonDecorResources.getWallDeco(false));
                    if(MathUtils.randomBoolean()) {
                        building.deco_positions.add(get_flag_position(0));
                        WallTorchObject torch = new WallTorchObject(building.chunk, building.object, getTorchPosition(0));
                        building.chunk.layerObjects.add(torch);
                    } else {
                        building.deco_positions.add(get_flag_position(1));
                        WallTorchObject torch = new WallTorchObject(building.chunk, building.object, getTorchPosition(1));
                        building.chunk.layerObjects.add(torch);
                    }
                }
                building.deco_flipped.add(MathUtils.randomBoolean());
                building.deco_centerY.add(25f);
            }

            case 2 -> {
                building.decoration_sprites.add(DungeonDecorResources.getWallDeco(false));
                building.deco_positions.add(get_flag_position(0));
                building.deco_flipped.add(MathUtils.randomBoolean());
                building.deco_centerY.add(25f);
                building.decoration_sprites.add(DungeonDecorResources.getWallDeco(false));
                building.deco_positions.add(get_flag_position(1));
                building.deco_flipped.add(MathUtils.randomBoolean());
                building.deco_centerY.add(25f);
            }
        }
    }

    public Vector2 getTorchPosition(int position) {
        if(position == 0) {
            Vector2 decoPosition1 = new Vector2(building.x + building.width / 3 / PPM - 0.1f, building.y + building.height / 2 / PPM - 0.6f);
            Vector2 torchPos = new Vector2(decoPosition1.x + building.width / 3 / PPM + 0.1f, building.y + building.height / 2 / PPM - 0.4f);
            return torchPos;
        } else {
            return new Vector2(building.x + building.width/3/PPM - 0.1f, building.y + building.height/2/PPM - 0.4f);
        }
    }

    public Vector2 get_centered_position() {
        float x = building.x * PPM + 17;
        float y = building.y * PPM + 18;
        return new Vector2(x,y);
    }

    public Vector2 get_flag_position(int position) {
        float x = building.x * PPM;
        float y = building.y * PPM;

        switch(name) {
            case "Pillar" -> {
                y += 20;
                x += 11;
            }
            case "Building2" -> {
                y += 12;
                if(position == 0) x += 19;
                else x += 68;
            }
        }
        return new Vector2(x, y);
    }


    public int getQuantity() {
        int[] WEIGHTED_ROLLS;
        switch(name) {
            case "Building1_s", "Building1_g", "Building2_s", "Building2_g" -> WEIGHTED_ROLLS = new int[]{0, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 6, 7, 7, 7, 8,};
            case "PillarH_s", "PillarH_g" -> WEIGHTED_ROLLS = new int[]{0, 1, 1, 2, 2, 3};
            default -> WEIGHTED_ROLLS = new int[]{0};
        }

        return WEIGHTED_ROLLS[(int) (Math.random() * WEIGHTED_ROLLS.length)];
    }

    private Vector2 getHerbsPosition() {
        float min_x = building.x * PPM - 10;
        float max_x = building.x * PPM + building.width - 54;
        float y = building.y * PPM - 1;

        float x = MathUtils.random(min_x, max_x);
        return new Vector2(x, y);
    }

}
