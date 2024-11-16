package wizardo.game.Spells.Hybrid.MeteorShower;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.SpellAnims.MeteorAnims;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class Meteor_Projectile extends MeteorShower_Spell {

    Vector2 startingPosition;
    Body body;
    RoundLight light;
    float lightRadius;
    float frameScale = 1;
    float frameOffset;

    float rotation;
    Vector2 direction;


    public Meteor_Projectile(Vector2 targetPosition) {
        this.targetPosition = targetPosition;
    }

    public void update(float delta) {
        if(!initialized) {
            setStartingPosition();
            initialized = true;
            pickAnim();
            createBody();
            createLight();
        }

        drawFrame();
        adjustLight();
        stateTime += delta;

        if(hasArrived()) {
            explode();
            world.destroyBody(body);
            light.dimKill(0.5f);
            screen.spellManager.toRemove(this);
        }

    }

    public boolean hasArrived() {

        float idealDistance = startingPosition.dst(targetPosition);
        float actualDistance = body.getPosition().dst(startingPosition);

        return body.getPosition().dst(targetPosition) < 1f || actualDistance > idealDistance;
    }

    public void explode() {
        Meteor_Explosion explosion = new Meteor_Explosion(targetPosition);
        explosion.setElements(this);
        explosion.setMeteor(this);
        screen.spellManager.toAdd(explosion);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        Vector2 spritePosition = new Vector2(body.getPosition().add(direction.cpy().scl(frameOffset)));
        frame.setCenter(spritePosition.x * PPM, spritePosition.y * PPM);
        frame.setRotation(rotation);
        if(frameScale != 1) {
            frame.setScale(frameScale);
        }
        screen.centerSort(frame, targetPosition.y * PPM);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        body = BodyFactory.spellProjectileCircleBody(startingPosition, 5, true);
        body.setUserData(this);

        direction = new Vector2(targetPosition.cpy().sub(startingPosition).nor());
        Vector2 velocity = direction.cpy().scl(speed);
        body.setLinearVelocity(velocity);

        rotation = velocity.angleDeg();
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, lightRadius, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public void setStartingPosition() {
        float angleVariation = MathUtils.random(-20, 20);

        float angleRad = angleVariation * MathUtils.degreesToRadians;

        float distance = 12;

        float offsetX = distance * MathUtils.sin(angleRad);
        float offsetY = distance * MathUtils.cos(angleRad);

        startingPosition = new Vector2(targetPosition.x + offsetX, targetPosition.y + offsetY);
    }

    public void pickAnim() {
        switch(anim_element) {
            case LIGHTNING -> {
                anim = MeteorAnims.meteor_anim_lightning;
                red = 0.7f;
                green = 0.55f;
                frameScale = 0.6f;
                frameOffset = 1.5f;
                lightAlpha = 1;
                lightRadius = 60f;
                speed = 11f;
            }
            case FIRE -> {
                anim = MeteorAnims.meteor_anim_fire;
                red = 0.85f;
                green = 0.25f;
                frameScale = 1f;
                frameOffset = 2f;
                lightAlpha = 1;
                lightRadius = 80f;
                speed = 8f;
            }
        }
    }
}
