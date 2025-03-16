package wizardo.game.Spells.Arcane.ArcaneMissiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Items.Equipment.Amulet.Legendary_MarkAmulet;
import wizardo.game.Items.Equipment.Hat.Epic_TeleportHat;
import wizardo.game.Items.Equipment.Staff.Epic_MissileStaff;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Spells.Unique.TeleportMonster;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Resources.SpellAnims.ArcaneMissileAnims.*;
import static wizardo.game.Spells.SpellUtils.hasLineOfSight;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Utils.Contacts.Masks.OBSTACLE;
import static wizardo.game.Wizardo.*;

public class ArcaneMissile_Projectile extends ArcaneMissile_Spell {

    Body body;
    RoundLight light;
    float frameScale = 0.225f;

    float rotation;
    Vector2 direction;

    Monster targetMonster;
    boolean targetLocked;

    int collisions = 0;
    int collisionsToSplit;
    boolean canSplit;
    boolean hasCollided;
    public boolean hasSplit;

    float duration = 1;
    float animTimeBuffer;


    public ArcaneMissile_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {
        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);

        speed = MathUtils.random(speed * 0.9f, speed * 1.1f);

        animTimeBuffer = (float) Math.random();
        stateTime = animTimeBuffer;

    }

    public void update(float delta) {
        if(!initialized) {
            collisionsToSplit = MathUtils.random(1, 5);
            speed = getScaledSpeed();
            initialized = true;
            pickAnim();
            createBody();
            createLight();
        }

        stateTime += delta;
        drawFrame();
        adjustLight();

        if(delta > 0 && stateTime >= 0.4f && !hasCollided) {
            arcaneTargeting();
        }

        if(scale <= 0.1f) {
            frostboltExplosion();
            light.dimKill(0.1f);
            world.destroyBody(body);
            screen.spellManager.remove(this);
            return;
        }

        if(hasCollided && body.isActive()) {
            body.setActive(false);
        }

        if((hasCollided || stateTime - animTimeBuffer >= duration) && delta > 0) {
            scale -= 0.02f;
            if(hasCollided) {
                scale -= 0.03f;
            }
        }

        if(canSplit && !hasSplit) {
            split();
        }
    }

    public void handleCollision(Monster monster) {
        collisions++;
        targetLocked = false;
        dealDmg(monster);

        if(player.inventory.equippedAmulet instanceof Legendary_MarkAmulet && collisions < 5) {
            double procChance = Math.pow(2, -(collisions-1));
            if(Math.random() <= procChance ) {
                monster.marked = true;
            }
        }

        if(!(player.inventory.equippedStaff instanceof Epic_MissileStaff)) {
            float scaleLoss = 0.225f - 0.025f * getLvl();
            scale -= scaleLoss;
        }

        frostbolt(monster);
        rifts();
        overheat();
        chargedbolts();
    }

    public void handleCollision(Fixture fix) {
        boolean isObstacle = (fix.getFilterData().categoryBits & OBSTACLE) != 0;
        if(isObstacle) {
            hasCollided = true;
        }

    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.rotate(rotation);

        frame.setScale(scale * frameScale);

        screen.centerSort(frame, body.getPosition().y * PPM - 20);
        screen.addSortedSprite(frame);
    }

    public void pickAnim(){
        switch (anim_element) {
            case ARCANE -> {
                anim = arcanemissile_anim_arcane;
                red = 0.6f;
                green = 0.1f;
                blue = 0.9f;
            }
            case FIRE -> {
                anim = arcanemissile_anim_fire;
                red = 0.85f;
                green = 0.2f;
            }
            case FROST -> {
                anim = arcanemissile_anim_frost;
                blue = 0.7f;
            }
            case LIGHTNING -> {
                anim = arcanemissile_anim_lightning;
                green = 0.5f;
                red = 0.5f;
            }
        }
    }

    public void createBody() {
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction.set(1,0);
        }

        direction.rotateDeg(MathUtils.random(-25,25));
        speed *= MathUtils.random(0.95f, 1.05f);

        Vector2 offset = new Vector2(direction.cpy().scl(1));
        Vector2 adjustedSpawn = new Vector2(spawnPosition.add(offset));

        body = BodyFactory.spellProjectileCircleBody(adjustedSpawn, 5, true);
        body.setUserData(this);

        int missiles = 2 + player.spellbook.arcanemissile_lvl / 3;

        float angleVariation = Math.min(4f * missiles, 16);
        float randomAngle = MathUtils.random(-angleVariation, angleVariation);
        direction.rotateDeg(randomAngle);

        Vector2 velocity = direction.cpy().scl(speed);
        body.setLinearVelocity(velocity);
        rotation = velocity.angleDeg();

    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, 0.9f, 30, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        if(light != null) {
            light.pointLight.setPosition(body.getPosition().scl(PPM));
        }
    }

    public void arcaneTargeting() {
        if(!targetLocked && !screen.monsterManager.liveMonsters.isEmpty()) {

            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(body.getPosition(), 7, true);
            Collections.shuffle(inRange);

            float max = 0;
            for(Monster monster : inRange) {
                if(monster.hp > max && monster.body != null && hasLineOfSight(body.getPosition(), monster.body.getPosition())) {
                    max = monster.hp;
                    targetMonster = monster;
                    targetLocked = true;
                    if(MathUtils.randomBoolean()) {
                        break;
                    }
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
            float maxRotationPerFrame = turnSpeed;
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
        screen.spellManager.remove(this);

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
            screen.spellManager.add(missile);
        }
    }

    public void frostboltExplosion() {
        if(frostbolt) {
            Frostbolt_Explosion explosion = new Frostbolt_Explosion();
            explosion.setElements(this);
            explosion.targetPosition = new Vector2(body.getPosition());
            screen.spellManager.add(explosion);
        }
    }
    public void frostbolt(Monster monster) {
        if(frostbolt) {
            float freezeProc = 0.9f - 0.1f * player.spellbook.frostbolt_lvl;
            if(Math.random() >= freezeProc) {
                monster.applyFreeze(1.5f, 3);
            } else {
                monster.applySlow(1.5f, 0.8f);
            }
        }
    }

    public void rifts() {
        if(rift && scale >= 0.05f) {
            float procRate = 0.95f - 0.05f * player.spellbook.rift_lvl;
            if(Math.random() >= procRate) {
                Rift_Zone rift = new Rift_Zone(body.getPosition());
                if(chargedbolts) {
                    ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                    bolt.arcaneMissile = true;
                    rift.nested_spell = bolt;
                }
                rift.setElements(this);
                screen.spellManager.add(rift);
                scale -= 0.2f;
            }
        }
    }

    public void chargedbolts() {
        if(chargedbolts) {
            float procRate = 0.9f - 0.1f * player.spellbook.chargedbolt_lvl;
            int quantity = 3 + player.spellbook.chargedbolts_bonus_proj;
            if(Math.random() >= procRate) {
                for (int i = 0; i < quantity; i++) {
                    ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                    bolt.spawnPosition = new Vector2(body.getPosition());
                    bolt.targetPosition = new Vector2(SpellUtils.getRandomVectorInRadius(body.getPosition(), 2));
                    bolt.setElements(this);
                    screen.spellManager.add(bolt);
                }
            }
        }
    }

    public void overheat() {
        if(overheat && scale > 0.05f) {
            float procRate = scale;
            if(scale > 0.2f && Math.random() >= procRate) {
                ArcaneMissile_Explosion explosion = new ArcaneMissile_Explosion(body.getPosition());
                explosion.setElements(this);
                explosion.flamejet = flamejet;
                screen.spellManager.add(explosion);
                scale = 0;
            }
        }
    }
}
