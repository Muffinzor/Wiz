package wizardo.game.Monsters.MonsterArchetypes;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Screens.Battle.BattleScreen;

public abstract class MonsterMelee extends Monster {

    public float attackRange;
    public float hitboxRadius;
    public boolean canRush;
    public float rushDistance;


    public MonsterMelee(BattleScreen screen, Vector2 position) {
        super(screen, position);
    }

}
