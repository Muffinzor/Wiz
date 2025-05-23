package wizardo.game.Monsters.MonsterArchetypes;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;

public abstract class MonsterMelee extends Monster {

    public float attackRange;
    public float rushDistance;
    public float rushDuration = 0.5f;
    public float rushRatio = 1.8f;

    public MonsterMelee(BattleScreen screen, Vector2 position, MonsterSpawner spawner, Vector2 patrolDirection) {
        super(screen, position, spawner, patrolDirection);
    }

}
