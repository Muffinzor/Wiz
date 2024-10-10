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
    boolean kill;
    float dimSpeed;


    public RoundLight(RayHandler rayHandler) {
        pointLight = new PointLight(rayHandler, 15, Color.WHITE, 0, 0, 0);
    }

    public void update(float delta) {

        if(kill) {
            alpha = alpha - dimSpeed * delta;
            if(alpha < 0) {
                alpha = 0;
            }
            pointLight.setColor(red, green, blue, alpha);
        }

        if(alpha <= 0) {
            pointLight.setActive(false);
            //screen.displayManager.lightManager.remove(this);
        }

    }

    public void setLight(float red, float green, float blue, float alpha, float radius, Vector2 position) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.radius = radius;

        pointLight.setColor(red, green, blue, alpha);
        pointLight.setDistance(radius);
        pointLight.setPosition(position.x * PPM, position.y * PPM);
        pointLight.setActive(true);
    }

    public void resetLight() {
        pointLight.setColor(0,0,0,0);
        pointLight.setDistance(0);
        pointLight.setActive(false);
    }

}
