package wizardo.game.Screens.Battle.MonsterSpawner;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterTypes.MawDemon.MawDemon;
import wizardo.game.Monsters.TEST_BIGMONSTER;
import wizardo.game.Monsters.TEST_MELEE;
import wizardo.game.Monsters.TEST_RANGED;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyPool;

import static wizardo.game.Wizardo.player;

public abstract class MonsterSpawner {


    public BattleScreen screen;
    float stateTime;

    public boolean eliteAlive = false;

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
    float emptyQuadrantSpawnTimer = 0;

    public BodyPool bodyPool;
    int maxMeleeMonsters = 100;


    public MonsterSpawner(BattleScreen screen) {
        this.screen = screen;
        bodyPool = new BodyPool();

        playerPreviousLocation = player.pawn.getPosition();
        playerCurrentLocation = player.pawn.getPosition();

    }

    public void calculateTrajectory() {
        if(playerPreviousLocation.dst(playerCurrentLocation) > 1) {
            direction = new Vector2(playerCurrentLocation.cpy().sub(playerPreviousLocation));
        } else {
            direction = null;
        }

        playerPreviousLocation.set(playerCurrentLocation);
        playerCurrentLocation.set(player.pawn.getPosition());

    }

    public void update(float delta) {
        updateTimers(delta);

        if (killResetTimer >= cycleDuration) {
            monsterToughnessRatio = monsterToughnessRatio * 1.075f;
            monsterDamageRatio = monsterDamageRatio * 1.03f;
            updateSpawnRatio();
            killsLastCycle = 0;
            killResetTimer = 0;
        }

        if(directionTimer > 0.5f) {
            calculateTrajectory();
            directionTimer = 0;
        }

        spawnMeleeMonsters();
        spawnRangedMonsters();
        spawnPack();
        spawnMonstersInEmptyQuadrant();
        spawnDemon();

    }

    public void updateTimers(float delta) {
        stateTime += delta;
        directionTimer += delta;
        killResetTimer += delta;
        meleeSpawnTimer += delta;
        packTimer += delta;
        rangedSpawnTimer += delta;
        emptyQuadrantSpawnTimer += delta;
        demonTimer += delta;
        maxMeleeMonsters = Math.min(1000, (int) (100 * spawnRatio));
    }


    public void spawnDemon() {
        if(demonTimer > 300f) {
            demonTimer = 0;
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
                        monster = new TEST_BIGMONSTER(screen, null, this);
                    } else {
                        monster = new TEST_MELEE(screen, null, this);
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
                TEST_RANGED monster = new TEST_RANGED(screen, SpawnerUtils.getRandomRangeSpawnVector(), this);
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
            randomizedDirection.scl(36);
            Vector2 centerpoint = player.pawn.getPosition().add(randomizedDirection);

            int count = (int) (6 * spawnRatio);
            for (int i = 0; i < count; i++) {
                Vector2 spawnPoint = SpellUtils.getClearRandomPosition(centerpoint, Math.min(2 * spawnRatio, 10));
                Monster monster;
                if(Math.random() >= 0.95f) {
                    monster = new TEST_BIGMONSTER(screen, spawnPoint, this);
                } else {
                    monster = new TEST_MELEE(screen, spawnPoint, this);
                }
                spawnMonster(monster);
            }
        }
    }
    public void spawnMonstersInEmptyQuadrant() {
        if(emptyQuadrantSpawnTimer > 2 && screen.monsterManager.liveMonsters.size() < maxMeleeMonsters) {

            emptyQuadrantSpawnTimer = 0;
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
                Vector2 position = SpellUtils.getClearRandomPositionCone(player.pawn.getPosition(), 34, 45, angle);
                Monster monster = new TEST_MELEE(screen, position, this);
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
            return SpellUtils.getClearRandomPositionRing(playerPosition, 32, 45);
        } else {
            float angle = direction.angleDeg();
            return SpellUtils.getClearRandomPositionCone(playerPosition, 32, 45, angle);
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

        // Clamp the decrease to a maximum of 10% per cycle
        if (spawnRatio < previousRatio * 0.9f) {
            spawnRatio = previousRatio * 0.9f; // Limit to 10% reduction
        }
        if( spawnRatio > 10) {
            spawnRatio = 10;
        }
    }
}
