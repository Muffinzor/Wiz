package wizardo.game.Spells.Frost.Icespear;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Hybrid.CelestialStrike.CelestialStrike_Hit;
import wizardo.game.Spells.Hybrid.CelestialStrike.CelestialStrike_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Resources.SpellAnims.IcespearAnims.icespear_anim_frost;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Icespear_Projectile extends Icespear_Spell {

    boolean destroyed;
    Body body;
    RoundLight light;
    Vector2 direction;
    float rotation;

    float timerBeforeSplit;
    float duration;
    Monster splitMonster;

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

        if(nested_spell != null) {
            float chanceToProc = 0.5f;
            int quantity = 2 + nested_spell.getLvl();
            if(Math.random() > chanceToProc) {
                for (int i = 0; i < quantity; i++) {
                    Spell proj = nested_spell.clone();
                    proj.setElements(this);
                    proj.screen = screen;
                    proj.spawnPosition = new Vector2(monster.body.getPosition());
                    proj.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 0.5f);
                    screen.spellManager.toAdd(proj);
                }
            }
        }
    }

    public void split() {
        celestialStrike();
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
            spear.screen = screen;
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
        body = BodyFactory.spellProjectileCircleBody(adjustedSpawn, 8, true);
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

    public void celestialStrike() {
        if(celestialStrike) {
            float level = (getLvl() + player.spellbook.thunderstorm_lvl + player.spellbook.frozenorb_lvl) / 3f;
            float procRate = .85f - level * 0.05f;

            if(Math.random() >= procRate) {
                CelestialStrike_Hit strike = new CelestialStrike_Hit();
                strike.targetPosition = new Vector2(body.getPosition());
                strike.screen = screen;
                screen.spellManager.toAdd(strike);
            }
        }
    }
}
