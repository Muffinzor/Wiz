package wizardo.game.Monsters.MonsterMovement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Pathfinder {

    Monster monster;
    Body playerBody;

    public Pathfinder(Monster monster) {
        this.monster = monster;
        playerBody = player.pawn.body;
    }

    public Vector2 towardsPlayer() {

        Vector2 direction = new Vector2(player.pawn.getPosition().sub(monster.body.getPosition()));

        if(MonsterUtils.hasCompleteLoS(monster)) {
            return direction;
        } else {
            return avoidObstacle(direction, false);
        }

    }

    public Vector2 awayFromPlayer() {

        Vector2 backwards = player.pawn.getPosition().cpy().sub(monster.body.getPosition());
        backwards.scl(-1);

        if(SpellUtils.hasLineOfSight(monster.body.getPosition(), backwards)) {
            return backwards;
        } else {
            return avoidObstacle(backwards, true);
        }

    }

    private Vector2 avoidObstacle(Vector2 direction, boolean backwards) {

        Vector2 desiredDirection = new Vector2(direction);
        float dst = desiredDirection.len();
        if(backwards) {
            dst = 6;
        }
        direction.nor();

        // Handle obstacle avoidance
        world.rayCast((fixture, point, normal, fraction) -> {
            if (fixture.getBody().getUserData() != null && fixture.getBody().getUserData().equals("Obstacle")) {
                Vector2 avoidForce;

                Vector2 obstacleToMonster = monster.body.getPosition().cpy().sub(fixture.getBody().getPosition());

                if (obstacleToMonster.crs(direction) > 0) {
                    avoidForce = new Vector2(-normal.y, normal.x); // Steer right
                } else {
                    avoidForce = new Vector2(normal.y, -normal.x); // Steer left
                }

                float dstToObstacle = fixture.getBody().getPosition().dst(monster.body.getPosition());
                desiredDirection.add(avoidForce.scl((300 / (dstToObstacle * dstToObstacle))));
            }
            return fraction;
        }, monster.body.getPosition(), monster.body.getPosition().cpy().add(direction.scl(dst))); // Ray length: dst to player

        return desiredDirection;
    }

    /** returns an obstacle fixture if it
     *  encounters one */
    private Fixture findObstacleAhead(Vector2 rayOrigin, Vector2 rayDirection) {
        final Fixture[] hitFixture = {null};
        final Vector2 playerPosition = player.pawn.body.getPosition(); // Player position
        final float dst = Math.min(300, monster.body.getPosition().dst(playerPosition) * PPM);

        world.rayCast((fixture, point, normal, fraction) -> {

            if (fixture.getBody().getUserData() != null && fixture.getBody().getUserData().equals("Obstacle")) {
                hitFixture[0] = fixture;
                return 0;
            }

            return -1;
        }, rayOrigin, rayOrigin.cpy().add(rayDirection.scl(dst))); // Ray length: 500

        // Return the fixture that was hit (either player or obstacle)
        return hitFixture[0];
    }

}
