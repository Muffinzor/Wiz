package wizardo.game.Spells.Frost.Icespear;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Resources.SpellAnims.IcespearAnims.icespear_anim_frost;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.world;

public class Icespear_Projectile extends Icespear_Spell {

    boolean destroyed;
    Body body;
    RoundLight light;
    Vector2 direction;
    float rotation;

    float timerBeforeSplit;
    float duration;
    Monster splitMonster;

    public boolean frostbolt = true;

    public Icespear_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {

        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);
        timerBeforeSplit = 0;

        duration = MathUtils.random(0.5f, 0.75f);

        anim = icespear_anim_frost;

        screen = currentScreen;
    }

    public void update(float delta) {
        if(!initialized) {
            createBody();
            createLight();
            initialized = true;
        }

        timerBeforeSplit += delta;
        stateTime += delta;
        adjustLight();
        drawFrame();

        if(canSplit && currentSplits < maxSplits) {
            split();
            destroyed = true;
        }

        if(destroyed) {
            world.destroyBody(body);
            light.dimKill(0.2f);
            screen.spellManager.toRemove(this);
        }
    }

    public void handleCollision(Monster monster) {

        monster.hp -= dmg;

        if(timerBeforeSplit >= minimumTimeForSplit) {
            canSplit = true;
            splitMonster = monster;
        }

        Icespear_Hit hit = new Icespear_Hit(body.getPosition(), rotation);
        screen.spellManager.toAdd(hit);

        if(frostbolt) {
            float chanceToProc = 0.95f;
            if(Math.random() > chanceToProc) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion(monster.body.getPosition());
                screen.spellManager.toAdd(explosion);
            }
        }
    }

    public void split() {
        currentSplits++;
        normalSplit();
    }

    public void normalSplit() {
        int shards = 2;
        float initialAngle = rotation;
        float halfCone = 8f * shards / 2;
        float stepSize = 8f * shards / (shards - 1);

        for (int i = 0; i < shards; i++) {
            float angle = initialAngle - halfCone + (stepSize * i);
            Vector2 direction = new Vector2(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));
            Icespear_Projectile spear = new Icespear_Projectile(body.getPosition(), body.getPosition().cpy().add(direction));
            spear.currentSplits = currentSplits;
            spear.stateTime = stateTime;
            spear.setNextSpear(this);
            screen.spellManager.toAdd(spear);
        }
    }

    public void drawFrame() {

        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.rotate(rotation + 180);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

    }

    public void createBody() {
        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction = new Vector2(1,0);
        }

        Vector2 offset = new Vector2(direction.cpy().scl(1));
        Vector2 adjustedSpawn = new Vector2(spawnPosition.add(offset));
        body = BodyFactory.spellProjectileBody(adjustedSpawn, 8, true);
        body.setUserData(this);

        Vector2 velocity = direction.scl(speed);
        body.setLinearVelocity(velocity);
        rotation = velocity.angleDeg();
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0,0.1f,0.5f,1,25, body.getPosition());
        light.toLightManager();
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }
}
