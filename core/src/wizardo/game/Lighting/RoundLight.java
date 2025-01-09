package wizardo.game.Lighting;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Screens.BaseScreen;

import static wizardo.game.Utils.Constants.PPM;

public class RoundLight {

    public float red;
    public float green;
    public float blue;
    public float alpha;
    public float radius;

    public PointLight pointLight;
    float dimSpeed;

    RayHandler rayHandler;
    BaseScreen screen;

    public RoundLight(BaseScreen screen) {
        this.screen = screen;
        rayHandler = screen.rayHandler;
        pointLight = new PointLight(rayHandler, 20, Color.WHITE, 0, 0, 0);
    }

    public void update(float delta) {

        if(dimSpeed > 0 && delta > 0) {
            alpha = alpha - dimSpeed;
            if(alpha < 0) {
                alpha = 0;
            }
            pointLight.setColor(red, green, blue, alpha);
        }

        if(alpha <= 0) {
            kill();
        }

    }

    public void setLight(float red, float green, float blue, float alpha, float radius, Vector2 position) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.radius = radius;
        dimSpeed = 0;

        pointLight.setColor(red, green, blue, alpha);
        pointLight.setDistance(radius);
        pointLight.setPosition(position.x * PPM, position.y * PPM);
        pointLight.setActive(true);
    }

    public void resetLight() {
        pointLight.setColor(0,0,0,0);
        alpha = 0;
        pointLight.setDistance(0);
        pointLight.setActive(false);
    }

    public void toLightManager() {
        screen.lightManager.addLight(this);
    }

    public void dimKill(float dimSpeed) {
        this.dimSpeed = dimSpeed;
    }

    public void kill() {
        screen.lightManager.pool.poolLight(this);
    }

    public void dispose() {
        pointLight.remove();
    }

}
