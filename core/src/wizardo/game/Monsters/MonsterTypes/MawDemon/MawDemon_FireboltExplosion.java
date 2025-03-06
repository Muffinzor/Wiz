package wizardo.game.Monsters.MonsterTypes.MawDemon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.MonsterResources.MonsterProjectiles.MonsterExplosions;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class MawDemon_FireboltExplosion extends MawDemon_FireboltAttack {

    Animation<Sprite> anim;
    boolean flipX;
    boolean flipY;
    int rotation;
    float red;

    public MawDemon_FireboltExplosion(Monster monster, Vector2 targetPosition) {
        super(monster);
        this.targetPosition = new Vector2(targetPosition);

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

        anim = MonsterExplosions.distortion_explosion_red;
        red = 0.9f;

    }

    public void initialize() {
        createBody();
        createLight();
    }

    @Override
    public void checkState(float delta) {
        if(body.isActive() && stateTime >= 0.2f) {
            body.setActive(false);
            light.dimKill(0.03f);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.monsterSpellManager.toRemove(this);
        }
    }


    @Override
    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        frame.setScale(0.8f);
        screen.centerSort(frame, body.getPosition().y * PPM - 10);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        body = BodyFactory.monsterProjectileBody(targetPosition, 40);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, 0, 0, 1, 100, body.getPosition());
        screen.lightManager.addLight(light);
    }

    @Override
    public void dispose() {

    }
}
