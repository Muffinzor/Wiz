package wizardo.game.Monsters.MonsterActions.SmallProjectile;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.MonsterResources.MonsterProjectiles.SmallProjectileAnims;

import static wizardo.game.Utils.Constants.PPM;

public class SmallLaser_Explosion extends SmallLaser_Projectile {

    Animation<Sprite> anim;
    boolean flipX;
    boolean flipY;
    int rotation;

    public SmallLaser_Explosion(Vector2 spawnPosition, Monster monster) {
        super(spawnPosition, monster);

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

    }

    @Override
    public void checkState(float delta) {
        if(stateTime >= anim.getAnimationDuration()) {
            screen.monsterSpellManager.toRemove(this);
        }
    }

    @Override
    public void pickAnim() {
        anim = SmallProjectileAnims.green_hit_anim;
        red = 0.2f;
        green = 0.9f;
        blue = 0.25f;
    }

    @Override
    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(spawnPosition.x * PPM, spawnPosition.y * PPM);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        frame.setScale(0.8f);
        screen.centerSort(frame, spawnPosition.y * PPM - 10);
        screen.addSortedSprite(frame);
    }

    public void createBody() {

    }

    @Override
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, 1, 75, spawnPosition);
        screen.lightManager.addLight(light);
        light.dimKill(0.01f);
    }

    public void adjustLight() {

    }

    @Override
    public void dispose() {

    }
}
