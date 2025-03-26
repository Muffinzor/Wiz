package wizardo.game.Spells.Unique.FreezingBlast;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.IceGustAnims;
import wizardo.game.Spells.Spell;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FROST;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class FreezingBlast_Explosion extends Spell {

    RoundLight light;

    boolean flipX;
    boolean flipY;
    int rotation;

    public FreezingBlast_Explosion(Vector2 position) {
        this.targetPosition = new Vector2(position);

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

        anim_element = FROST;

        pickAnim();

    }


    public void update(float delta) {

        if(stateTime > 0.2f && !initialized) {
            initialize();
            createBody();
            createLight();
            initialized = true;
        }

        if(stateTime > 0.2f) {
            drawFrame();
        }
        stateTime += delta;

        if(stateTime >= 0.4f && body.isActive()) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration() + 0.2f) {
            screen.spellManager.remove(this);
            world.destroyBody(body);
        }
    }

    public void initialize() {
        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
    }


    public void drawFrame() {

        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime - 0.2f, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.rotate(rotation);
        frame.flip(flipX, flipY);
        frame.setScale(2);
        frame.setAlpha(0.7f);

        screen.centerSort(frame, targetPosition.y * PPM - 15);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

    }

    public void handleCollision(Monster monster) {

        monster.applyFreeze(2.5f, 5);


    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 80);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha,100, body.getPosition());
        light.toLightManager();
        light.dimKill(0.015f);
    }

    public void pickAnim() {
        anim = IceGustAnims.getIceGustAnim();
        red = 0.4f;
        green = 0.4f;
        blue = 0.8f;
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
