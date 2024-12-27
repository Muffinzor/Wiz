package wizardo.game.Spells.Arcane.Rifts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.SpellAnims.RiftsAnims;
import wizardo.game.Spells.Spell;

import static wizardo.game.Utils.Constants.PPM;

public class Rift_Zone extends Rifts_Spell {

    RoundLight light;

    public Rift_Zone(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);
    }

    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            initialized = true;
            createLight();
            createPullBody();
        }

        drawSprite();
        stateTime += delta;

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.toRemove(this);
        } else if(stateTime > 0.5f) {
            Rift_Explosion explosion = new Rift_Explosion(targetPosition);
            explosion.setRift(this);
            screen.spellManager.toAdd(explosion);
            screen.spellManager.toRemove(this);
        }

    }

    public void drawSprite() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);

        frame.setScale(0.65f);

        screen.addUnderSprite(frame);
    }

    public void createPullBody() {
        Rift_PullBody pull = new Rift_PullBody(targetPosition, radius + 10, overheat);
        screen.spellManager.toAdd(pull);
    }

    public void createLight() {
        float lightRadius = 80;

        light = screen.lightManager.pool.getLight();
        light.setLight(1, .4f, .6f, 0.75f, lightRadius, targetPosition);
        light.dimKill(0.015f);
        screen.lightManager.addLight(light);
    }

    public void pickAnim() {
        switch (anim_element) {
            case ARCANE -> {
                anim = RiftsAnims.rift_zone_anim_arcane;
                red = 1;
                green = 0.4f;
                blue = 0.6f;
            }
            case FROST -> {
                anim = RiftsAnims.rift_zone_anim_frost;
                red = 0.1f;
                green = 0.15f;
                blue = 0.8f;
            }
            case LIGHTNING -> {
                anim = RiftsAnims.rift_zone_anim_lightning;
                red = 0.5f;
                green = 0.25f;
            }
            case FIRE -> {
                anim = RiftsAnims.rift_zone_anim_fire;
                red = 0.8f;
                green = 0.15f;
            }
        }
    }


}
