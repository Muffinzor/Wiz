package wizardo.game.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Utils.BodyFactory;
import wizardo.game.Wizardo;

import static wizardo.game.Display.DisplayUtils.getLight;
import static wizardo.game.Display.DisplayUtils.getSprite;
import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Pawn {

    public BaseScreen screen;

    public Body body;
    private float stateTime;

    public Vector2 movementVector;
    public Vector2 targetVector;


    public RoundLight light;

    public Pawn(BaseScreen screen) {
        this.screen = screen;
        stateTime = 0;
        movementVector = new Vector2(0,0);
        targetVector = new Vector2(1,0);


    }

    public void update(float delta) {
        stateTime += delta;
        drawSprite();

        movement();
        adjustLight();
    }

    public void drawSprite() {
        Sprite frame = getSprite(screen);
        frame.set(PlayerResources.playerWalk.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM + 5);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
    }

    public void createPawn(Vector2 position) {
        body = BodyFactory.playerBody(position);
        createLight();
    }

    public void moveX(float value) {
        movementVector.x = value;
    }
    public void moveY(float value) {
        movementVector.y = -value;
    }

    public void stop() {
        if(body != null) {
            movementVector.x = 0;
            movementVector.y = 0;
            body.setLinearVelocity(movementVector);
        }
    }

    public void aimX(float value) {
        targetVector.x = value;
    }
    public void aimY(float value) {
        targetVector.y = value;
    }

    public void movement() {

        if(!movementVector.isZero()) {
            movementVector.nor();
        }
        if(player != null) {
            body.setLinearVelocity(movementVector.cpy().scl(player.runSpeed));
        }
    }

    public void createLight() {
        light = getLight(screen);
        light.setLight(0,0,0,0.8f,120, body.getPosition());

    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public Vector2 getPosition() {
        return new Vector2(body.getPosition());
    }

    public float getBodyX() {
        return body.getPosition().x;
    }

    public float getBodyY() {
        return body.getPosition().y;
    }

    public void dispose() {
        if(body != null) {
            world.destroyBody(body);
        }
        body = null;
    }

}
