package wizardo.game.Spells.Fire.Overheat;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.OverheatAnims;
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

    public Overheat_Explosion(Vector2 targetPosition) {

        this.targetPosition = new Vector2(targetPosition);

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            pickAnim();
            createBody();
            createLight();
            sendProjectiles();
        }

        drawFrame();

        stateTime += delta;

        if(stateTime >= 0.2f) {
            body.setActive(false);
        }

        delayedFrostbolts(delta);

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);

        fireball(monster);

        Vector2 direction = monster.body.getPosition().sub(body.getPosition());
        if(thunderstorm) {
            float strength = 10 + 0.5f * player.spellbook.thunderstorm_lvl;
            monster.movementManager.applyPush(direction, strength, 0.75f, 0.89f);
        } else {
            monster.movementManager.applyPush(direction, 5, 0.3f, 0.92f);
        }
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.setScale(1.2f);
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
                red = 0.7f;
                green = 0.3f;
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
        if(thunderstorm) {
            radius = radius - 20;
        }
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, 250, targetPosition);
        light.dimKill(0.015f);
        screen.lightManager.addLight(light);
    }

    public void immediateFrostbolts() {
        if(frostbolts) {
            int quantity = 2 + 2 * player.spellbook.frostbolt_lvl;
            float radius = 5 + 0.12f * player.spellbook.frostbolt_lvl;
            for (int i = 0; i < quantity; i++) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.setElements(this);
                explosion.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), radius);
                screen.spellManager.toAdd(explosion);
            }
        }
    }

    public void delayedFrostbolts(float delta) {

        if(frostbolts) {

            float level = (getLvl() + player.spellbook.frostbolt_lvl)/2f;
            float interval = 0.2f - 0.015f * level;

            if(stateTime % interval < delta) {

                float radius = 5 + 0.12f * player.spellbook.frostbolt_lvl;
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.setElements(this);
                explosion.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), radius);
                screen.spellManager.toAdd(explosion);

            }
        }

    }

    public void fireball(Monster monster) {

        if(fireball) {
            float level = (getLvl() + player.spellbook.fireball_lvl)/2f;
            float procRate = 0.85f - 0.025f * level;

            if(Math.random() >= procRate) {
                Overheat_TriggerExplosion fireball = new Overheat_TriggerExplosion();
                fireball.frozenorb = frozenorb;
                fireball.nested_spell = nested_spell;
                fireball.setElements(this);
                fireball.targetPosition = new Vector2(monster.body.getPosition());
                screen.spellManager.toAdd(fireball);
            }
        }

    }

    public void chainLightning() {
        int quantity = 2 + player.spellbook.chainlightning_lvl / 5;
        ArrayList<Monster> possibleTargets = SpellUtils.findMonstersInRangeOfVector(getSpawnPosition(), 5, true);
        if(!possibleTargets.isEmpty()) {
            Collections.shuffle(possibleTargets);
            for (int i = 0; i < quantity; i++) {
                if(!possibleTargets.isEmpty()) {
                    ChainLightning_Hit chain = new ChainLightning_Hit(possibleTargets.removeFirst());
                    chain.setElements(this);
                    chain.fireball = fireball;
                    if(chargedbolts) {
                        chain.nested_spell = new ChargedBolts_Spell();
                    }
                    chain.originBody = body;
                    screen.spellManager.toAdd(chain);
                }
            }
        }
    }

    public void icespear() {
        if(icespear) {
            int quantity = 9 + 3 * player.spellbook.icespear_lvl;
            for (int i = 0; i < quantity; i++) {
                Icespear_Spell spear = new Icespear_Spell();
                spear.duration = 1f;
                spear.maxSplits = 0;
                spear.frostbolts = frostbolts;
                spear.setElements(this);
                spear.spawnPosition = new Vector2(body.getPosition());
                spear.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                screen.spellManager.toAdd(spear);
            }
        }
    }

    public void sendProjectiles() {
        immediateFrostbolts();
        icespear();

        if(chainlightning) {
            chainLightning();
        }

        if(nested_spell != null) {

            int quantity = getQuantity();

            for (int i = 0; i < quantity; i++) {
                Spell proj = nested_spell.clone();
                proj.setElements(this);
                proj.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 5);
                proj.spawnPosition = new Vector2(body.getPosition());
                screen.spellManager.toAdd(proj);

            }
        }

    }


    public int getQuantity() {
        int quantity = 0;
        double level = (getLvl() + nested_spell.getLvl()) /2f;

        if(nested_spell instanceof Icespear_Spell) {
            quantity = 5 + (int) (3 * level);
        }

        return quantity;
    }
}
