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

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Flamejet_Projectile extends Flamejet_Spell {

    public Body body;
    Vector2 direction;
    Vector2 velocity;
    float rotation;
    boolean flipX;

    float distance = 5f;

    int lightFrameCounter;

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
            screen.spellManager.remove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        frostbolts(monster);
        rift(monster);

        if(monster.flamejetStacks < player.spellbook.flamejetBonus) {
            monster.flamejetStacks++;
        }
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setOrigin(frame.getWidth()/2f, 0);
        frame.setRotation(rotation - 90);

        Vector2 offset = new Vector2(direction);
        offset.nor().scl(-0.5f);
        Vector2 trueSpawnPosition = spawnPosition.cpy().add(offset);

        frame.setPosition(trueSpawnPosition.x * PPM - frame.getWidth()/2, trueSpawnPosition.y * PPM);



        float dst = spawnPosition.dst(body.getPosition()) + 2;
        float totalLength = frame.getHeight();
        float scale = dst*PPM/totalLength;
        frame.setScale(1.2f, 1.2f);
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


        if(!icespear && quantity > 1) {
            float angleVariation = Math.min(quantity * 5, 20);
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
        screen.spellManager.add(detector);
    }

    public void createLight() {
        lightFrameCounter++;
        if(lightFrameCounter >= 4) {
            lightFrameCounter = 0;
            if(multicasted) {
                lightAlpha = lightAlpha/2f;
            }
            RoundLight light = screen.lightManager.pool.getLight();
            light.setLight(red, green, blue, lightAlpha, 75, body.getPosition());
            screen.lightManager.addLight(light);
            light.dimKill(0.02f);
        }
    }

    public void frostbolts(Monster monster) {
        if(frostbolts) {
            float procRate = .90f - player.spellbook.frostbolt_lvl * 0.05f;
            if(Math.random() >= procRate) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 0.5f);
                explosion.setElements(this);
                screen.spellManager.add(explosion);
            }
        }
    }

    public void rift(Monster monster) {
        if(rift) {
            float procRate = .90f - player.spellbook.rift_lvl * 0.05f;
            if(Math.random() >= procRate) {
                Rift_Zone rift = new Rift_Zone(monster.body.getPosition());
                rift.setElements(this);
                screen.spellManager.add(rift);
            }
        }
    }

    public void pickAnim() {
        if(castByPawn) {
            if (quantity > 3) {
                lightAlpha = 0.85f - (quantity - 2) * 0.04f;
            } else {
                lightAlpha = 0.80f;
            }
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
            case FROST, COLDLITE -> {
                if(MathUtils.randomBoolean()) {
                    anim = FlamejetAnims.flamejet_frost_anim;
                } else {
                    anim = FlamejetAnims.flamejet_frost_anim2;
                }
                blue = 0.8f;
            }
            case ARCANE -> {
                if(MathUtils.randomBoolean()) {
                    anim = FlamejetAnims.flamejet_arcane_anim;
                } else {
                    anim = FlamejetAnims.flamejet_arcane_anim2;
                }
                red = 0.5f;
                green = 0.2f;
                blue = 0.75f;
            }
            case LIGHTNING -> {
                if(MathUtils.randomBoolean()) {
                    anim = FlamejetAnims.flamejet_lightning_anim;
                } else {
                    anim = FlamejetAnims.flamejet_lightning_anim2;
                }
                red = 0.55f;
                green = 0.5f;
            }
        }

    }

    public float getDistanceFromSpawn() {
        return body.getPosition().dst(spawnPosition);
    }
}
