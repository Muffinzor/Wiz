package wizardo.game.Spells.Unique.BlackHole;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Wizardo.world;

public class BlackHole_KillBody extends Spell {

    public BlackHole_KillBody(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
        }
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 85);
        body.setUserData(this);
    }

    public void terminate() {
        world.destroyBody(body);
        screen.spellManager.toRemove(this);
    }

    public void handleCollision(Monster monster) {
        monster.hp = 0;
        monster.spaghettified = true;
    }

    public void handleCollision(MonsterSpell proj) {
        proj.blackholed = true;
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }

    @Override
    public int getDmg() {
        return 0;
    }
}
