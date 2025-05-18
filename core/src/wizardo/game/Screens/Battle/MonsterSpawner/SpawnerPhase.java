package wizardo.game.Screens.Battle.MonsterSpawner;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;

public interface SpawnerPhase {
    void update(float delta, MonsterSpawner spawner);
}
