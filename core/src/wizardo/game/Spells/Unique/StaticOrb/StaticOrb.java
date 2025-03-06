package wizardo.game.Spells.Unique.StaticOrb;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Book.Legendary_PulseBook;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.SpellAnims.StaticOrbAnims;
import wizardo.game.Spells.Spell;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class StaticOrb extends Spell {

    RoundLight light;
    float alpha = 1;
    float scale = 0.1f;

    boolean flipX;
    boolean flipY;
    int rotation;

    Legendary_PulseBook book;

    public StaticOrb(Vector2 targetPosition, Legendary_PulseBook book) {
        this.targetPosition = new Vector2(targetPosition);
        this.book = book;

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            pickAnim();
            createBody();
            createLight();

        }

        stateTime += delta;
        if(stateTime % 0.5 < delta) {
            StaticPulse pulse = new StaticPulse(body.getPosition());
            pulse.anim_element = anim_element;
            screen.spellManager.add(pulse);
        }

        if(stateTime >= 2.5f) {
            alpha -= 0.02f;
            if(light != null) {
                light.dimKill(0.02f);
                light = null;
                book.orbsAlive--;
            }
        }

        if(alpha <= 0.05f) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }

        drawFrame();
    }

    public void pickAnim() {
        switch(anim_element) {
            case FROST, COLDLITE -> {
                blue = 0.9f;
                green = 0.25f;
                anim = StaticOrbAnims.staticorb_anim_frost;
            }
            case LIGHTNING -> {
                red = 0.5f;
                green = 0.5f;
                anim = StaticOrbAnims.staticorb_anim_lightning;
            }
            case FIRE, FIRELITE -> {
                red = 0.8f;
                green = 0.15f;
                anim = StaticOrbAnims.staticorb_anim_fire;
            }
            case ARCANE -> {
                red = 0.75f;
                blue = 0.9f;
                anim = StaticOrbAnims.staticorb_anim_arcane;
            }
        }
    }

    public void drawFrame() {
        scale += 0.05f;
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.flip(flipX, flipY);
        frame.setRotation(rotation);
        if(alpha < 1) {
            frame.setAlpha(alpha);
        }
        if(scale < 1) {
            frame.setScale(scale);
        }
        screen.addSortedSprite(frame);
        screen.centerSort(frame, body.getPosition().y * PPM - 15);
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 10);
        body.setUserData(this);
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, 1, 50, body.getPosition());
        screen.lightManager.addLight(light);
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
