package wizardo.game.Monsters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Pathfinder {
    Monster monster;
    Vector2 currentPosition;
    Vector2 targetPosition;
    Body playerBody;

    // Push-back handling
    Vector2 pushBackForce = new Vector2();
    float pushBackTimer = 0; // Time remaining for push-back effect
    float pushDecayRate = 0;

    int frameCounter = 0;

    public Pathfinder(Monster monster) {
        this.monster = monster;
        playerBody = player.pawn.body;
        currentPosition = new Vector2();
    }

    public void update(float delta) {

        currentPosition.set(monster.body.getPosition());
        targetPosition = new Vector2(playerBody.getPosition());

        frameCounter++;
        if(frameCounter >= 10) {
            monster.body.setLinearVelocity(getMovementVector());
            frameCounter = 0;
        }

        if (pushBackTimer > 0) {
            pushBackTimer -= delta;
            if(delta > 0) {
                monster.body.setLinearVelocity(pushBackForce);
                pushBackForce.scl(pushDecayRate);
            }
        } else {
            pushBackForce.setZero();
        }


        if(delta == 0) {
            monster.body.setLinearVelocity(0,0);
        }

    }

    public void applyPush(Vector2 pushDirection, float strength, float duration, float decayRate) {
        pushBackForce.set(pushDirection.nor().scl(strength));
        pushBackTimer = duration;
        pushDecayRate = decayRate;
    }

    private Vector2 getMovementVector() {

        Vector2 direction = targetPosition.cpy().sub(currentPosition);
        Vector2 desiredVelocity = targetPosition.cpy().sub(currentPosition).nor().scl(monster.speed);

        if(monster.freezeTimer > 0) {
            desiredVelocity.set(0,0);
        }

        // Handle obstacle avoidance
        world.rayCast((fixture, point, normal, fraction) -> {
            if (fixture.getBody().getUserData() != null && fixture.getBody().getUserData().equals("Obstacle")) {
                Vector2 avoidForce;

                Vector2 obstacleToMonster = currentPosition.cpy().sub(fixture.getBody().getPosition());
                Vector2 obstacleToPlayer = targetPosition.cpy().sub(fixture.getBody().getPosition());

                float cross = obstacleToMonster.crs(obstacleToPlayer);
                if (cross > 0) {
                    avoidForce = new Vector2(-normal.y, normal.x);
                } else {
                    avoidForce = new Vector2(normal.y, -normal.x);
                }

                float dst = fixture.getBody().getPosition().dst(monster.body.getPosition());
                desiredVelocity.add(avoidForce.scl((150 / (dst * dst))));
            }
            return fraction;
        }, currentPosition, currentPosition.cpy().add(direction.scl(500))); // Ray length: 500

        // Combine push-back and desired velocity

        float trueSpeed = monster.speed;
        if(monster.slowedTimer > 0) {
            trueSpeed *= monster.slowRatio;
        }

        return desiredVelocity.nor().scl(trueSpeed);
    }
}
