package wizardo.game.Spells.Hybrid.MeteorShower;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.SpellAnims.FireballAnims;
import wizardo.game.Resources.SpellAnims.MeteorAnims;
import wizardo.game.Spells.Fire.Overheat.Overheat_Explosion;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Meteor_Projectile extends MeteorShower_Spell {

    Vector2 startingPosition;
    Body body;
    RoundLight light;
    float lightRadius;
    float frameScale = 1;

    float rotation;
    Vector2 direction;

    boolean overheatProc;


    public Meteor_Projectile(Vector2 targetPosition) {
        this.targetPosition = targetPosition;
    }

    public void setup() {
        if(overheat) {
            float procRate = 0.92f - 0.02f * player.spellbook.overheat_lvl;
            if(Math.random() >= procRate) {
                overheatProc = true;
                frameScale = 1.3f;
            }
        }
    }

    public void update(float delta) {
        if(!initialized) {
            setup();
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
        if(overheatProc) {
            overheatExplosion();
        } else {
            regularExplosion();
        }
    }

    public void regularExplosion() {
        Meteor_Explosion explosion = new Meteor_Explosion(targetPosition);
        explosion.setElements(this);
        explosion.setMeteor(this);
        screen.spellManager.toAdd(explosion);
    }
    public void overheatExplosion() {
        Overheat_Explosion explosion = new Overheat_Explosion(targetPosition);
        explosion.thunderstorm = thunderstorm;
        explosion.setElements(this);
        screen.spellManager.toAdd(explosion);

        if(anim_element == FIRE) {
            Meteor_Crater crater = new Meteor_Crater(targetPosition);
            crater.setElements(this);
            screen.spellManager.toAdd(crater);
        }
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));

        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
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
        if(overheatProc) {
            lightRadius *= 1.3f;
        }
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
                anim = FireballAnims.fireball_anim_lightning;
                red = 0.7f;
                green = 0.55f;
                frameScale = 0.6f;
                lightAlpha = 1;
                lightRadius = 60f;
                speed = 11f;
            }
            case FIRE -> {
                anim = FireballAnims.fireball_anim_fire;
                red = 0.85f;
                green = 0.25f;
                lightAlpha = 1;
                lightRadius = 80f;
                speed = 8f;
            }
            case ARCANE -> {
                anim = FireballAnims.fireball_anim_arcane;
                red = 0.55f;
                blue = 0.85f;
                frameScale = 0.8f;
                lightRadius = 80f;
                speed = 8f;
            }
        }
    }
}
