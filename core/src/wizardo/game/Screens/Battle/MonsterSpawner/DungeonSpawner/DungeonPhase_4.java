package wizardo.game.Screens.Battle.MonsterSpawner.DungeonSpawner;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.DungeonMonsters.Bat;
import wizardo.game.Monsters.DungeonMonsters.MawDemon.MawDemon;
import wizardo.game.Monsters.DungeonMonsters.Skeleton;
import wizardo.game.Monsters.DungeonMonsters.SkeletonGiant;
import wizardo.game.Monsters.DungeonMonsters.Skeleton_T2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;
import wizardo.game.Screens.Battle.MonsterSpawner.SpawnerPhase;
import wizardo.game.Screens.Battle.MonsterSpawner.SpawnerUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Wizardo.player;

public class DungeonPhase_4 implements SpawnerPhase {

    float stateTime = 0;
    float skellyTimer = 0;
    float packTimer = 0;
    float quadrantTimer = 0;
    MonsterSpawner spawner;

    public DungeonPhase_4(MonsterSpawner spawner) {
        this.spawner = spawner;
    }

    @Override
    public void update(float delta, MonsterSpawner spawner) {
        stateTime += delta;
        skellyTimer += delta;
        packTimer += delta;
        quadrantTimer += delta;
        spawnBats();
        if(stateTime >= 240) {
            spawner.phase = new DungeonPhase_2(spawner);
        }
    }

    public void spawnBats() {
        if(skellyTimer > 1.2f) {
            skellyTimer = 0;
            if(spawner.screen.monsterManager.liveMonsters.size() < spawner.maxMeleeMonsters) {
                for (int i = 0; i < 3; i++) {
                    Vector2 position = SpawnerUtils.getClearRandomPositionRing(player.pawn.getPosition(), 30, 32);
                    Vector2 difference = player.pawn.getPosition().cpy().sub(position);
                    difference.rotateDeg(MathUtils.random(-25,25));
                    difference.scl(15);
                    Vector2 patrolDirection = position.cpy().add(difference);
                    spawner.spawnMonster(new Bat(spawner.screen, position, spawner, patrolDirection));
                }
            }
        }
    }

    public void spawnSkellies2() {
        if(skellyTimer > 1.2f) {
            if(spawner.screen.monsterManager.liveMonsters.size() < spawner.maxMeleeMonsters) {
                for (int i = 0; i < 5; i++) {
                    Monster monster = new Skeleton(spawner.screen, null, spawner, null);
                    spawner.spawnMonster(monster);
                }
            }
        }
    }
}
