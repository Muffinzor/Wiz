package wizardo.game.Lighting;

import box2dLight.RayHandler;
import wizardo.game.Screens.BaseScreen;

import java.util.ArrayDeque;

public class LightPool {

    private final ArrayDeque<RoundLight> array;

    public LightPool() {
        array = new ArrayDeque<>();
    }

    public RoundLight getLight(RayHandler rayHandler) {
        if(array.isEmpty()) {
            return new RoundLight(rayHandler);
        } else {
            return array.removeLast();
        }
    }

    public void poolLight(RoundLight light) {
        light.resetLight();
        array.add(light);
    }
}
