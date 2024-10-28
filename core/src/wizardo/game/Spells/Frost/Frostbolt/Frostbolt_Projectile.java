package wizardo.game.Spells.Frost.Frostbolt;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Utils.BodyFactory;
import static wizardo.game.Resources.SpellAnims.FrostboltAnims.frostbolt_anim_frost;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.world;

public class Frostbolt_Projectile extends Frostbolt_Spell{

    private boolean hasCollided;
    private Body body;
    private float alpha = 1;
    private RoundLight light;
    private float rotation;
    private Vector2 direction;

    public Frostbolt_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {

        this.spawnPosition = spawnPosition;
        this.targetPosition = targetPosition;
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));

        speed = speed * MathUtils.random(0.85f, 1.15f);
        soundPath = "Sounds/Spells/IceThrow1.wav";

        screen = currentScreen;

    }

    @Override
    public void update(float delta) {

        stateTime += delta;

        if(!initialized) {
            //playSound(screen.player.pawn.getPosition());
            createBody();
            createLight();
            initialized = true;
        }

        drawFrame();
        updateLight();

        if(hasCollided) {

            Frostbolt_Explosion explosion = new Frostbolt_Explosion();
            explosion.targetPosition = new Vector2(body.getPosition());
            explosion.inherit(this);
            screen.spellManager.toAdd(explosion);
            world.destroyBody(body);
            body = null;
            light.kill();
            screen.spellManager.toRemove(this);

        } else if(stateTime > 2) {

            alpha -= 0.02f;

            if(alpha <= 0) {
                world.destroyBody(body);
                body = null;
                light.kill();
                screen.spellManager.toRemove(this);
            }
        }
    }

    public void handleCollision(Monster monster) {
        hasCollided = true;
    }

    public void handleCollision(Fixture obstacle) {
        hasCollided = true;
    }

    public void createBody() {
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction = new Vector2(1,0);
        }

        if(projectiles > 1) {
            float angleVariation = Math.min(projectiles * 2, 15);
            float randomAngle = MathUtils.random(-angleVariation, angleVariation);
            direction.rotateDeg(randomAngle);
        }

        Vector2 offset = new Vector2(direction.cpy().scl(1));
        Vector2 adjustedSpawn = new Vector2(spawnPosition.add(offset));
        body = BodyFactory.spellProjectileCircleBody(adjustedSpawn, 5, true);
        body.setUserData(this);

        Vector2 velocity = direction.scl(speed);
        body.setLinearVelocity(velocity);
        rotation = velocity.angleDeg();
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0,0,0.8f,1,25, body.getPosition());
    }

    public void updateLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public void drawFrame() {

        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(frostbolt_anim_frost.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.rotate(rotation);
        if(alpha < 1) {
            frame.setAlpha(alpha);
        }
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

    }

    public void dispose() {

    }
}
