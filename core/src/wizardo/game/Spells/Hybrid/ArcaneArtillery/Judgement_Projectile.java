package wizardo.game.Spells.Hybrid.ArcaneArtillery;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.SpellAnims.EnergyBeamAnims;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class Judgement_Projectile extends Judgement_Spell {

    Body body;
    RoundLight light;

    Sprite tile;
    Sprite endTile;
    float alpha = 1;

    public Judgement_Projectile() {

        speed = 35;

    }

    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            createBody();
            initialized = true;
        }

        drawFrame();

        stateTime += delta;

        if(body.isActive() && hasArrived()) {
            explode();
            body.setLinearVelocity(0,0);
            body.setActive(false);
        } else if (body.isActive() && delta > 0) {
            moreLights(delta);
        }

        if(alpha <= 0.1f) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        } else if(!body.isActive() && delta > 0) {
            alpha -= 0.05f;
        }

    }

    public void drawFrame() {
        float dst = spawnPosition.cpy().dst(body.getPosition());

        Sprite frame = screen.getSprite();
        frame.set(tile);
        frame.setScale(0.5f);
        frame.setOrigin(0, frame.getHeight()/2);
        frame.setRotation(270);
        frame.setSize(dst * PPM * 2, frame.getHeight());
        frame.setPosition(spawnPosition.x * PPM, spawnPosition.y * PPM);

        if(alpha < 1) {
            frame.setAlpha(alpha);
        }

        screen.centerSort(frame, targetPosition.y * PPM);
        screen.addSortedSprite(frame);


        Sprite frame2 = screen.getSprite();
        frame2.set(endTile);
        frame2.setScale(0.5f);
        frame2.setOrigin(0, frame2.getHeight()/2);
        frame2.setRotation(270);
        frame2.setPosition(body.getPosition().x * PPM, body.getPosition().y * PPM);

        if(alpha < 1) {
            frame2.setAlpha(alpha);
        }

        screen.centerSort(frame2, targetPosition.y * PPM);
        screen.addSortedSprite(frame2);



    }

    public void pickAnim() {
        switch (anim_element) {
            case ARCANE -> {
                tile = EnergyBeamAnims.energyrain_tile_arcane;
                endTile = EnergyBeamAnims.energyrain_end_arcane;
            }
            case FROST -> {
                tile = EnergyBeamAnims.energyrain_tile_frost;
                endTile = EnergyBeamAnims.energyrain_end_frost;
                red = 0.2f;
                blue = 0.9f;
            }
            case FIRE -> {
                tile = EnergyBeamAnims.energyrain_tile_fire;
                endTile = EnergyBeamAnims.energyrain_end_fire;
                red = 0.75f;
                green = 0.15f;
            }
        }
    }
    public void createBody() {
        spawnPosition = targetPosition.cpy().add(0, 20);

        body = BodyFactory.spellProjectileCircleBody(spawnPosition,5,true);
        body.setUserData(this);

        Vector2 direction = new Vector2(targetPosition.cpy().sub(spawnPosition).nor());
        Vector2 velocity = direction.cpy().scl(speed);
        body.setLinearVelocity(velocity);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public void moreLights(float delta) {
        if(stateTime % 0.035f < delta) {
            RoundLight light = screen.lightManager.pool.getLight();
            light.setLight(red, green, blue, 0.7f, 125, body.getPosition());
            light.dimKill(0.02f);
            screen.lightManager.addLight(light);
        }
    }

    public boolean hasArrived() {
        float dst = body.getPosition().dst(targetPosition);
        if(Math.abs(dst) < 0.35) {
            return true;
        }
        return false;
    }

    public void explode() {
        Judgement_Explosion explosion = new Judgement_Explosion(targetPosition);
        explosion.setElements(this);
        explosion.frozenorb = frozenorb;
        explosion.arcaneMissiles = arcaneMissiles;
        explosion.rift = rift;
        explosion.rift = rift;
        screen.spellManager.add(explosion);
    }

}
