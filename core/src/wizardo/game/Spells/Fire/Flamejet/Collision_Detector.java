package wizardo.game.Spells.Fire.Flamejet;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Spells.Spell;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.world;

public class Collision_Detector extends Spell {

    Flamejet_Projectile proj;
    Body body;
    Vector2 velocity;
    boolean hasCollided;

    public Collision_Detector(Vector2 velocity, Flamejet_Projectile proj) {
        this.velocity = new Vector2(velocity);
        this.proj = proj;
        screen = currentScreen;
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
        }
        if(hasCollided || body.getPosition().dst(spawnPosition) > 5) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
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

    public void createBody() {
        spawnPosition = new Vector2(proj.body.getPosition());
        body = BodyFactory.spellProjectileCircleBody(proj.body.getPosition(), 2, true);
        body.setUserData(this);
        body.setLinearVelocity(velocity);
    }

}
