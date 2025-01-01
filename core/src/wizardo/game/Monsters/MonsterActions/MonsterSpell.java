package wizardo.game.Monsters.MonsterActions;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public abstract class MonsterSpell implements Cloneable {

    public Body body;
    public RoundLight light;
    public float alpha = 1;
    public Monster originMonster;

    public boolean initialized;
    public float stateTime;
    public float speed;
    public boolean isProjectile;
    public float radius;
    public float rotation;
    public Vector2 spawnPosition;
    public Vector2 targetPosition;
    public Vector2 direction;

    public BattleScreen screen;

    public boolean sentBack;     //Repulsion Field flag
    public Body blackholeBody = null;
    public boolean blackholed;
    public float pulledTime;
    public float redshift = 0.75f;

    public MonsterSpell(Monster monster) {
        this.originMonster = monster;
    }


    public void update(float delta) {
        if(!initialized) {
            initialize();
            initialized = true;
        }

        stateTime += delta;
        drawFrame();

        if(isProjectile && blackholed) {
            blackholed();
        } else {
            adjustLight();
            blackholeTargeting();
            checkState(delta);
        }


    }

    public abstract void checkState(float delta);

    public void handleCollision(Fixture fix) {

    }

    public void handleCollision(Body playerBody) {

    }

    public abstract void drawFrame();

    public void adjustLight() {

    }

    public void blackholed() {
        if(light != null) {
            body.setActive(false);
            light.dimKill(0.1f);
            light = null;
        }

        alpha -= 0.025f;
        redshift += 0.025f;

        if(alpha <= 0.015f) {
            world.destroyBody(body);
            screen.monsterSpellManager.toRemove(this);
        }
    }
    public void blackholeTargeting() {

        if (isProjectile && blackholeBody != null) {
            float dst = body.getPosition().dst(blackholeBody.getPosition());

            if(dst >= 220f/PPM) {
                blackholeBody = null;
                return;
            }

            Vector2 targetPosition = blackholeBody.getPosition();
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
            float maxRotationPerFrame = Math.max(8/dst, 2);
            float rotationStep = MathUtils.clamp(angleDiff, -maxRotationPerFrame, maxRotationPerFrame);

            direction.rotateDeg(rotationStep);

            if(direction.len() > 0) {
                direction.nor().scl(speed);
            }
            body.setLinearVelocity(direction);
            rotation = direction.angleDeg();
        }
    }

    public abstract void initialize();

    /** Default set up for projectile **/
    public void createBody() {

        if(targetPosition == null) {
            targetPosition = new Vector2(player.pawn.getPosition());
        }

        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        direction.nor();

        body = BodyFactory.monsterProjectileBody(spawnPosition, radius);
        body.setUserData(this);
        Vector2 velocity = direction.scl(speed);
        rotation = velocity.angleDeg();
        body.setLinearVelocity(velocity);

    }

    public abstract void dispose();

    @Override
    public MonsterSpell clone() {
        try {
            MonsterSpell clone = (MonsterSpell) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
