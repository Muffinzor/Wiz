package wizardo.game.Screens.Battle.MonsterSpawner;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterTypes.MawDemon.MawDemon;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyPool;

import java.util.ArrayList;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Wizardo.player;

public abstract class MonsterSpawner {

    public ArrayList<Monster> monster_to_spawn;

    public BattleScreen screen;
    float stateTime;

    Vector2 playerPreviousLocation;
    Vector2 playerCurrentLocation;
    Vector2 direction;
    float directionTimer = 0;

    public float spawnRatio = 1.0f;
    float cycleDuration = 20;
    int killsLastCycle = 0;
    float killResetTimer = 0;

    float monsterToughnessRatio = 1;
    float monsterDamageRatio = 1;

    float meleeSpawnTimer = 0;
    float rangedSpawnTimer = 0;
    float packTimer = 0;
    float eliteTimer = 0;
    float emptyQuadrantSpawnTimer = 0;

    public BodyPool bodyPool;
    int maxMeleeMonsters = 100;


    public MonsterSpawner(BattleScreen screen) {
        this.screen = screen;
        bodyPool = new BodyPool();
        monster_to_spawn = new ArrayList<>();

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
        eliteTimer += delta;
        maxMeleeMonsters = Math.min(1000, (int) (100 * spawnRatio));
    }


    public void spawnDemon() {
        if(eliteTimer > 300f) {
            eliteTimer = 0;
            Monster monster = new MawDemon(screen, null, this);
            spawnMonster(monster);
        }
    }
    public void spawnMeleeMonsters() {

    }
    public void spawnRangedMonsters() {

    }
    public void spawnPack() {

    }
    public void spawnMonstersInEmptyQuadrant() {

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

        // Clamp the decrease to a maximum of 10% per cycle
        if (spawnRatio < previousRatio * 0.9f) {
            spawnRatio = previousRatio * 0.9f; // Limit to 10% reduction
        }
        if( spawnRatio > 10) {
            spawnRatio = 10;
        }
    }
}
