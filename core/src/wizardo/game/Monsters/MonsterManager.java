package wizardo.game.Monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterArchetypes.MonsterRanged;
import wizardo.game.Screens.Battle.BattleScreen;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class MonsterManager {

    public ArrayList<Monster> liveMonsters;
    public ArrayList<Monster> dyingMonsters;

    public BattleScreen screen;

    int bodiesRemovalCounter;

    public MonsterManager(BattleScreen screen) {
        this.screen = screen;

        liveMonsters = new ArrayList<>();
        dyingMonsters = new ArrayList<>();

    }

    public void update(float delta) {

        updateLiveMonsters(delta);
        updateDeadMonsters(delta);

    }

    public void addMonster(Monster monster) {
        monster.update(Gdx.graphics.getDeltaTime());
        liveMonsters.add(monster);
    }

    public void updateLiveMonsters(float delta) {

        for(Monster monster : liveMonsters) {
            monster.update(delta);
            if(monster.dead) {
                screen.monsterSpawner.registerKill();
                player.currentXP += (monster.xp * (1 + player.stats.bonus_experience/100f));
                monster.stateTime = 0;
                dyingMonsters.add(monster);
                if(monster.light != null) {
                    monster.light.dimKill(0.01f);
                    monster.light = null;
                }
            } else if(monster.tooFar) {
                if(monster.light != null) {
                    monster.light.dimKill(0.5f);
                    monster.light = null;
                }
                dyingMonsters.add(monster);
                monster.alpha = 0.05f;
            }
        }

        liveMonsters.removeIf(monster -> monster.dead);
        liveMonsters.removeIf(monster -> monster.tooFar);

        for(Monster monster : liveMonsters) {
            monster.movement(delta);
        }


    }

    public void updateDeadMonsters(float delta) {
        for(Monster monster : dyingMonsters) {

            if(monster.body.isActive()) {
                monster.deathPosition = new Vector2(monster.body.getPosition());
                monster.body.setActive(false);
            }
            monster.drawDeathFrame(delta);
            monster.stateTime += delta;

        }

        if(delta > 0) {
            bodiesRemoval();
        }

    }

    /** spaced to prevent garbage collection crazyness **/
    public void bodiesRemoval() {
        bodiesRemovalCounter++;
        if(bodiesRemovalCounter >= 120) {
            bodiesRemovalCounter = 0;

            ArrayList<Monster> monstersRemoved = new ArrayList<>();

            for(Monster monster : dyingMonsters) {
                if(monster.alpha <= 0) {
                    if(monster.basic) {
                        screen.monsterSpawner.bodyPool.poolBody(monster.body);
                    } else {
                        world.destroyBody(monster.body);
                    }
                    monstersRemoved.add(monster);
                }
            }

            dyingMonsters.removeAll(monstersRemoved);
            monstersRemoved.clear();
        }

    }

    public int getRangedMonstersCount() {
        int count = 0;
        for(Monster monster : liveMonsters) {
            if(monster instanceof MonsterRanged) {
                count++;
            }
        }
        return count;
    }

}
