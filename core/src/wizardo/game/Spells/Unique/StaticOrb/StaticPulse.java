package wizardo.game.Spells.Unique.StaticOrb;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.StaticOrbAnims;
import wizardo.game.Spells.Spell;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class StaticPulse extends Spell {

    RoundLight light;

    boolean flipX;
    boolean flipY;
    int rotation;

    public StaticPulse(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);

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

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.2f) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.remove(this);
            world.destroyBody(body);
        }
    }

    public void drawFrame() {

        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.flip(flipX, flipY);
        frame.setRotation(rotation);
        screen.addSortedSprite(frame);
        screen.centerSort(frame, body.getPosition().y * PPM);
    }

    public void pickAnim() {
        main_element = anim_element;
        switch(anim_element) {
            case FROST, COLDLITE -> {
                blue = 0.9f;
                green = 0.25f;
                anim = StaticOrbAnims.staticpulse_anim_frost;
            }
            case LIGHTNING -> {
                red = 0.5f;
                green = 0.5f;
                blue = 0.3f;
                anim = StaticOrbAnims.staticpulse_anim_lightning;
            }
            case FIRE, FIRELITE -> {
                red = 0.8f;
                green = 0.15f;
                anim = StaticOrbAnims.staticpulse_anim_fire;
            }
            case ARCANE -> {
                red = 0.75f;
                blue = 0.9f;
                anim = StaticOrbAnims.staticpulse_anim_arcane;
            }
        }
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 80);
        body.setUserData(this);
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, 1, 160, body.getPosition());
        screen.lightManager.addLight(light);
        light.dimKill(0.012f);
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
        return 24 + 4 * player.level;
    }
}
