package wizardo.game.Spells.Arcane.Rifts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Wizardo.world;

public class Rift_PullBody extends Spell {

    Body body;

    float strength;
    float duration;
    float decay;

    public Rift_PullBody(Vector2 targetPosition, float radius, boolean overheat) {
        this.targetPosition = new Vector2(targetPosition);
        this.radius = radius;

        if(overheat) {
            strength = 3;
            duration = 0.85f;
            decay = 0.92f;
        } else {
            strength = 1.5f;
            duration = 0.55f;
            decay = 0.89f;
        }

    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
        }
        stateTime += delta;
        if(stateTime >= 0.3f) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }

    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void handleCollision(Monster monster) {
        try {
            Vector2 toCenter = body.getPosition().sub(monster.body.getPosition());
            monster.movementManager.applyPush(toCenter, strength, duration, 0.89f);
        } catch (Exception e) {
            // Do nothing, just be a failure.
        }
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
