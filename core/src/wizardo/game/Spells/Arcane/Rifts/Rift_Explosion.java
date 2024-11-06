package wizardo.game.Spells.Arcane.Rifts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.RiftsAnims;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Rift_Explosion extends Rifts_Spell {

    Body body;
    RoundLight light;

    int rotation;
    boolean flipX;
    boolean flipY;

    public Rift_Explosion(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

    }

    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            initialized = true;
            createBody();
            createLight();
        }

        drawFrame();

        stateTime += delta;

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        } else if(stateTime > 0.2f) {
            body.setActive(false);
        }


    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);

        if(frostbolt) {
            frostbolt(monster);
        }
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.flip(flipX, flipY);
        frame.setRotation(rotation);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
        System.out.println(body.getPosition());
        System.out.println(player.pawn.body.getPosition());
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, 120, body.getPosition());
        light.dimKill(0.015f);
        screen.lightManager.addLight(light);
    }

    public void pickAnim() {
        switch (anim_element) {
            case ARCANE -> {
                anim = RiftsAnims.rift_explosion_anim_arcane;
                red = 1;
                green = 0.4f;
                blue = 0.6f;
            }
            case FROST -> {
                anim = RiftsAnims.rift_explosion_anim_frost;
                red = 0.1f;
                green = 0.15f;
                blue = 0.8f;
            }
        }
    }

    public void frostbolt(Monster monster) {
        monster.applySlow(4, 0.75f);
        float frozenproc = 0.9f - player.spellbook.frostbolt_lvl * .05f;

        if(monster.freezeImmunityTimer <= 0 && Math.random() > frozenproc) {
            monster.applyFreeze(3, 7);
            monster.hp -= 20 * player.spellbook.frostbolt_lvl;
        }
    }
}
