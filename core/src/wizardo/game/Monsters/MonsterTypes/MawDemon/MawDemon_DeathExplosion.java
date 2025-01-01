package wizardo.game.Monsters.MonsterTypes.MawDemon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.MonsterResources.MawDemonAnims;

import static wizardo.game.Utils.Constants.PPM;

public class MawDemon_DeathExplosion extends MonsterSpell {

    Animation<Sprite> anim;

    public MawDemon_DeathExplosion(Monster monster) {
        super(monster);
    }

    @Override
    public void checkState(float delta) {
        if(stateTime >= anim.getAnimationDuration()) {
            screen.monsterSpellManager.toRemove(this);
        }
    }

    @Override
    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(originMonster.body.getPosition().x * PPM, originMonster.body.getPosition().y * PPM + 80);
        frame.flip(!originMonster.frameReversed, false);
        screen.addSortedSprite(frame);
        screen.centerSort(frame,originMonster.body.getPosition().y * PPM - 20);
    }

    public void createLight() {
        Vector2 position = new Vector2(originMonster.body.getPosition().x, originMonster.body.getPosition().y + 2.5f);
        light = screen.lightManager.pool.getLight();
        light.setLight(0.9f, 0.35f, 0, 1, 130, position);
        screen.lightManager.addLight(light);
        light.dimKill(0.01f);
    }


    @Override
    public void initialize() {
        anim = MawDemonAnims.mawDemon_skullFlame;
        createLight();
    }

    @Override
    public void dispose() {

    }
}
