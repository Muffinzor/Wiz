package wizardo.game.Monsters.MonsterArchetypes;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterProjectiles.MonsterProjectile;
import wizardo.game.Screens.Battle.BattleScreen;

public abstract class MonsterRanged extends Monster {

    public MonsterProjectile projectile;

    public MonsterRanged(BattleScreen screen, Vector2 position) {
        super(screen, position);
    }

}
