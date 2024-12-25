package wizardo.game.Spells.Unique.BlackHole;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.EffectAnims.BlackHoleAnims;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class BlackHole_Effect extends BlackHole_Spell {

    RoundLight light;
    boolean lightKilled;
    BlackHole_KillBody killbody;

    boolean bodySpawned;
    boolean bodyKilled;

    int rotation;
    boolean flipX;
    boolean flipY;

    float duration;
    float lightRadius = 100;

    ArrayList<MonsterSpell> projs;

    public BlackHole_Effect(Vector2 targetPosition) {

        this.targetPosition = new Vector2(targetPosition);
        projs = new ArrayList<>();

        anim = BlackHoleAnims.blackhole_anim;

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

        duration = anim.getAnimationDuration();

        red = 0.6f;
        blue = 0.9f;

    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createLight();
        }

        drawFrame();
        stateTime += delta;

        if(delta > 0) {
            rotation += 1;
        }

        adjustLight();

        if(!bodySpawned && stateTime >= anim.getAnimationDuration() * 0.1f) {
            createPullBody();
            createKillBody();
            bodySpawned = true;
        }

        if(!bodyKilled && stateTime >= anim.getAnimationDuration() * 0.9f) {
            world.destroyBody(body);
            killbody.terminate();
            for(MonsterSpell proj : projs) {
                proj.blackholeBody = null;
            }
            bodyKilled = true;
        }

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.toRemove(this);
        }
    }

    public void createPullBody() {
        radius = 220;
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createKillBody() {
        BlackHole_KillBody body = new BlackHole_KillBody(targetPosition);
        screen.spellManager.toAdd(body);
        killbody = body;
    }

    public void handleCollision(Monster monster) {
        try {
            float radius = 200;
            float startStrength = 1.0f; // Strength at radius
            float endStrength = 4.0f; // Strength at center

            float dst = body.getPosition().dst(monster.body.getPosition()) * PPM;
            dst = Math.min(dst, radius);
            float strength = startStrength + (endStrength - startStrength) * (1 - dst / radius);

            float duration = anim.getAnimationDuration() - stateTime;

            Vector2 toCenter = body.getPosition().sub(monster.body.getPosition());
            monster.movementManager.applyPush(toCenter, strength, duration, 1.01f);

        } catch (Exception e) {
            // Do nothing, just be a failure.
        }
    }

    public void handleCollision(MonsterSpell proj) {
        try {
            proj.blackholeBody = body;
            projs.add(proj);
        } catch (Exception e) {
            // Do nothing, just be a failure.
        }
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0.6f, 0, 0.9f, 1, lightRadius, targetPosition);
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        if(lightRadius < 240 && stateTime < duration/2) {
            lightRadius += 4;
            light.setLight(red, green, blue, 0.9f, lightRadius, targetPosition);
        }

        if(stateTime > duration * 0.9f && !lightKilled) {
            light.dimKill(0.01f);
            lightKilled = true;
        }
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setScale(1.45f);
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        screen.addUnderSprite(frame);
    }

}
