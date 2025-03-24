package wizardo.game.Items.Drop;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import wizardo.game.Lighting.RoundLight;
import wizardo.game.Screens.Battle.BattleScreen;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;


public abstract class Drop {

    public boolean initialized;
    float stateTime;
    public boolean pickedUp;

    public float alpha = 1;
    public RoundLight light;
    public float lightRadius = 35f;
    public Vector2 position;
    public Vector2 velocity;

    public boolean goToPlayer;
    public float travelSpeed = 10;
    public boolean tooFar;
    public float pickupRadius = 5;
    int frameCounter;

    public Sprite sprite;
    public Animation<Sprite> flareAnim;
    public Animation<Sprite> pickupAnim;
    float red;
    float green;
    float blue;
    float flareScale = 1;
    float flareRotation;
    public float displayScale = 1;
    boolean flipX;

    BattleScreen screen;

    public void initialDrop() {
        Vector2 target = DropUtils.getRandomDropVector(position, 0.5f);
        velocity = new Vector2(target.sub(position));
        if(!velocity.isZero()) {
            velocity.nor();
        }
        velocity.scl(MathUtils.random(1.5f,2.5f));
    }

    public void update(float delta) {
        frameCounter++;
        stateTime += delta;
        if(!initialized) {
            position = new Vector2(position.x, position.y - 0.1f);
            initialized = true;
            initialDrop();
            setup();
            flipX = MathUtils.randomBoolean();
            createLight();
        }
        // Simulates fall on ground
        if(stateTime < 1 && !pickedUp) {
            if(!velocity.isZero()) {
                position.add(velocity.x/PPM, velocity.y/PPM);
                velocity.scl(0.97f);
            }
        }

        if(frameCounter >= 60) {
            frameCounter = 0;
            checkDistance();
        }

        if(!tooFar) {
            drawFrame();
        }
        checkLight();


        if(goToPlayer) {
            goToPlayer(delta);
        }

    }

    public void checkLight() {

        if(!tooFar && light == null) {
            createLight();
        }

        if(tooFar && light != null) {
            light.dimKill(0.5f);
            light = null;
        }

        if(light != null) {
            adjustLight();
        }
    }



    public void checkDistance() {

        float dst = position.dst(player.pawn.getPosition());
        if(dst > 40) {
            tooFar = true;
        } else {
            tooFar = false;
            if(dst < pickupRadius * (1 + player.stats.bonus_pickup_radius/100f)) {
                goToPlayer = !(this instanceof EquipmentDrop) || player.inventory.hasSpace();
            }
        }

    }

    public void drawFrame() {

        if(!pickedUp) {
            if (flareAnim != null) {
                Sprite frame2 = screen.getSprite();
                frame2.set(flareAnim.getKeyFrame(stateTime, true));
                frame2.setScale(0.8f * flareScale);
                frame2.setAlpha(alpha);
                frame2.setCenter(position.x * PPM, position.y * PPM);
                screen.addSortedSprite(frame2);
                screen.centerSort(frame2, position.y * PPM + 5);
            }

            Sprite frame = screen.getSprite();
            frame.set(sprite);
            frame.setCenter(position.x * PPM, position.y * PPM);
            frame.flip(flipX, false);
            frame.setAlpha(alpha);
            frame.setScale(displayScale);
            screen.addSortedSprite(frame);
            screen.centerSort(frame, position.y * PPM);
        } else {
            Sprite frame = screen.getSprite();
            frame.set(pickupAnim.getKeyFrame(stateTime, false));
            frame.rotate(flareRotation);
            frame.setCenter(position.x * PPM, position.y * PPM);
            screen.addSortedSprite(frame);
        }

    }


    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,1, lightRadius, position);
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        if(light != null) {
            light.pointLight.setPosition(position.cpy().scl(PPM));
        }
    }

    public void goToPlayer(float delta) {

        if(!pickedUp) {
            velocity = new Vector2(player.pawn.getPosition().sub(position));
            velocity.nor().scl(travelSpeed);
            position.add(velocity.x * delta, velocity.y * delta);

            float dst = position.dst(player.pawn.getPosition());
            if (dst < 0.5f) {
                pickup();
            }
        }

    }

    public abstract void setup();

    public abstract void pickup();

    public void dispose() {
        if(light != null) {
            light.dimKill(0.1f);
            light = null;
        }
    }


}
