package wizardo.game.Spells.Fire.Overheat;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.OverheatAnims;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Overheat_Explosion extends Overheat_Spell {

    Body body;
    RoundLight light;

    boolean flipX;
    boolean flipY;
    int rotation;

    public Overheat_Explosion(Vector2 targetPosition) {

        this.targetPosition = new Vector2(targetPosition);

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

        screen = currentScreen;

        anim = OverheatAnims.overheat_anim_fire;
    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            createLight();
        }

        drawFrame();

        stateTime += delta;

        if(stateTime >= 0.2f) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void handleCollision(Monster monster) {
        monster.hp -= dmg;
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        screen.centerSort(frame, body.getPosition().y * PPM - 30);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        if(targetPosition == null) {
            targetPosition = new Vector2(player.pawn.body.getPosition());
        }
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(1f, .4f, 0, 1, 250, targetPosition);
        light.dimKill(0.015f);
        screen.lightManager.addLight(light);
    }
}
