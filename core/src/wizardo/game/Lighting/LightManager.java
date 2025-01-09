package wizardo.game.Lighting;

import box2dLight.RayHandler;
import wizardo.game.Screens.BaseScreen;

import java.util.ArrayList;

public class LightManager {

    public LightPool pool;
    public BaseScreen screen;

    public ArrayList<RoundLight> activeLights;
    public RayHandler rayHandler;

    public LightManager(BaseScreen screen) {

        this.screen = screen;
        pool = new LightPool(screen);
        activeLights = new ArrayList<>();

    }

    public void update(float delta) {

        for(RoundLight roundLight : activeLights) {
            roundLight.update(delta);
        }

        activeLights.removeIf(roundLight -> roundLight.alpha <= 0);

        if(pool.getPoolSize() > 400) {
            pool.trimPoolSize();
        }

    }

    public void addLight(RoundLight light) {
        activeLights.add(light);
    }

    public void removeLight(RoundLight light) {
        activeLights.remove(light);
    }
}
