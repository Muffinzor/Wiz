package wizardo.game.Spells.Frost.Frozenorb;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.FrozenorbAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.world;

public class Frozenorb_Projectile extends Frozenorb_Spell {

    Body body;
    RoundLight light;
    boolean hasCollided;
    float scale = 1;
    int frameCounter = 0;
    int rotation;

    public Frozenorb_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {
        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);
        anim = FrozenorbAnims.frozenorb_anim_frost;
        rotation = MathUtils.random(360);
        screen = currentScreen;
    }

    public void update(float delta) {
        if(!initialized) {
            createBody();
            createLight();
            //setInterval();
            initialized = true;
        }

        stateTime += delta;
        drawFrame(delta);

        areaDmg(delta);
        adjustMovement();
        adjustLight();
        adjustScale();

    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().x * PPM, body.getPosition().y * PPM);
        if(scale < 1 && scale > 0.97) {
            light.dimKill(0.02f);
        }
    }

    public void adjustMovement() {
        if(hasCollided) {
            body.setLinearVelocity(0,0);
        }
    }

    public void createBody() {
        Vector2 direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction.set(1,0);
        }

        if(castByPawn) {
            Vector2 offset = new Vector2(direction.cpy().scl(30));
            spawnPosition = new Vector2(spawnPosition.add(offset));
        }

        body = BodyFactory.spellProjectileBody(spawnPosition, 20, true);
        body.setUserData(this);

        Vector2 velocity = new Vector2(direction.scl(speed));
        body.setLinearVelocity(velocity);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0,0,0.5f, 1, 200, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void drawFrame(float delta) {
        if(delta > 0) {
            rotation += 5;
        }
        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setScale(scale * 0.4f);
        frame.setRotation(rotation);
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
    }

    public void adjustScale() {
        if(stateTime > duration - 0.5f || hasCollided) {
            scale = scale - 0.025f;
            if(scale <= 0) {
                screen.spellManager.toRemove(this);
                world.destroyBody(body);
                body = null;
            }
        }
    }

    public void areaDmg(float delta) {
        if(delta > 0) {
            frameCounter++;
            if (frameCounter == 5) {
                frameCounter = 0;
                float radius = 3;
                BattleScreen screen = (BattleScreen) currentScreen;
                for (Monster monster : screen.monsterManager.liveMonsters) {
                    if (monster.body.getPosition().dst(body.getPosition()) < radius) {
                        monster.hp -= dmg * 5;
                    }
                }
            }
        }
    }
}
