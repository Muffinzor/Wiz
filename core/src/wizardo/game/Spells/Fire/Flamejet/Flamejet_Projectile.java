package wizardo.game.Spells.Fire.Flamejet;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.FlamejetAnims;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Flamejet_Projectile extends Flamejet_Spell {

    public Body body;
    Vector2 direction;
    Vector2 velocity;
    float rotation;
    boolean flipX;

    float distance = 5f;

    public Flamejet_Projectile() {

        flipX = MathUtils.randomBoolean();

    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            pickAnim();
            createBody();
            createDetector(velocity);
        }

        drawFrame();
        stateTime += delta;

        if(getDistanceFromSpawn() > distance && !body.getLinearVelocity().isZero()) {
            body.setLinearVelocity(0,0);
        }

        if(!body.getLinearVelocity().isZero() && delta > 0) {
            createLight();
        }

        if(stateTime > 0.5f) {
            body.setActive(false);
        }

        if(stateTime > anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        frostbolts(monster);
        rift(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setOrigin(frame.getWidth()/2f, 0);
        frame.setRotation(rotation - 90);
        frame.setPosition(spawnPosition.x * PPM - frame.getWidth()/2f, spawnPosition.y * PPM);

        float dst = spawnPosition.dst(body.getPosition()) + 2;
        float totalLength = frame.getHeight();
        float scale = dst*PPM/totalLength;
        frame.setScale(1, scale);
        frame.flip(flipX, false);

        if(scale < 0.9f) {
            screen.addSortedSprite(frame);
        } else {
            screen.addOverSprite(frame);
        }
        //screen.centerSort(frame, originBody.getPosition().y * PPM);
    }

    public void createBody() {
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction = new Vector2(1,0);
        }


        if(!icespear) {
            float angleVariation = Math.min(quantity * 4, 16);
            float randomAngle = MathUtils.random(-angleVariation, angleVariation);
            direction.rotateDeg(randomAngle);
        }


        Vector2 offset = new Vector2(direction.cpy().scl(1f));
        Vector2 adjustedSpawn = new Vector2(spawnPosition.add(offset));
        body = BodyFactory.spellProjectileCircleBody(adjustedSpawn, 25, true);
        body.setUserData(this);

        velocity = direction.scl(speed);
        body.setLinearVelocity(velocity);
        rotation = velocity.angleDeg();
    }

    public void createDetector(Vector2 velocity) {
        Collision_Detector detector = new Collision_Detector(velocity, this);
        detector.screen = screen;
        screen.spellManager.toAdd(detector);
    }

    public void createLight() {
        RoundLight light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, 75, body.getPosition());
        screen.lightManager.addLight(light);
        light.dimKill(0.04f);
    }

    public void frostbolts(Monster monster) {
        if(frostbolts) {
            float procRate = .92f - player.spellbook.frostbolt_lvl * 0.02f;
            if(Math.random() >= procRate) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 0.5f);
                explosion.setElements(this);
                screen.spellManager.toAdd(explosion);
            }
        }
    }

    public void rift(Monster monster) {
        if(rift) {
            float procRate = .975f - player.spellbook.rift_lvl * 0.025f;
            if(Math.random() >= procRate) {
                Rift_Zone rift = new Rift_Zone(monster.body.getPosition());
                rift.setElements(this);
                screen.spellManager.toAdd(rift);
            }
        }
    }

    public void pickAnim() {
        if(quantity > 3) {
            lightAlpha = 0.75f - (quantity - 2) * 0.06f;
        } else {
            lightAlpha = 0.65f;
        }
        switch(anim_element) {
            case FIRE -> {
                if(MathUtils.randomBoolean()) {
                    anim = FlamejetAnims.flamejet_fire_anim;
                } else {
                    anim = FlamejetAnims.flamejet_fire_anim2;
                }
                red = 0.5f;
                green = 0.2f;
            }
            case FROST -> {
                if(MathUtils.randomBoolean()) {
                    anim = FlamejetAnims.flamejet_frost_anim;
                } else {
                    anim = FlamejetAnims.flamejet_frost_anim2;
                }
                blue = 0.5f;
            }
            case ARCANE -> {
                anim = FlamejetAnims.flamejet_arcane_anim;
                red = 0.5f;
                green = 0.2f;
                blue = 0.75f;
            }
        }

    }

    public float getDistanceFromSpawn() {
        return body.getPosition().dst(spawnPosition);
    }
}
