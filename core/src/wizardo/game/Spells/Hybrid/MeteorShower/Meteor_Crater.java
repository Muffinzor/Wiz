package wizardo.game.Spells.Hybrid.MeteorShower;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Resources.SpellAnims.MeteorAnims;
import wizardo.game.Spells.Spell;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Utils.Constants.PPM;

public class Meteor_Crater extends Spell {

    int rotation;
    boolean flipX;
    boolean flipY;

    int frameCounter;

    public Meteor_Crater(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);
        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

        main_element = FIRE;
        anim = MeteorAnims.meteor_crater_anim;

    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
        }

        drawFrame();
        stateTime += delta;

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.remove(this);
        }

    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.setRotation(rotation);
        frame.setScale(1.2f);
        frame.flip(flipX, flipY);
        screen.addUnderSprite(frame);
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
