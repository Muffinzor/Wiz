package wizardo.game.Spells.Lightning.ChargedBolts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Resources.SpellAnims.ChargedboltsAnims.*;
import static wizardo.game.Spells.SpellUtils.Spell_Element.FROST;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class ChargedBolts_Projectile extends ChargedBolts_Spell {

    Body body;
    RoundLight light;
    float alpha = 1;
    int collisions = 0;

    public int rotation;
    public boolean reverseRotation;
    public boolean flipX;
    public boolean flipY;


    public Vector2 direction;
    private Vector2 perpendicular;
    private float nextWobbleTime;
    private float wobbleInterval;
    private Vector2 currentWobbleOffset;
    private Vector2 targetWobbleOffset;
    private float wobbleChangeInterval;

    public ChargedBolts_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {

        stateTime = (float) Math.random();
        speed = speed * MathUtils.random(0.9f, 1.1f);
        duration = duration * MathUtils.random(0.9f, 1.1f);

        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);

        rotation = MathUtils.random(360);
        reverseRotation = Math.random() > 0.5f;
        flipX = Math.random() > 0.5f;
        flipY = Math.random() > 0.5f;

        wobbleInterval = MathUtils.random(0.25f, 0.65f);
        wobbleChangeInterval = wobbleInterval;
        nextWobbleTime = wobbleInterval;

        currentWobbleOffset = new Vector2();
        targetWobbleOffset = new Vector2();

        main_element = SpellUtils.Spell_Element.LIGHTNING;

        screen = currentScreen;

    }

    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            initialized = true;
            createBody();
            createLight();
        }

        stateTime += delta;
        drawFrame();
        adjustLight();

        if(stateTime > 3 || collisions > 5) {
            if(alpha == 1) {
                light.dimKill(0.05f);
                light = null;
            }
            alpha -= 0.05f;
        } else if (delta > 0){
            wobble(delta);
        }

        if(alpha <= 0) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }

    }

    public void handleCollision(Monster monster) {
        collisions ++;

        frostbolts(monster);

        dealDmg(monster);
    }

    public void handleCollision(Fixture fix) {

        collisions += 100;
        body.setLinearVelocity(0,0);
    }

    public void drawFrame() {

        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.rotate(rotation);
        if(alpha < 1) {
            frame.setAlpha(alpha);
        }

        screen.centerSort(frame, body.getPosition().y * PPM);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

    }

    private void createBody() {
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction.set(1,0);
        }

        perpendicular = new Vector2(-direction.y, direction.x);

        Vector2 offset = new Vector2(direction.cpy().scl(0.25f));
        Vector2 adjustedSpawn = new Vector2(spawnPosition.add(offset));

        body = BodyFactory.spellProjectileCircleBody(adjustedSpawn, 10, true);
        body.setUserData(this);

        float angleVariation = Math.min(2f * bolts, 20);
        float randomAngle = MathUtils.random(-angleVariation, angleVariation);
        direction.rotateDeg(randomAngle);

        Vector2 velocity = direction.scl(speed);
        body.setLinearVelocity(velocity);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, 0.85f, 25, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        if(light != null) {
            light.pointLight.setPosition(body.getPosition().scl(PPM));
        }
    }


    private void wobble(float delta) {
        if (stateTime >= nextWobbleTime) {
            float wobbleAmount = MathUtils.random(-0.07f, 0.07f);
            targetWobbleOffset = perpendicular.cpy().scl(wobbleAmount);
            nextWobbleTime += wobbleChangeInterval;
        }

        //interpolate towards the new wobble offset
        currentWobbleOffset.lerp(targetWobbleOffset, delta / wobbleChangeInterval);

        body.setTransform(body.getPosition().add(currentWobbleOffset), body.getAngle());

    }

    /**
     * NEED TO PROC RATE
     * @param monster
     */
    private void frostbolts(Monster monster) {
        if(frostbolts) {

            float procTreshold = .825f - player.spellbook.frostbolt_lvl * .025f;
            if(Math.random() >= procTreshold) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.targetPosition = new Vector2(monster.body.getPosition());
                explosion.screen = screen;
                explosion.setElements(this);
                explosion.anim_element = SpellUtils.Spell_Element.LIGHTNING;
                screen.spellManager.toAdd(explosion);
            }

        }
    }

    public void pickAnim() {
        switch (anim_element) {
            case FROST -> {
                anim = chargedbolt_frost_anim;
                red = 0.4f;
                green = 0.6f;
                blue = 0.3f;
            }
            case LIGHTNING -> {
                anim = chargedbolt_lightning_anim;
                red = 0.4f;
                green = 0.4f;
                if(bonus_element == FROST) {
                    red = 0;
                    green = 0.5f;
                    blue = 0.6f;
                }
            }
        }

    }




}
