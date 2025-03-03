package wizardo.game.Screens.Shop.Anims;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Resources.EffectAnims.GearFlareAnims;
import wizardo.game.Screens.CharacterScreen.Anims.Screen_Anim;
import wizardo.game.Screens.Shop.ShopButton_Equipment;
import wizardo.game.Screens.Shop.ShopButton_Reagent;
import wizardo.game.Screens.Shop.ShopButton_Scroll;
import wizardo.game.Screens.Shop.ShopScreen;

import static wizardo.game.Spells.SpellUtils.whatElementIsThis;

public class Reagent_Anim extends Screen_Anim {

    Animation<Sprite> anim;
    ShopButton_Reagent button;
    ShopScreen screen;

    boolean flipX;
    boolean flipY;
    int rotation;

    Vector2 position;

    public Reagent_Anim(ShopScreen screen, ShopButton_Reagent button) {
        this.screen = screen;
        this.position = button.getCenterPoint();
        this.button = button;

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

        anim = GearFlareAnims.gear_pop;

    }

    @Override
    public void draw(SpriteBatch batch) {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setScale(2);
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
