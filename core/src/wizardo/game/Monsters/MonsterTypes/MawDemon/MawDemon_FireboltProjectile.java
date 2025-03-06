package wizardo.game.Monsters.MonsterTypes.MawDemon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Monsters.MonsterActions.SmallLaser.SmallLaser_Explosion;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.FrostboltAnims;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class MawDemon_FireboltProjectile extends MawDemon_FireboltAttack{

    Animation<Sprite> anim;
    boolean hasCollided;


    public MawDemon_FireboltProjectile(Monster monster) {
        super(monster);
        isProjectile = true;

        speed = 5f;
    }

    @Override
    public void checkState(float delta) {
        if(hasCollided || stateTime >= 4) {
            explode();
            light.dimKill(0.5f);
            world.destroyBody(body);
            screen.monsterSpellManager.toRemove(this);
        } else

        if(sentBack) {
            SmallLaser_Explosion hit = new SmallLaser_Explosion(body.getPosition(), originMonster);
            hit.sentBack = sentBack;
            screen.monsterSpellManager.toAdd(hit);
            world.destroyBody(body);
            light.dimKill(0.5f);
            screen.monsterSpellManager.toRemove(this);
        }
    }

    public void explode() {
        MawDemon_FireboltExplosion explosion = new MawDemon_FireboltExplosion(originMonster, body.getPosition());
        screen.monsterSpellManager.toAdd(explosion);
    }

    public void handleCollision(Fixture fix) {
        hasCollided = true;
        body.setLinearVelocity(0,0);
    }

    public void handleCollision(Body playerBody) {
        hasCollided = true;
        body.setLinearVelocity(0,0);
        originMonster.dealDmg();
    }

    public void createBody() {
        body = BodyFactory.monsterProjectileBody(spawnPosition, 10);
        body.setUserData(this);

        if(direction.isZero()) {
            direction.set(1,0);
        } else {
            direction.nor();
        }

        rotation = direction.angleDeg();
        Vector2 velocity = direction.scl(speed);
        body.setLinearVelocity(velocity);

    }

    @Override
    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.setScale(1f);
        screen.centerSort(frame, body.getPosition().y * PPM - 20);
        screen.addSortedSprite(frame);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0.9f, 0, 0, 0.8f, 35, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    @Override
    public void initialize() {
        anim = FrostboltAnims.frostbolt_projectile_anim_fire;
        createBody();
        createLight();
    }
}
