package wizardo.game.Monsters.MonsterActions.SmallFirebolts;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Monsters.MonsterActions.SmallLaser.SmallFirebolts_Explosion;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Resources.MonsterResources.MonsterProjectiles.SmallFireboltAnims;
import wizardo.game.Resources.MonsterResources.MonsterProjectiles.SmallLaserAnims;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class SmallFirebolts_Projectile extends MonsterSpell {

    boolean hasCollided;

    Animation<Sprite> anim;

    float red;
    float green;
    float blue;

    public SmallFirebolts_Projectile(Vector2 spawnPosition, Monster monster) {
        super(monster);
        isProjectile = true;

        this.spawnPosition = new Vector2(spawnPosition);

        speed = 4.25f;
        radius = 5;
    }

    @Override
    public void checkState(float delta) {

        if(hasCollided || stateTime > 5f || sentBack) {
            SmallFirebolts_Explosion hit = new SmallFirebolts_Explosion(body.getPosition(), originMonster);
            hit.sentBack = sentBack;
            screen.monsterSpellManager.toAdd(hit);
            world.destroyBody(body);
            light.dimKill(0.5f);
            screen.monsterSpellManager.toRemove(this);
        }

    }

    @Override
    public void createBody() {
        targetPosition = new Vector2(player.pawn.getPosition());
        direction = targetPosition.cpy().sub(originMonster.body.getPosition());

        if(direction.isZero()) {
            direction.set(1,0);
        } else {
            direction.nor();
        }

        body = BodyFactory.monsterProjectileBody(spawnPosition, radius);
        body.setUserData(this);

        rotation = MathUtils.random(-8, 8);
        direction.rotateDeg(rotation);
        rotation = direction.angleDeg();

        Vector2 velocity = direction.scl(speed * MathUtils.random(0.9f,1.1f));
        body.setLinearVelocity(velocity);

    }

    public void pickAnim() {
        anim = SmallFireboltAnims.small_firebolt_anim;
        red = 0.9f;
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

    @Override
    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        if(alpha < 1) {
            frame.setColor(redshift, 0.75f, 0.75f, alpha);
        }
        screen.centerSort(frame, body.getPosition().y * PPM - 25);
        screen.addSortedSprite(frame);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, 0.8f, 25, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    @Override
    public void initialize() {
        pickAnim();
        createBody();
        createLight();
    }

    @Override
    public void dispose() {

    }
}
