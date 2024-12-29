package wizardo.game.Spells.Frost.Frostbolt;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Toon;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Spells.Unique.ThundergodBolt.ThundergodBolt_Projectile;
import wizardo.game.Utils.BodyFactory;

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
            playSound(body.getPosition());
            initialized = true;
            chargedbolts();
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.2) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.toRemove(this);
            world.destroyBody(body);
        }
    }

    public void initialize() {
        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
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

        if(!anim_element.equals(FIRE)) {
            if(Math.random() >= (1 - player.spellbook.frostboltBonus/100f)) {
                monster.applyFreeze(2, 4);
            } else {
                monster.applySlow(2.5f, 0.7f);
            }
        }

    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 35);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha,80, body.getPosition());
        light.toLightManager();
        if(bonus_element == FIRE) {
            light.dimKill(0.01f);
        } else {
            light.dimKill(0.015f);
        }
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
            case COLDLITE -> {
                green = 0.5f;
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
                    screen.spellManager.toAdd(bolt);
                }
            }
        }
    }

    public void dispose() {

    }
}
