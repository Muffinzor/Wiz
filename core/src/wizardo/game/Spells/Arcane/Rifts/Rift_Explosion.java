package wizardo.game.Spells.Arcane.Rifts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.RiftsAnims;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Hit;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Spells.SpellUtils.hasLineOfSight;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Rift_Explosion extends Rifts_Spell {

    Body body;
    RoundLight light;

    int rotation;
    boolean flipX;
    boolean flipY;

    public Rift_Explosion(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

    }

    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            initialized = true;
            createBody();
            createLight();
            chargedbolts();
            chainlightning();
            sendProjs();
        }

        drawFrame();

        stateTime += delta;

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        } else if(stateTime > 0.2f) {
            body.setActive(false);
        }


    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);

        if(frostbolt) {
            frostbolt(monster);
        }
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.flip(flipX, flipY);
        frame.setRotation(rotation);
        if(flamejet) {
            frame.setScale(1.2f);
        } else if (overheat) {
            frame.setScale(2f);
        }
        frame.setScale(0.6f);
        screen.addSortedSprite(frame);
        screen.centerSort(frame, body.getPosition().y * PPM - 10);
    }

    public void createBody() {
        if(overheat) {
            radius = radius * 2.2f;
        }
        if(flamejet) {
            radius = radius * 1.2f;
        }
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createLight() {
        float lightRadius = 120;
        if(flamejet) {
            lightRadius = 150;
        } else if (overheat) {
            lightRadius = 250;
        }
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, lightRadius, body.getPosition());
        light.dimKill(0.015f);
        screen.lightManager.addLight(light);
    }

    public void pickAnim() {
        anim = RiftsAnims.getExplosionAnim(anim_element);
        switch (anim_element) {
            case ARCANE -> {
                red = 1;
                green = 0.4f;
                blue = 0.6f;
            }
            case FROST -> {
                red = 0.1f;
                green = 0.15f;
                blue = 0.8f;
            }
            case LIGHTNING -> {
                red = 0.5f;
                green = 0.25f;
            }
            case FIRE -> {
                red = 0.8f;
                green = 0.15f;
            }
        }
    }

    public void frostbolt(Monster monster) {
        monster.applySlow(4, 0.75f);
        float frozenproc = 0.9f - player.spellbook.frostbolt_lvl * .05f;

        if(monster.freezeImmunityTimer <= 0 && Math.random() > frozenproc) {
            monster.applyFreeze(3, 7);
            monster.hp -= 20 * player.spellbook.frostbolt_lvl;
        }
    }

    public void chargedbolts() {
        if(chargedbolt) {
            float procRate = 0.33f;
            if (Math.random() >= procRate) {
                int quantity = 3 + player.spellbook.chargedbolt_lvl / 2;
                for (int i = 0; i < quantity; i++) {
                    ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                    bolt.setElements(this);
                    bolt.spawnPosition = new Vector2(body.getPosition());
                    bolt.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                    screen.spellManager.add(bolt);
                }
            }
        }
    }

    public void chainlightning() {
        if(chainlightning) {
            float procRate = 0.95f - 0.05f * player.spellbook.chainlightning_lvl;
            if (Math.random() >= procRate) {
                Monster target = null;
                ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(body.getPosition(), 3, true);
                if(!inRange.isEmpty()) {
                    Collections.shuffle(inRange);
                    for(Monster monster : inRange) {
                        if(hasLineOfSight(monster.body.getPosition(), body.getPosition())) {
                            target = monster;
                            break;
                        }
                    }
                }

                if(target != null) {
                    ChainLightning_Hit chain = new ChainLightning_Hit(target);
                    chain.originBody = body;
                    chain.setElements(this);
                    chain.arcaneMissile = arcanemissiles;
                    screen.spellManager.add(chain);
                }
            }
        }
    }

    public void sendProjs() {
        if(nested_spell != null) {
            float procRate = getProcRate();
            int quantity = getQuantity();
            if(Math.random() >= procRate) {
                for (int i = 0; i < quantity; i++) {
                    Spell clone = nested_spell.clone();
                    if(clone instanceof Flamejet_Spell) {
                        clone.lightAlpha = 0.8f;
                    }
                    clone.setElements(this);
                    clone.spawnPosition = new Vector2(body.getPosition());
                    clone.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                    clone.originBody = body;
                    screen.spellManager.add(clone);

                }
            }
        }
    }

    public float getProcRate() {
        return 0.25f;
    }
    public int getQuantity() {
        return 3;
    }
}
