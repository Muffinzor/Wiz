package wizardo.game.Spells.Hybrid.RepulsionField;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.RepulsionFieldAnims;
import wizardo.game.Spells.Spell;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class RepulsionField_Projectile extends Spell {

    RoundLight light;
    Body body;

    float rotation;
    Vector2 direction;

    boolean hasCollided;

    public boolean missile;
    public Monster targetMonster;


    public RepulsionField_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {

        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));

        speed = 320f/PPM * MathUtils.random(0.9f, 1.1f);

    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            createLight();
        }
        stateTime += delta;
        drawFrame();
        adjustLight();

        if(missile) {
            arcaneTargeting();
        }

        if(hasCollided || stateTime >= 2f) {
            world.destroyBody(body);
            light.dimKill(0.5f);
            RepulsionField_ProjExplosion explosion = new RepulsionField_ProjExplosion(body.getPosition());
            explosion.setElements(this);
            explosion.arcaneMissile = missile;
            screen.spellManager.toAdd(explosion);
            screen.spellManager.toRemove(this);
        }

    }

    public void handleCollision(Monster mosnter) {
        hasCollided = true;
    }
    public void handleCollision(Fixture obstacle) {
        hasCollided = true;
    }


    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(RepulsionFieldAnims.purple_proj);
        frame.setRotation(rotation);
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);

        screen.addSortedSprite(frame);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public void createBody() {
        body = BodyFactory.spellProjectileCircleBody(spawnPosition, 12, true);
        body.setUserData(this);

        direction.nor();
        Vector2 velocity = direction.scl(speed);
        body.setLinearVelocity(velocity);
        rotation = velocity.angleDeg();
    }

    public void createLight() {
        red = 0.6f;
        blue = 0.9f;
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha, 55, spawnPosition);
        screen.lightManager.addLight(light);
    }

    public void arcaneTargeting() {
        if (targetMonster != null && targetMonster.body != null && targetMonster.hp > 0) {
            Vector2 targetPosition = targetMonster.body.getPosition();
            Vector2 missilePosition = body.getPosition();

            Vector2 targetDirection = new Vector2(targetPosition).sub(missilePosition);
            if(targetDirection.len() > 0) {
                targetDirection.nor();
            } else {
                targetDirection.set(1,0);
            }
            float targetAngle = targetDirection.angleDeg();
            float currentAngle = direction.angleDeg();
            float angleDiff = targetAngle - currentAngle;

            //Find the shortest rotation turn
            if (angleDiff > 180) {
                angleDiff -= 360;
            } else if (angleDiff < -180) {
                angleDiff += 360;
            }

            //Clamp the angle difference to the maximum allowed increment
            float maxRotationPerFrame = 5f;
            float rotationStep = MathUtils.clamp(angleDiff, -maxRotationPerFrame, maxRotationPerFrame);

            direction.rotateDeg(rotationStep);

            if(direction.len() > 0) {
                direction.nor().scl(speed);
            }
            body.setLinearVelocity(direction);
            rotation = direction.angleDeg();
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
