package wizardo.game.Monsters.MonsterActions.TrackingMissile;

import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterArchetypes.Monster;

public class TrackingMissile_Action extends MonsterSpell {

    public TrackingMissile_Action(Monster monster) {
        super(monster);
    }

    @Override
    public void checkState(float delta) {
        TrackingMissile_Projectile laser = new TrackingMissile_Projectile(originMonster.body.getPosition(), originMonster);
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
