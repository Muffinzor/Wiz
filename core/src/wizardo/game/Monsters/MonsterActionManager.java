package wizardo.game.Monsters;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Monsters.MonsterArchetypes.Monster;

import static wizardo.game.Monsters.MonsterUtils.MONSTER_STATE.ATTACKING;

public class MonsterActionManager {

    public Monster monster;
    public float timer;
    public float attackCD;

    public MonsterActionManager(Monster monster, float attackCD) {
        this.monster = monster;
        this.timer = attackCD * MathUtils.random(0.8f, 1.2f);
        this.attackCD = attackCD * MathUtils.random(0.9f, 1.1f);
    }

    public void update(float delta) {
        timer -= delta;
        if(monster.state == ATTACKING && timer <= 0 && monster.movementManager.pushBackTimer <= 0 && monster.freezeTimer < 0) {
            monster.launchAttack();
            timer = attackCD;
        }
    }

}
