package wizardo.game.Screens.Battle.MonsterSpawner;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class SpawnerUtils {

    public static Vector2 getRandomRangeSpawnVector() {
        return SpellUtils.getClearRandomPositionRing(player.pawn.getPosition(), 8, 24);
    }
}
