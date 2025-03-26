package wizardo.game.Spells.Fire.Overheat;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Toon;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

/**
 * Smaller explosions coming from procs of overheat mechanic
 */
public class Overheat_TriggerExplosion extends Spell {

    Body body;
    RoundLight light;
    float delayFuse;

    boolean flipX;
    boolean flipY;
    int rotation;

    public float radius;

    public boolean frozenorb;

    public Overheat_TriggerExplosion() {

        delayFuse = MathUtils.random(0, 0.2f);

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

        radius = 40;

        dmg = player.spellbook.fireball_lvl * 35;

    }


    @Override
    public void update(float delta) {
        stateTime += delta;
        if(stateTime < delayFuse) {
            return;
        }

        if(!initialized) {
            initialized = true;
            pickAnim();
            createBody();
            createLight();
            sendProjs();
        }

        drawFrame();

        if(body.isActive() && stateTime > 0.2f + delayFuse) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }

    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.flip(flipX, flipY);
        frame.setRotation(rotation);
        frame.setScale(0.75f);
        screen.centerSort(frame, body.getPosition().y * PPM - 10);
        screen.addSortedSprite(frame);
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        if(frozenorb && monster.freezeImmunityTimer <= 0) {
            monster.applyFreeze(2, 4);
        }
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha, 100, body.getPosition());
        light.dimKill(0.03f);
        screen.lightManager.addLight(light);
    }

    public void pickAnim() {
        switch(anim_element) {
            case FROST -> {
                anim = ExplosionAnims_Toon.getExplosionAnim(FROST);
                green = 0.15f;
                blue = 0.5f;
            }
            case FIRE -> {
                anim = ExplosionAnims_Toon.getExplosionAnim(FIRE);
                red = 0.7f;
                green = 0.3f;
            }
            case LIGHTNING -> {
                anim = ExplosionAnims_Toon.getExplosionAnim(LIGHTNING);
                red = 0.25f;
                green = 0.2f;
            }
        }
    }

    public void sendProjs() {
        if(nested_spell != null) {
            for (int i = 0; i < 3; i++) {
                Spell clone = nested_spell.clone();
                clone.spawnPosition = body.getPosition();
                clone.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                clone.setElements(this);
                screen.spellManager.add(clone);
            }

        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }

    @Override
    public int getDmg() {
        return player.spellbook.fireball_lvl * 25;
    }
}
