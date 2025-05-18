package wizardo.game.Screens.Battle.MonsterSpawner;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterTypes.MawDemon.MawDemon;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.DungeonSpawner.DungeonPhase_1;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyPool;

import java.util.ArrayList;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Wizardo.player;

public abstract class MonsterSpawner {

    public ArrayList<Monster> monster_to_spawn;

    public BattleScreen screen;
    public SpawnerPhase phase;
    public float stateTime;

    public Vector2 playerPreviousLocation;
    public Vector2 playerCurrentLocation;
    public Vector2 direction;
    public float directionTimer = 0;

    public float spawnRatio = 1.0f;
    public float cycleDuration = 20;
    public int killsLastCycle = 0;
    public float killResetTimer = 0;

    public float monsterToughnessRatio = 1;
    public float monsterDamageRatio = 1;

    public BodyPool bodyPool;
    public int maxMeleeMonsters = 100;


    public MonsterSpawner(BattleScreen screen) {
        this.screen = screen;
        phase = new DungeonPhase_1(this);
        bodyPool = new BodyPool();
        monster_to_spawn = new ArrayList<>();

        playerPreviousLocation = player.pawn.getPosition();
        playerCurrentLocation = player.pawn.getPosition();

    }

    /**
     * calculates the direction of the player
     * used to spawn monsters ahead of the player
     */
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

        for(Monster monster : monster_to_spawn) {
            spawnMonster(monster);
        }
        monster_to_spawn.clear();
        phase.update(delta, this);
    }

    public void updateTimers(float delta) {
        stateTime += delta;
        directionTimer += delta;
        killResetTimer += delta;
        maxMeleeMonsters = Math.min(1000, (int) (100 * spawnRatio));
    }

    /**
     * sets the monster initial values and adds it to the spawn list
     */
    public void spawnMonster(Monster monster) {
        monster.maxHP = monster.maxHP * monsterToughnessRatio;
        monster.hp = monster.hp * monsterToughnessRatio;
        monster.dmg = (int) (monster.dmg * monsterDamageRatio);
        screen.monsterManager.addMonster(monster);
    }

    /**
     * returns a spawn position in the direction the player is moving
     * if player is not moving, returns a random position around them
     */
    public Vector2 getSpawnPositionAhead() {
        Vector2 playerPosition = player.pawn.getPosition();
        if(direction == null) {
            return SpellUtils.getClearRandomPositionRing(playerPosition, 35 * xRatio, 42 * xRatio);
        } else {
            float angle = direction.angleDeg();
            return SpellUtils.getClearRandomPositionCone(playerPosition, 35 * xRatio, 42 * xRatio, angle);
        }
    }


    public void registerKill(Monster monster) {
        if(!monster.basic) {
            killsLastCycle += 5;
        } else {
            killsLastCycle++;
        }
    }

    private void updateSpawnRatio() {
        spawnRatio += 0.1f;
        float previousRatio = spawnRatio;

        // Calculate the new ratio based on kills
        if (killsLastCycle > 5) {
            spawnRatio = 1.0f + (killsLastCycle - 5 * spawnRatio) * 0.1f; // 0.1 is arbitrary and just a test
            if(spawnRatio > previousRatio * 1.15f) {
                spawnRatio = previousRatio * 1.15f; // Limit to 15% increase
            }
        } else {
            spawnRatio = 1.0f;
        }

        if (spawnRatio < previousRatio * 0.95f) {
            spawnRatio = previousRatio * 0.95f; // Limit to 5% reduction
        }
        if(spawnRatio > 10) {
            spawnRatio = 10;
        }
        System.out.println("Spawn ratio: " + spawnRatio);
    }

    /**
     * calculates the angle from the player of the screen quadrant with the
     * smallest amount of monsters
     */
    public int getEmptyQuadrantAngle() {
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
        return angle;
    }
}
