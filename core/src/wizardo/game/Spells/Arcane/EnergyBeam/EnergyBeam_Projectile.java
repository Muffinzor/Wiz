package wizardo.game.Spells.Arcane.EnergyBeam;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Hit;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Resources.SpellAnims.EnergyBeamAnims.*;
import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class EnergyBeam_Projectile extends EnergyBeam_Spell {

    boolean hasCollided;
    Vector2 direction;
    float rotation;

    Body body;
    Sprite bodyTile;
    Sprite endTile;
    float alpha = 1;
    int frameCounter;
    int chainFrameInterval;

    int monstersHit = 0;

    public EnergyBeam_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {

        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);

        screen = currentScreen;

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
        frozenOrbBodies();
        chainLightning();

        if(stateTime > 0.2f && delta > 0) {
            alpha -= 0.03f;
        }

        if(alpha < 0.03f) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        frostbolts(monster);
        chargedbolts();
        thunderstorm(monster);
        monstersHit ++;
    }

    public void drawFrame() {

        float length = body.getPosition().dst(spawnPosition.x, spawnPosition.y);
        Vector2 originPosition = new Vector2 (spawnPosition.cpy().add(direction.cpy().nor().scl(7.5f/PPM)));
        Vector2 startFrame = new Vector2 (spawnPosition.cpy().add(direction.cpy().nor().scl(-12.5f/PPM)));


        //Start of laser
        Sprite frame = screen.getSprite();
        frame.set(endTile);
        frame.setScale(0.5f);
        frame.setCenter(startFrame.x * PPM, startFrame.y * PPM);
        frame.setRotation(rotation + 180);
        frame.setAlpha(alpha);
        screen.addUnderSprite(frame);


        //Body of laser
        Sprite frame1 = screen.getSprite();
        frame1.set(bodyTile);
        frame1.setScale(0.5f);
        frame1.setPosition(originPosition.x * PPM - bodyTile.getWidth()/2, originPosition.y * PPM - bodyTile.getHeight()/2);
        frame1.setRotation(rotation);
        frame1.setSize(length * PPM * 2, frame1.getHeight());
        frame1.setAlpha(alpha);
        screen.addUnderSprite(frame1);

        //End of laser
        Sprite frame2 = screen.getSprite();
        frame2.set(endTile);
        Vector2 endPosition = new Vector2(body.getPosition().add(direction.cpy().nor().scl(0.3f)));
        frame2.setScale(0.5f);
        frame2.setSize(frame2.getWidth() * 3, frame2.getHeight());
        frame2.setPosition(endPosition.x * PPM - endTile.getWidth()/2, endPosition.y * PPM - endTile.getHeight()/2);
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


        body = BodyFactory.spellProjectileDiamondBody(bodySpawn, 100, 20, rotation, true);
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

    public void frozenOrbBodies() {
        if(frozenorb) {
            EnergyBeam_FreezeBody freezeBody = new EnergyBeam_FreezeBody();
            freezeBody.setElements(this);
            freezeBody.frostbolt = frostbolt;
            freezeBody.targetPosition = body.getPosition();
            screen.spellManager.toAdd(freezeBody);
        }
    }

    public void pickAnim() {
        lightAlpha = 0.7f;
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

    public void frostbolts(Monster monster) {
        if(frostbolt) {
            int random = MathUtils.random(1,3);

            monster.applySlow(3, 0.7f);

            float procRate = .825f - .025f * player.spellbook.frostbolt_lvl;
            if(monstersHit == 0) {
                procRate = 0;
            }
            if (Math.random() > procRate) {
                Vector2 randomSpawn = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 0.75f);

                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.setElements(this);
                switch(random) {
                    case 1 -> explosion.anim_element = FIRE;
                    case 2 -> explosion.anim_element = FROST;
                    case 3 -> explosion.anim_element = LIGHTNING;
                }
                explosion.targetPosition = randomSpawn;
                screen.spellManager.toAdd(explosion);
            }
        }
    }

    public void chargedbolts() {
        if(chargedbolts) {
            float procRate = .9f - 0.05f * player.spellbook.chargedbolt_lvl;
            if(monstersHit == 0) {
                procRate = 0;
            }
            if(Math.random() >= procRate) {
                int quantity = 3 + player.spellbook.chargedbolt_lvl/2;
                for (int i = 0; i < quantity; i++) {
                    ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                    bolt.spawnPosition = new Vector2(body.getPosition());
                    bolt.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                    bolt.setElements(this);
                    screen.spellManager.toAdd(bolt);
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
                screen.spellManager.toAdd(chainBody);
                frameCounter = 0;
            }
        }
    }

    public void thunderstorm(Monster monster) {
        if(thunderstorm) {
            int level = (getLvl() + player.spellbook.thunderstorm_lvl) / 2;
            float procRate = 0.825f - 0.025f * level;
            if(monstersHit == 0) {
                procRate = 0;
            }
            if(Math.random() >= procRate) {
                Thunderstorm_Hit thunder = new Thunderstorm_Hit(monster.body.getPosition());
                thunder.setElements(this);
                screen.spellManager.toAdd(thunder);
            }
        }

    }
}
