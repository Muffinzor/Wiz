package wizardo.game.Spells.Fire.Overheat;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.OverheatAnims;
import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Projectile;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Hit;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Overheat_Explosion extends Overheat_Spell {

    Body body;
    RoundLight light;

    boolean flipX;
    boolean flipY;
    int rotation;

    float pushStrength;
    float pushDuration;
    float pushDecay = 0.89f;

    public boolean fromThunder;  //spawned from thunderstorm + overheat +chargedbolt
    public boolean small; // spawned from superBolt

    public Overheat_Explosion(Vector2 targetPosition) {

        this.targetPosition = new Vector2(targetPosition);

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

    }

    public void setup() {
        pickAnim();
        createBody();
        createLight();
        sendProjectiles();
        if(thunderstorm) {
            pushStrength = 7 + 2 * player.spellbook.thunderstorm_lvl;
            pushDuration = 0.75f;
            pushDecay = 0.91f;
        } else {
            pushStrength = 4;
            pushDuration = 0.3f;
        }
        pushStrength *= (1 + player.spellbook.pushbackBonus/100f);
    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            setup();
        }

        drawFrame();

        stateTime += delta;

        if(stateTime >= 0.2f) {
            body.setActive(false);
            light.dimKill(0.01f);
        }

        delayedFrostbolts(delta);

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        fireball(monster);
        pushBack(monster);
    }

    public void pushBack(Monster monster) {
        Vector2 direction = monster.body.getPosition().sub(body.getPosition());
        if (fromThunder) {
            monster.movementManager.applyPush(direction, 1, 0.25f, 0.89f);
        } else {
            monster.movementManager.applyPush(direction, pushStrength, pushDuration, pushDecay);
        }
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.setScale(1.4f);
        if(small) {
            frame.setScale(0.6f);
        }
        frame.flip(flipX, flipY);
        screen.centerSort(frame, body.getPosition().y * PPM - 30);
        screen.addSortedSprite(frame);
    }

    public void pickAnim() {
        switch(anim_element) {
            case FROST -> {
                anim = OverheatAnims.overheat_anim_frost;
                green = 0.15f;
                blue = 0.5f;
            }
            case FIRE -> {
                anim = OverheatAnims.overheat_anim_fire;
                red = 0.75f;
                green = 0.25f;
            }
            case LIGHTNING -> {
                anim = OverheatAnims.overheat_anim_lightning;
                red = 0.5f;
                green = 0.45f;
            }
        }
    }

    public void createBody() {
        if(targetPosition == null) {
            targetPosition = new Vector2(player.pawn.body.getPosition());
        }
        if(small) {
            radius = radius/2.4f;
        } else if(thunderstorm) {
            radius = radius - 20;
        }
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createLight() {
        float lightRadius = 500;
        if(small) {
            lightRadius = 150;
        }
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, 1, lightRadius, targetPosition);
        screen.lightManager.addLight(light);
    }

    public void immediateFrostbolts() {
        if(frostbolts) {
            int quantity = 3 + 3 * player.spellbook.frostbolt_lvl;
            float radius = 4 + player.spellbook.frostbolt_lvl;
            for (int i = 0; i < quantity; i++) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.setElements(this);
                explosion.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), radius);
                screen.spellManager.add(explosion);
            }
        }
    }

    public void delayedFrostbolts(float delta) {
        if(frostbolts) {
            float interval = 0.2f - 0.04f * player.spellbook.frostbolt_lvl;
            if(stateTime % interval < delta) {
                float radius = 4 + player.spellbook.frostbolt_lvl;
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.setElements(this);
                explosion.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), radius);
                screen.spellManager.add(explosion);
            }
        }
    }

    public void fireball(Monster monster) {
        if(fireball) {
            float procRate = 0.9f - 0.1f * player.spellbook.fireball_lvl;
            if(Math.random() >= procRate) {
                Overheat_TriggerExplosion fireball = new Overheat_TriggerExplosion();
                fireball.frozenorb = frozenorb;
                fireball.setElements(this);
                fireball.targetPosition = new Vector2(monster.body.getPosition());
                screen.spellManager.add(fireball);
            }
        }
    }

    public void chainLightning() {
        if(chainlightning) {
            int quantity = 2 + player.spellbook.chainlightning_lvl;
            ArrayList<Monster> possibleTargets = SpellUtils.findMonstersInRangeOfVector(getSpawnPosition(), 5, true);
            if (!possibleTargets.isEmpty()) {
                Collections.shuffle(possibleTargets);
                for (int i = 0; i < quantity; i++) {
                    if (!possibleTargets.isEmpty()) {
                        ChainLightning_Hit chain = new ChainLightning_Hit(possibleTargets.removeFirst());
                        chain.setElements(this);
                        chain.fireball = fireball;
                        if (chargedbolts) {
                            chain.nested_spell = new ChargedBolts_Spell();
                        }
                        chain.originBody = body;
                        screen.spellManager.add(chain);
                    }
                }
            }
        }
    }

    public void icespear() {
        if(icespear) {
            int quantity = 6 + 6 * player.spellbook.icespear_lvl;
            for (int i = 0; i < quantity; i++) {
                Icespear_Spell spear = new Icespear_Spell();
                spear.duration = 1f;
                spear.maxSplits = 0;
                spear.frostbolts = frostbolts;
                spear.setElements(this);
                spear.spawnPosition = new Vector2(body.getPosition());
                spear.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                screen.spellManager.add(spear);
            }
        }
    }

    public void flameBeams() {
        if(flameBeam) {
            int quantity = 1 + player.spellbook.energybeam_lvl;
            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(body.getPosition(), 6, true);
            Collections.shuffle(inRange);
            int trueQuantity = Math.min(inRange.size(), quantity);
            for (int i = 0; i < trueQuantity; i++) {
                EnergyBeam_Projectile beam = new EnergyBeam_Projectile(body.getPosition(), inRange.removeFirst().body.getPosition());
                beam.setElements(this);
                beam.flamejet = true;
                screen.spellManager.add(beam);
            }
        }
    }

    public void sendProjectiles() {
        immediateFrostbolts();
        icespear();
        flameBeams();
        chainLightning();
    }

}
