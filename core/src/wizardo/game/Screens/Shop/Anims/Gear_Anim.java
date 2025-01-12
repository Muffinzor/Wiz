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
import wizardo.game.Screens.Shop.ShopButton_Scroll;
import wizardo.game.Screens.Shop.ShopScreen;

import static wizardo.game.Spells.SpellUtils.whatElementIsThis;

public class Gear_Anim extends Screen_Anim {

    Animation<Sprite> anim;
    Animation<Sprite> flareAnim;
    ShopButton_Equipment button;
    float flareAlpha = 1;
    ShopScreen screen;

    boolean flipX;
    boolean flipY;
    int rotation;

    Vector2 position;

    public Gear_Anim(ShopScreen screen, ShopButton_Equipment button) {
        this.screen = screen;
        this.position = button.getCenterPoint();
        this.button = button;

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

        anim = GearFlareAnims.gear_pop;
        flareAnim = button.flareAnim;
    }

    @Override
    public void draw(SpriteBatch batch) {
        backFrame(batch);
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setScale(2);
        frame.setCenter(position.x, position.y);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        frame.draw(batch);
    }

    public void backFrame(SpriteBatch batch) {
        Sprite frame = screen.getSprite();
        frame.set(flareAnim.getKeyFrame(stateTime, false));
        if(button.piece.quality == ItemUtils.EquipQuality.LEGENDARY) {
            frame.setScale(1.3f);
        }
        frame.setAlpha(flareAlpha);
        frame.setCenter(position.x, position.y);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        frame.draw(batch);
        flareAlpha -= 0.025f;
    }

    @Override
    public boolean isFinished() {
        return stateTime >= anim.getAnimationDuration();
    }
}
