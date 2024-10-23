package wizardo.game.Spells.Arcane.Rifts;

import com.badlogic.gdx.graphics.g2d.Sprite;
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

    public Rift_Explosion(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);
        screen = currentScreen;

        anim = RiftsAnims.rift_explosion_anim_arcane;

    }

    public void update(float delta) {
        if(!initialized) {
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
        monster.hp -= dmg;
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
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
        light.setLight(1, .4f, .6f, 1, 120, body.getPosition());
        light.dimKill(0.015f);
        screen.lightManager.addLight(light);
    }
}
