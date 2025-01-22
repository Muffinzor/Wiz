package wizardo.game.Spells.Arcane.EnergyBeam;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Items.Equipment.Ring.Rare_BeamRing;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Fire.Fireball.Fireball_Explosion;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_Explosion;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Hit;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Resources.SpellAnims.EnergyBeamAnims.*;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class EnergyBeam_Projectile extends EnergyBeam_Spell {

    boolean hasCollided;
    boolean hasExploded;
    Vector2 direction;
    float rotation;

    Body body;
    Sprite bodyTile;
    Sprite endTile;
    float alpha = 1;
    float beamWidthRatio = 1;
    int frameCounter;
    int chainFrameInterval;

    public EnergyBeam_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {

        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);

    }

    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            initialized = true;
            createBody();
        }

        stateTime += delta;

        drawFrame();
        createLight(delta);
        frozenOrbBodies(delta);
        chainLightning();

        if(body.isActive() && body.getLinearVelocity().isZero()) {
            body.setActive(false);
        }

        if(stateTime > 0.2f && delta > 0) {
            alpha -= 0.03f;
        }

        if(hasCollided && !hasExploded) {
            explode();
            hasExploded = true;
        }

        if(alpha < 0.03f) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }
    }

    public void handleCollision(Monster monster) {
        monstersHit++;
        dealDmg(monster);
        frostbolts(monster);
        chargedbolts();
        thunderstorm(monster);
        flamejets();
        fireball(monster);

    }

    public void handleCollision(Fixture fix) {
        hasCollided = true;
        body.setLinearVelocity(0,0);
    }

    public void explode() {
        EnergyBeam_Explosion explosion = new EnergyBeam_Explosion();
        explosion.targetPosition = new Vector2(body.getPosition());
        explosion.setElements(this);
        screen.spellManager.add(explosion);
    }

    public void drawFrame() {

        float length = body.getPosition().dst(spawnPosition.x, spawnPosition.y);

        //Start of laser
        Sprite frame = screen.getSprite();
        frame.set(endTile);
        float height = frame.getHeight();
        frame.setSize(frame.getWidth() * beamWidthRatio, height * beamWidthRatio);
        frame.setOrigin(0, height/2 * beamWidthRatio);
        frame.setPosition(spawnPosition.x * PPM, spawnPosition.y * PPM - height/2 * beamWidthRatio);
        frame.setRotation(rotation + 180);
        frame.setAlpha(alpha);
        screen.addUnderSprite(frame);


        //Body of laser
        Sprite frame1 = screen.getSprite();
        frame1.set(bodyTile);
        frame1.setSize(length * PPM, height * beamWidthRatio);
        frame1.setOrigin(0, height/2 * beamWidthRatio);
        frame1.setPosition(spawnPosition.x * PPM , spawnPosition.y * PPM - height/2 * beamWidthRatio);
        frame1.setRotation(rotation);

        frame1.setAlpha(alpha);
        screen.addUnderSprite(frame1);

        //End of laser
        Sprite frame2 = screen.getSprite();
        frame2.set(endTile);
        Vector2 endPosition = new Vector2(body.getPosition());
        frame2.setSize(frame2.getWidth() * 2 * beamWidthRatio, height * beamWidthRatio);
        frame2.setOrigin(0, height/2 * beamWidthRatio);
        frame2.setPosition(endPosition.x * PPM, endPosition.y * PPM - height/2 * beamWidthRatio);
        frame2.setAlpha(alpha);
        frame2.setRotation(rotation);

        screen.addUnderSprite(frame2);

    }

    private void createBody() {
        direction = this.targetPosition.cpy().sub(this.spawnPosition);
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction.set(1,0);
        }
        rotation = direction.angleDeg();
        Vector2 bodySpawn = new Vector2(spawnPosition.cpy().add(direction.x * 2, direction.y * 2));

        int height = 28;
        if(player.inventory.equippedRing instanceof Rare_BeamRing) {
            height = 40;
        }
        body = BodyFactory.spellProjectileDiamondBody(bodySpawn, 100, height, rotation, true);
        body.setUserData(this);

        Vector2 velocity = direction.cpy().scl(150);
        body.setLinearVelocity(velocity);

    }

    public void createLight(float delta) {
        if(!hasCollided && delta > 0) {
            RoundLight light = screen.lightManager.pool.getLight();
            light.setLight(red, green, blue, lightAlpha, 125, body.getPosition());
            screen.lightManager.addLight(light);
            light.dimKill(0.01f);
        }
    }

    public void frozenOrbBodies(float delta) {
        if(frozenorb && body.isActive() && delta > 0) {
            EnergyBeam_FreezeBody freezeBody = new EnergyBeam_FreezeBody();
            freezeBody.setElements(this);
            freezeBody.frostbolt = frostbolt;
            freezeBody.targetPosition = body.getPosition();
            screen.spellManager.add(freezeBody);
        }
    }

    public void pickAnim() {
        lightAlpha = 0.7f;
        if(player.inventory.equippedRing instanceof Rare_BeamRing) {
            beamWidthRatio *= 1.5f;
        }

        switch (anim_element) {
            case ARCANE -> {
                bodyTile = arcaneTile;
                endTile = arcaneEndTile;
                red = 0.4f;
                green = 0.1f;
                blue = 0.8f;
            }
            case FIRE -> {
                bodyTile = fireTile;
                endTile = fireEndTile;
                red = 0.6f;
                green = 0.2f;
            }
            case LIGHTNING -> {
                bodyTile = lightningTile;
                endTile = lightningEndTile;
                red = 0.4f;
                green = 0.15f;
            }
            case FROST -> {
                bodyTile = frostTile;
                endTile = frostEndTile;
                blue = 0.8f;
                green = 0.2f;
            }
        }
    }

    public void flamejets() {
        if(flamejet) {
            float procRate = 0.833f - 0.033f * player.spellbook.flamejet_lvl;

            if(Math.random() >= procRate) {
                int jets = MathUtils.random(2,4);
                float totalConeAngle = Math.min(jets * 15, 60);
                float halfConeAngle = totalConeAngle / 2;
                float stepSize = totalConeAngle / (jets - 1);
                float initialAngle = direction.angleDeg();

                for (int i = 0; i < jets; i++) {
                    Vector2 newDirection;

                    float angle = initialAngle - halfConeAngle + (stepSize * i);
                    newDirection = new Vector2(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));


                    Flamejet_Spell jet = new Flamejet_Spell();
                    jet.setElements(this);
                    jet.spawnPosition = new Vector2(body.getPosition());
                    jet.targetPosition = new Vector2(body.getPosition().add(newDirection));
                    screen.spellManager.add(jet);
                }

            }
        }
    }

    public void fireball(Monster monster) {
        if(fireball && monster.heavy) {
            if(overheat) {
                float procRate = 0.84f - 0.04f * player.spellbook.overheat_lvl;
                if(Math.random() >= procRate) {
                    Overheat_Explosion overheat = new Overheat_Explosion(new Vector2(body.getPosition()));
                    overheat.setElements(this);
                    overheat.radius = overheat.radius - 30;
                    screen.spellManager.add(overheat);
                }
            }
            Fireball_Explosion explosion = new Fireball_Explosion();
            explosion.setElements(this);
            explosion.flamejets = flamejet;
            explosion.targetPosition = new Vector2(body.getPosition());
            screen.spellManager.add(explosion);

            hasCollided = true;
            body.setLinearVelocity(0,0);

            if(rift) {
                rifts(monster);
            }
        }
    }

    public void frostbolts(Monster monster) {
        if(frostbolt) {

            monster.applySlow(3, 0.7f);

            float procRate = .825f - .025f * player.spellbook.frostbolt_lvl;
            if(monstersHit == 1) {
                procRate = 0;
            }
            if (Math.random() > procRate) {
                Vector2 randomSpawn = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 0.75f);

                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.setElements(this);

                explosion.targetPosition = randomSpawn;
                screen.spellManager.add(explosion);
            }
        }
    }

    public void rifts(Monster monster) {
        int quantity = 4 + player.spellbook.rift_lvl/3;
        for (int i = 0; i < quantity; i++) {
            Rift_Zone rift = new Rift_Zone(SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 3.5f));
            rift.setElements(this);
            screen.spellManager.add(rift);
        }
    }

    public void chargedbolts() {
        if(chargedbolts) {
            float procRate = .9f - 0.05f * player.spellbook.chargedbolt_lvl;
            if(monstersHit == 1) {
                procRate = 0;
            }
            if(Math.random() >= procRate) {
                int quantity = 3 + player.spellbook.chargedbolt_lvl/2;
                for (int i = 0; i < quantity; i++) {
                    ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                    bolt.spawnPosition = new Vector2(body.getPosition());
                    bolt.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                    bolt.setElements(this);
                    screen.spellManager.add(bolt);
                }
            }
        }
    }

    public void chainLightning() {
        if(chainlightning) {
            int level = (getLvl() + player.spellbook.chainlightning_lvl) /2;
            if (level >= 8) {
                chainFrameInterval = 1;
            } else if (level >= 4) {
                chainFrameInterval = 2;
            } else {
                chainFrameInterval = 3;
            }

            frameCounter++;
            if (frameCounter >= chainFrameInterval) {
                EnergyBeam_ChainLightningBody chainBody = new EnergyBeam_ChainLightningBody();
                chainBody.chargedbolts = chargedbolts;
                chainBody.targetPosition = new Vector2(body.getPosition());
                chainBody.setElements(this);
                screen.spellManager.add(chainBody);
                frameCounter = 0;
            }
        }
    }

    public void thunderstorm(Monster monster) {
        if(thunderstorm) {
            int level = (getLvl() + player.spellbook.thunderstorm_lvl) / 2;
            float procRate = 0.825f - 0.025f * level;
            if(monstersHit == 1) {
                procRate = 0;
            }
            if(Math.random() >= procRate) {
                Thunderstorm_Hit thunder = new Thunderstorm_Hit(monster.body.getPosition());
                thunder.setElements(this);
                screen.spellManager.add(thunder);
            }
        }

    }
}
