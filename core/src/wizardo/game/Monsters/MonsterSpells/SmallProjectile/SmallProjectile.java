package wizardo.game.Monsters.MonsterSpells.SmallProjectile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterSpells.MonsterSpell;
import wizardo.game.Resources.MonsterResources.MonsterProjectiles.SmallProjectileAnims;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class SmallProjectile extends MonsterSpell {

    boolean hasCollided;

    Sprite sprite;

    float red;
    float green;
    float blue;

    public SmallProjectile(Vector2 spawnPosition, Monster monster) {
        super(spawnPosition, monster);

        speed = 7;
        radius = 8;
    }

    @Override
    public void checkState() {

        if(hasCollided || stateTime > 2.5f) {
            SmallProjectile_Explosion hit = new SmallProjectile_Explosion(body.getPosition(), originMonster);
            screen.monsterProjManager.toAdd(hit);
            world.destroyBody(body);
            light.dimKill(0.5f);
            screen.monsterProjManager.toRemove(this);
        }

    }

    @Override
    public void pickAnim() {
        sprite = SmallProjectileAnims.small_green;
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
    }

    @Override
    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(sprite);
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.setScale(0.8f);
        screen.centerSort(frame, body.getPosition().y * PPM - 15);
        screen.addSortedSprite(frame);
    }

    @Override
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, 0.8f, 35, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    @Override
    public void dispose() {

    }
}
