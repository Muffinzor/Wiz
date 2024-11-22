package wizardo.game.Monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public abstract class Monster {

    public BattleScreen screen;
    public Animation<Sprite> walk_anim;
    public Animation<Sprite> death_anim;
    public float alpha = 1;

    public float stateTime;
    public int frameCounter;

    public Body body;
    public float massValue;
    public float bodyRadius;
    public float speed;
    public Vector2 position;
    public Vector2 deathPosition;
    public Pathfinder pathfinder;

    public float hp;
    public float maxHP;
    public boolean dead;

    boolean initialized;

    public float thunderImmunityTimer = 0;
    public float freezeImmunityTimer = 0;
    public float freezeTimer = 0;
    public float slowedTimer = 0;
    public float slowRatio = 1;


    public Monster(BattleScreen screen, Vector2 position) {
        this.screen = screen;
        this.position = new Vector2(position);
        stateTime = 0;
        frameCounter = 0;
        pathfinder = new Pathfinder(this);
    }

    public void update(float delta) {
        if(!initialized) {
            initialize();
        }
        timers(delta);
        drawFrame();

        if(hp <= 0) {
            dead = true;
            stateTime = 0;
        }
    }

    public void movement(float delta) {

        if(body != null) {

            frameCounter++;
            if (frameCounter == 1) {
                pathfinder.update(delta);
                frameCounter = 0;
            }
        }

    }

    public void createBody() {
        body = BodyFactory.monsterBody(position, bodyRadius);
        body.setUserData(this);
        MassData mass = new MassData();
        float massMin = massValue * 0.8f;
        float massMax = massValue * 1.2f;
        float newMass = MathUtils.random(massMin, massMax);
        massValue = newMass;
        mass.mass = newMass;
        body.setMassData(mass);
    }

    public void drawFrame() {
        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(walk_anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        boolean flip = player.pawn.getBodyX() < body.getPosition().x;
        frame.flip(flip, false);
        if(freezeTimer > 0) {
            Color tint = new Color(0.3f, 0.3f, 0.8f, 1.0f);
            frame.setColor(tint);
        } else if(slowedTimer > 0) {
            Color tint = new Color(0.5f, 0.5f, 0.8f, 1.0f);
            frame.setColor(tint);
        }
        screen.addSortedSprite(frame);
    }

    public void drawDeathFrame(float delta) {
        if(delta > 0) {
            alpha -= 0.005f;
            if(alpha < 0) {
                alpha = 0;
            }
        }
        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(death_anim.getKeyFrame(stateTime, false));
        frame.setCenter(deathPosition.x * PPM, deathPosition.y * PPM);
        frame.setAlpha(alpha);
        screen.displayManager.spriteRenderer.under_sprites.add(frame);
    }

    public void dispose() {
        if(body != null) {
            //world.destroyBody(body);
            //body = null;
        }
    }

    public void timers(float delta) {
        thunderImmunityTimer -= delta;
        freezeTimer -= delta;
        freezeImmunityTimer -= delta;
        slowedTimer -= delta;

        if(freezeTimer <= 0) {
            stateTime += delta;
        }
    }

    /**
     * slows monster
     * @param duration in seconds
     * @param ratio between 0 and 1.0f
     */
    public void applySlow(float duration, float ratio) {
        slowedTimer = duration;
        slowRatio = ratio;
    }

    public void applyFreeze(float duration, float immunity) {
        if(freezeImmunityTimer <= 0) {
            freezeTimer = duration;
            freezeImmunityTimer = immunity;
        } else {
            slowedTimer = duration;
            slowRatio = 0.75f;
        }
    }
    public abstract void initialize();
}
