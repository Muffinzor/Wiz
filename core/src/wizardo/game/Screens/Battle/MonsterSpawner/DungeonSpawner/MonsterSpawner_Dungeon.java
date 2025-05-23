package wizardo.game.Screens.Battle.MonsterSpawner.DungeonSpawner;

import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;

public class MonsterSpawner_Dungeon extends MonsterSpawner {

    public MonsterSpawner_Dungeon(BattleScreen screen) {
        super(screen);
        phase = new DungeonPhase_4(this);
    }
}
