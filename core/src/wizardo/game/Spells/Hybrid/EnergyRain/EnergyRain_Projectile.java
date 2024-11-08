package wizardo.game.Spells.Hybrid.EnergyRain;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.SpellAnims.EnergyBeamAnims;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FROST;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class EnergyRain_Projectile extends EnergyRain_Spell {

    Body body;
    RoundLight light;

    Sprite tile;
    Sprite endTile;
    float alpha = 1;

    public EnergyRain_Projectile() {

        speed = 35;

        anim_element = FROST;

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

        if(body.isActive() && hasArrived()) {
            explode();
            body.setLinearVelocity(0,0);
            body.setActive(false);
            light.kill();
        } else if (body.isActive() && delta > 0) {
            moreLights(delta);
        }

        if(alpha <= 0.1f) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        } else if(!body.isActive()) {
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

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha,100, spawnPosition);
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
        EnergyRain_Explosion explosion = new EnergyRain_Explosion();
        explosion.targetPosition = new Vector2(targetPosition);
        explosion.setElements(this);
        explosion.frostbolt = frostbolt;
        screen.spellManager.toAdd(explosion);
    }

}
