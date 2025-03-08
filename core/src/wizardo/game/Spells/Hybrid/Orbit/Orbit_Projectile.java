package wizardo.game.Spells.Hybrid.Orbit;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Items.Equipment.Hat.Epic_OrbitHat;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.FrozenorbAnims;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Orbit_Projectile extends Orbit_Spell {

    public boolean reverse;

    Body body;
    RoundLight light;
    float alpha = 1;

    float angle;
    int rotation;

    float animTime;

    public Orbit_Projectile(Vector2 spawnPosition, float angle) {
        this.spawnPosition = new Vector2(spawnPosition);
        this.angle = angle;
        radius = 24 * (1 + player.spellbook.iceRadiusBonus/100f);

        rotation = MathUtils.random(360);
        anim = FrozenorbAnims.orbit_anim_frost;
    }

    public void update(float delta) {
        if(!initialized) {
            animTime = (float) Math.random();
            initialized = true;
            speed = getScaledSpeed();
            pickAnim();
            createBody();
            createLight();
        }

        stateTime += delta;
        if(reverse) {
            angle -= speed * delta;
        } else {
            angle += speed * delta;
        }
        float x = player.pawn.getBodyX() + orbitRadius * MathUtils.cos(angle);
        float y = player.pawn.getBodyY() + orbitRadius * MathUtils.sin(angle);
        body.setTransform(x, y, body.getAngle());
        if(delta > 0) {
            rotation -= 5;
        }

        adjustLight();
        drawFrame();

        if(alpha <= 0.02f) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        } else if(stateTime >= duration) {
            alpha -= 0.02f;
            light.dimKill(0.01f);
        }

    }

    public void pickAnim() {
        switch(anim_element) {
            case FROST -> {
                anim = FrozenorbAnims.orbit_anim_frost;
                blue = 0.9f;
            }
            case ARCANE -> {
                anim = FrozenorbAnims.orbit_anim_arcane;
                red = 0.9f;
                green = 0.4f;
                blue = 1;
            }
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        frostbolts(monster);
        if(player.inventory.equippedHat instanceof Epic_OrbitHat) {
            rift(monster);
        }
    }

    public void rift(Monster monster) {
        float procRate = 0.9f;
        if(Math.random() >= procRate) {
            Rift_Zone rift = new Rift_Zone(monster.body.getPosition());
            rift.setElements(this);
            rift.frostbolt = frostbolt;
            screen.spellManager.add(rift);
        }
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime + animTime, true));
        frame.setRotation(rotation);

        if(alpha < 1) {
            frame.setAlpha(alpha);
        }

        if(player.spellbook.iceRadiusBonus > 0) {
            frame.setScale(1 + (player.spellbook.iceRadiusBonus/100f));
        }


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
        light.setLight(red, green, blue, lightAlpha, 75, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public void frostbolts(Monster monster) {
        if(frostbolt) {
            float procRate = 0.95f - 0.05f * player.spellbook.frostbolt_lvl;
            if (Math.random() >= procRate) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 1);
                explosion.setElements(this);
                screen.spellManager.add(explosion);
            }
        }
    }

}
