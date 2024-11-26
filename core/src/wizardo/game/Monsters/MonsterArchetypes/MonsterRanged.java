package wizardo.game.Monsters.MonsterArchetypes;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterSpells.MonsterSpell;
import wizardo.game.Screens.Battle.BattleScreen;

public abstract class MonsterRanged extends Monster {

    public MonsterSpell projectile;

    public MonsterRanged(BattleScreen screen, Vector2 position) {
        super(screen, position);
    }

}
