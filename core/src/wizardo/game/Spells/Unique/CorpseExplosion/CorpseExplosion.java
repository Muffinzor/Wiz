package wizardo.game.Spells.Unique.CorpseExplosion;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.EffectAnims.CorpseExplosionAnims;
import wizardo.game.Spells.Spell;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.GameSettings.dmg_text_on;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class CorpseExplosion extends Spell {

    Monster monster;
    RoundLight light;

    int rotation;
    boolean flipX;
    boolean flipY;

    public CorpseExplosion(Monster monster) {
        dmg = (int) (monster.maxHP * MathUtils.random(0.35f, 0.4f));
        anim = CorpseExplosionAnims.corpse_explosion;

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

        this.monster = monster;
    }

    @Override
    public void update(float delta) {
        if(!initialized && stateTime >= 0.15f) {
            initialized = true;
            createBody();
            createLight();
        }

        drawFrame();
        stateTime += delta;

        if(stateTime >= 0.35f && body.isActive()) {
            body.setActive(false);
            light.dimKill(0.02f);
        }

        if(stateTime >= anim.getAnimationDuration() + 0.15f) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
    }

    public void drawFrame() {
        if(initialized) {
            Sprite frame = screen.getSprite();
            frame.set(anim.getKeyFrame(stateTime - 0.15f, false));
            frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
            frame.setRotation(rotation);
            frame.flip(flipX, flipY);
            screen.addSortedSprite(frame);
            screen.centerSort(frame, body.getPosition().y * PPM - 10);
        }
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(monster.body.getPosition(), 60);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0.9f, 0, 0, 1, 120, body.getPosition());
        screen.lightManager.addLight(light);
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }

    public void dealDmg(Monster monster) {
        float dmg = getDmg();
        dmg = getScaledDmg(dmg);
        float randomFactor = MathUtils.random(1 - dmgVariance, 1 + dmgVariance);
        dmg *= randomFactor;
        monster.hp -= dmg;
    }
    public int getScaledDmg(float unscaledDmg) {
        float scaledDmg = unscaledDmg;
        scaledDmg *= (1 + player.spellbook.allBonusDmg/100f);
        return (int) scaledDmg;
    }

    @Override
    public int getDmg() {
        return dmg;
    }
}
