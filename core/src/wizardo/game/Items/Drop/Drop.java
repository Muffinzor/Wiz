package wizardo.game.Items.Drop;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.EffectAnims.GearFlareAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Utils.Contacts.Masks.*;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public abstract class Drop {

    public boolean initialized;
    public boolean intangible = true;
    float stateTime;
    public boolean pickedUp;

    public Body body;
    public RoundLight light;
    public Vector2 spawnPosition;
    public boolean goToPlayer;
    public Vector2 pickedUpLocation;
    public float pickupRadius = 3;
    int frameCounter;

    public Sprite sprite;
    public Animation<Sprite> flareAnim;
    float red;
    float green;
    float blue;
    float flareScale = 1;
    float flareRotation;
    public float displayScale = 1;
    boolean flipX;

    BattleScreen screen;

    public void update(float delta) {
        frameCounter++;

        if(!initialized) {
            initialized = true;
            setup();
            flipX = MathUtils.randomBoolean();
            createBody();
            createLight();
        }

        if(intangible && stateTime >= 1f) {
            becomeTangible();
        }

        stateTime += delta;
        drawFrame();
        adjustLight();

        if(goToPlayer) {
            goToPlayer();
        } else {
            frameCounter++;
        }

        if(body.isActive() && pickedUp) {
            light.dimKill(0.02f);
            light = null;
            body.setActive(false);
        }

        if(!goToPlayer && frameCounter >= 120) {
            checkDistance();
        }
    }

    public void checkDistance() {
        float dst = body.getPosition().dst(player.pawn.getPosition());
        if(dst < pickupRadius * (1 + player.stats.pickupRadiusBonus/100f)) {
            if(player.inventory.hasSpace()) {
                goToPlayer = true;
            }
        }
    }

    public void drawFrame() {
        if(!pickedUp) {
            if (flareAnim != null) {
                Sprite frame2 = screen.getSprite();
                frame2.set(flareAnim.getKeyFrame(stateTime, true));
                frame2.setScale(0.8f * flareScale);
                frame2.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
                screen.addSortedSprite(frame2);
                screen.centerSort(frame2, body.getPosition().y * PPM + 5);

            }

            Sprite frame = screen.getSprite();
            frame.set(sprite);
            frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
            frame.flip(flipX, false);
            frame.setScale(displayScale);
            screen.addSortedSprite(frame);
            screen.centerSort(frame, body.getPosition().y * PPM);
        } else {
            Sprite frame = screen.getSprite();
            frame.set(GearFlareAnims.gear_pop.getKeyFrame(stateTime, false));
            frame.rotate(flareRotation);
            frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
            screen.addSortedSprite(frame);
        }
    }

    public void createBody() {
        body = BodyFactory.dropItemBody(spawnPosition, 10);
        body.setUserData(this);
        Vector2 target = DropUtils.getRandomDropVector(spawnPosition, 0.5f);
        Vector2 direction = target.sub(spawnPosition);
        if(direction.len() > 0) {
            direction.nor();
        }
        direction.scl(3.5f);
        body.setLinearVelocity(direction);
    }

    public void becomeTangible() {
        Filter filter = new Filter();
        filter.categoryBits = DROP;
        filter.maskBits = DROP_MASK;
        body.getFixtureList().first().setFilterData(filter);
        body.getFixtureList().first().setSensor(false);
        intangible = false;
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,1, 35, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        if(light != null) {
            light.pointLight.setPosition(body.getPosition().scl(PPM));
        }
    }

    public void goToPlayer() {
        Vector2 playerPosition = player.pawn.getPosition();
        Vector2 currentPosition = body.getPosition();
        Vector2 direction = new Vector2(playerPosition.sub(currentPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction.set(1,0);
        }

        direction.scl(5);
        body.setLinearVelocity(direction);
    }

    public void handleCollision() {
        pickup();
    }

    public abstract void setup();

    public abstract void pickup();

    public void dispose() {
        world.destroyBody(body);
        if(light != null) {
            light.dimKill(0.1f);
        }
    }


}
