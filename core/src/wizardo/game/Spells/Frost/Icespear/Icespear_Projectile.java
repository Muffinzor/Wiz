package wizardo.game.Spells.Frost.Icespear;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Items.Equipment.Amulet.Legendary_MarkAmulet;
import wizardo.game.Items.Equipment.Amulet.Rare_IcespearAmulet;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Fire.Fireball.Fireball_Explosion;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_TriggerExplosion;
import wizardo.game.Spells.Fire.Overheat.Overheat_miniExplosion;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Hybrid.CelestialStrike.CelestialStrike_Hit;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Hit;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Spells.Unique.StaticOrb.StaticOrb;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;

import static wizardo.game.Resources.SpellAnims.IcespearAnims.*;
import static wizardo.game.Spells.SpellUtils.Spell_Element.FROST;
import static wizardo.game.Spells.SpellUtils.Spell_Element.LIGHTNING;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Icespear_Projectile extends Icespear_Spell {

    boolean destroyed;
    boolean hasSplit;
    Body body;
    RoundLight light;
    boolean lightKilled;
    Vector2 direction;
    float rotation;

    float timerBeforeSplit;
    Monster splitMonster;

    float scale = 1;

    int collisions;

    boolean breakAnimationDone; //for multifixture contact, prevents duplicate anims


    public Icespear_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {

        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);
        timerBeforeSplit = 0;

        if(player.inventory.equippedAmulet instanceof Rare_IcespearAmulet) {
            maxCollisions += 2;
        }

    }

    public void update(float delta) {
        if(!initialized) {
            minimumTimeForSplit *= (1 - player.spellbook.projSpeedBonus/100f);
            duration = duration * MathUtils.random(0.75f, 1);
            pickAnim();
            createBody();
            createLight();
            initialized = true;
        }

        timerBeforeSplit += delta;
        stateTime += delta;
        adjustLight();
        drawFrame();

        if(canSplit && currentSplits < maxSplits && !hasSplit) {
            split();
            if(indestructible) {
                hasSplit = true;
            } else {
                destroyed = true;
            }
        }

        if(collisions >= maxCollisions) {
            destroyed = true;
            Icespear_Break hit = new Icespear_Break(body.getPosition());
            screen.spellManager.add(hit);
        }

        if(destroyed || scale <= 0.05f) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
            if(!lightKilled) {
                light.dimKill(0.5f);
                lightKilled = true;
            }
        }

        if(stateTime >= duration) {
            scale -= 0.05f;
            if(!lightKilled) {
                light.dimKill(0.05f);
                lightKilled = true;
            }
        }
    }

    /**
     * BESOIN FAIRE PROC RATE
     */
    public void handleCollision(Monster monster) {
        if(overheat) {
            overheatChance();
        }

        if(frozenorb && anim_element == FROST) {
            float duration = 0.8f + 0.2f * player.spellbook.frozenorb_lvl;
            monster.applyFreeze(duration, duration * 2);
        }

        dealDmg(monster);

        if(scale > 0.5f) {

            if (timerBeforeSplit >= minimumTimeForSplit) {
                canSplit = true;
                splitMonster = monster;
            }

            Icespear_Hit hit = new Icespear_Hit(body.getPosition(), rotation);
            hit.beam = beam;
            hit.setElements(this);
            screen.spellManager.add(hit);

            if (nested_spell != null) {
                float chanceToProc = getProcRate();
                int quantity = getQuantity();
                if (Math.random() > chanceToProc) {
                    for (int i = 0; i < quantity; i++) {
                        Spell proj = nested_spell.clone();
                        proj.setElements(this);
                        proj.screen = screen;
                        proj.spawnPosition = new Vector2(monster.body.getPosition());
                        proj.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 0.5f);
                        screen.spellManager.add(proj);
                    }
                }
            }

            rift();
            frostbolts();
            thunderspear();

            collisions++;

            if(player.inventory.equippedAmulet instanceof Legendary_MarkAmulet && collisions < 5) {
                double procChance = Math.pow(2, -(collisions-1));
                if(Math.random() <= procChance ) {
                    monster.marked = true;
                }
            }


        }
    }

    public void handleCollision(Fixture fixture) {
        destroyed = true;
        if(!breakAnimationDone) {
            Icespear_Break hit = new Icespear_Break(body.getPosition());
            screen.spellManager.add(hit);
            breakAnimationDone = true;
        }
    }

    public float getProcRate() {
        float procRate = 1;
        float level = (getLvl() + nested_spell.getLvl())/2f;
        if(nested_spell instanceof ChargedBolts_Spell) {
            procRate = 0.925f - 0.025f * level;
        }
        return procRate;
    }

    public int getQuantity() {
        int quantity = 1;
        int level = getLvl() + nested_spell.getLvl();
        if(nested_spell instanceof ChargedBolts_Spell) {
            quantity = 3 + level/2;
        }
        return quantity;
    }

    public void split() {
        celestialStrike();
        lightingOrbSplit();
        thundersplit();
        currentSplits++;

        if(overheatBall && currentSplits == 1) {
            overheatSplit();
        } else if(fireball && currentSplits == 2) {
            fireballSplit();
        } else if(arcaneMissile) {
            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(body.getPosition(), 5, true);
            inRange.remove(splitMonster);
            if(!inRange.isEmpty()) {
                arcaneSplit(inRange);
            } else {
                normalSplit();
            }
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
            screen.spellManager.add(spear);

            flamejetSplit(direction);
        }
    }

    public void arcaneSplit(ArrayList<Monster> inRange) {
        int shards = 2;
        inRange.remove(splitMonster);
        if(!inRange.isEmpty()) {
            for (int i = 0; i < shards; i++) {
                int index = (int) (Math.random() * inRange.size());
                Monster target = inRange.get(index);

                Icespear_Projectile spear = new Icespear_Projectile(body.getPosition(), target.body.getPosition());

                spear.currentSplits = currentSplits;
                spear.stateTime = stateTime;
                spear.setNextSpear(this);
                screen.spellManager.add(spear);

                Vector2 direction = new Vector2(MathUtils.cosDeg(rotation), MathUtils.sinDeg(rotation));
                flamejetSplit(direction);

            }
        }
    }

    public void drawFrame() {

        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.rotate(rotation);
        if(scale != 1) {
            frame.setScale(scale);
        }
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
        screen.centerSort(frame, body.getPosition().y * PPM - 8);

    }

    public void pickAnim() {
        if(beam) {
            scale = 2.5f;
        }
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
                red = 0.25f;
                green = 0.1f;
                blue = 0.75f;
            }
            case LIGHTNING -> {
                anim = icespear_anim_lightning;
                green = 0.5f;
                blue = 0.65f;
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
        int radius = 45;
        if(beam) {
            radius = 75;
        }

        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,1,radius, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public void celestialStrike() {
        if(celestialStrike) {
            float level = (getLvl() + player.spellbook.thunderstorm_lvl + player.spellbook.frozenorb_lvl) / 3f;
            float procRate = .95f - level * 0.05f;

            if(Math.random() >= procRate) {
                CelestialStrike_Hit strike = new CelestialStrike_Hit();
                strike.targetPosition = new Vector2(body.getPosition());
                strike.screen = screen;
                screen.spellManager.add(strike);
            }
        }
    }
    public void lightingOrbSplit() {
        if(frozenorb && anim_element == LIGHTNING) {
            float procRate = 0.95f - 0.025f * player.spellbook.frozenorb_lvl;
            if(Math.random() >= procRate) {
                Frozenorb_Spell orb = new Frozenorb_Spell();
                orb.setElements(this);
                orb.nested_spell = new ChainLightning_Spell();
                orb.duration = 1.5f;
                orb.speed = 0;
                orb.spawnPosition = new Vector2(body.getPosition());
                orb.targetPosition =  new Vector2(body.getPosition().x + 1, body.getPosition().y + 1);
                screen.spellManager.add(orb);
            }
        }
    }

    public void flamejetSplit(Vector2 direction) {
        if(flamejet) {
            Flamejet_Spell jet = new Flamejet_Spell();
            jet.frostbolts = frostbolts;
            jet.lightAlpha = 0.75f;
            jet.spawnPosition = new Vector2(body.getPosition());
            jet.targetPosition = new Vector2(body.getPosition().add(direction));
            jet.icespear = true;
            jet.setElements(this);
            screen.spellManager.add(jet);
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
        screen.spellManager.add(explosion);
    }

    public void overheatSplit() {
        Overheat_miniExplosion explosion = new Overheat_miniExplosion();
        explosion.setElements(this);
        explosion.fireball = true;
        explosion.targetPosition = new Vector2(body.getPosition());
        screen.spellManager.add(explosion);
    }

    public void rift() {
        if(rift) {
            float procTreshold = 0.883f - .033f * player.spellbook.rift_lvl;

            if(Math.random() >= procTreshold) {
                Rift_Zone rift = new Rift_Zone(body.getPosition());
                rift.frostbolt = frostbolts;
                rift.setElements(this);
                screen.spellManager.add(rift);
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
                screen.spellManager.add(explosion);
            }
        }
    }

    public void thunderspear() {
        if(thunderspear) {
            float chargedboltsProc = 0.95f - 0.05f * player.spellbook.chargedbolt_lvl;

            if(Math.random() >= chargedboltsProc) {
                int quantity = 3 + player.spellbook.chargedbolt_lvl/2;
                for (int i = 0; i < quantity; i++) {
                    ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                    bolt.setElements(this);
                    bolt.anim_element = FROST;
                    bolt.spawnPosition = new Vector2(body.getPosition());
                    bolt.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 3);
                    screen.spellManager.add(bolt);
                }
            }
        }
    }
    public void thundersplit() {
        if(thunderspear) {
            float thunderProc = 0.65f - 0.05f * player.spellbook.thunderstorm_lvl;
            if(Math.random() >= thunderProc) {
                Thunderstorm_Hit thunder = new Thunderstorm_Hit(body.getPosition());
                thunder.setElements(this);
                thunder.anim_element = FROST;
                screen.spellManager.add(thunder);
            }
        }
    }

    public void overheatChance() {
        float procRate = 1 - .05f * player.spellbook.overheat_lvl;
        if(Math.random() >= procRate) {
            Overheat_TriggerExplosion explosion = new Overheat_TriggerExplosion();
            explosion.setElements(this);
            explosion.targetPosition = new Vector2(body.getPosition());
            screen.spellManager.add(explosion);
        }
    }
}
