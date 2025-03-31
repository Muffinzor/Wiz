package wizardo.game.Monsters.MonsterActions.TrackingMissile;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Monsters.MonsterActions.SmallLaser.SmallLaser_Explosion;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Resources.MonsterResources.MonsterProjectiles.SmallLaserAnims;
import wizardo.game.Resources.MonsterResources.MonsterProjectiles.VoidOrbAnims;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Spells.SpellUtils.hasLineOfSight;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class TrackingMissile_Projectile extends MonsterSpell {

    boolean hasCollided;

    Sprite sprite;

    float red;
    float green;
    float blue;

    public TrackingMissile_Projectile(Vector2 spawnPosition, Monster monster) {
        super(monster);
        isProjectile = true;
        this.spawnPosition = new Vector2(spawnPosition);

        speed = 4;
        radius = 8;
        rotation = MathUtils.random(360);
    }

    @Override
    public void checkState(float delta) {
        if(delta > 0) {
            rotation += 5;
            PlayerTracking();
        }

        if(hasCollided || stateTime > 5f || sentBack) {
            SmallLaser_Explosion hit = new SmallLaser_Explosion(body.getPosition(), originMonster);
            hit.sentBack = sentBack;
            screen.monsterSpellManager.toAdd(hit);
            world.destroyBody(body);
            light.dimKill(0.5f);
            screen.monsterSpellManager.toRemove(this);
        }
    }

    @Override
    public void createBody() {
        if(targetPosition == null) {
            targetPosition = new Vector2(player.pawn.getPosition());
        }

        int random_angle = MathUtils.random(25, 45);
        if(MathUtils.randomBoolean()) {
            random_angle = -random_angle;
        }
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        direction.rotateDeg(random_angle);
        direction.nor();

        body = BodyFactory.monsterProjectileBody(spawnPosition, radius);
        body.setUserData(this);
        Vector2 velocity = direction.scl(speed);
        body.setLinearVelocity(velocity);
    }

    public void pickAnim() {
        sprite = VoidOrbAnims.void_orb;
        red = 0.7f;
        green = 0.9f;
        blue = 0.25f;
    }

    public void handleCollision(Fixture fix) {
        hasCollided = true;
        body.setLinearVelocity(0,0);
    }

    public void handleCollision(Body playerBody) {
        hasCollided = true;
        body.setLinearVelocity(0,0);
        originMonster.dealDmg();
    }

    @Override
    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(sprite);
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.setScale(0.8f);
        if(alpha < 1) {
            frame.setColor(redshift, 0.75f, 0.75f, alpha);
        }
        screen.centerSort(frame, body.getPosition().y * PPM - 25);
        screen.addSortedSprite(frame);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, 0.8f, 35, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    @Override
    public void initialize() {
        pickAnim();
        createBody();
        createLight();
    }

    @Override
    public void dispose() {

    }

    public void PlayerTracking() {
        Vector2 targetPosition = player.pawn.body.getPosition();
        Vector2 missilePosition = body.getPosition();

        Vector2 targetDirection = new Vector2(targetPosition).sub(missilePosition);
        if (targetDirection.len() > 0) {
            targetDirection.nor();
        } else {
            targetDirection.set(1, 0);
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

        float maxRotationPerFrame = 1;
        float rotationStep = MathUtils.clamp(angleDiff, -maxRotationPerFrame, maxRotationPerFrame);

        direction.rotateDeg(rotationStep);

        if (direction.len() > 0) {
            direction.nor().scl(speed);
        }
        body.setLinearVelocity(direction);
        rotation = direction.angleDeg();
    }
}
