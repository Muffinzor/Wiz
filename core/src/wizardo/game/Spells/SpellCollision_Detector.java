package wizardo.game.Spells;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Wizardo.world;

public class SpellCollision_Detector extends Spell {

    Spell proj;
    Vector2 velocity;
    float maxDistance;
    boolean hasCollided;
    float offset;

    public SpellCollision_Detector(Vector2 velocity, Spell proj, float offset, float maxDistance) {
        this.velocity = new Vector2(velocity);
        this.proj = proj;
        this.offset = offset;
        this.maxDistance = maxDistance;
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            calculateSpawnPosition();
            createBody();
        }
        if(hasCollided || body.getPosition().dst(spawnPosition) > maxDistance) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }
    }

    public void handleCollision(Fixture fix) {
        proj.body.setLinearVelocity(0,0);
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
        body = BodyFactory.spellProjectileCircleBody(spawnPosition, 2, true);
        body.setUserData(this);
        body.setLinearVelocity(velocity);
    }

    public void calculateSpawnPosition() {
        Vector2 direction = velocity.cpy().nor().scl(offset);
        Vector2 initialPosition = new Vector2(proj.body.getPosition());
        spawnPosition = initialPosition.add(direction);
    }

}
