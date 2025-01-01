package wizardo.game.Monsters.MonsterArchetypes;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;

public abstract class MonsterMelee extends Monster {

    public float attackRange;
    public float hitboxRadius;
    public boolean canRush;
    public float rushDistance;


    public MonsterMelee(BattleScreen screen, Vector2 position, MonsterSpawner spawner) {
        super(screen, position, spawner);
    }

}
