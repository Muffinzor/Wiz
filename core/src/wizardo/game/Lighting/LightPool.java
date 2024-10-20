package wizardo.game.Lighting;

import wizardo.game.Screens.BaseScreen;

import java.util.ArrayDeque;

public class LightPool {

    protected final ArrayDeque<RoundLight> array;
    BaseScreen screen;

    public LightPool(BaseScreen screen) {
        this.screen = screen;
        array = new ArrayDeque<>();
    }

    public RoundLight getLight() {
        if(array.isEmpty()) {
            return new RoundLight(screen);
        } else {
            return array.removeLast();
        }
    }

    public void poolLight(RoundLight light) {
        light.resetLight();
        array.add(light);
    }
}
