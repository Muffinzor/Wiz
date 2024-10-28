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

    Vector2 lastSidestepDirection;

    public Pathfinder(Monster monster) {
        this.monster = monster;
        playerBody = player.pawn.body;
        currentPosition = new Vector2();
        lastSidestepDirection = new Vector2();
    }

    public void update() {
        currentPosition.set(monster.body.getPosition());
        this.targetPosition = new Vector2(playerBody.getPosition());
        Vector2 targetDirection = targetPosition.cpy().sub(currentPosition).nor();
        detectObstacles(targetDirection);
    }

    private void detectObstacles(Vector2 direction) {
        Vector2 desiredVelocity = new Vector2();

        float rayLength = 500;
        Vector2 rayEnd = currentPosition.cpy().add(direction.scl(rayLength));
        desiredVelocity.set(targetPosition.cpy().sub(currentPosition).nor().scl(monster.speed));

        world.rayCast((fixture, point, normal, fraction) -> {
            if (fixture.getBody().getUserData() != null && fixture.getBody().getUserData().equals("Obstacle")) {
                Vector2 avoidForce;


                Vector2 obstacleToMonster = currentPosition.cpy().sub(fixture.getBody().getPosition());
                Vector2 obstacleToPlayer = targetPosition.cpy().sub(fixture.getBody().getPosition());

                // Cross product to determine relative position of player to obstacle
                float cross = obstacleToMonster.crs(obstacleToPlayer);

                // If the cross product is positive, sidestep to the right, otherwise to the left
                if (cross > 0) {
                    // Sidestep right
                    avoidForce = new Vector2(-normal.y, normal.x); // Sidestep right
                } else {
                    // Sidestep left
                    avoidForce = new Vector2(normal.y, -normal.x); // Sidestep left
                }

                // Avoidance force, scale it by distance to the obstacle
                float dst = fixture.getBody().getPosition().dst(monster.body.getPosition());
                desiredVelocity.add(avoidForce.scl((150 / dst / dst)));
            }
            return fraction;
        }, currentPosition, rayEnd);

        float trueSpeed = monster.speed;
        if(monster.slowedTimer > 0) {
            trueSpeed = monster.speed * monster.slowRatio;
        }
        monster.body.setLinearVelocity(desiredVelocity.nor().scl(trueSpeed));
    }


}
