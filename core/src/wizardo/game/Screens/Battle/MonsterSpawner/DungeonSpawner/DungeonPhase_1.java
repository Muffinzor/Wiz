package wizardo.game.Screens.Battle.MonsterSpawner.DungeonSpawner;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.DungeonMonsters.Skeleton;
import wizardo.game.Monsters.DungeonMonsters.SkeletonGiant;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;
import wizardo.game.Screens.Battle.MonsterSpawner.SpawnerPhase;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Wizardo.player;

public class DungeonPhase_1 implements SpawnerPhase {

    float stateTime = 0;
    float skellyTimer = 0;
    float packTimer = 0;
    MonsterSpawner spawner;

    public DungeonPhase_1(MonsterSpawner spawner) {
        this.spawner = spawner;
    }

    @Override
    public void update(float delta, MonsterSpawner spawner) {
        stateTime += delta;
        skellyTimer += delta;
        packTimer += delta;
        spawnSkellies();
        spawnPack();
    }

    public void spawnSkellies() {
        if(skellyTimer > 2f) {
            skellyTimer = 0;
            if(spawner.screen.monsterManager.liveMonsters.size() < spawner.maxMeleeMonsters) {
                for (int i = 0; i < spawner.spawnRatio; i++) {
                    Monster monster;
                    if(stateTime > 120 && Math.random() >= 0.9f) {
                        monster = new SkeletonGiant(spawner.screen, null, spawner);
                    } else {
                        monster = new Skeleton(spawner.screen, null, spawner);
                    }
                    spawner.spawnMonster(monster);
                }
            }
        }
    }
    public void spawnPack() {
        if(packTimer >= 15 && spawner.screen.monsterManager.liveMonsters.size() < spawner.maxMeleeMonsters) {
            packTimer = 0;

            Vector2 randomizedDirection;
            if(spawner.direction != null) {
                randomizedDirection = spawner.direction.cpy().nor().rotateDeg(MathUtils.random(-20,20));
            } else {
                randomizedDirection = SpellUtils.getRandomVectorInRadius(player.pawn.getPosition(), 1);
                randomizedDirection.nor();
            }
            randomizedDirection.scl(36 * xRatio);
            Vector2 centerPoint = player.pawn.getPosition().add(randomizedDirection);

            int count = (int) (6 * spawner.spawnRatio);
            for (int i = 0; i < count; i++) {
                Vector2 spawnPoint = SpellUtils.getClearRandomPosition(centerPoint, Math.min(2 * spawner.spawnRatio, 6));
                Monster monster;
                if(stateTime >= 120 && Math.random() >= 0.95f) {
                    monster = new SkeletonGiant(spawner.screen, spawnPoint, spawner);
                } else {
                    monster = new Skeleton(spawner.screen, spawnPoint, spawner);
                }
                spawner.spawnMonster(monster);
            }
        }
    }
}
