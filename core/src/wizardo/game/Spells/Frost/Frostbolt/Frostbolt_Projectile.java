package wizardo.game.Spells.Frost.Frostbolt;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Items.Equipment.Amulet.Legendary_FireballAmulet;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Fire.Overheat.Overheat_Explosion;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Hit;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Resources.SpellAnims.FrostboltAnims.*;
import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Spells.SpellUtils.Spell_Element.FROST;
import static wizardo.game.Spells.SpellUtils.hasLineOfSight;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Frostbolt_Projectile extends Frostbolt_Spell{

    int totalCollisions;
    private boolean hasCollided;
    private Body body;
    private float alpha = 1;
    private float frameScale = 1;
    private RoundLight light;
    private float rotation;
    private Vector2 direction;

    Monster targetMonster;
    boolean targetLocked;

    public Frostbolt_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {
        stateTime = (float) Math.random();
        this.spawnPosition = spawnPosition;
        this.targetPosition = targetPosition;
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));

        speed = speed * MathUtils.random(0.9f, 1.1f);

    }

    @Override
    public void update(float delta) {

        stateTime += delta;

        if(!initialized) {
            speed = getScaledSpeed();
            pickAnim();
            createBody();
            createLight();
            initialized = true;
        }

        drawFrame();
        updateLight();

        if(missile) {
            arcaneTarget();
        }

        if(hasCollided) {

            if(superBolt) {
                superExplosion();
            } else {
                regularExplosion();
            }

            world.destroyBody(body);
            body = null;
            light.dimKill(0.5f);
            screen.spellManager.remove(this);

        } else if(stateTime > 2) {
            alpha -= 0.02f;

            if(alpha <= 0) {
                world.destroyBody(body);
                body = null;
                light.dimKill(0.1f);
                screen.spellManager.remove(this);
            }
        }
    }

    public void handleCollision(Monster monster) {
        if(beam) {
            dealDmg(monster);
        }
        hasCollided = true;
        legendaryAmuletEffect(monster);
        rift();
        totalCollisions++;
    }

    public void legendaryAmuletEffect(Monster monster) {
        if(player.inventory.equippedAmulet instanceof Legendary_FireballAmulet) {
            dealDmg(monster);
            hasCollided = false;
            if(Math.random() > 0.9f || totalCollisions == 0) {
                regularExplosion();
            }
        }
    }

    public void pickAnim() {
        switch(anim_element) {
            case FROST -> {
                anim = frostbolt_projectile_anim_frost;
                blue = 0.8f;
            }
            case LIGHTNING, COLDLITE -> {
                anim = frostbolt_projectile_anim_lightning;
                red = 0.5f;
                green = 0.5f;
                frameScale = 0.5f;
            }
            case FIRE -> {
                anim = frostbolt_projectile_anim_frost;
                red = 0.7f;
                green = 0.25f;
            }
            case ARCANE -> {
                anim = frostbolt_projectile_anim_arcane;
                red = 0.8f;
                blue = 0.75f;
            }
        }
    }

    public void regularExplosion() {
        thunder();

        Frostbolt_Explosion explosion = new Frostbolt_Explosion();
        explosion.targetPosition = new Vector2(body.getPosition());
        explosion.setBolt(this);
        explosion.setElements(this);
        screen.spellManager.add(explosion);
    }

    public void thunder() {
        if(thunderstorm) {
            float proc_chance = 0.90f - 0.05f * player.spellbook.thunderstorm_lvl;
            if(Math.random() >= proc_chance) {
                Vector2 randomPos = SpellUtils.getClearRandomPosition(body.getPosition(), 1);
                Thunderstorm_Hit thunder = new Thunderstorm_Hit(randomPos);
                thunder.setElements(this);
                screen.spellManager.add(thunder);
            }
        }
    }

    public void superExplosion() {
        float procRate = 0.98f - 0.01f * (player.spellbook.overheat_lvl + player.spellbook.fireball_lvl);
        if(Math.random() > procRate) {
            Overheat_Explosion mini_overheat = new Overheat_Explosion(new Vector2(body.getPosition()));
            mini_overheat.setElements(this);
            mini_overheat.small = true;
            mini_overheat.fireball = true;
            screen.spellManager.add(mini_overheat);
        } else {
            regularExplosion();
        }
    }

    public void handleCollision(Fixture obstacle) {
        hasCollided = true;
    }

    public void createBody() {
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction = new Vector2(1,0);
        }

        if(projectiles > 1) {
            float angleVariation = Math.min(projectiles * 2, 8);
            float randomAngle = MathUtils.random(-angleVariation, angleVariation);
            direction.rotateDeg(randomAngle);
        }

        Vector2 offset = new Vector2(direction.cpy().scl(0.5f));
        Vector2 adjustedSpawn = new Vector2(spawnPosition.add(offset));
        body = BodyFactory.spellProjectileCircleBody(adjustedSpawn, 6, true);
        body.setUserData(this);

        Vector2 velocity = direction.scl(speed);
        body.setLinearVelocity(velocity);
        rotation = velocity.angleDeg();
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,1,35, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void updateLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public void drawFrame() {

        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.rotate(rotation);
        frame.setScale(frameScale);
        if(alpha < 1) {
            frame.setAlpha(alpha);
        }
        if(anim_element.equals(FIRE)) {
            frame.setColor(1,0.5f,0.5f,alpha);
        }
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
        screen.centerSort(frame, body.getPosition().y * PPM - 5);

    }

    public void arcaneTarget() {

        if(stateTime > 0.25f) {

            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(body.getPosition(), 8, true);

            if(!targetLocked && !inRange.isEmpty()) {

                float min = Float.MAX_VALUE;
                for(Monster monster : inRange) {
                    if(monster.hp > 0 && monster.body != null && hasLineOfSight(body.getPosition(), monster.body.getPosition())) {
                        float dst = body.getPosition().dst(monster.body.getPosition());
                        if(dst < min) {
                            targetMonster = monster;
                            targetLocked = true;
                            min = dst;
                        }
                    }
                }

            } else if (targetLocked && targetMonster != null && targetMonster.hp > 0) {
                Vector2 targetPosition = targetMonster.body.getPosition();
                Vector2 missilePosition = body.getPosition();

                Vector2 targetDirection = new Vector2(targetPosition).sub(missilePosition);
                if(targetDirection.len() > 0) {
                    targetDirection.nor();
                } else {
                    targetDirection = new Vector2 (1,0);
                }
                float targetAngle = targetDirection.angleDeg();
                float currentAngle = direction.angleDeg();
                float angleDiff = targetAngle - currentAngle;

                // find the shortest rotation direction
                if (angleDiff > 180) {
                    angleDiff -= 360;
                } else if (angleDiff < -180) {
                    angleDiff += 360;
                }

                float maxRotationPerFrame = 3 + 0.3f * player.spellbook.arcanemissile_lvl;
                float rotationStep = MathUtils.clamp(angleDiff, -maxRotationPerFrame, maxRotationPerFrame);

                direction.rotateDeg(rotationStep);

                if (direction.len() > 0) {
                    direction.nor().scl(speed);
                } else {
                    direction = new Vector2(1,0);
                }
                body.setLinearVelocity(direction);
                rotation = direction.angleDeg();
            } else if (targetMonster != null && targetMonster.hp <= 0) {
                targetLocked = false;
            }
        }
    }

    public void rift() {
        if(rifts) {
            float procRate = 0.75f - 0.025f * player.spellbook.rift_lvl;
            if(Math.random() >= procRate) {
                Rift_Zone rift = new Rift_Zone(new Vector2(body.getPosition()));
                rift.setElements(this);
                screen.spellManager.add(rift);
            }
        }
    }


    public void dispose() {

    }
}
