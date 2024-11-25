package wizardo.game.Monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Screens.Battle.BattleScreen;

import java.util.ArrayList;

import static wizardo.game.Wizardo.world;

public class MonsterManager {

    public ArrayList<Monster> liveMonsters;
    public ArrayList<Monster> dyingMonsters;

    public BattleScreen screen;

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
                monster.stateTime = 0;
                dyingMonsters.add(monster);
                if(monster.light != null) {
                    monster.light.dimKill(0.2f);
                }
            }
        }
        liveMonsters.removeIf(monster -> monster.dead);

        for(Monster monster : liveMonsters) {
            monster.movement(delta);
        }


    }

    /**
     * doit etre implemented avec anim
     * @param delta
     */
    public void updateDeadMonsters(float delta) {
        for(Monster monster : dyingMonsters) {

            if(monster.body.isActive()) {
                monster.deathPosition = new Vector2(monster.body.getPosition());
                monster.body.setActive(false);
            }
            monster.drawDeathFrame(delta);
            monster.stateTime += delta;

            if(monster.alpha <= 0) {
                world.destroyBody(monster.body);
            }
        }

        dyingMonsters.removeIf(monster -> monster.alpha <= 0);
    }

}
