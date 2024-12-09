package wizardo.game.Spells.Frost.Icespear;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.CharacterScreenResources;
import wizardo.game.Resources.SpellAnims.IcespearAnims;
import wizardo.game.Spells.Spell;

import static wizardo.game.Utils.Constants.PPM;

public class Icespear_Break extends Spell {

    RoundLight light;

    boolean flipX;
    boolean flipY;
    int rotation;

    public Icespear_Break(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);
        anim = IcespearAnims.icespear_break;

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createLight();
        }

        drawFrame();
        stateTime += delta;

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.toRemove(this);
        }

    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0,0,0, 1, 75, targetPosition);
        light.dimKill(0.02f);
        screen.lightManager.addLight(light);
    }

    public void drawFrame() {

        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.rotate(rotation);
        frame.flip(flipX,flipY);
        frame.setScale(0.25f);

        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
        screen.centerSort(frame, targetPosition.y * PPM);

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
        return 0;
    }
}
