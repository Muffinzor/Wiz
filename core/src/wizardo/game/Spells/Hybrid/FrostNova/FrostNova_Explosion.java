package wizardo.game.Spells.Hybrid.FrostNova;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Elemental;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class FrostNova_Explosion extends FrostNova_Spell {

    Body body;
    RoundLight light;

    float radius = 135;
    float sizeScaling = 1;
    float freezeDuration = 2.25f;

    public FrostNova_Explosion(){

        anim = ExplosionAnims_Elemental.frostnova_anim;

        sizeScaling = 1 + ((player.spellbook.overheat_lvl - 1) * 0.05f) + (player.spellbook.iceRadiusBonus/100f); // 1.5 at lvl 10
        freezeDuration += 0.25f * player.spellbook.frozenorb_lvl;

    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            createLight();
        }
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.1f) {
            body.setActive(false);
        }

        if(stateTime > anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }

        drawFrame();
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);

        if(monster.freezeImmunityTimer <= 0) {
            monster.applyFreeze(freezeDuration, 1 + freezeDuration * 2);
        } else {
            float slowScale = .62f - 0.02f * player.spellbook.frozenorb_lvl;
            monster.applySlow(4, slowScale);
        }

        Vector2 direction = monster.body.getPosition().sub(body.getPosition());
        float strength = 3 * (1 + player.spellbook.pushbackBonus/100f);
        monster.movementManager.applyPush(direction, strength, 0.5f, 0.9f);

        frostbolts(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.setScale(1.15f * sizeScaling);
        frame.setAlpha(0.8f);
        //screen.centerSort(frame, targetPosition.y * PPM);
        screen.addSortedSprite(frame);

    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, radius * sizeScaling);
        body.setUserData(this);
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0,0.35f, 0.75f, 1, 350, targetPosition);
        screen.lightManager.addLight(light);
        light.dimKill(0.005f);
    }

    public void frostbolts(Monster monster) {
        if(frostbolts) {

            float procRate = 0.8f - 0.05f * player.spellbook.frostbolt_lvl;
            if(Math.random() >= procRate) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 0.5f);
                explosion.setElements(this);
                screen.spellManager.add(explosion);
            }
        }
    }

}
