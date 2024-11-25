package wizardo.game.Monsters.MonsterProjectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.MonsterResources.MonsterProjectiles.SmallProjectileFrames;

import static wizardo.game.Utils.Constants.PPM;

public class SmallProjectile extends MonsterProjectile {

    public SmallProjectile(Vector2 spawnPosition, Monster monster) {
        super(spawnPosition, monster);

        speed = 7;
    }

    @Override
    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(SmallProjectileFrames.small_green);
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.setScale(0.8f);
        screen.centerSort(frame, body.getPosition().y * PPM - 15);
        screen.addSortedSprite(frame);
    }

    @Override
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0.2f, 0.9f, 0.2f, 1, 35, body.getPosition());
        screen.lightManager.addLight(light);
    }

    @Override
    public void dispose() {

    }
}
