package wizardo.game.Spells.Hybrid.FrostNova;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.FrostNovaAnims;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class FrostNova_Explosion extends FrostNova_Spell {

    Body body;
    RoundLight light;

    float radius = 110;
    float sizeScaling = 1;
    float freezeDuration = 2.3f;

    public FrostNova_Explosion(){

        anim = FrostNovaAnims.frostnova_anim;

        sizeScaling = 1 + player.spellbook.overheat_lvl * 0.1f; // 2 at lvl 10
        freezeDuration += 0.2f * player.spellbook.frozenorb_lvl;

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
            screen.spellManager.toRemove(this);
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

        frostbolts(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.setScale(1.8f * sizeScaling);
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
        light.setLight(0,0.35f, 0.75f, 1, 250, targetPosition);
        screen.lightManager.addLight(light);
        light.dimKill(0.01f);
    }

    public void frostbolts(Monster monster) {
        if(frostbolts) {

            float procRate = 0.8f - 0.05f * player.spellbook.frostbolt_lvl;
            if(Math.random() >= procRate) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 0.5f);
                explosion.setElements(this);
                if(MathUtils.randomBoolean()) {
                    explosion.anim_element = SpellUtils.Spell_Element.FIRE;
                }
                screen.spellManager.toAdd(explosion);
            }
        }
    }

}
