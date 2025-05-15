package wizardo.game.Screens.Battle.MonsterSpawner;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.DungeonMonsters.*;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterTypes.MawDemon.MawDemon;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyPool;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Wizardo.player;

public class MonsterSpawner_Dungeon extends MonsterSpawner {

    float stateTime;

    Vector2 playerPreviousLocation;
    Vector2 playerCurrentLocation;
    Vector2 direction;
    float directionTimer = 0;

    float spawnRatio = 1.0f;
    float cycleDuration = 20;
    int killsLastCycle = 0;
    float killResetTimer = 0;

    float monsterToughnessRatio = 1;
    float monsterDamageRatio = 1;

    float meleeSpawnTimer = 0;
    float rangedSpawnTimer = 0;
    float packTimer = 0;
    float demonTimer = 0;
    float targetedSpawnTimer = 0;
    float sludgeTimer = 0;

    public BodyPool bodyPool;
    int maxMeleeMonsters = 100;

    public MonsterSpawner_Dungeon(BattleScreen screen) {
        super(screen);
        bodyPool = new BodyPool();
        playerPreviousLocation = player.pawn.getPosition();
        playerCurrentLocation = player.pawn.getPosition();
    }

    public void update(float delta) {
        updateTimers(delta);
        if (killResetTimer >= cycleDuration) {
            monsterToughnessRatio = monsterToughnessRatio * 1.075f;
            monsterDamageRatio = monsterDamageRatio * 1.03f;
            updateSpawnRatio();
            killsLastCycle = 0;
            killResetTimer = 0;

            System.out.println("Spawn Ratio : " + spawnRatio);
            System.out.println("Damage Ratio : " + monsterDamageRatio + ", Health Ratio : " + monsterToughnessRatio);
        }

        if(directionTimer > 0.5f) {
            calculateTrajectory();
            directionTimer = 0;
        }

        //spawnMeleeMonsters();
        //spawnRangedMonsters();
        //spawnPack();
        //spawnMonstersInEmptyQuadrant();
        spawnDemon();
        spawnSludges();

        for(Monster monster : monster_to_spawn) {
            spawnMonster(monster);
        }
        monster_to_spawn.clear();
    }

    public void updateTimers(float delta) {
        stateTime += delta;
        directionTimer += delta;
        killResetTimer += delta;
        meleeSpawnTimer += delta;
        packTimer += delta;
        rangedSpawnTimer += delta;
        targetedSpawnTimer += delta;
        demonTimer += delta;
        sludgeTimer += delta;
        maxMeleeMonsters = Math.min(1000, (int) (100 * spawnRatio));
    }

    public void spawnSludges() {
        if(sludgeTimer > 1 && stateTime > 0) {
            sludgeTimer = 0;
            if(Math.random() >= 0.75f) {
                Monster monster2 = new AcolytePurple(screen, SpawnerUtils.getRandomRangeSpawnVector(), this);
                spawnMonster(monster2);
            }
            if(Math.random() >= 0.95f) {
                Monster monster2 = new Zombie(screen, null, this);
                spawnMonster(monster2);
            }
            for (int i = 0; i < 2 * spawnRatio; i++) {
                Monster monster2 = new GreenSludge(screen, null, this);
                spawnMonster(monster2);
            }
        }
    }

    public void spawnDemon() {
        if(demonTimer > 1) {
            demonTimer = -1000000;
            Monster monster = new MawDemon(screen, null, this);
            spawnMonster(monster);
        }
    }

    public void spawnMeleeMonsters() {
        if(meleeSpawnTimer > 2f) {
            meleeSpawnTimer = 0;
            if(screen.monsterManager.liveMonsters.size() < maxMeleeMonsters) {
                for (int i = 0; i < spawnRatio; i++) {
                    Monster monster;
                    if(Math.random() >= 0.9f && stateTime > 0) {
                        monster = new SkeletonGiant(screen, null, this);
                    } else {
                        monster = new Skeleton(screen, null, this);
                    }
                    spawnMonster(monster);
                }
            }
        }
    }
    public void spawnRangedMonsters() {
        if(rangedSpawnTimer > 3.2f && stateTime > 120) {
            rangedSpawnTimer = 0;
            if(screen.monsterManager.getRangedMonstersCount() < 30) {
                Monster monster = new AcolyteBlue(screen, SpawnerUtils.getRandomRangeSpawnVector(), this);
                spawnMonster(monster);
            }
        }
    }

    public void spawnPack() {
        if(packTimer >= 15 && screen.monsterManager.liveMonsters.size() < maxMeleeMonsters) {
            packTimer = 0;

            Vector2 randomizedDirection;
            if(direction != null) {
                randomizedDirection = direction.cpy().nor().rotateDeg(MathUtils.random(-20,20));
            } else {
                randomizedDirection = SpellUtils.getRandomVectorInRadius(player.pawn.getPosition(), 1);
                randomizedDirection.nor();
            }
            randomizedDirection.scl(36 * xRatio);
            Vector2 centerpoint = player.pawn.getPosition().add(randomizedDirection);

            int count = (int) (6 * spawnRatio);
            for (int i = 0; i < count; i++) {
                Vector2 spawnPoint = SpellUtils.getClearRandomPosition(centerpoint, Math.min(2 * spawnRatio, 6));
                Monster monster;
                if(Math.random() >= 0.95f) {
                    monster = new SkeletonGiant(screen, spawnPoint, this);
                } else {
                    monster = new Skeleton(screen, spawnPoint, this);
                }
                spawnMonster(monster);
            }
        }
    }

    public void spawnMonstersInEmptyQuadrant() {
        if(targetedSpawnTimer > 2 && screen.monsterManager.liveMonsters.size() < maxMeleeMonsters) {

            targetedSpawnTimer = 0;
            int topLeft = 0, topRight = 0, bottomLeft = 0, bottomRight = 0;

            Vector2 playerPos = player.pawn.getPosition();
            for (Monster monster : screen.monsterManager.liveMonsters) {
                Vector2 pos = monster.body.getPosition();
                if (pos.x < playerPos.x && pos.y > playerPos.y) {
                    topLeft++;
                } else if (pos.x > playerPos.x && pos.y > playerPos.y) {
                    topRight++;
                } else if (pos.x < playerPos.x && pos.y < playerPos.y) {
                    bottomLeft++;
                } else {
                    bottomRight++;
                }
            }

            int minQuadrant = Math.min(Math.min(topLeft, topRight), Math.min(bottomLeft, bottomRight));
            int angle;
            if (minQuadrant == topLeft) {
                angle = 135;
            } else if (minQuadrant == topRight) {
                angle = 45;
            } else if (minQuadrant == bottomLeft) {
                angle = 225;
            } else {
                angle = 315;
            }

            for (int i = 0; i < 6 * spawnRatio; i++) {
                Vector2 position = SpellUtils.getClearRandomPositionCone(player.pawn.getPosition(), 32 * xRatio, 42 * xRatio, angle);
                Monster monster = new Skeleton(screen, position, this);
                spawnMonster(monster);
            }
        }
    }

    public void spawnMonster(Monster monster) {
        monster.maxHP = monster.maxHP * monsterToughnessRatio;
        monster.hp = monster.hp * monsterToughnessRatio;
        monster.dmg = (int) (monster.dmg * monsterDamageRatio);
        screen.monsterManager.addMonster(monster);
    }

    public Vector2 getSpawnPosition() {
        Vector2 playerPosition = player.pawn.getPosition();
        if(direction == null) {
            return SpellUtils.getClearRandomPositionRing(playerPosition, 32 * xRatio, 42 * xRatio);
        } else {
            float angle = direction.angleDeg();
            return SpellUtils.getClearRandomPositionCone(playerPosition, 32 * xRatio, 42 * xRatio, angle);
        }
    }

    public void registerKill() {
        killsLastCycle++;
    }

    private void updateSpawnRatio() {
        float previousRatio = spawnRatio;

        // Calculate the new ratio based on kills
        if (killsLastCycle > 10) {
            spawnRatio = 1.0f + (killsLastCycle - 10 * spawnRatio) * 0.025f; // 0.025 is arbitrary and just a test
            if(spawnRatio > previousRatio * 1.15f) {
                spawnRatio = previousRatio * 1.15f; // Limit to 15% increase
            }
        } else {
            spawnRatio = 1.0f;
        }

        // Clamp the decrease to a maximum of 5% per cycle
        if (spawnRatio < previousRatio * 0.95f) {
            spawnRatio = previousRatio * 0.95f; // Limit to 5% reduction
        }
        if(spawnRatio > 10) {
            spawnRatio = 10;
        }
    }
}
