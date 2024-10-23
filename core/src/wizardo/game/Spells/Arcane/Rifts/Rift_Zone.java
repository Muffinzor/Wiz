package wizardo.game.Spells.Arcane.Rifts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.SpellAnims.RiftsAnims;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;

public class Rift_Zone extends Rifts_Spell {

    RoundLight light;

    public Rift_Zone(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);

        screen = currentScreen;

        anim = RiftsAnims.rift_zone_anim_arcane;
    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createLight();
        }

        drawSprite();
        stateTime += delta;

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.toRemove(this);
        } else if(stateTime > 0.5f) {
            Rift_Explosion explosion = new Rift_Explosion(targetPosition);
            screen.spellManager.toAdd(explosion);
            screen.spellManager.toRemove(this);
        }

    }

    public void drawSprite() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.setScale(0.8f);
        screen.addUnderSprite(frame);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(1, .4f, .6f, 0.75f, 80, targetPosition);
        light.dimKill(0.015f);
        screen.lightManager.addLight(light);
    }
}
