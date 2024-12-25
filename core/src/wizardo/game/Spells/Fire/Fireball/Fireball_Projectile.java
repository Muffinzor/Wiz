package wizardo.game.Spells.Fire.Fireball;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.FireballAnims;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
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

    }

    public void update(float delta) {

        if(!initialized) {
            speed = getScaledSpeed();
            picKAnim();
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
            Fireball_Explosion explosion = new Fireball_Explosion();
            explosion.targetPosition = new Vector2(body.getPosition());
            explosion.inherit(this);
            screen.spellManager.toAdd(explosion);
            world.destroyBody(body);
            body = null;
            light.dimKill(0.5f);
            screen.spellManager.toRemove(this);
        }
    }

    public void drawSprite() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setRotation(rotation);
        frame.setScale(0.7f);
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
        body = BodyFactory.spellProjectileCircleBody(adjustedSpawn, 12, true);
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
        light.setLight(red, green, blue, lightAlpha, 180, spawnPosition);
        screen.lightManager.addLight(light);
    }
    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public void picKAnim() {
        lightAlpha = 0.8f;
        switch(anim_element) {
            case FIRE -> {
                anim = FireballAnims.fireball_anim_fire;
                red = 1f;
                green = 0.4f;
            }
            case FROST -> {
                anim = FireballAnims.fireball_anim_frost;
                red = 0.1f;
                blue = 0.8f;
            }
            case LIGHTNING -> {
                anim = FireballAnims.fireball_anim_lightning;
                red = 0.75f;
                green = 0.55f;
            }
            case ARCANE -> {
                anim = FireballAnims.fireball_anim_arcane;
                red = 0.6f;
                green = 0.0f;
                blue = 0.85f;
            }
        }
    }
}
