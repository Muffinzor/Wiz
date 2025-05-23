package wizardo.game.Screens.Battle.MonsterSpawner.DungeonSpawner;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.DungeonMonsters.*;
import wizardo.game.Monsters.DungeonMonsters.MawDemon.MawDemon;
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
    float acolytepackTimer = 0;
    float quadrantTimer = 0;
    MonsterSpawner spawner;

    boolean demonSpawned;
    MawDemon demon;

    public DungeonPhase_2(MonsterSpawner spawner) {
        this.spawner = spawner;
    }

    @Override
    public void update(float delta, MonsterSpawner spawner) {
        stateTime += delta;
        skellyTimer += delta;
        packTimer += delta;
        rangeTimer += delta;
        quadrantTimer += delta;
        spawnSkellies();
        spawnAcolyte();
        spawnPack();
        spawnQuadrant();

        if(stateTime >= 120 && acolytepackTimer >= 60) {
            spawnAcolytePack();
            acolytepackTimer = 0;
        }

        if(!demonSpawned && stateTime >= 240) {
            spawnDemon();
            demonSpawned = true;
        } else if (demonSpawned && demon.dead) {
            spawner.phase = new DungeonPhase_3(spawner);
        }
    }

    public void spawnDemon() {
        Vector2 position = SpawnerUtils.getRandomRangeSpawnVector();
        demon = new MawDemon(spawner.screen, position, spawner, null);
        spawner.spawnMonster(demon);
    }

    public void spawnSkellies() {
        if(skellyTimer > 1.2f) {
            skellyTimer = 0;
            if(spawner.screen.monsterManager.liveMonsters.size() < spawner.maxMeleeMonsters) {
                for (int i = 0; i < spawner.spawnRatio; i++) {
                    Monster monster;
                    if(stateTime > 0 && Math.random() >= 0.92f) {
                        monster = new SkeletonGiant(spawner.screen, null, spawner, null);
                    } else {
                        if(Math.random() >= 0.7f) {
                            monster = new Skeleton_T2(spawner.screen, null, spawner, null);
                        } else {
                            monster = new Skeleton(spawner.screen, null, spawner, null);
                        }

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
                    monster = new AcolytePurple(spawner.screen, SpawnerUtils.getRandomRangeSpawnVector(), spawner, null);
                } else {
                    monster = new AcolyteBlue(spawner.screen, SpawnerUtils.getRandomRangeSpawnVector(), spawner, null);
                }
                spawner.spawnMonster(monster);
            }
        }
    }
    public void spawnPack() {
        int packSize = Math.max(20, (int) (20 * spawner.spawnRatio/2));
        float packDelay = 12 / (spawner.spawnRatio/2);
        if(packTimer >= packDelay && spawner.screen.monsterManager.liveMonsters.size() < spawner.maxMeleeMonsters) {
            packTimer = 0;
            Vector2 randomizedDirection;
            if(spawner.direction != null) {
                randomizedDirection = spawner.direction.cpy().nor().rotateDeg(MathUtils.random(-20,20));
            } else {
                randomizedDirection = SpellUtils.getRandomVectorInRadius(player.pawn.getPosition(), 1);
                randomizedDirection.nor();
            }
            randomizedDirection.scl(42 * xRatio);
            Vector2 centerPoint = player.pawn.getPosition().add(randomizedDirection);

            for (int i = 0; i < packSize; i++) {
                Vector2 spawnPoint = SpellUtils.getClearRandomPosition(centerPoint, Math.min(2 * spawner.spawnRatio, 5));
                Monster monster;
                if(stateTime >= 120 && Math.random() >= 0.92f) {
                    monster = new SkeletonGiant(spawner.screen, spawnPoint, spawner, null);
                } else {
                    if(Math.random() >= 0.7f) {
                        monster = new Skeleton_T2(spawner.screen, null, spawner, null);
                    } else {
                        monster = new Skeleton(spawner.screen, null, spawner, null);
                    }
                }
                spawner.spawnMonster(monster);
            }
        }
    }
    void spawnQuadrant() {
        float quadrantDelay = 8 / (spawner.spawnRatio/2);
        if(quadrantTimer >= quadrantDelay) {
            quadrantTimer = 0;
            int quadrantAngle = spawner.getEmptyQuadrantAngle();
            for (int i = 0; i < 2 * spawner.spawnRatio; i++) {
                Monster monster;
                Vector2 spawnPoint = SpawnerUtils.getClearRandomVectorInConeRing(player.pawn.getPosition(),
                        34 * xRatio, 42 * xRatio, quadrantAngle);
                if(stateTime > 120 && Math.random() >= 0.92f) {
                    monster = new SkeletonGiant(spawner.screen, spawnPoint, spawner, null);
                } else {
                    if(Math.random() >= 0.7f) {
                        monster = new Skeleton_T2(spawner.screen, null, spawner, null);
                    } else {
                        monster = new Skeleton(spawner.screen, null, spawner, null);
                    }
                }
                spawner.spawnMonster(monster);
            }
        }
    }
    void spawnAcolytePack() {
        for (int i = 0; i < 8; i++) {
            Monster monster = new AcolyteBlue(spawner.screen, SpawnerUtils.getRandomRangeSpawnVector(), spawner, null);
            spawner.spawnMonster(monster);
        }
    }

}
