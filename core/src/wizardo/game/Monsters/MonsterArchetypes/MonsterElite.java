package wizardo.game.Monsters.MonsterArchetypes;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner_Dungeon;

public abstract class MonsterElite extends Monster {
    public MonsterElite(BattleScreen screen, Vector2 position, MonsterSpawner_Dungeon spawner) {
        super(screen, position, spawner);
    }
}
