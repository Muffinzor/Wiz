package wizardo.game.Spells.Arcane.EnergyBeam;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Toon;
import wizardo.game.Spells.Hybrid.EnergyRain.EnergyRain_Spell;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.ARCANE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class EnergyBeam_Explosion extends EnergyRain_Spell {

    Body body;
    RoundLight light;

    int rotation;
    boolean flipX;
    boolean flipY;

    public EnergyBeam_Explosion() {

        main_element = ARCANE;

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
    }

    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            createBody();
            createLight();
            initialized = true;
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.2f) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        if(frostbolt) {
            monster.applySlow(5, 0.7f);
        }
        dealDmg(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.flip(flipX, false);
        frame.setScale(1.2f);
        screen.centerSort(frame, body.getPosition().y * PPM - 10);
        screen.addSortedSprite(frame);
    }

    public void pickAnim() {
        anim = ExplosionAnims_Toon.getExplosionAnim(anim_element);
        switch(anim_element) {
            case FROST -> {
                red = 0.2f;
                blue = 0.9f;
            }
            case FIRE -> {
                red = 0.75f;
                blue = 0.2f;
            }
            case ARCANE -> {
                red = 0.7f;
                green = 0.15f;
                blue = 0.9f;
            }
            case LIGHTNING -> {
                red = 0.45f;
                green = 0.4f;
            }
        }
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 90);
        body.setUserData(this);
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha,150,targetPosition);
        light.dimKill(0.015f);
        screen.lightManager.addLight(light);
    }

}
