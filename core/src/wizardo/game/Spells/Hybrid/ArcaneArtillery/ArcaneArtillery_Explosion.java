package wizardo.game.Spells.Hybrid.ArcaneArtillery;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Elemental;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Hybrid.EnergyRain.EnergyRain_Explosion;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FROST;
import static wizardo.game.Spells.SpellUtils.getClearRandomPosition;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class ArcaneArtillery_BetterExplosion extends ArcaneArtillery_Spell {


    RoundLight light;
    boolean flipX;
    boolean flipY;
    int rotation;

    float explosionInterval = 0.15f;
    float explosions = 0;
    int explosionsCast = 0;

    public ArcaneArtillery_BetterExplosion(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
    }

    public void setup() {
        if(rift) {
            explosions = 3;
            updateNbrOfExplosions();
        }
        explosionInterval = 1f/explosions;
    }
    public void updateNbrOfExplosions() {
        float bonus = (player.spellbook.rift_lvl - 1) / 2f;
        if((bonus % 1) > 0) {
            float remainder = bonus % 1;
            if(Math.random() >= 1 - remainder) {
                explosions ++;
            }
            explosions += (float) Math.floor(bonus);
        } else {
            explosions += bonus;
        }
    }

    public void update(float delta) {
        if(!initialized) {
            setup();
            initialized = true;
            pickAnim();
            createBody();
            createLight();
            frozenorb();
        }

        drawFrame();
        stateTime += delta;

        if(explosionsCast < explosions && stateTime > explosionsCast * explosionInterval + Math.min(explosionInterval, 0.25f)) {
            EnergyRain_Explosion explosion = new EnergyRain_Explosion();
            explosion.targetPosition = getClearRandomPosition(targetPosition, 4);
            explosion.arcaneMissiles = arcaneMissiles;
            explosion.setElements(this);
            screen.spellManager.toAdd(explosion);
            explosionsCast++;
        }

        if(body.isActive() && stateTime >= 0.3f) {
            body.setActive(false);
            light.dimKill(0.016f);
        }

        if(stateTime >= anim.getAnimationDuration() && explosionsCast >= explosions) {
            screen.spellManager.toRemove(this);
            world.destroyBody(body);
        }
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        screen.centerSort(frame, targetPosition.y * PPM - 35);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 120);
        body.setUserData(this);
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, 275, targetPosition);
        screen.lightManager.addLight(light);
    }

    public void pickAnim() {
        anim = ExplosionAnims_Elemental.getExplosionAnim(anim_element);
        switch(anim_element) {
            case FROST -> {
                green = 0.15f;
                blue = 0.75f;
            }
            case ARCANE -> {
                red = 0.6f;
                green = 0.15f;
                blue = 0.9f;
            }
            case FIRE -> {
                red = 0.75f;
                green = 0.15f;

            }
        }

    }

    public void frozenorb() {
        if(frozenorb) {
            Frozenorb_Spell orb = new Frozenorb_Spell();
            orb.spawnPosition = new Vector2(targetPosition);
            orb.targetPosition = new Vector2(0,0);
            orb.speed = 0;
            orb.duration = 2.2f;
            orb.lightAlpha = 0.7f;
            orb.anim_element = FROST;
            screen.spellManager.toAdd(orb);
        }
    }
}
