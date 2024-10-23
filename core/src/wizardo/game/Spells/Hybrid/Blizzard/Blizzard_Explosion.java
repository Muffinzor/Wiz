package wizardo.game.Spells.Hybrid.Blizzard;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.BlizzardAnims;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.world;

public class Blizzard_Explosion extends Blizzard_Spell {

    boolean flipX;
    boolean flipY;
    int rotation;

    Body body;
    RoundLight light;

    public Blizzard_Explosion() {

        anim = BlizzardAnims.blizzard_hit_anim;

    }

    public void update(float delta) {
        if(!initialized) {
            flipX = MathUtils.randomBoolean();
            flipY = MathUtils.randomBoolean();
            rotation = MathUtils.random(360);
            initialized = true;
            createBody();
            createLight();
            frostbolts();
        }

        drawFrame();
        stateTime += delta;

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.toRemove(this);
            world.destroyBody(body);
        }
    }

    public void handleCollision(Monster monster) {
        monster.hp -= dmg;
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, 0.4f, 0.75f, 1, 50, body.getPosition());
        light.dimKill(0.03f);
        screen.lightManager.addLight(light);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        if(frostbolts) {
            frame.setScale(1.2f);
        }
        screen.centerSort(frame, targetPosition.y * PPM);
        screen.addSortedSprite(frame);
    }

    public void frostbolts() {
        if(frostbolts) {
            Frostbolt_Explosion bolt = new Frostbolt_Explosion();
            bolt.screen = screen;
            bolt.setElements(this);
            bolt.targetPosition = new Vector2(targetPosition);
            screen.spellManager.toAdd(bolt);
        }
    }
}
