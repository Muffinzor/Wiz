package wizardo.game.Monsters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Screens.Battle.BattleScreen;

import java.util.ArrayList;

import static wizardo.game.Wizardo.world;

public class MonsterManager {

    public ArrayList<Monster> liveMonsters;
    public ArrayList<Monster> dyingMonsters;

    public ArrayList<Body> bodiesToDestroy;

    public BattleScreen screen;

    public MonsterManager(BattleScreen screen) {
        this.screen = screen;
        liveMonsters = new ArrayList<>();
        dyingMonsters = new ArrayList<>();
        bodiesToDestroy = new ArrayList<>();
    }

    public void update(float delta) {

        updateLiveMonsters(delta);

        for(Monster monster : liveMonsters) {
            if(monster.hp <= 0) {
                monster.stateTime = 0;
                dyingMonsters.add(monster);
            }
        }

        updateDeadMonsters(delta);
    }

    public void addMonster(Monster monster) {
        liveMonsters.add(monster);
    }

    public void updateLiveMonsters(float delta) {

        for(Monster monster : liveMonsters) {
            monster.update(delta);
            if(monster.dead) {
                dyingMonsters.add(monster);
            }
        }
        for(Monster monster : liveMonsters) {
            monster.movement(delta);
        }
        liveMonsters.removeIf(monster -> monster.dead);

    }

    /**
     * doit etre implemented avec anim
     * @param delta
     */
    public void updateDeadMonsters(float delta) {
        for(Monster monster : dyingMonsters) {
            if(monster.body != null) {
                monster.deathPosition = new Vector2(monster.body.getPosition());
                world.destroyBody(monster.body);
                monster.body = null;
            }
            monster.drawDeathFrame(delta);
            monster.stateTime += delta;
        }

        dyingMonsters.removeIf(monster -> monster.alpha <= 0);
    }

}
