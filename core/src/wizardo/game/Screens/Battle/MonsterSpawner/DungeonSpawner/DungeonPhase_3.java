package wizardo.game.Screens.Battle.MonsterSpawner.DungeonSpawner;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.DungeonMonsters.GreenSludge;
import wizardo.game.Monsters.DungeonMonsters.MawDemon.MawDemon;
import wizardo.game.Monsters.DungeonMonsters.Skeleton;
import wizardo.game.Monsters.DungeonMonsters.SkeletonGiant;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;
import wizardo.game.Screens.Battle.MonsterSpawner.SpawnerPhase;
import wizardo.game.Screens.Battle.MonsterSpawner.SpawnerUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Wizardo.player;

public class DungeonPhase_3 implements SpawnerPhase {

    float stateTime = 0;
    float sludgeAheadTimer = 0;
    float sludgeAroundTimer = 0;
    MonsterSpawner spawner;

    public DungeonPhase_3(MonsterSpawner spawner) {
        this.spawner = spawner;
    }

    @Override
    public void update(float delta, MonsterSpawner spawner) {
        stateTime += delta;
        sludgeAheadTimer += delta;
        sludgeAroundTimer += delta;
        spawnSludgesAhead();
        spawnSludgesAround();
    }

    public void spawnSludgesAhead() {
        if(sludgeAheadTimer > 1.2f) {
            sludgeAheadTimer = 0;
            int quantity = Math.max(1, (int) spawner.spawnRatio/2);
            if(spawner.screen.monsterManager.liveMonsters.size() < spawner.maxMeleeMonsters) {
                for (int i = 0; i < quantity; i++) {
                    GreenSludge sludge = new GreenSludge(spawner.screen, null, spawner, null);
                    spawner.spawnMonster(sludge);
                }
            }
        }
    }
    public void spawnSludgesAround() {
        float delay = 2.4f/(spawner.spawnRatio/2);
        if(sludgeAroundTimer > delay) {
            sludgeAroundTimer = 0;
            Vector2 position = SpawnerUtils.getClearRandomPositionRing(player.pawn.getPosition(), 36 * xRatio, 40 * xRatio);
            GreenSludge sludge = new GreenSludge(spawner.screen, position, spawner, null);
            spawner.spawnMonster(sludge);
        }
    }
}
