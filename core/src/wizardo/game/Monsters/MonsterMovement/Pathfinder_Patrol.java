package wizardo.game.Monsters.MonsterMovement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterUtils;

import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Pathfinder_Patrol implements Pathfinder {

    Monster monster;
    Vector2 direction;

    public Pathfinder_Patrol(Monster monster, Vector2 direction) {
        this.monster = monster;
        this.direction = new Vector2(direction);
    }

    @Override
    public Vector2 getDirection(boolean backwards) {
        float dst = player.pawn.getPosition().dst(monster.body.getPosition());
        if(dst < 7) {
            monster.movementManager.pathfinder = new Pathfinder_PlayerChase(monster);
            monster.patrolling = false;
            return monster.movementManager.pathfinder.getDirection(false);
        }
        Vector2 movement_direction = new Vector2(direction);
        if(MonsterUtils.hasCompleteLoS_withPosition(monster, movement_direction)) {
            return direction;
        } else {
            return avoidObstacle(movement_direction);
        }
    }

    private Vector2 avoidObstacle(Vector2 direction) {
        Vector2 desiredDirection = new Vector2(direction);
        float rayLength = 3;

        direction.nor();

        // multiple rays
        float[] rayAngles = {-12, -6, 0, 6, 12}; // Wider spread to detect obstacles

        for (float angle : rayAngles) {
            Vector2 rayDir = new Vector2(direction).rotateDeg(angle);

            world.rayCast((fixture, point, normal, fraction) -> {
                Body body = fixture.getBody();

                if (body.getUserData() != null && (body.getUserData().equals("Obstacle") || body.getUserData().equals("Decor"))) {
                    Vector2 obstacleCenter = body.getPosition();
                    Vector2 obstacleToMonster = monster.body.getPosition().cpy().sub(obstacleCenter);

                    if (obstacleToMonster.isZero()) {
                        obstacleToMonster.set(0, 1);
                    }

                    Vector2 avoidForce;
                    if (obstacleToMonster.crs(rayDir) > 0) {
                        avoidForce = new Vector2(-normal.y, normal.x); // Steer right
                    } else {
                        avoidForce = new Vector2(normal.y, -normal.x); // Steer left
                    }

                    float dstToObstacle = obstacleCenter.dst(monster.body.getPosition());
                    desiredDirection.add(avoidForce.scl(200 / (dstToObstacle * dstToObstacle + 1)));

                    return 0;
                }
                return -1;
            }, monster.body.getPosition(), monster.body.getPosition().cpy().add(rayDir.scl(rayLength)));
        }

        return desiredDirection.nor();
    }
}
