package wizardo.game.Monsters.MonsterStateManager;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Monsters.MonsterUtils.MONSTER_STATE.*;
import static wizardo.game.Wizardo.player;

public class RangedStateManager implements StateManager {

    Monster monster;

    float minDst;
    float shootDst;

    int frameCounter;

    public RangedStateManager(Monster monster, float minDst, float shootDst) {
        this.monster = monster;
        this.minDst = MathUtils.random(minDst, minDst * 1.6f);
        this.shootDst = MathUtils.random(shootDst * 0.8f, shootDst * 1.2f);
    }

    public void updateState(float delta) {
        if(!monster.spawned) {
            return;
        }

        frameCounter ++;
        if(frameCounter >= 20) {
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

        if(dst < 1.1f * shootDst && dst > minDst) {
            monster.state = ATTACKING;
        } else if(dst < minDst) {
            monster.state = FLEEING;
        } else if (dst > shootDst){
            monster.state = ADVANCING;
        }

    }
}
