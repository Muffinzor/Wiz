package wizardo.game.Spells.Arcane.ArcaneMissiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;

import static wizardo.game.Resources.SpellAnims.ArcaneMissileAnims.arcanemissile_arcane_anim;
import static wizardo.game.Spells.SpellUtils.hasLineOfSight;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;

public class ArcaneMissile_Projectile extends ArcaneMissile_Spell {

    Body body;
    RoundLight light;

    float rotation;
    Vector2 direction;

    Monster targetMonster;
    boolean targetLocked;

    int collisions = 0;


    public ArcaneMissile_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {
        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);

        screen = currentScreen;

        anim = arcanemissile_arcane_anim;
    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            createLight();
        }

        stateTime += delta;
        drawFrame();
        adjustLight();
        arcaneTargeting();

    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.rotate(rotation);
        screen.centerSort(frame, body.getPosition().y * PPM - 20);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction.set(1,0);
        }

        Vector2 offset = new Vector2(direction.cpy().scl(1));
        Vector2 adjustedSpawn = new Vector2(spawnPosition.add(offset));

        body = BodyFactory.spellProjectileCircleBody(adjustedSpawn, 5, true);
        body.setUserData(this);

        int missiles = 2;

        float angleVariation = Math.min(3f * missiles, 15);
        float randomAngle = MathUtils.random(-angleVariation, angleVariation);
        direction.rotateDeg(randomAngle);

        Vector2 velocity = direction.cpy().scl(speed);
        body.setLinearVelocity(velocity);
        rotation = velocity.angleDeg();

    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0.6f, 0.1f, 0.9f, 0.75f, 35, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        if(light != null) {
            light.pointLight.setPosition(body.getPosition().scl(PPM));
        }
    }

    public void arcaneTargeting() {
        if(!targetLocked && !screen.monsterManager.liveMonsters.isEmpty()) {

            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(body.getPosition(), 6);

            float max = 0;
            for(Monster monster : inRange) {
                if(monster.hp > max && monster.body != null && hasLineOfSight(body.getPosition(), monster.body.getPosition())) {
                    max = monster.hp;
                    targetMonster = monster;
                    targetLocked = true;
                }
            }

        } else if (targetLocked && targetMonster != null && targetMonster.body != null && targetMonster.hp > 0) {
            Vector2 targetPosition = targetMonster.body.getPosition();
            Vector2 missilePosition = body.getPosition();

            Vector2 targetDirection = new Vector2(targetPosition).sub(missilePosition);
            if(targetDirection.len() > 0) {
                targetDirection.nor();
            } else {
                targetDirection.set(1,0);
            }
            float targetAngle = targetDirection.angleDeg();
            float currentAngle = direction.angleDeg();
            float angleDiff = targetAngle - currentAngle;

            //Find the shortest rotation turn
            if (angleDiff > 180) {
                angleDiff -= 360;
            } else if (angleDiff < -180) {
                angleDiff += 360;
            }

            //Clamp the angle difference to the maximum allowed increment
            float maxRotationPerFrame = 6f;
            float rotationStep = MathUtils.clamp(angleDiff, -maxRotationPerFrame, maxRotationPerFrame);

            direction.rotateDeg(rotationStep);

            if(direction.len() > 0) {
                direction.nor().scl(speed);
            }
            body.setLinearVelocity(direction);
            rotation = direction.angleDeg();
        } else if (targetMonster != null && targetMonster.hp <= 0) {
            targetLocked = false;
        }

    }
}
