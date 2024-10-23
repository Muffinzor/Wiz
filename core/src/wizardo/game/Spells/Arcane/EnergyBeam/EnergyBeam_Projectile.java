package wizardo.game.Spells.Arcane.EnergyBeam;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Resources.SpellAnims.EnergyBeamAnims.arcaneEndTile;
import static wizardo.game.Resources.SpellAnims.EnergyBeamAnims.arcaneTile;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.world;

public class EnergyBeam_Projectile extends EnergyBeam_Spell {

    boolean hasCollided;
    Vector2 direction;
    float rotation;

    Body body;
    Sprite bodyTile;
    Sprite endTile;
    float alpha = 1;

    public EnergyBeam_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {

        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);

        screen = currentScreen;

        bodyTile = arcaneTile;
        endTile = arcaneEndTile;

    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
        }

        stateTime += delta;

        drawFrame();
        createLight(delta);

        if(stateTime > 0.2f && delta > 0) {
            alpha -= 0.03f;
        }

        if(alpha < 0.03f) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void drawFrame() {

        float length = body.getPosition().dst(spawnPosition.x, spawnPosition.y);
        Vector2 originPosition = new Vector2 (spawnPosition.cpy().add(direction.cpy().nor().scl(7.5f/PPM)));
        Vector2 startFrame = new Vector2 (spawnPosition.cpy().add(direction.cpy().nor().scl(-12.5f/PPM)));

        System.out.println(length);

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

        /**
        if(frozenorb) {
            float rad = 15 + 10 * player.spellbook.frozenorbLvl;
            frozenorbBody = BodyFactory.spellLightBody(world, bodySpawn, rad, 0, 0, false, true);
            frozenorbBody.setUserData(this);
            frozenorbBody.setLinearVelocity(velocity);
        }
         */
    }

    public void createLight(float delta) {
        if(!hasCollided && delta > 0) {
            RoundLight light = screen.lightManager.pool.getLight();
            light.setLight(0.4f, 0.1f, 0.8f, 0.7f, 125, body.getPosition());
            screen.lightManager.addLight(light);
            light.dimKill(0.01f);
        }
    }
}
