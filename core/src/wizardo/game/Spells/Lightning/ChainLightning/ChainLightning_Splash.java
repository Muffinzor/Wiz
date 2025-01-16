package wizardo.game.Spells.Lightning.ChainLightning;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Elemental;
import wizardo.game.Spells.Spell;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class ChainLightning_Splash extends ChainLightning_Spell {

    public boolean arcaneMissiles;

    Body body;
    RoundLight light;

    int rotation;
    boolean flipX;
    boolean flipY;

    public ChainLightning_Splash(Vector2 targetPosition) {

        this.targetPosition = new Vector2(targetPosition);
        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

    }

    public void update(float delta) {
        if(!initialized) {
            dmg = dmg/2;
            pickAnim();
            createBody();
            createLight();
            initialized = true;
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.2f) {
            body.setActive(false);
            light.dimKill(0.01f);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        frame.setScale(1.2f);
        screen.centerSort(frame, body.getPosition().y * PPM - 10);
        screen.addSortedSprite(frame);
    }

    public void pickAnim() {
        anim = ExplosionAnims_Elemental.getLightningCloud(anim_element);
        switch(anim_element) {
            case FROST, COLDLITE -> {
                red = 0.2f;
                blue = 0.9f;
            }
            case ARCANE -> {
                red = 0.75f;
                blue = 0.95f;
            }
            case LIGHTNING -> {
                red = 0.55f;
                green = 0.35f;
            }
            case FIRE, FIRELITE -> {
                red = 0.85f;
                green = 0.15f;
            }
        }
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 50);
        body.setUserData(this);
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha,100,targetPosition);
        screen.lightManager.addLight(light);
    }

}
