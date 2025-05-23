package wizardo.game.Monsters;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;

import java.util.concurrent.atomic.AtomicBoolean;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class MonsterUtils {

    public enum MONSTER_STATE {
        SPAWNING,
        ADVANCING,
        STANDBY,
        ATTACKING,
        HOVER,
        CHARGING,
        FLEEING,
        DEAD
    }

    /**
     * Checks if monster has LoS with specific Vector
     */
    public static boolean hasCompleteLoS_withPosition(Monster monster, Vector2 position) {

        AtomicBoolean clearLOS = new AtomicBoolean(true);

        Vector2 origin = monster.body.getPosition();
        Vector2 target = new Vector2(position);
        Vector2 direction = position.sub(monster.body.getPosition());
        if(direction.isZero()) {
            direction.set(1,0);
        } else {
            direction.nor();
        }
        Vector2 perpendicular = new Vector2(-direction.y, direction.x).scl(monster.bodyRadius/PPM);

        Vector2 leftOrigin = origin.cpy().add(perpendicular);  // Offset to the left
        Vector2 rightOrigin = origin.cpy().sub(perpendicular); // Offset to the right

        world.rayCast((fixture, point, normal, fraction) -> {
            short obstacleCategory = 0x0010;
            short short_obstacleCategory = 0x0020;

            if (fixture != null &&
                    ((fixture.getFilterData().categoryBits & obstacleCategory) != 0 || (fixture.getFilterData().categoryBits & short_obstacleCategory) != 0)) {
                clearLOS.set(false);
                return 0; // Stop raycast
            }
            return 1; // Continue raycast
        }, leftOrigin, target);

        world.rayCast((fixture, point, normal, fraction) -> {
            short obstacleCategory = 0x0010;
            short short_obstacleCategory = 0x0020;

            if (fixture != null &&
                    ((fixture.getFilterData().categoryBits & obstacleCategory) != 0 || (fixture.getFilterData().categoryBits & short_obstacleCategory) != 0)) {
                clearLOS.set(false);
                return 0; // Stop raycast
            }
            return 1; // Continue raycast
        }, rightOrigin, target);

        return clearLOS.get();

    }

    /**
     * Checks if monster has LoS with player
     */
    public static boolean hasCompleteLoS_withPlayer(Monster monster) {

        AtomicBoolean clearLOS = new AtomicBoolean(true);

        Vector2 origin = monster.body.getPosition();
        Vector2 target = player.pawn.getPosition();
        Vector2 direction = player.pawn.getPosition().sub(monster.body.getPosition());
        if(direction.isZero()) {
            direction.set(1,0);
        } else {
            direction.nor();
        }
        Vector2 perpendicular = new Vector2(-direction.y, direction.x).scl(monster.bodyRadius/PPM);

        Vector2 leftOrigin = origin.cpy().add(perpendicular);  // Offset to the left
        Vector2 rightOrigin = origin.cpy().sub(perpendicular); // Offset to the right

        world.rayCast((fixture, point, normal, fraction) -> {
            short obstacleCategory = 0x0010;
            short short_obstacleCategory = 0x0020;

            if (fixture != null &&
                    ((fixture.getFilterData().categoryBits & obstacleCategory) != 0 || (fixture.getFilterData().categoryBits & short_obstacleCategory) != 0)) {
                clearLOS.set(false);
                return 0; // Stop raycast
            }
            return 1; // Continue raycast
        }, leftOrigin, target);

        world.rayCast((fixture, point, normal, fraction) -> {
            short obstacleCategory = 0x0010;
            short short_obstacleCategory = 0x0020;

            if (fixture != null &&
                    ((fixture.getFilterData().categoryBits & obstacleCategory) != 0 || (fixture.getFilterData().categoryBits & short_obstacleCategory) != 0)) {
                clearLOS.set(false);
                return 0; // Stop raycast
            }
            return 1; // Continue raycast
        }, rightOrigin, target);

        return clearLOS.get();

    }
}
