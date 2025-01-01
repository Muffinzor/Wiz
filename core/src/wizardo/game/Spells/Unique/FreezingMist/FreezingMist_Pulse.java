package wizardo.game.Spells.Unique.FreezingMist;

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

public class FreezingMist_Pulse extends Spell {

    RoundLight light;

    boolean flipX;
    boolean flipY;
    int rotation;

    public FreezingMist_Pulse(Vector2 position) {
        this.targetPosition = new Vector2(position);

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

        main_element = FROST;
        anim_element = FROST;

    }


    public void update(float delta) {

        if(!initialized) {
            initialize();
            pickAnim();
            createBody();
            createLight();
            initialized = true;
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.2) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
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
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.rotate(rotation);
        frame.flip(flipX, flipY);
        frame.setScale(2.2f);
        frame.setAlpha(0.7f);

        screen.centerSort(frame, targetPosition.y * PPM - 15);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

    }

    public void handleCollision(Monster monster) {

        if(Math.random() >= 0.65f) {
            monster.applyFreeze(2, 4);
        } else {
            monster.applySlow(2.5f, 0.7f);
        }

    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 150);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha,150, body.getPosition());
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
