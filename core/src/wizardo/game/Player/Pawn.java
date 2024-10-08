package wizardo.game.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Display.SpriteRenderer.toRender;
import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class Pawn {

    public Body body;
    private float stateTime;

    private Vector2 movementVector;
    public Vector2 targetVector;
    public Sprite controllerTargetSprite;

    public Pawn() {
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
    }

    public void drawSprite() {
        Sprite frame = PlayerResources.playerWalk.getKeyFrame(stateTime, true);
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        toRender.add(frame);
    }

    public void drawControllerTarget() {
        if(controllerActive) {
            Vector2 playerPosition = body.getPosition();
            if(targetVector.len() > 0.1f) {
                targetVector.nor();
            }
            float aimRadius = 7;
            Vector2 aimPosition = new Vector2(targetVector).scl(aimRadius);
            Vector2 cursorPosition = playerPosition.add(aimPosition);

            Sprite frame = controllerTargetSprite;
            frame.setCenter(cursorPosition.x * PPM, cursorPosition.y * PPM);
            float angle = targetVector.angleDeg();
            frame.setRotation(angle - 45);
            toRender.add(frame);
        }
    }


    public void createPawn(Vector2 position) {
        body = BodyFactory.playerBody(world, position);
    }

    public void moveX(float value) {
        movementVector.x = value;
    }
    public void moveY(float value) {
        movementVector.y = -value;
    }

    public void stop() {
        movementVector.x = 0;
        movementVector.y = 0;
        body.setLinearVelocity(movementVector);
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




}
