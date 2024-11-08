package wizardo.game.Spells.Arcane.EnergyBeam;

import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class EnergyBeam_FreezeBody extends Spell {

    Body body;

    public EnergyBeam_FreezeBody() {

    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            createBody();
            initialized = true;
        }

        stateTime += delta;

        if(stateTime >= 0.2f) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }

    }

    public void createBody() {
        float rad = 24 + 6 * player.spellbook.frozenorb_lvl;
        body = BodyFactory.spellExplosionBody(targetPosition, rad);
        body.setUserData(this);
    }

    public void handleCollision(Monster monster) {
        float freezeDuration = 1.4f + 0.1f * player.spellbook.frozenorb_lvl;
        monster.applyFreeze(freezeDuration, freezeDuration * 2.5f);
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
