package wizardo.game.Spells.Hybrid.Laser;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Items.Equipment.Amulet.Legendary_MarkAmulet;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Hit;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.ARCANE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

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

    int collisions = 0;

    public Laser_Projectile() {
        main_element = ARCANE;
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

        if(stateTime >= 2) {
            world.destroyBody(body);
            light.dimKill(0.5f);
            screen.spellManager.remove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        if(player.inventory.equippedAmulet instanceof Legendary_MarkAmulet && collisions == 0) {
            dealDmg(monster);
        }
        rifts(monster);
        frostbolts(monster);
        chargedbolts(monster);
        thunderstorm(monster);
        flamejet(monster);
        collisions ++;
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
        screen.lightManager.addLight(light);
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

    public void flamejet(Monster monster) {
        if(flamejet) {
            float procRate;
            if(collisions == 0) {
                procRate = 0.33f - player.spellbook.flamejet_lvl * 0.03f;
            } else {
                procRate = 0.96f - player.spellbook.flamejet_lvl * 0.02f;
            }
            if(Math.random() >= procRate) {
                Flamejet_Spell flame = new Flamejet_Spell();
                Vector2 direction = new Vector2(body.getLinearVelocity());
                Vector2 spawn = new Vector2(monster.body.getPosition());
                flame.spawnPosition = spawn;
                flame.targetPosition = spawn.cpy().add(direction);
                flame.setElements(this);
                screen.spellManager.add(flame);
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
                screen.spellManager.add(rift);
            }

        }
    }

    public void frostbolts(Monster monster) {
        if(frostbolt) {

            float procTreshold = 0.9833f - .0333f * player.spellbook.frostbolt_lvl;
            if(monster == target) {
                procTreshold -= .35f;
            }

            if(Math.random() > procTreshold) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.targetPosition = new Vector2(monster.body.getPosition());
                explosion.setElements(this);
                screen.spellManager.add(explosion);
            }

        }
    }

    public void chargedbolts(Monster monster) {
        if(chargedbolt) {

            float procTreshold = 0.9833f - .0333f * player.spellbook.chargedbolt_lvl;
            if(monster == target) {
                procTreshold -= .35f;
            }

            if(Math.random() > procTreshold) {
                int quantity = 2 + player.spellbook.chargedbolt_lvl/3;
                for (int i = 0; i < quantity; i++) {
                    ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                    bolt.spawnPosition = new Vector2(monster.body.getPosition());
                    bolt.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 2);
                    bolt.setElements(this);
                    screen.spellManager.add(bolt);
                }
            }

        }
    }

    public void thunderstorm(Monster monster) {
        if(thunderstorm) {

            float procTreshold = 1 - .025f * player.spellbook.thunderstorm_lvl;
            if(monster == target) {
                procTreshold -= .2f;
            }

            if(Math.random() > procTreshold) {
                Thunderstorm_Hit thunder = new Thunderstorm_Hit(monster.body.getPosition());
                thunder.setElements(this);
                screen.spellManager.add(thunder);
            }
        }
    }

}
