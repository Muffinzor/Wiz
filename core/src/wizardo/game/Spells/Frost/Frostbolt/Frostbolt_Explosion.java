package wizardo.game.Spells.Frost.Frostbolt;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Items.Equipment.Staff.Epic_FrostboltStaff;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Toon;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Hit;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Resources.SpellAnims.FrostboltAnims.*;
import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Frostbolt_Explosion extends Frostbolt_Spell{

    Body body;
    RoundLight light;

    float frameScale = 1;
    float rotation;
    boolean flipX;
    boolean flipY;

    int soundType;

    public Frostbolt_Explosion() {
        super();
        soundType = MathUtils.random(1,3);
        soundPath = "Sounds/Spells/IceExplosion" + soundType + ".wav";
    }

    public void update(float delta) {
        if(!initialized) {
            initialize();
            pickAnim();
            createBody();
            createLight();
            initialized = true;
            chargedbolts();
            playSound(body.getPosition());
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.1f) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.remove(this);
            world.destroyBody(body);
        }
    }

    public void initialize() {
        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        float scale_max = 1 + player.spellbook.frostbolts_bonus_splash/100f;
        frameScale = MathUtils.random(1, scale_max);
    }


    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.rotate(rotation);
        frame.setScale(frameScale);
        frame.flip(flipX, flipY);

        screen.centerSort(frame, targetPosition.y * PPM - 15);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        chainlightning(monster);

        if(anim_element.equals(FROST)) {
            float duration = 2;
            float immunity = 4;
            float treshold = (0.8f - player.spellbook.frostboltBonus/100f);
            if(player.inventory.equippedStaff instanceof Epic_FrostboltStaff) {
                duration = 3;
                immunity = 6;
                treshold = treshold/2;
            }
            if(Math.random() >= treshold) {
                monster.applyFreeze(duration, immunity);
            } else {
                monster.applySlow(2.5f, 0.7f);
            }
        }
    }

    public void chainlightning(Monster monster) {
        if(chainlightning) {
            float proc_chance = 0.85f - 0.05f * player.spellbook.chainlightning_lvl;
            if(Math.random() >= proc_chance) {
                ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(monster.body.getPosition(), 3, true);
                if(!inRange.isEmpty()) {
                    Collections.shuffle(inRange);
                    ChainLightning_Hit chain = new ChainLightning_Hit(inRange.getFirst());
                    chain.originBody = monster.body;
                    chain.setElements(this);
                    chain.maxHits = 0;
                    screen.spellManager.add(chain);
                }
            }
        }
    }

    public void createBody() {
        float radius = 35 * frameScale;
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha,80 * frameScale,  body.getPosition());
        light.toLightManager();
        light.dimKill(0.015f);
    }

    public void pickAnim() {
        anim = getAnim(anim_element);
        switch(anim_element) {
            case FROST -> {
                blue = 0.8f;
            }
            case FIRE -> {
                anim = ExplosionAnims_Toon.getExplosionAnim(FIRE);
                red = 0.85f;
                green = 0.25f;
                frameScale = 0.65f;
            }
            case LIGHTNING -> {
                red = 0.5f;
                green = 0.5f;
            }
            case COLDLITE -> {
                green = 0.5f;
                blue = 0.65f;
            }
            case ARCANE -> {
                red = 0.82f;
                blue = 0.75f;
            }
        }
    }

    public void chargedbolts() {
        if(chargedbolt) {
            float procRate = 0.66f - 0.06f * player.spellbook.chargedbolt_lvl;
            if(Math.random() >= procRate) {
                for (int i = 0; i < 3; i++) {
                    ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                    bolt.setElements(this);
                    bolt.spawnPosition = new Vector2(body.getPosition());
                    bolt.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                    screen.spellManager.add(bolt);
                }
            }
        }
    }

    public void dispose() {

    }
}
