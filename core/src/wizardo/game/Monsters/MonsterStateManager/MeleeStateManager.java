package wizardo.game.Monsters.MonsterStateManager;

import wizardo.game.Monsters.MonsterArchetypes.MonsterMelee;
import wizardo.game.Monsters.MonsterActions.Rush.Rush;
import wizardo.game.Monsters.MonsterUtils;

import static wizardo.game.Monsters.MonsterUtils.MONSTER_STATE.*;
import static wizardo.game.Wizardo.player;

public class MeleeStateManager implements StateManager {

    MonsterMelee monster;

    int frameCounter;
    float sprintCD;


    public MeleeStateManager(MonsterMelee monster) {
        this.monster = monster;
    }

    @Override
    public void updateState(float delta) {
        sprintCD -= delta;
        frameCounter ++;

        if(monster.state == CHARGING) {
            float actualDst = player.pawn.getPosition().dst(monster.body.getPosition());
            if(actualDst < monster.attackRange) {
                monster.state = ATTACKING;
            }
        }

        if(frameCounter >= 10) {
            frameCounter = 0;

            float actualDst = player.pawn.getPosition().dst(monster.body.getPosition());
            boolean hasLoS = MonsterUtils.hasCompleteLoS(monster);

            if (hasLoS) {
                hasLoS(actualDst);
            } else {
                monster.state = ADVANCING;
            }

        }
    }

    public void hasLoS(float dst) {

        if(dst < monster.attackRange) {
            monster.state = ATTACKING;
        } else if (dst < monster.rushDistance && sprintCD <= 0 && monster.state == ADVANCING) {
            monster.state = CHARGING;
            Rush sprint = new Rush(monster, 0.5f, 1.8f);
            monster.screen.monsterSpellManager.toAdd(sprint);
            sprintCD = 10;
        } else {
            monster.state = ADVANCING;
        }

    }
}
