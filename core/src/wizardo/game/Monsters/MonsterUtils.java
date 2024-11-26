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

    public static boolean hasCompleteLoS(Monster monster) {

        AtomicBoolean clearLOS = new AtomicBoolean(true);

        Vector2 origin = new Vector2(monster.body.getPosition());
        Vector2 target = player.pawn.getPosition();
        Vector2 direction = player.pawn.getPosition().cpy().sub(monster.body.getPosition()).nor();
        Vector2 perpendicular = new Vector2(-direction.y, direction.x).scl(monster.bodyRadius/PPM);

        Vector2 leftOrigin = origin.cpy().add(perpendicular);  // Offset to the left
        Vector2 rightOrigin = origin.cpy().sub(perpendicular); // Offset to the right

        world.rayCast((fixture, point, normal, fraction) -> {
            short obstacleCategory = 0x0010;

            if ((fixture.getFilterData().categoryBits & obstacleCategory) != 0) {
                clearLOS.set(false);
                return 0; // Stop raycast
            }
            return 1; // Continue raycast
        }, leftOrigin, target);

        world.rayCast((fixture, point, normal, fraction) -> {
            short obstacleCategory = 0x0010;

            if ((fixture.getFilterData().categoryBits & obstacleCategory) != 0) {
                clearLOS.set(false);
                return 0; // Stop raycast
            }
            return 1; // Continue raycast
        }, rightOrigin, target);

        return clearLOS.get();

    }
}
