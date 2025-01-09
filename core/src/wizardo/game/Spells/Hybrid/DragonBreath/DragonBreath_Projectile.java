package wizardo.game.Spells.Hybrid.DragonBreath;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.DragonbreathAnims;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Fire.Fireball.Fireball_Explosion;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class DragonBreath_Projectile extends DragonBreath_Spell {

    Vector2 direction;

    ArrayList<Body> bodies;
    public boolean bodiesInactive;

    int fireballProcs = 0;
    public int maxFireballProcs = 5;

    float angle;

    int lightFrames;

    boolean flipY;

    public DragonBreath_Projectile() {

        bodies = new ArrayList<>();
        main_element = SpellUtils.Spell_Element.FIRE;
        speed = 11;

        flipY = MathUtils.randomBoolean();

    }
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            pickAnim();
            createBodies();
        }

        lightFrames--;
        if(!bodiesInactive && lightFrames <= 0 && delta > 0) {
            lightFrames = 2;
            createLights();
        }

        drawFrame();
        stateTime += delta;

        if(stateTime > 0.41f && !bodiesInactive) {
            bodiesInactive = true;
            for(Body body : bodies) {
                body.setLinearVelocity(0,0);
                body.setActive(false);
            }
        }

        if(stateTime >= anim.getAnimationDuration()) {
            for (Body body : bodies) {
                world.destroyBody(body);
            }
            screen.spellManager.remove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        if(frostbolts) {
            frostbolts(monster);
        }

        if(rift) {
            rift(monster);
        }

        if(frozenorb) {
            if(monster.freezeImmunityTimer <= 0) {
                monster.applyFreeze(3, 7f);
            } else {
                monster.applySlow(4, 0.75f);
            }
        }

        if(fireball) {
            fireball(monster);
        }
    }

    public void rift(Monster monster) {
        float procRate = 0.985f - 0.015f * player.spellbook.rift_lvl;
        if(Math.random() >= procRate) {
            Rift_Zone rift = new Rift_Zone(monster.body.getPosition());
            rift.setElements(this);
            rift.nested_spell = new Flamejet_Spell();
            screen.spellManager.add(rift);
        }
    }

    public void frostbolts(Monster monster) {
        float level = (getLvl() + player.spellbook.frostbolt_lvl)/2f;
        float procRate = 0.925f - 0.025f * level;
        if(Math.random() >= procRate) {
            Frostbolt_Explosion explosion = new Frostbolt_Explosion();
            explosion.targetPosition = new Vector2(monster.body.getPosition());
            explosion.setElements(this);
            screen.spellManager.add(explosion);
        }
    }

    public void fireball(Monster monster) {
        float procRate = .985f - (0.005f * player.spellbook.fireball_lvl);
        if(fireballProcs < maxFireballProcs && Math.random() >= procRate) {
            Fireball_Explosion explosion1 = new Fireball_Explosion();
            explosion1.targetPosition = new Vector2(monster.body.getPosition());
            explosion1.setElements(this);
            screen.spellManager.add(explosion1);
            fireballProcs ++;
        }
    }

    public void drawFrame() {

        Vector2 offset = new Vector2(direction);
        offset.nor().scl(-3.5f);
        Vector2 trueSpawn = new Vector2(spawnPosition.cpy().add(offset));

        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setPosition(trueSpawn.x * PPM, trueSpawn.y * PPM - frame.getHeight()/2);
        frame.setOrigin(0, frame.getHeight()/2);
        frame.setScale(0.9f, 1);
        frame.setRotation(angle);
        frame.flip(false, flipY);
        screen.addUnderSprite(frame);
    }

    public void createBodies() {
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
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
        screen.spellManager.add(detector);
    }

    public void createLights() {
        for(Body body : bodies) {
            if(body.isActive()) {
                RoundLight light = screen.lightManager.pool.getLight();
                float ownAlpha = Math.min(0.33f + stateTime, 0.8f);
                light.setLight(red, green, blue, ownAlpha, 80, body.getPosition());
                light.dimKill(0.02f);
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
