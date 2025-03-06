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
            System.out.println("OBSTACLE DETECTED");
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
        float rayLength = backwards ? 6 : direction.len(); // Shorter ray when moving backwards

        direction.nor();

        // Define angles for multiple rays
        float[] rayAngles = {-12, -6, 0, 6, 12}; // Wider spread to detect obstacles

        for (float angle : rayAngles) {
            Vector2 rayDir = new Vector2(direction).rotateDeg(angle);

            // Perform ray cast
            world.rayCast((fixture, point, normal, fraction) -> {
                Body body = fixture.getBody();

                if (body.getUserData() != null && (body.getUserData().equals("Obstacle") || body.getUserData().equals("Decor"))) {
                    Vector2 obstacleCenter = body.getPosition();
                    Vector2 obstacleToMonster = monster.body.getPosition().cpy().sub(obstacleCenter);

                    if (obstacleToMonster.isZero()) {
                        obstacleToMonster.set(0, 1);
                    }

                    // Determine avoidance direction
                    Vector2 avoidForce;
                    if (obstacleToMonster.crs(rayDir) > 0) {
                        avoidForce = new Vector2(-normal.y, normal.x); // Steer right
                    } else {
                        avoidForce = new Vector2(normal.y, -normal.x); // Steer left
                    }

                    // Scale avoidance force based on proximity to the whole obstacle, not just a fixture
                    float dstToObstacle = obstacleCenter.dst(monster.body.getPosition());
                    desiredDirection.add(avoidForce.scl(200 / (dstToObstacle * dstToObstacle + 1))); // Adjust scaling

                    return 0; // Stop checking further fixtures on this body
                }
                return -1;
            }, monster.body.getPosition(), monster.body.getPosition().cpy().add(rayDir.scl(rayLength)));
        }

        return desiredDirection.nor(); // Normalize final movement direction
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

    // OLD AVOID OBSTACLE, DOES NOT WORK WELL WITH ELLIPTICAL OBJECTS

    /**
    private Vector2 avoidObstacle(Vector2 direction, boolean backwards) {
          Vector2 desiredDirection = new Vector2(direction);
          float rayLength = backwards ? 6 : direction.len(); // Shorter ray when moving backwards

          direction.nor();

          // Define angles for multiple rays
          float[] rayAngles = {-12, -6, 0, 6, 12}; // Wider spread to detect obstacles

          for (float angle : rayAngles) {
              Vector2 rayDir = new Vector2(direction).rotateDeg(angle);

              // Perform ray cast
              world.rayCast((fixture, point, normal, fraction) -> {
                  Body body = fixture.getBody();

               if (body.getUserData() != null && (body.getUserData().equals("Obstacle") || body.getUserData().equals("Decor"))) {
                    Vector2 obstacleCenter = body.getPosition();
                      Vector2 obstacleToMonster = monster.body.getPosition().cpy().sub(obstacleCenter);

                      if (obstacleToMonster.isZero()) {
                          obstacleToMonster.set(0, 1);
                      }

                      // Determine avoidance direction
                      Vector2 avoidForce;
                      if (obstacleToMonster.crs(rayDir) > 0) {
                          avoidForce = new Vector2(-normal.y, normal.x); // Steer right
                      } else {
                          avoidForce = new Vector2(normal.y, -normal.x); // Steer left
                      }

                      // Scale avoidance force based on proximity to the whole obstacle, not just a fixture
                      float dstToObstacle = obstacleCenter.dst(monster.body.getPosition());
                      desiredDirection.add(avoidForce.scl(200 / (dstToObstacle * dstToObstacle + 1))); // Adjust scaling

                      return 0; // Stop checking further fixtures on this body
                 }
                 return -1;
              }, monster.body.getPosition(), monster.body.getPosition().cpy().add(rayDir.scl(rayLength)));
          }

          return desiredDirection.nor(); // Normalize final movement direction
      }
    */

}
