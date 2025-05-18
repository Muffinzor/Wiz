package wizardo.game.Screens.Battle.MonsterSpawner.DungeonSpawner;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.DungeonMonsters.AcolyteBlue;
import wizardo.game.Monsters.DungeonMonsters.AcolytePurple;
import wizardo.game.Monsters.DungeonMonsters.Skeleton;
import wizardo.game.Monsters.DungeonMonsters.SkeletonGiant;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;
import wizardo.game.Screens.Battle.MonsterSpawner.SpawnerPhase;
import wizardo.game.Screens.Battle.MonsterSpawner.SpawnerUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Wizardo.player;

public class DungeonPhase_2 implements SpawnerPhase {

    float stateTime = 0;
    float skellyTimer = 0;
    float packTimer = 0;
    float rangeTimer = 0;
    MonsterSpawner spawner;

    boolean acolyte_wave_spawned;

    public DungeonPhase_2(MonsterSpawner spawner) {
        this.spawner = spawner;
    }

    @Override
    public void update(float delta, MonsterSpawner spawner) {
        stateTime += delta;
        skellyTimer += delta;
        packTimer += delta;
        rangeTimer += delta;
        spawnSkellies();
        spawnAcolyte();
        spawnPack();

        if(!acolyte_wave_spawned) {
            spawnAcolytePack();
            acolyte_wave_spawned = true;
        }
    }

    public void spawnSkellies() {
        if(skellyTimer > 2f) {
            skellyTimer = 0;
            if(spawner.screen.monsterManager.liveMonsters.size() < spawner.maxMeleeMonsters) {
                for (int i = 0; i < spawner.spawnRatio; i++) {
                    Monster monster;
                    if(stateTime > 0 && Math.random() >= 0.9f) {
                        monster = new SkeletonGiant(spawner.screen, null, spawner);
                    } else {
                        monster = new Skeleton(spawner.screen, null, spawner);
                    }
                    spawner.spawnMonster(monster);
                }
            }
        }
    }
    public void spawnAcolyte() {
        if(rangeTimer > 3.2f && stateTime > 0) {
            rangeTimer = 0;
            if(spawner.screen.monsterManager.getRangedMonstersCount() < 8) {
                Monster monster;
                if(stateTime >= 120 && Math.random() >= 0.9f) {
                    monster = new AcolytePurple(spawner.screen, SpawnerUtils.getRandomRangeSpawnVector(), spawner);
                } else {
                    monster = new AcolyteBlue(spawner.screen, SpawnerUtils.getRandomRangeSpawnVector(), spawner);
                }
                spawner.spawnMonster(monster);
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

            int count = (int) (5 * spawner.spawnRatio);
            for (int i = 0; i < count; i++) {
                Vector2 spawnPoint = SpellUtils.getClearRandomPosition(centerPoint, Math.min(2 * spawner.spawnRatio, 6));
                Monster monster;
                if(stateTime >= 0 && Math.random() >= 0.9f) {
                    monster = new SkeletonGiant(spawner.screen, spawnPoint, spawner);
                } else {
                    monster = new Skeleton(spawner.screen, spawnPoint, spawner);
                }
                spawner.spawnMonster(monster);
            }
        }
    }
    void spawnAcolytePack() {
        for (int i = 0; i < 8; i++) {
            Monster monster = new AcolyteBlue(spawner.screen, SpawnerUtils.getRandomRangeSpawnVector(), spawner);
            spawner.spawnMonster(monster);
        }
    }
}
