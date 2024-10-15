package wizardo.game.Monsters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import static wizardo.game.Wizardo.world;

public class Pathfinder {

    Monster monster;
    Vector2 currentPosition;
    Vector2 targetPosition;
    Body playerBody;

    Vector2 lastSidestepDirection;

    public Pathfinder(Monster monster) {
        this.monster = monster;
        playerBody = monster.screen.playerPawn.body;
        currentPosition = new Vector2();
        lastSidestepDirection = new Vector2();
    }

    public void update() {
        currentPosition.set(monster.body.getPosition());
        this.targetPosition = new Vector2(playerBody.getPosition());
        Vector2 targetDirection = targetPosition.cpy().sub(currentPosition).nor();
        raycast(targetDirection);
    }

    private void raycast(Vector2 direction) {
        Vector2 desiredVelocity = new Vector2();

        float rayLength = 500;
        Vector2 rayEnd = currentPosition.cpy().add(direction.scl(rayLength));
        desiredVelocity.set(targetPosition.cpy().sub(currentPosition).nor().scl(monster.speed));


        world.rayCast((fixture, point, normal, fraction) -> {

            if (fixture.getBody().getUserData() != null && fixture.getBody().getUserData().equals("Obstacle")) {

                Vector2 avoidForce;
                if(monster.leftie) {
                    avoidForce = new Vector2(normal.y, -normal.x);
                } else {
                    avoidForce = new Vector2(-normal.y, normal.x);
                }

                float dst = fixture.getBody().getPosition().dst(monster.body.getPosition());
                desiredVelocity.add(avoidForce.scl( (150/dst/dst)));
            }
            return fraction;
        }, currentPosition, rayEnd);

        monster.body.setLinearVelocity(desiredVelocity.nor().scl(monster.speed));
    }


}
