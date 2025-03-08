package wizardo.game.Maps.Forest.ForestBuildings;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import static wizardo.game.Maps.Forest.ForestBuildings.ForestBuilding_Decoration.Deco_Type.BUSH;
import static wizardo.game.Resources.DecorResources.ForestDecorResources.*;
import static wizardo.game.Utils.Constants.PPM;

public class ForestBuilding_Decoration {

    ForestBuilding building;
    String name;

    public enum Deco_Type {
        VINES,
        GRASS,
        BUSH
    }

    ArrayList<Deco_Type> types;

    public ForestBuilding_Decoration(ForestBuilding building) {
        this.building = building;
        this.name = building.name;

        this.types = new ArrayList<>();

        randomizeTypes();
        randomizeSprites();
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

    public void randomizeTypes() {
        for (int i = 0; i < getQuantity(); i++) {
            if(name.equals("PillarH_s") || name.equals("PillarH_g")) {
                if(Math.random() > 0.5) types.add(Deco_Type.GRASS);
                else types.add(BUSH);
            } else {
                int random = MathUtils.random(1, 3);
                switch (random) {
                    case 1 -> types.add(Deco_Type.VINES);
                    case 2 -> types.add(Deco_Type.BUSH);
                    case 3 -> types.add(Deco_Type.GRASS);
                }
            }
        }
    }

    public void randomizeSprites() {
        for (Deco_Type type : types) {
            switch (type) {
                case VINES -> {
                    building.decoration_sprites.add(vines[(int) (Math.random() * vines.length)]);
                    building.deco_positions.add(getVinesPosition());
                    building.deco_flipped.add(MathUtils.randomBoolean());
                    building.deco_centerY.add(25f);
                }
                case GRASS -> {
                    building.decoration_sprites.add(herbs[(int) (Math.random() * herbs.length)]);
                    building.deco_positions.add(getHerbsPosition());
                    building.deco_flipped.add(MathUtils.randomBoolean());
                    building.deco_centerY.add(0f);
                }
                case BUSH -> {
                    int bush_type = (int) (Math.random() * bushes.length);
                    building.decoration_sprites.add(bushes[bush_type]);
                    building.deco_positions.add(getBushPosition(bush_type));
                    building.deco_flipped.add(MathUtils.randomBoolean());
                    building.deco_centerY.add(160f);
                }
            }
        }
    }

    private Vector2 getHerbsPosition() {
        float min_x = building.x * PPM - 10;
        float max_x = building.x * PPM + building.width - 54;
        float y = building.y * PPM - 1;

        float x = MathUtils.random(min_x, max_x);
        return new Vector2(x, y);
    }

    public Vector2 getVinesPosition() {
        float min_x = building.x * PPM;
        float max_x = building.x * PPM + building.width;
        float y = building.y * PPM;

        if(name.equals("Building1_s") || name.equals("Building1_g")) {
            y += 4;
            min_x += 8;
            max_x -= 48;
        }

        if(name.equals("Building2_s") || name.equals("Building2_g")) {
            y += 36;
            min_x += 8;
            max_x -= 48;
        }

        float x = MathUtils.random(min_x, max_x);
        return new Vector2(x, y);
    }

    public Vector2 getBushPosition(int type) {
        float x = 0;
        float y = 0;
        switch(type) {
            case 0 -> x = MathUtils.random(0, building.width - 72);
            case 1 -> x = MathUtils.random(0, building.width - 50);
            case 2 -> x = MathUtils.random(0, building.width - 40);
        }

        if(name.equals("Building1_s") || name.equals("Building1_g")) {
            if(MathUtils.randomBoolean()) {
                y = MathUtils.random(40,52);
            } else {
                y = MathUtils.random(150,156);
            }
        }
        if(name.equals("Building2_s") || name.equals("Building2_g")) {
            if(MathUtils.randomBoolean()) {
                y = MathUtils.random(70,82);
            } else {
                y = MathUtils.random(172,178);
            }
        }
        if(name.equals("PillarH_s") || name.equals("PillarH_g")) {
            if(MathUtils.randomBoolean()) {
                y = MathUtils.random(58,60);
            } else {
                y = MathUtils.random(100,108);
            }
        }
        return new Vector2(building.x * PPM + x, building.y * PPM + y);


    }


}
