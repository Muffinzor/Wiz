package wizardo.game.Monsters.MonsterArchetypes;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;

public abstract class MonsterRanged extends Monster {

    public MonsterSpell projectile;

    public MonsterRanged(BattleScreen screen, Vector2 position, MonsterSpawner spawner, Vector2 patrolDirection) {
        super(screen, position, spawner, patrolDirection);
    }

    @Override
    public void launchAttack() {
        MonsterSpell proj = projectile.clone();
        screen.monsterSpellManager.toAdd(proj);
    }

}
