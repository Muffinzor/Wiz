package wizardo.game.Screens.Shop.Anims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Resources.ScreenResources.CharacterScreenResources;
import wizardo.game.Screens.CharacterScreen.Anims.Screen_Anim;
import wizardo.game.Screens.Shop.ShopButton_Scroll;
import wizardo.game.Screens.Shop.ShopScreen;
import wizardo.game.Spells.SpellUtils;

import java.lang.annotation.ElementType;

import static wizardo.game.Spells.SpellUtils.whatElementIsThis;

public class Scroll_Anim extends Screen_Anim {

    Animation<Sprite> anim;
    ShopScreen screen;

    boolean flipX;
    boolean flipY;
    int rotation;

    Vector2 position;

    public Scroll_Anim(ShopScreen screen, ShopButton_Scroll button) {
        this.screen = screen;
        this.position = button.getCenterPoint();
        getAnim(whatElementIsThis(button.mastery));

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);
    }

    public void getAnim(SpellUtils.Spell_Element element) {
        switch(element) {
            case FIRE -> anim = CharacterScreenResources.fire_anim;
            case FROST -> anim = CharacterScreenResources.frost_anim;
            case LIGHTNING -> anim = CharacterScreenResources.lightning_anim;
            case ARCANE -> anim = CharacterScreenResources.arcane_anim;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(position.x, position.y);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        frame.draw(batch);
    }

    @Override
    public boolean isFinished() {
        return stateTime >= anim.getAnimationDuration();
    }
}
