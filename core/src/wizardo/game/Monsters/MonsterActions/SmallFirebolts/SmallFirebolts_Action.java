package wizardo.game.Monsters.MonsterActions.SmallFirebolts;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterArchetypes.Monster;

public class SmallFirebolts_Action extends MonsterSpell {

    public int quantity;

    public SmallFirebolts_Action(Monster monster) {
        super(monster);
    }

    @Override
    public void checkState(float delta) {

        quantity = MathUtils.random(2,3);

        for (int i = 0; i < quantity; i++) {
            SmallFirebolts_Projectile bolt = new SmallFirebolts_Projectile(originMonster.body.getPosition(), originMonster);
            screen.monsterSpellManager.toAdd(bolt);
        }

        screen.monsterSpellManager.toRemove(this);

    }

    @Override
    public void drawFrame() {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void dispose() {

    }
}
