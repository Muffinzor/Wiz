package wizardo.game.Monsters.MonsterActions.SmallProjectile;

import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterArchetypes.Monster;

public class SmallLaser_Action extends MonsterSpell {

    public SmallLaser_Action(Monster monster) {
        super(monster);
    }

    @Override
    public void checkState(float delta) {

        SmallLaser_Projectile laser = new SmallLaser_Projectile(originMonster.body.getPosition(), originMonster);
        screen.monsterSpellManager.toAdd(laser);

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
