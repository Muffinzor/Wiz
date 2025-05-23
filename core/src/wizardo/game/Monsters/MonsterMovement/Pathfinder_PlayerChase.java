package wizardo.game.Monsters.MonsterMovement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Pathfinder_PlayerChase implements Pathfinder {

    Monster monster;
    Body playerBody;

    public Pathfinder_PlayerChase(Monster monster) {
        this.monster = monster;
        playerBody = player.pawn.body;
    }

    @Override
    public Vector2 getDirection(boolean backwards) {
        if(backwards) {
            return moveBackwards();
        } else {
            return moveTowards();
        }
    }

    public Vector2 moveTowards() {
        Vector2 direction = new Vector2(player.pawn.getPosition().sub(monster.body.getPosition()));

        if(MonsterUtils.hasCompleteLoS_withPlayer(monster)) {
            return direction;
        } else {
            return avoidObstacle(direction, false);
        }
    }

    public Vector2 moveBackwards() {
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
        float rayLength = backwards ? 6 : direction.len(); // Shorter ray when moving backwards

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
