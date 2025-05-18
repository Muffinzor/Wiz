package wizardo.game.Screens.Battle.MonsterSpawner.DungeonSpawner;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.DungeonMonsters.*;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterTypes.MawDemon.MawDemon;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;
import wizardo.game.Screens.Battle.MonsterSpawner.SpawnerUtils;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyPool;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Wizardo.player;

public class MonsterSpawner_Dungeon extends MonsterSpawner {

    public MonsterSpawner_Dungeon(BattleScreen screen) {
        super(screen);
        phase = new DungeonPhase_1(this);
    }

    public void spawnRangedMonsters() {
        if(0 > 3.2f && stateTime > 120) {
            float rangedSpawnTimer = 0;
            if(screen.monsterManager.getRangedMonstersCount() < 30) {
                Monster monster = new AcolyteBlue(screen, SpawnerUtils.getRandomRangeSpawnVector(), this);
                spawnMonster(monster);
            }
        }
    }

    public void spawnPack() {
        if(0 >= 15 && screen.monsterManager.liveMonsters.size() < maxMeleeMonsters) {
            float packTimer = 0;

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
}
