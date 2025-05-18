package wizardo.game.Screens.Battle.MonsterSpawner.ForestSpawner;

import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;
import wizardo.game.Utils.BodyPool;

public class MonsterSpawner_Forest extends MonsterSpawner {

    public MonsterSpawner_Forest(BattleScreen screen) {
        super(screen);
        bodyPool = new BodyPool();
    }

    public void update(float delta) {
        super.update(delta);
    }

}
