package wizardo.game.Spells.Frost.Frostbolt;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Resources.SpellAnims.FrostboltAnims.*;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class Frostbolt_Explosion extends Frostbolt_Spell{

    Animation<Sprite> anim;

    Body body;
    RoundLight light;

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
        }

        drawFrame();
        stateTime += delta;

        if(stateTime >= 0.25f) {
            if(body != null) {
                world.destroyBody(body);
                body = null;
            }
        }

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.toRemove(this);
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
        frame.flip(flipX, flipY);
        screen.centerSort(frame, targetPosition.y * PPM - 25);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        monster.applySlow(2.5f, 0.7f);

    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 35);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        switch(anim_element) {
            case FROST -> light.setLight(0,0,0.8f,1,100, body.getPosition());
            case LIGHTNING -> light.setLight(0,0.5f,0.6f,1f,100, body.getPosition());
            case FIRE -> light.setLight(0,0.3f,0.85f,1f,100, body.getPosition());

        }

        light.toLightManager();
        light.dimKill(0.015f);
    }

    public void pickAnim() {

        switch(anim_element) {
            case FROST -> {
                if(bonus_element == SpellUtils.Spell_Element.FIRE) {
                    if(MathUtils.randomBoolean()) {
                        anim = frostbolt_explosion_anim_fire1;
                    } else {
                        anim = frostbolt_explosion_anim_fire2;
                    }
                } else {
                    anim = frostbolt_explosion_anim_frost;
                }
                blue = 0.8f;
            }
            case FIRE -> {
                if(MathUtils.randomBoolean()) {
                    anim = frostbolt_explosion_anim_fire1;
                } else {
                    anim = frostbolt_explosion_anim_fire2;
                }
                blue = 1f;
            }
        }


        if(anim_element == SpellUtils.Spell_Element.LIGHTNING) {
            anim = frostbolt_explosion_anim_lightning;
        }
        if(anim_element == SpellUtils.Spell_Element.FROST || anim_element == null) {
            anim = frostbolt_explosion_anim_frost;
        }
        if(anim_element == SpellUtils.Spell_Element.FIRE) {
            if(MathUtils.randomBoolean()) {
                anim = frostbolt_explosion_anim_fire1;
            } else {
                anim = frostbolt_explosion_anim_fire2;
            }
        }

    }

    public void dispose() {

    }
}
