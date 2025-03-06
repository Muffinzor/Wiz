package wizardo.game.Monsters.MonsterActions.SmallLaser;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Resources.MonsterResources.MonsterProjectiles.SmallLaserAnims;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class SmallLaser_Projectile extends MonsterSpell {

    boolean hasCollided;

    Sprite sprite;

    float red;
    float green;
    float blue;

    public SmallLaser_Projectile(Vector2 spawnPosition, Monster monster) {
        super(monster);
        isProjectile = true;

        this.spawnPosition = new Vector2(spawnPosition);

        speed = 7;
        radius = 8;
    }

    @Override
    public void checkState(float delta) {

        if(hasCollided || stateTime > 5f || sentBack) {
            SmallLaser_Explosion hit = new SmallLaser_Explosion(body.getPosition(), originMonster);
            hit.sentBack = sentBack;
            screen.monsterSpellManager.toAdd(hit);
            world.destroyBody(body);
            light.dimKill(0.5f);
            screen.monsterSpellManager.toRemove(this);
        }

    }

    public void pickAnim() {
        sprite = SmallLaserAnims.small_green;
        red = 0.2f;
        green = 0.9f;
        blue = 0.25f;
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
        frame.set(sprite);
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        if(alpha < 1) {
            frame.setColor(redshift, 0.75f, 0.75f, alpha);
        }
        frame.setScale(0.8f);
        screen.centerSort(frame, body.getPosition().y * PPM - 25);
        screen.addSortedSprite(frame);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, 0.8f, 35, body.getPosition());
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
