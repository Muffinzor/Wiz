package wizardo.game.Spells.Fire.Overheat;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.OverheatAnims;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Overheat_miniExplosion extends Overheat_Spell{

    Body body;
    RoundLight light;

    public Overheat_miniExplosion() {

        this.dmg = dmg/2f;
        radius = radius/2f;

    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            pickAnim();
            createBody();
            createLight();
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime > 0.2f) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void handleCollision(Monster monster) {
        fireball(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setScale(0.6f);
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        screen.centerSort(frame, body.getPosition().y * PPM - 10);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha,150,body.getPosition());
        light.dimKill(0.02f);
        screen.lightManager.addLight(light);
    }

    public void pickAnim() {
        switch(anim_element) {
            case FROST -> {
                anim = OverheatAnims.overheat_anim_frost;
                green = 0.15f;
                blue = 0.5f;
            }
            case FIRE -> {
                anim = OverheatAnims.overheat_anim_fire;
                red = 0.7f;
                green = 0.3f;
            }
        }
    }

    public void fireball(Monster monster) {
        if(fireball) {

            float level = (getLvl() + player.spellbook.fireball_lvl)/2f;
            float procRate = 0.8f - 0.05f * level;

            if(Math.random() >= procRate) {
                Overheat_TriggerExplosion fireball = new Overheat_TriggerExplosion();
                fireball.setElements(this);
                fireball.targetPosition = monster.body.getPosition();
                screen.spellManager.toAdd(fireball);
            }
        }
    }
}
