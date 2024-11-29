package wizardo.game.Monsters.MonsterActions.Rush;

import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;

public class Rush extends MonsterSpell {

    float originalSpeed;
    float newSpeed;

    float duration;
    float ratio;


    public Rush(Monster monster, float duration, float ratio) {
        super(monster);

        this.duration = duration;
        this.ratio = ratio;
    }

    @Override
    public void checkState(float delta) {

        originMonster.stateTime += delta/2f;

        if(stateTime >= duration) {
            originMonster.speed = originalSpeed;
            screen.monsterSpellManager.toRemove(this);
        }

    }

    @Override
    public void drawFrame() {

    }

    @Override
    public void initialize() {
        originalSpeed = originMonster.speed;
        newSpeed = originalSpeed * ratio;
        if(originMonster.slowedTimer > 0) {
            newSpeed = newSpeed * originMonster.slowRatio;
        }
        originMonster.speed = newSpeed;
    }

    @Override
    public void dispose() {

    }
}
