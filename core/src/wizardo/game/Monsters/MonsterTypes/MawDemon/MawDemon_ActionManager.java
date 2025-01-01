package wizardo.game.Monsters.MonsterTypes.MawDemon;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Monsters.MonsterActionManager;
import wizardo.game.Monsters.MonsterActions.SelfFireExplosion.SelfFireExplosion;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterUtils;

import static wizardo.game.Monsters.MonsterUtils.MONSTER_STATE.*;
import static wizardo.game.Monsters.MonsterUtils.MONSTER_STATE.CHARGING;

class MawDemon_ActionManager extends MonsterActionManager {

    MawDemon demon;

    public MawDemon_ActionManager(Monster monster, float attackCD) {
        super(monster, attackCD);
        demon = (MawDemon) monster;
    }

    public void update(float delta) {
        timer -= delta;

        if(demon.state == ADVANCING && timer <= 0) {
            if(MonsterUtils.hasCompleteLoS(demon)) {
                demon.launchAttack();
                timer = attackCD;
            }
        }

        if(demon.state == CHARGING) {
            demon.explosionDelay -= delta;
            if(demon.explosionDelay <= 0) {
                SelfFireExplosion explosion = new SelfFireExplosion(demon);
                demon.screen.monsterSpellManager.toAdd(explosion);
                demon.explosionDelay = 0.25f;
            }
        }

        if(demon.state == CHARGING && demon.chargeTimer > 0) {
            demon.stateTime += delta;
            demon.chargeTimer -= delta;
            demon.body.setLinearVelocity(demon.toPlayer);
        } else if(demon.state == CHARGING) {
            demon.charging = false;
            demon.state = ADVANCING;
        }
    }


}
