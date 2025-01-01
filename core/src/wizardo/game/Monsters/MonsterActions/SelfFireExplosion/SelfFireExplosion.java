package wizardo.game.Monsters.MonsterActions.SelfFireExplosion;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Resources.SpellAnims.OverheatAnims.minifireball_anim_fire;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class SelfFireExplosion extends MonsterSpell {

    Animation<Sprite> anim;
    boolean flipX;
    boolean flipY;

    public SelfFireExplosion(Monster monster) {
        super(monster);
        anim = minifireball_anim_fire;
    }

    @Override
    public void checkState(float delta) {
        if(body.isActive() && stateTime >= 0.1f) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            screen.monsterSpellManager.toRemove(this);
            world.destroyBody(body);
        }
    }

    @Override
    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.flip(flipX,flipY);
        screen.addSortedSprite(frame);
        screen.centerSort(frame, body.getPosition().y * PPM - 10);
    }

    @Override
    public void initialize() {
        createBody();
        createLight();
        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
    }

    public void createBody() {
        body = BodyFactory.monsterProjectileBody(originMonster.body.getPosition(), 40);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(1f, 0.65f, 0.4f, 1, 60, body.getPosition());
        screen.lightManager.addLight(light);
        light.dimKill(0.02f);
    }

    @Override
    public void dispose() {

    }
}
