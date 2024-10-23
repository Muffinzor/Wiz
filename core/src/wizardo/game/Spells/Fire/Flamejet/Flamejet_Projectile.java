package wizardo.game.Spells.Fire.Flamejet;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.FlamejetAnims;
import wizardo.game.Spells.SpellManager;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.world;

public class Flamejet_Projectile extends Flamejet_Spell {

    public Body body;
    Vector2 direction;
    Vector2 velocity;
    float rotation;
    boolean flipX;

    float distance = 5f;

    public Flamejet_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {
        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);

        screen = currentScreen;

        flipX = MathUtils.randomBoolean();

        anim = FlamejetAnims.flamejet_fire_anim;
    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            pickAnim();
            createBody();
            createDetector(velocity);
        }

        drawFrame();
        stateTime += delta;

        if(getDistanceFromSpawn() > distance && !body.getLinearVelocity().isZero()) {
            body.setLinearVelocity(0,0);
        }

        if(!body.getLinearVelocity().isZero()) {
            createLight();
        }

        if(stateTime > 0.5f) {
            body.setActive(false);
        }

        if(stateTime > 1) {
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
        frame.setOrigin(frame.getWidth()/2f, 0);
        frame.setRotation(rotation - 90);
        frame.setPosition(spawnPosition.x * PPM - frame.getWidth()/2f, spawnPosition.y * PPM);
        screen.addSortedSprite(frame);

        float dst = spawnPosition.dst(body.getPosition()) + 2;
        float totalLength = frame.getHeight();
        float scale = dst*PPM/totalLength;
        frame.setScale(1, scale);
        frame.flip(flipX, false);

        Vector2 center = getSpriteCenter(frame);
        screen.centerSort(frame, center.y - 25);
    }

    public void createBody() {
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction = new Vector2(1,0);
        }

        if(flames > 1) {
            float angleVariation = Math.min(flames * 5, 25);
            float randomAngle = MathUtils.random(-angleVariation, angleVariation);
            direction.rotateDeg(randomAngle);
        }

        //Vector2 offset = new Vector2(direction.cpy().scl(1));
        Vector2 adjustedSpawn = new Vector2(spawnPosition);
        body = BodyFactory.spellProjectileCircleBody(adjustedSpawn, 25, true);
        body.setUserData(this);

        velocity = direction.scl(speed);
        body.setLinearVelocity(velocity);
        rotation = velocity.angleDeg();
    }

    public void createDetector(Vector2 velocity) {
        Collision_Detector detector = new Collision_Detector(velocity, this);
        screen.spellManager.toAdd(detector);
    }

    public void createLight() {
        float lightIntensity = 0.75f - flames * 0.035f;
        RoundLight light = screen.lightManager.pool.getLight();
        light.setLight(0.5f, 0.2f, 0, lightIntensity, 75, body.getPosition());
        screen.lightManager.addLight(light);
        light.dimKill(0.02f);
    }

    public void pickAnim() {
        if(MathUtils.randomBoolean()) {
            anim = FlamejetAnims.flamejet_fire_anim;
        } else {
            anim = FlamejetAnims.flamejet_fire_anim2;
        }
    }

    public float getDistanceFromSpawn() {
        return body.getPosition().dst(spawnPosition);
    }

    public Vector2 getSpriteCenter(Sprite frame) {
        // Get the width and height of the sprite
        float spriteWidth = frame.getWidth();
        float spriteHeight = frame.getHeight();

        // Get the current position (bottom-left corner) of the sprite
        float x = frame.getX();
        float y = frame.getY();

        // Calculate the origin point (in local coordinates)
        float originX = spriteWidth / 2f;  // Horizontal center
        float originY = 0;                 // Bottom of the sprite (as set in setOrigin)

        // Calculate the offset from the origin to the center of the sprite (before rotation)
        float offsetX = spriteWidth / 2f;  // The center horizontally
        float offsetY = spriteHeight / 2f; // The center vertically

        // Apply the rotation to these offsets
        float rotation = frame.getRotation(); // Rotation in degrees
        float cosRotation = MathUtils.cosDeg(rotation);
        float sinRotation = MathUtils.sinDeg(rotation);

        // Rotated offset coordinates
        float rotatedOffsetX = offsetX * cosRotation - offsetY * sinRotation;
        float rotatedOffsetY = offsetX * sinRotation + offsetY * cosRotation;

        // Calculate the world position of the center of the sprite
        float centerX = x + originX + rotatedOffsetX;
        float centerY = y + originY + rotatedOffsetY;

        return new Vector2(centerX, centerY);
    }
}
