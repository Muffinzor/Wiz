package wizardo.game.Spells.Fire.Fireball;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.FireballAnims;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.world;

public class Fireball_Projectile extends Fireball_Spell {

    boolean initialized;
    boolean hasCollided;

    public Body body;
    public RoundLight light;
    public Vector2 direction;
    public float rotation;

    public Fireball_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {

        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);

        screen = currentScreen;

        anim = FireballAnims.fireball_anim_fire;

    }

    public void update(float delta) {
        if(!initialized) {
            createBody();
            createLight();
            initialized = true;
        }

        stateTime += delta;
        drawSprite();
        adjustLight();
        explode();

    }

    @Override
    public void handleCollision(Monster monster) {
        hasCollided = true;
    }

    @Override
    public void handleCollision(Fixture fix) {
        hasCollided = true;
    }

    public void explode() {
        if(hasCollided || stateTime > 3) {
            Fireball_Explosion explosion = new Fireball_Explosion(body.getPosition());
            screen.spellManager.toAdd(explosion);
            world.destroyBody(body);
            body = null;
            light.kill();
            screen.spellManager.toRemove(this);
        }
    }

    public void drawSprite() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setRotation(rotation);
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        screen.centerSort(frame, body.getPosition().y * PPM);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction = new Vector2(1,0);
        }

        Vector2 offset = new Vector2(direction.cpy().scl(1));
        Vector2 adjustedSpawn = new Vector2(spawnPosition.add(offset));
        body = BodyFactory.spellProjectileCircleBody(adjustedSpawn, 10, true);
        body.setUserData(this);

        /*
        if(player.staff instanceof FireballStaff) {
            float angleVariation = 7;
            float randomAngle = MathUtils.random(-angleVariation, angleVariation);
            direction.rotateDeg(randomAngle);
        }

        */
        Vector2 velocity = direction.scl(speed);
        body.setLinearVelocity(velocity);
        rotation = velocity.angleDeg();
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(1, 0.4f, 0, 0.8f, 100, spawnPosition);
    }
    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }
}
