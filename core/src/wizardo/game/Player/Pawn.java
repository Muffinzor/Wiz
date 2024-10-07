package wizardo.game.Player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Display.SpriteRenderer.toRender;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class Pawn {

    public Body body;
    private float stateTime;

    private Vector2 movementVector;

    public Pawn() {
        stateTime = 0;
        movementVector = new Vector2();
    }

    public void update(float delta) {
        stateTime += delta;
        drawSprite();


        movement();
    }

    public void drawSprite() {
        Sprite frame = PlayerResources.playerWalk.getKeyFrame(stateTime, true);
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        toRender.add(frame);
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

    public void movement() {

        if(!movementVector.isZero()) {
            movementVector.nor();
        }
        body.setLinearVelocity(movementVector.cpy().scl(5));

    }




}
