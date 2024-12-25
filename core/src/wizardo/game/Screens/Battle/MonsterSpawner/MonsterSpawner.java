package wizardo.game.Screens.Battle.MonsterSpawner;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.TEST_BIGMONSTER;
import wizardo.game.Monsters.TEST_MELEE;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class MonsterSpawner {

    public BattleScreen screen;

    float stateTime;

    Vector2 playerPreviousLocation;
    Vector2 playerCurrentLocation;
    Vector2 direction;
    float directionTimer = 0;

    float spawnTimer_around = 0;
    float spawnTimer_ahead = 0;


    public MonsterSpawner(BattleScreen screen) {
        this.screen = screen;

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
        stateTime += delta;
        directionTimer += delta;

        if(directionTimer > 0.5f) {
            calculateTrajectory();
            directionTimer = 0;
        }

        if(stateTime >= 0.5f && screen.monsterManager.liveMonsters.size() < 1000) {
            for (int i = 0; i < 3; i++) {
                Monster monster;
                if(Math.random() > 0.9f) {
                    monster = new TEST_BIGMONSTER(screen, getSpawnPosition());
                } else {
                    monster = new TEST_MELEE(screen, getSpawnPosition());
                }
                screen.monsterManager.addMonster(monster);
            }

            for (int i = 0; i < 1; i++) {
                TEST_MELEE monster = new TEST_MELEE(screen, SpellUtils.getClearRandomPositionRing(player.pawn.getPosition(), 32, 40));
                screen.monsterManager.addMonster(monster);
            }
            stateTime = 0;
        }
    }

    public Vector2 getSpawnPosition() {
        Vector2 playerPosition = player.pawn.getPosition();
        if(direction == null) {
            return SpellUtils.getClearRandomPositionRing(playerPosition, 32, 40);
        } else {
            float angle = direction.angleDeg();
            return SpellUtils.getClearRandomPositionCone(playerPosition, 32, 40, angle);
        }
    }
}
