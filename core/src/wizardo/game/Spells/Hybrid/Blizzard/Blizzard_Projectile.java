package wizardo.game.Spells.Hybrid.Blizzard;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.SpellAnims.IcespearAnims;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class Blizzard_Projectile extends Blizzard_Spell {

    Body body;
    RoundLight light;
    Vector2 startingPosition = new Vector2();
    Vector2 direction;
    float rotation;

    public Blizzard_Projectile(Vector2 targetPosition) {

        this.targetPosition = new Vector2(targetPosition);

    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            pickAnim();
            setStartingPosition();
            createBody();
            createLight();
        }

        drawFrame();
        stateTime += delta;

        adjustLight();

        if(stateTime > 0.35f) {
            Blizzard_Explosion hit = new Blizzard_Explosion();
            hit.targetPosition = new Vector2(body.getPosition());
            hit.screen = screen;
            hit.frostbolts = frostbolts;
            hit.setElements(this);
            screen.spellManager.toAdd(hit);
            light.dimKill(0.5f);
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        screen.centerSort(frame, targetPosition.y * PPM);
        screen.addSortedSprite(frame);
    }

    public void setStartingPosition() {
        float angleVariation = MathUtils.random(-10, 10);

        float angleRad = angleVariation * MathUtils.degreesToRadians;

        float distance = 10;

        float offsetX = distance * MathUtils.sin(angleRad);
        float offsetY = distance * MathUtils.cos(angleRad);

        startingPosition.set(targetPosition.x + offsetX, targetPosition.y + offsetY);
    }

    public void pickAnim() {
        switch(anim_element) {
            case FROST -> {
                anim = IcespearAnims.icespear_anim_frost;
                green = 0.4f;
                blue = 0.7f;
            }
            case ARCANE -> {
                anim = IcespearAnims.icespear_anim_arcane;
                red = 0.75f;
                green = 0.2f;
                blue = 0.9f;
            }
        }
    }

    public void createBody() {
        body = BodyFactory.spellProjectileCircleBody(startingPosition, 1, true);
        body.setUserData(this);

        direction = new Vector2(targetPosition.cpy().sub(startingPosition).nor());
        Vector2 velocity = direction.cpy().scl(speed);
        body.setLinearVelocity(velocity);


        rotation = velocity.angleDeg();
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue,1, 25, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }
}
