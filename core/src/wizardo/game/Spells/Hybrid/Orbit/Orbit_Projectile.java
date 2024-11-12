package wizardo.game.Spells.Hybrid.Orbit;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.FrozenorbAnims;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FROST;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Orbit_Projectile extends Orbit_Spell {

    Body body;
    RoundLight light;
    float alpha = 1;

    float angle;
    float duration;
    int rotation;

    float animTime;

    public Orbit_Projectile(Vector2 spawnPosition, float angle) {
        this.spawnPosition = new Vector2(spawnPosition);
        this.angle = angle;
        radius = 18;
        duration = 6;

        rotation = MathUtils.random(360);
        anim = FrozenorbAnims.frozenorb_anim_frost;
    }

    public void update(float delta) {
        if(!initialized) {
            animTime = (float) Math.random();
            initialized = true;
            createBody();
            createLight();
        }

        stateTime += delta;
        angle += speed * delta;
        float x = player.pawn.getBodyX() + orbitRadius * MathUtils.cos(angle);
        float y = player.pawn.getBodyY() + orbitRadius * MathUtils.sin(angle);
        body.setTransform(x, y, body.getAngle());

        adjustLight();
        drawFrame();

        if(alpha <= 0.1f) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        } else if(stateTime >= duration) {
            alpha -= 0.02f;
            light.dimKill(0.01f);
        }

    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        bombs();
        frostbolts(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime + animTime, true));
        frame.setRotation(rotation);
        rotation -= 5;

        if(alpha < 1) {
            frame.setAlpha(alpha);
        }

        frame.setScale(0.4f);

        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        screen.centerSort(frame, body.getPosition().y * PPM - 20);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        body = BodyFactory.spellProjectileCircleBody(spawnPosition, radius, true);
        body.setUserData(this);
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0.2f, 0, 0.8f, lightAlpha, 55, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public void bombs() {
        float procRate = 0.9f;
        if(Math.random() >= procRate) {
            Rift_Zone rift = new Rift_Zone(body.getPosition());
            rift.setElements(this);
            rift.anim_element = FROST;
            screen.spellManager.toAdd(rift);
        }
    }

    public void frostbolts(Monster monster) {
        float procRate = 0.95f - 0.05f * player.spellbook.frostbolt_lvl;
        if(Math.random() >= procRate) {
            Frostbolt_Explosion explosion = new Frostbolt_Explosion();
            explosion.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 1);
            explosion.setElements(this);
            screen.spellManager.toAdd(explosion);
        }
    }

}
