package wizardo.game.Spells.Hybrid.Laser;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class Laser_Projectile extends Laser_Spell {

    public static Sprite arcaneLaser = new Sprite(new Texture("Spells/Laser/LaserSprite.png"));
    public static Sprite frostLaser = new Sprite(new Texture("Spells/Laser/LaserFrostSprite.png"));
    public static Sprite fireLaser = new Sprite(new Texture("Spells/Laser/LaserFireSprite.png"));
    public static Sprite lightningLaser = new Sprite(new Texture("Spells/Laser/LaserLightningSprite.png"));

    Body body;
    RoundLight light;
    Sprite frame;

    Vector2 direction;
    float rotation;

    public Laser_Projectile() {

    }

    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            createBody();
            createLight();
            initialized = true;
        }

        drawFrame();
        adjustLight();

        stateTime += delta;
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        rifts(monster);
        frostbolts(monster);
    }

    public void drawFrame() {
        Sprite clone = screen.getSprite();
        clone.set(frame);
        clone.setRotation(rotation);
        clone.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        screen.addSortedSprite(clone);
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
        body = BodyFactory.spellProjectileCircleBody(adjustedSpawn, 7, true);
        body.setUserData(this);

        Vector2 velocity = direction.scl(speed);
        body.setLinearVelocity(velocity);
        rotation = velocity.angleDeg();
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha,40,body.getPosition());
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public void pickAnim() {
        switch(anim_element) {
            case FIRE -> {
                frame = fireLaser;
                red = 0.3f;
                green = 0.1f;
            }
            case FROST -> {
                frame = frostLaser;
                blue = 0.7f;
            }
            case ARCANE -> {
                frame = arcaneLaser;
                red = 0.6f;
                green = 0.1f;
                blue = 0.9f;
            }
            case LIGHTNING -> {
                frame = lightningLaser;
                red = 0.3f;
                green = 0.3f;
            }
        }
    }

    public void rifts(Monster monster) {
        if(rifts) {

            float procTreshold = 1 - .025f * player.spellbook.rift_lvl;
            if(monster == target) {
                procTreshold -= .2f;
            }

            if(Math.random() > procTreshold) {
                Rift_Zone rift = new Rift_Zone(monster.body.getPosition());
                rift.setElements(this);
                screen.spellManager.toAdd(rift);
            }

        }
    }

    public void frostbolts(Monster monster) {
        if(frostbolt) {

            float procTreshold = 1 - .0333f * player.spellbook.frostbolt_lvl;
            if(monster == target) {
                procTreshold -= .35f;
            }

            if(Math.random() > procTreshold) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.targetPosition = new Vector2(monster.body.getPosition());
                explosion.setElements(this);
                screen.spellManager.toAdd(explosion);
            }

        }
    }

}
