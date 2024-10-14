package wizardo.game.Lighting;

import box2dLight.RayHandler;
import wizardo.game.Display.SpritePool;
import wizardo.game.Screens.BaseScreen;

import java.util.ArrayList;

public class LightManager {

    public LightPool pool;
    public BaseScreen screen;

    public ArrayList<RoundLight> roundLights;
    public RayHandler rayHandler;

    public LightManager(BaseScreen screen) {

        this.screen = screen;
        rayHandler = screen.rayHandler;
        pool = new LightPool(screen);
        roundLights = new ArrayList<>();

    }

    public void update(float delta) {

        for(RoundLight roundLight : roundLights) {
            roundLight.update(delta);
        }

    }
}
