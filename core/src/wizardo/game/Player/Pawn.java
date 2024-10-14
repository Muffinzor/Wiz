package wizardo.game.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Display.DisplayUtils.getLight;
import static wizardo.game.Display.DisplayUtils.getSprite;
import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class Pawn {

    public BaseScreen screen;

    public Body body;
    private float stateTime;

    private Vector2 movementVector;
    public Vector2 targetVector;
    public Sprite controllerTargetSprite;

    public RoundLight light;

    public Pawn(BaseScreen screen) {
        this.screen = screen;
        stateTime = 0;
        movementVector = new Vector2(0,0);
        targetVector = new Vector2(1,0);
        controllerTargetSprite = new Sprite(new Texture("Cursors/Controller_Cursor.png"));

    }

    public void update(float delta) {
        stateTime += delta;
        drawSprite();
        drawControllerTarget();

        movement();
        adjustLight();
    }

    public void drawSprite() {
        Sprite frame = getSprite(screen);
        frame.set(PlayerResources.playerWalk.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM + 5);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
    }


    public void drawControllerTarget() {
        if(controllerActive) {
            Vector2 playerPosition = body.getPosition();
            if(targetVector.len() > 0.1f) {
                targetVector.nor();
            }
            float aimRadius = 5;
            Vector2 aimPosition = new Vector2(targetVector).scl(aimRadius);
            Vector2 cursorPosition = playerPosition.add(aimPosition);

            Sprite frame = getSprite(screen);
            frame.set(controllerTargetSprite);
            frame.setCenter(cursorPosition.x * PPM, cursorPosition.y * PPM);
            float angle = targetVector.angleDeg();
            frame.setRotation(angle - 45);
            screen.displayManager.spriteRenderer.ui_sprites.add(frame);
        }
    }


    public void createPawn(Vector2 position) {
        body = BodyFactory.playerBody(world, position);
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
        body.setLinearVelocity(movementVector.cpy().scl(5));
    }

    public void createLight() {
        light = getLight(screen);
        light.setLight(0,0,0,0.8f,120, body.getPosition());

    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public float getBodyX() {
        return body.getPosition().x;
    }

    public float getBodyY() {
        return body.getPosition().y;
    }

    public void dispose() {
        world.destroyBody(body);
        body = null;
    }

}
