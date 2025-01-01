package wizardo.game.Spells.Unique;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Resources.EffectAnims.AuraAnims;
import wizardo.game.Spells.Spell;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class BookAura extends Spell {

    int rotation;
    boolean flipX;
    boolean flipY;

    float stateTime2;
    float rotation2;
    boolean flipX2;
    boolean flipY2;

    // DST OF EFFECT = 5.625

    public BookAura() {
        anim = AuraAnims.vogon_aura;
        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

        rotation2 = MathUtils.random(360);
        flipX2 = MathUtils.randomBoolean();
        flipY2 = MathUtils.randomBoolean();
        stateTime2 = MathUtils.random(1.5f, 2f);
    }


    @Override
    public void update(float delta) {
        stateTime += delta;
        stateTime2 += 0.8f * delta;

        drawFrame(0);
        drawFrame(1);

        rotation += 1;
        rotation2 -= 1.2f;
    }

    public void drawFrame(int i) {
        Sprite frame = screen.getSprite();
        if(i == 0) {
            frame.set(anim.getKeyFrame(stateTime, true));
            frame.setRotation(rotation);
            frame.flip(flipX, flipY);
        } else {
            frame.set(anim.getKeyFrame(stateTime2, true));
            frame.setRotation(rotation2);
            frame.flip(flipX2, flipY2);
        }
        frame.setCenter(player.pawn.getBodyX() * PPM, player.pawn.getBodyY() * PPM);

        frame.setScale(2f);
        frame.setAlpha(0.6f);
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
