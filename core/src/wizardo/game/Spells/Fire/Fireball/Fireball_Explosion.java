package wizardo.game.Spells.Fire.Fireball;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.SpellAnims.FireballAnims;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.world;

public class Fireball_Explosion extends Fireball_Spell {

    Body body;
    RoundLight light;

    float rotation;
    boolean flipX;
    boolean flipY;

    public Fireball_Explosion(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);
        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

        screen = currentScreen;

        anim = FireballAnims.fireball_explosion_anim_fire;
    }

    public void update(float delta) {
        stateTime += delta;
        if(!initialized) {
            createBody();
            createLight();
            initialized = true;
        }

        if(stateTime >= 0.15f && body.isActive()) {
            body.setActive(false);
        }

        drawFrame();

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void drawFrame() {

        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        //frame.rotate(rotation);
        frame.flip(flipX, false);
        screen.centerSort(frame, body.getPosition().y * PPM - 30);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(1f, 0.25f, 0f, 0.8f, 250, body.getPosition());
        light.toLightManager();
        light.dimKill(0.01f);
    }
}
