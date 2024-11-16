package wizardo.game.Spells.Arcane.ArcaneMissiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Frost.Icespear.Icespear_Projectile;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;

import static wizardo.game.Resources.SpellAnims.ArcaneMissileAnims.arcanemissile_arcane_anim;
import static wizardo.game.Spells.SpellUtils.hasLineOfSight;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class ArcaneMissile_Projectile extends ArcaneMissile_Spell {

    Body body;
    RoundLight light;
    float scale = 1;

    float rotation;
    Vector2 direction;

    Monster targetMonster;
    boolean targetLocked;

    int collisions = 0;
    int collisionsToSplit;
    boolean canSplit;
    boolean hasCollided;
    public boolean hasSplit;


    public ArcaneMissile_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {
        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);

        anim = arcanemissile_arcane_anim;
    }

    public void update(float delta) {
        if(!initialized) {
            collisionsToSplit = MathUtils.random(1, 5);
            initialized = true;
            createBody();
            createLight();
        }

        stateTime += delta;
        drawFrame();
        adjustLight();
        arcaneTargeting();

        if(scale <= 0.1f) {
            light.dimKill(0.1f);
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
        if(stateTime >= 2.5f || hasCollided) {
            scale -= 0.02f;
        }

        if(canSplit) {
            split();
        }

    }

    public void handleCollision(Monster monster) {
        collisions++;
        dealDmg(monster);

        if(icespear && collisions >= collisionsToSplit) {
            canSplit = true;
        }

        if(rift && scale >= 0.05f) {
            float procRate = 0.925f - 0.025f * player.spellbook.rift_lvl;
            if(Math.random() >= procRate) {
                Rift_Zone rift = new Rift_Zone(body.getPosition());
                if(riftBolts) {
                    ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                    bolt.arcaneMissile = true;
                    rift.nested_spell = bolt;
                }
                rift.setElements(this);
                screen.spellManager.toAdd(rift);
                scale -= (0.5f - 0.025f * player.spellbook.rift_lvl);
            }

        }
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.rotate(rotation);
        if(scale < 1) {
            frame.setScale(scale);
        }
        screen.centerSort(frame, body.getPosition().y * PPM - 20);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction.set(1,0);
        }

        Vector2 offset = new Vector2(direction.cpy().scl(1));
        Vector2 adjustedSpawn = new Vector2(spawnPosition.add(offset));

        body = BodyFactory.spellProjectileCircleBody(adjustedSpawn, 5, true);
        body.setUserData(this);

        int missiles = 2;

        float angleVariation = Math.min(3f * missiles, 15);
        float randomAngle = MathUtils.random(-angleVariation, angleVariation);
        direction.rotateDeg(randomAngle);

        Vector2 velocity = direction.cpy().scl(speed);
        body.setLinearVelocity(velocity);
        rotation = velocity.angleDeg();

    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0.6f, 0.1f, 0.9f, 0.75f, 35, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        if(light != null) {
            light.pointLight.setPosition(body.getPosition().scl(PPM));
        }
    }

    public void arcaneTargeting() {
        if(!targetLocked && !screen.monsterManager.liveMonsters.isEmpty()) {

            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(body.getPosition(), 6, true);

            float max = 0;
            for(Monster monster : inRange) {
                if(monster.hp > max && monster.body != null && hasLineOfSight(body.getPosition(), monster.body.getPosition())) {
                    max = monster.hp;
                    targetMonster = monster;
                    targetLocked = true;
                }
            }

        } else if (targetLocked && targetMonster != null && targetMonster.body != null && targetMonster.hp > 0) {
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
            float maxRotationPerFrame = 6f;
            float rotationStep = MathUtils.clamp(angleDiff, -maxRotationPerFrame, maxRotationPerFrame);

            direction.rotateDeg(rotationStep);

            if(direction.len() > 0) {
                direction.nor().scl(speed);
            }
            body.setLinearVelocity(direction);
            rotation = direction.angleDeg();
        } else if (targetMonster != null && targetMonster.hp <= 0) {
            targetLocked = false;
        }

    }

    public void split() {
        world.destroyBody(body);
        light.dimKill(0.5f);
        screen.spellManager.toRemove(this);

        int missiles = 2 + player.spellbook.icespear_lvl / 5;
        float initialAngle = rotation;
        float halfCone = 12f * missiles / 2;
        float stepSize = 12f * missiles / (missiles - 1);

        for (int i = 0; i < missiles; i++) {
            float angle = initialAngle - halfCone + (stepSize * i);
            Vector2 direction = new Vector2(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));
            ArcaneMissile_Projectile missile = new ArcaneMissile_Projectile(body.getPosition(), body.getPosition().cpy().add(direction));
            missile.hasSplit = true;
            missile.setElements(this);
            missile.setMissile(this);
            screen.spellManager.toAdd(missile);
        }
    }
}
