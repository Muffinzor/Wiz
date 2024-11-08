package wizardo.game.Spells.Frost.Icespear;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Arcane.Rifts.Rifts_Spell;
import wizardo.game.Spells.Fire.Fireball.Fireball_Explosion;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_miniExplosion;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Hybrid.CelestialStrike.CelestialStrike_Hit;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;

import static wizardo.game.Resources.SpellAnims.IcespearAnims.*;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Icespear_Projectile extends Icespear_Spell {

    boolean destroyed;
    Body body;
    RoundLight light;
    Vector2 direction;
    float rotation;

    float timerBeforeSplit;
    float duration;
    Monster splitMonster;

    public Icespear_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {

        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);
        timerBeforeSplit = 0;

        duration = MathUtils.random(0.5f, 0.75f);

    }

    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            createBody();
            createLight();
            initialized = true;
        }

        timerBeforeSplit += delta;
        stateTime += delta;
        adjustLight();
        drawFrame();

        if(canSplit && currentSplits < maxSplits) {
            split();
            destroyed = true;
        }

        if(destroyed || stateTime > 1.5f) {
            world.destroyBody(body);
            light.dimKill(0.2f);
            screen.spellManager.toRemove(this);
        }
    }

    /**
     * BESOIN FAIRE PROC RATE
     */
    public void handleCollision(Monster monster) {

        dealDmg(monster);

        if(timerBeforeSplit >= minimumTimeForSplit) {
            canSplit = true;
            splitMonster = monster;
        }

        Icespear_Hit hit = new Icespear_Hit(body.getPosition(), rotation);
        hit.setElements(this);
        screen.spellManager.toAdd(hit);

        if(nested_spell != null) {
            float chanceToProc = 0.5f;
            int quantity = 2 + nested_spell.getLvl();
            if(Math.random() > chanceToProc) {
                for (int i = 0; i < quantity; i++) {
                    Spell proj = nested_spell.clone();
                    proj.setElements(this);
                    proj.screen = screen;
                    proj.spawnPosition = new Vector2(monster.body.getPosition());
                    proj.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 0.5f);
                    screen.spellManager.toAdd(proj);
                }
            }
        }

        rift(monster);
        frostbolts();


    }

    public void split() {
        celestialStrike();
        currentSplits++;

        if(overheat && currentSplits == 1) {
            overheatSplit();
        } else if(fireball && currentSplits == 2) {
            fireballSplit();
        } else if(arcaneMissile) {
            arcaneSplit();
        } else {
            normalSplit();
        }
    }

    public void normalSplit() {
        int shards = 2;
        float initialAngle = rotation;
        float halfCone = 8f * shards / 2;
        float stepSize = 8f * shards / (shards - 1);

        for (int i = 0; i < shards; i++) {
            float angle = initialAngle - halfCone + (stepSize * i);
            Vector2 direction = new Vector2(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));
            Icespear_Projectile spear = new Icespear_Projectile(body.getPosition(), body.getPosition().cpy().add(direction));
            spear.currentSplits = currentSplits;
            spear.stateTime = stateTime;
            spear.setNextSpear(this);
            screen.spellManager.toAdd(spear);

            flamejetSplit(direction);
        }
    }

    public void arcaneSplit() {
        int shards = 2;
        ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(body.getPosition(), 5);
        inRange.remove(splitMonster);
        if(!inRange.isEmpty()) {
            for (int i = 0; i < shards; i++) {
                int index = (int) (Math.random() * inRange.size());
                Monster target = inRange.get(index);

                Icespear_Projectile spear = new Icespear_Projectile(body.getPosition(), target.body.getPosition());

                spear.currentSplits = currentSplits;
                spear.stateTime = stateTime;
                spear.setNextSpear(this);
                screen.spellManager.toAdd(spear);

                Vector2 direction = new Vector2(MathUtils.cosDeg(rotation), MathUtils.sinDeg(rotation));
                flamejetSplit(direction);

            }
        }
    }

    public void drawFrame() {


        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.rotate(rotation + 180);
        if(beam) {
            frame.setScale(2.5f);
        }
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

    }

    public void pickAnim() {
        switch(anim_element) {
            case FROST -> {
                anim = icespear_anim_frost;
                green = 0.15f;
                blue = 0.5f;
            }
            case FIRE -> {
                anim = icespear_anim_fire;
                red = 0.7f;
                green = 0.3f;
            }
            case ARCANE -> {
                anim = icespear_anim_arcane;
                red = 0.2f;
                green = 0.3f;
                blue = 0.75f;
            }
        }
    }


    public void createBody() {
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction = new Vector2(1,0);
        }

        int radius = 8;
        float actualSpeed = speed;
        if(beam) {
            radius = 20;
            actualSpeed += actualSpeed/2;
        }

        Vector2 offset = new Vector2(direction.cpy().scl(1));
        Vector2 adjustedSpawn = new Vector2(spawnPosition.add(offset));
        body = BodyFactory.spellProjectileCircleBody(adjustedSpawn, radius, true);
        body.setUserData(this);


        Vector2 velocity = direction.scl(actualSpeed);
        body.setLinearVelocity(velocity);
        rotation = velocity.angleDeg();
    }

    public void createLight() {
        int radius = 25;
        if(beam) {
            radius = 75;
        }

        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,1,radius, body.getPosition());
        light.toLightManager();
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public void celestialStrike() {
        if(celestialStrike) {
            float level = (getLvl() + player.spellbook.thunderstorm_lvl + player.spellbook.frozenorb_lvl) / 3f;
            float procRate = .85f - level * 0.05f;

            if(Math.random() >= procRate) {
                CelestialStrike_Hit strike = new CelestialStrike_Hit();
                strike.targetPosition = new Vector2(body.getPosition());
                strike.screen = screen;
                screen.spellManager.toAdd(strike);
            }
        }
    }

    public void flamejetSplit(Vector2 direction) {
        if(flamejet) {
            Flamejet_Spell jet = new Flamejet_Spell();
            jet.frostbolts = frostbolts;
            jet.spawnPosition = new Vector2(body.getPosition());
            jet.targetPosition = new Vector2(body.getPosition().add(direction));
            jet.icespear = true;
            jet.setElements(this);
            screen.spellManager.toAdd(jet);
        }
    }

    public void fireballSplit() {
        Fireball_Explosion explosion = new Fireball_Explosion();
        explosion.setElements(this);
        explosion.frostbolts = frostbolts;
        if(flamejet) {
            explosion.nested_spell = new Flamejet_Spell();
        }
        explosion.targetPosition = new Vector2(body.getPosition());
        screen.spellManager.toAdd(explosion);
    }

    public void overheatSplit() {
        Overheat_miniExplosion explosion = new Overheat_miniExplosion();
        explosion.setElements(this);
        explosion.fireball = true;
        explosion.targetPosition = new Vector2(body.getPosition());
        screen.spellManager.toAdd(explosion);
    }

    public void rift(Monster monster) {
        if(rift) {
            float procTreshold = 0.833f - .033f * player.spellbook.rift_lvl;

            if(Math.random() >= procTreshold) {
                Rift_Zone rift = new Rift_Zone(body.getPosition());
                rift.frostbolt = frostbolts;
                //rift.frozenorb = frozenrift;
                rift.setElements(this);
                screen.spellManager.toAdd(rift);
            }
        }
    }

    public void frostbolts() {
        if(frostbolts) {
            float procTreshold = 0.85f - .05f * player.spellbook.frostbolt_lvl;

            if(Math.random() >= procTreshold) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.targetPosition = new Vector2(body.getPosition());
                explosion.setElements(this);
                screen.spellManager.toAdd(explosion);
            }
        }
    }
}
