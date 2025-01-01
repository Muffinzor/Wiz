package wizardo.game.Spells.Hybrid.CelestialStrike;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Resources.SpellAnims.CelestialStrikeAnims;
import wizardo.game.Resources.SpellAnims.OverheatAnims;
import wizardo.game.Spells.Spell;

import static wizardo.game.Utils.Constants.PPM;

public class CelestialStrike_Explosion extends Spell {

    Animation<Sprite> anim2;

    int rotation;
    boolean flipX;
    boolean flipY;

    public CelestialStrike_Explosion(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);
        anim = CelestialStrikeAnims.celestial_strike_explosion_anim;
        anim2 = OverheatAnims.overheat_anim_coldlite;

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
    }


    @Override
    public void update(float delta) {
        drawFrame();
        stateTime += delta;
        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.remove(this);
        }
    }

    public void drawFrame() {
        if(stateTime < anim2.getAnimationDuration()) {
            Sprite frame2 = screen.getSprite();
            frame2.set(anim2.getKeyFrame(stateTime, false));
            frame2.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
            frame2.setRotation(rotation);
            frame2.flip(flipX, flipY);
            screen.centerSort(frame2, targetPosition.y * PPM - 15);
            screen.addSortedSprite(frame2);
        }

        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        screen.centerSort(frame, targetPosition.y * PPM - 15);
        screen.addSortedSprite(frame);
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
