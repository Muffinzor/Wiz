package wizardo.game.Screens.Character.Anims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Resources.ScreenResources.CharacterScreenResources;
import wizardo.game.Resources.SpellAnims.IcespearAnims;
import wizardo.game.Screens.Character.BookTable.SpellIcon_Button;
import wizardo.game.Screens.Character.CharacterScreen;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;


public class SpellSwap_Anim extends Screen_Anim {

    CharacterScreen screen;
    Vector2 position;

    SpellIcon_Button button;
    SpellUtils.Spell_Element element;

    Animation<Sprite> anim1;
    Animation<Sprite> anim2;
    boolean flipX;
    boolean flipY;
    int rotation;

    public SpellSwap_Anim(Vector2 position, CharacterScreen screen, SpellUtils.Spell_Element element) {
        this.screen = screen;
        this.element = element;
        anim1 = IcespearAnims.icespear_break;

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

        pickAnim();
        this.position = new Vector2(position);
    }

    public void draw(SpriteBatch batch) {

        if (stateTime < anim1.getAnimationDuration()) {
            drawAnim1(batch);
        }
        if(stateTime < anim2.getAnimationDuration()) {
            drawAnim2(batch);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public void drawAnim1(SpriteBatch batch) {

        Sprite frame = screen.getSprite();
        frame.set(anim1.getKeyFrame(stateTime, false));
        frame.setCenter(position.x, position.y);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        frame.setScale(xRatio * 0.8f, yRatio * 0.8f);

        frame.draw(batch);

    }

    public void drawAnim2(SpriteBatch batch) {

        Sprite frame = screen.getSprite();
        frame.set(anim2.getKeyFrame(stateTime, false));
        frame.setCenter(position.x, position.y);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        frame.setScale(xRatio * 0.8f, yRatio * 0.8f);

        frame.draw(batch);

    }

    public void pickAnim() {
        switch(element) {
            case FIRE, FIRELITE -> anim2 = CharacterScreenResources.fire_anim;
            case FROST -> anim2 = CharacterScreenResources.frost_anim;
            case ARCANE -> anim2 = CharacterScreenResources.arcane_anim;
            case LIGHTNING, COLDLITE -> anim2 = CharacterScreenResources.lightning_anim;
        }
    }

    public Vector2 setPosition() {
        return button.getCenter();
    }
}
