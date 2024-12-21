package wizardo.game.Screens.Character.Anims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Resources.ScreenResources.CharacterScreenResources;
import wizardo.game.Resources.SpellAnims.FrostNovaAnims;
import wizardo.game.Resources.SpellAnims.OverheatAnims;
import wizardo.game.Screens.Character.CharacterScreen;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Screens.BaseScreen.screenRatio;
import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;

public class SpellCreation_Anim extends Screen_Anim {

    CharacterScreen screen;
    Vector2 position;

    SpellUtils.Spell_Element element;

    public Animation<Sprite> anim1;
    public Animation<Sprite> anim2;
    boolean flipX;
    boolean flipY;
    int rotation;
    float scale2 = 1;
    float alpha2 = 1;

    boolean equipped;

    public SpellCreation_Anim(SpellUtils.Spell_Element element, CharacterScreen screen, boolean equipped) {

        this.element = element;
        this.screen = screen;

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);
        this.equipped = equipped;

        pickAnim();
        position = setPosition();

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
        return stateTime >= anim1.getAnimationDuration() && stateTime >= anim2.getAnimationDuration();
    }

    public void drawAnim1(SpriteBatch batch) {

        Sprite frame = screen.getSprite();
        frame.set(anim1.getKeyFrame(stateTime, false));
        frame.setCenter(position.x, position.y);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        frame.setScale(screenRatio * 1.5f);

        frame.draw(batch);

    }

    public void drawAnim2(SpriteBatch batch) {

        if(stateTime > anim2.getAnimationDuration()/2f) {
            alpha2 -= 0.025f;
            if(alpha2 < 0) {
                alpha2 = 0;
            }
        }

        Sprite frame = screen.getSprite();
        frame.set(anim2.getKeyFrame(stateTime, false));
        frame.setCenter(position.x, position.y);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        frame.setScale(screenRatio * scale2);
        frame.setAlpha(alpha2);

        if(element == FIRE) {
            frame.setColor(1, 0, 0, alpha2);
        }

        frame.draw(batch);

    }

    public Vector2 setPosition() {
        if(equipped) {
            return screen.equippedSpells_table.getCenter();
        } else {
            return screen.knownSpells_table.getCenter();
        }
    }

    public void pickAnim() {

        switch(element) {
            case FIRE, FIRELITE -> anim1 = CharacterScreenResources.fire_anim;
            case FROST -> anim1 = CharacterScreenResources.frost_anim;
            case ARCANE -> anim1 = CharacterScreenResources.arcane_anim;
            case LIGHTNING, COLDLITE -> anim1 = CharacterScreenResources.lightning_anim;
        }

        switch(element) {
            case FIRE -> {
                anim2 = FrostNovaAnims.frostnova_anim;
                scale2 = 1.4f;
            }
            case FROST, COLDLITE -> {
                anim2 = FrostNovaAnims.frostnova_anim;
                scale2 = 1.6f;
            }
            case ARCANE -> {
                anim2 = CharacterScreenResources.arcane_anim;
            }
            case LIGHTNING, FIRELITE -> {
                anim2 = OverheatAnims.overheat_anim_lightning;
                scale2 = 1.8f;
            }
        }

    }
}
