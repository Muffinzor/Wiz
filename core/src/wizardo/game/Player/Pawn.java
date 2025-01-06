package wizardo.game.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Resources.PlayerResources;
import wizardo.game.Resources.ScreenResources.LevelUpResources;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Display.DisplayUtils.getLight;
import static wizardo.game.Display.DisplayUtils.getSprite;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Pawn {

    public BaseScreen screen;

    public Body body;
    private float stateTime;
    private boolean levelingUp;

    public Vector2 movementVector;
    public Vector2 targetVector;
    public Vector2 levelUpVector;

    Vector2 pushBackForce = new Vector2();
    float pushBackTimer = 0;
    float pushDecayRate = 0;

    public RoundLight light;


    public Pawn(BaseScreen screen) {
        this.screen = screen;
        stateTime = 0;
        movementVector = new Vector2(0,0);
        targetVector = new Vector2(1,0);
    }

    public void update(float delta) {
        stateTime += delta;
        drawFrame();

        if(pushBackTimer > 0) {
            pushPlayer(delta);
        } else {
            movement();
        }

        adjustLight();

        if(delta > 0) {
            checkXP();
        }

    }



    public void checkXP() {
        if(player.currentXP >= player.neededXP && !levelingUp) {
            player.level ++;
            player.currentXP = player.currentXP - player.neededXP;
            player.neededXP += ((player.level - 1) * 20);
            levelingUp = true;
            stateTime = 0;
            levelUpVector = new Vector2(player.pawn.getBodyX(), player.pawn.getBodyY() + 2);
            RoundLight light = screen.lightManager.pool.getLight();
            light.setLight(0.6f, 0, 0.9f, 1, 200, levelUpVector);
            light.dimKill(0.0075f);
            screen.lightManager.addLight(light);
        } else {
            levelUP();
        }
    }

    public void levelUP() {
        if(levelingUp) {
            Sprite frame = getSprite(screen);
            frame.set(LevelUpResources.level_up_anim.getKeyFrame(stateTime, false));
            frame.setCenter(levelUpVector.x * PPM, levelUpVector.y * PPM);
            frame.setScale(1.2f);
            screen.displayManager.spriteRenderer.post_lightning_sprites.add(frame);
        }
        if(levelingUp && stateTime >= LevelUpResources.level_up_anim.getAnimationDuration()) {
            levelingUp = false;
            screen.game.addNewScreen(new LevelUpScreen(screen.game));
        }
    }

    public void applyPush(Vector2 pushDirection, float strength, float duration, float decayRate) {
            float pushStrength = strength;
            float pushDuration = duration;
            pushBackForce.set(pushDirection.nor().scl(pushStrength));
            pushBackTimer = pushDuration;
            pushDecayRate = decayRate;
    }

    public void pushPlayer(float delta) {
        body.setLinearVelocity(pushBackForce);
        pushBackForce.scl(pushDecayRate);
        pushBackTimer -= delta;
    }


    public void drawFrame() {
        Sprite frame = getSprite(screen);
        frame.set(PlayerResources.playerWalk.getKeyFrame(stateTime, true));
        if(player.pawn.pushBackTimer > 0) {
            frame.setColor(0.75f,0,0,1);
        }
        frame.setPosition(body.getPosition().x * PPM - frame.getWidth()/2f, body.getPosition().y * PPM - 8);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

        Vector3 mouseUnprojected = screen.mainCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Vector2 mousePosition = new Vector2(mouseUnprojected.x, mouseUnprojected.y);

        if(mousePosition.x < body.getPosition().x * PPM && !screen.paused) {
            frame.flip(true, false);
        }

    }

    public void createPawn(Vector2 position) {
        body = BodyFactory.playerBody(position);
        createLight();
    }

    public void moveX(float value) {
        movementVector.x = value;
    }
    public void moveY(float value) {
        movementVector.y = -value;
    }

    public void stop() {
        if(body != null) {
            movementVector.x = 0;
            movementVector.y = 0;
            body.setLinearVelocity(movementVector);
        }
    }

    public void aimX(float value) {
        targetVector.x = value;
    }
    public void aimY(float value) {
        targetVector.y = value;
    }

    public void movement() {

        if(!movementVector.isZero()) {
            movementVector.nor();
        }
        if(player != null) {
            body.setLinearVelocity(movementVector.cpy().scl(player.stats.runSpeed));
        }
    }

    public void createLight() {
        light = getLight(screen);
        light.setLight(0,0,0,0.65f,120, body.getPosition());

    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    public Vector2 getPosition() {
        return new Vector2(body.getPosition());
    }

    public float getBodyX() {
        return body.getPosition().x;
    }

    public float getBodyY() {
        return body.getPosition().y;
    }

    public void dispose() {
        if(body != null) {
            world.destroyBody(body);
        }
        body = null;
    }

}
