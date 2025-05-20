package wizardo.game.Monsters.MonsterArchetypes;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;

public abstract class MonsterFlying extends Monster {

    public float attackRange;
    public float rushDistance;

    public MonsterFlying(BattleScreen screen, Vector2 position, MonsterSpawner spawner) {
        super(screen, position, spawner);
    }

}
