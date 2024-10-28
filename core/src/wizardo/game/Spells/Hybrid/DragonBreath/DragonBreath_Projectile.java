package wizardo.game.Spells.Hybrid.DragonBreath;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.DragonbreathAnims;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class DragonBreath_Projectile extends DragonBreath_Spell {

    ArrayList<Body> bodies;
    public boolean bodiesInactive;

    float angle;

    public DragonBreath_Projectile() {

        bodies = new ArrayList<>();
        main_element = SpellUtils.Spell_Element.FIRE;
        speed = 40;

    }
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            pickAnim();
            createBodies();
        }

        if(!bodiesInactive) {
            createLights();
        }

        drawFrame();
        stateTime += delta;

        if(stateTime > 0.13f && !bodiesInactive) {
            bodiesInactive = true;
            for(Body body : bodies) {
                body.setLinearVelocity(0,0);
                body.setActive(false);
            }
        }
    }

    public void handleCollision(Monster monster) {
        if(frostbolts) {
            frostbolts(monster);
        }

        if(frozenorb) {
            if(monster.freezeImmunityTimer <= 0) {
                monster.applyFreeze(3, 7f);
            } else {
                monster.applySlow(4, 0.75f);
            }
        }
    }

    public void frostbolts(Monster monster) {
        float level = (getLvl() + player.spellbook.frostbolt_lvl)/2f;
        float procRate = 0.75f - 0.05f * level;
        if(Math.random() >= procRate) {
            Frostbolt_Explosion explosion = new Frostbolt_Explosion();
            explosion.targetPosition = new Vector2(monster.body.getPosition());
            explosion.setElements(this);
            screen.spellManager.toAdd(explosion);
        }
    }
    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(spawnPosition.x * PPM, spawnPosition.y * PPM);
        frame.setRotation(angle);
        screen.addUnderSprite(frame);
    }

    public void createBodies() {
        Vector2 direction = new Vector2(targetPosition.sub(spawnPosition));
        if(!direction.isZero()) {
            direction.nor();
        } else {
            direction = new Vector2(1,0);
        }

        Vector2 velocity = direction.scl(1);
        angle = direction.angleDeg();



        for (int i = 0; i < 5; i++) {
            Body body = BodyFactory.spellProjectileCircleBody(spawnPosition, 35, true);
            body.setUserData(this);
            Vector2 ownVelocity = velocity.cpy().rotateDeg(-20 + (i * 10));
            body.setLinearVelocity(ownVelocity.scl(speed));
            createDetector(ownVelocity, body);
            bodies.add(body);
        }
    }

    public void createDetector(Vector2 velocity, Body body) {
        DragonBreath_CollisionDetector detector = new DragonBreath_CollisionDetector(velocity, body);
        detector.screen = screen;
        screen.spellManager.toAdd(detector);
    }

    public void createLights() {
        for(Body body : bodies) {
            if(body.isActive()) {
                RoundLight light = screen.lightManager.pool.getLight();
                float ownAlpha = 0.75f + stateTime * 2;
                light.setLight(red, green, blue, ownAlpha, 75, body.getPosition());
                light.dimKill(0.0125f);
                screen.lightManager.addLight(light);
            }
        }
    }
    public void pickAnim() {
        switch(anim_element) {
            case FIRE -> {
                anim = DragonbreathAnims.dragonbreath_anim_fire;
                red = 0.7f;
                green = 0.3f;
            }
            case FROST -> {
                anim = DragonbreathAnims.dragonbreath_anim_frost;
                red = 0.1f;
                green = 0.25f;
                blue = 1f;
            }
        }
    }
}
