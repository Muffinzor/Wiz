package wizardo.game.Spells.Unique.ThundergodBolt;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.SpellAnims.IcespearAnims;
import wizardo.game.Resources.SpellAnims.LightningBoltAnims;
import wizardo.game.Spells.Hybrid.Blizzard.Blizzard_Explosion;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class ThundergodBolt_Projectile extends ThundergodBolt_Spell{

    Body body;
    RoundLight light;
    Vector2 startingPosition = new Vector2();
    Vector2 direction;
    float rotation;
    float frameScale;

    public ThundergodBolt_Projectile(Vector2 targetPosition) {
        stateTime = (float) Math.random();
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

        checkDistance();

    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.setScale(1, 0.35f);

        screen.centerSort(frame, targetPosition.y * PPM);
        screen.addSortedSprite(frame);
    }

    public void checkDistance() {
        float dst = body.getPosition().dst(targetPosition);
        if(dst < 0.5f) {
            ThundergodBolt_Explosion explosion = new ThundergodBolt_Explosion(body.getPosition());
            screen.spellManager.toAdd(explosion);

            screen.spellManager.toRemove(this);
            light.dimKill(0.5f);
            world.destroyBody(body);
        }
    }

    public void setStartingPosition() {
        float angleVariation = MathUtils.random(10, 12);

        float angleRad = angleVariation * MathUtils.degreesToRadians;

        float distance = MathUtils.random(14,16);

        float offsetX = distance * MathUtils.sin(angleRad);
        float offsetY = distance * MathUtils.cos(angleRad);

        startingPosition.set(targetPosition.x + offsetX, targetPosition.y + offsetY);
        targetPosition = SpellUtils.getRandomVectorInRadius(targetPosition, 1);
    }

    public void pickAnim() {
        anim = LightningBoltAnims.lightningbolt_anim;
        red = 0.5f;
        green = 0.35f;
        frameScale = 0.6f;
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
        light.setLight(red, green, blue,1, 50, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }
}
