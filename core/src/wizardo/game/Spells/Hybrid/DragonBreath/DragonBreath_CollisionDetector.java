package wizardo.game.Spells.Hybrid.DragonBreath;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Spells.Spell;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Wizardo.world;

public class DragonBreath_CollisionDetector extends Spell {

    Body proj;
    Body body;
    Vector2 velocity;
    boolean hasCollided;

    public DragonBreath_CollisionDetector(Vector2 velocity, Body proj) {
        this.velocity = new Vector2(velocity.cpy().scl(1.5f));
        this.proj = proj;
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
        }
        if(hasCollided || body.getPosition().dst(spawnPosition) > 12) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void handleCollision(Fixture fix) {
        proj.setLinearVelocity(0,0);
        body.setLinearVelocity(0,0);
        hasCollided = true;
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

    public void createBody() {
        spawnPosition = new Vector2(proj.getPosition());
        body = BodyFactory.spellProjectileCircleBody(spawnPosition, 2, true);
        body.setUserData(this);
        body.setLinearVelocity(velocity);
    }

}
