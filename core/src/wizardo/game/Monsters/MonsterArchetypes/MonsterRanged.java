package wizardo.game.Monsters.MonsterArchetypes;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterActions.SmallProjectile.SmallLaser_Projectile;
import wizardo.game.Screens.Battle.BattleScreen;

public abstract class MonsterRanged extends Monster {

    public MonsterSpell projectile;

    public MonsterRanged(BattleScreen screen, Vector2 position) {
        super(screen, position);
    }

    @Override
    public void launchAttack() {
        MonsterSpell proj = projectile.clone();
        screen.monsterSpellManager.toAdd(proj);
    }

}
