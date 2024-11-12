package wizardo.game.Spells.Hybrid.EnergyRain;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionsAnims;
import wizardo.game.Resources.SpellAnims.OverheatAnims;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Resources.SpellAnims.ExplosionsAnims.getExplosionAnim;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class EnergyRain_Explosion extends EnergyRain_Spell {

    Body body;
    RoundLight light;

    int rotation;
    boolean flipX;
    boolean flipY;

    public EnergyRain_Explosion() {

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

    }

    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            createBody();
            createLight();
            initialized = true;
            chargedbolts();
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.2f) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void handleCollision(Monster monster) {
        if(frostbolt) {
            monster.applySlow(5, 0.7f);
            frostbolts(monster);
        }
        dealDmg(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setScale(1.75f);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        screen.centerSort(frame, body.getPosition().y * PPM - 10);
        screen.addSortedSprite(frame);
    }

    public void pickAnim() {
        anim = getExplosionAnim(anim_element);
        switch(anim_element) {
            case FROST -> {
                red = 0.2f;
                blue = 0.9f;
            }
            case ARCANE -> {
                red = 0.75f;
                blue = 0.95f;
            }
            case LIGHTNING -> {
                red = 0.55f;
                blue = 0.35f;
            }
        }
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 90);
        body.setUserData(this);
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha,150,targetPosition);
        light.dimKill(0.015f);
        screen.lightManager.addLight(light);
    }

    public void frostbolts(Monster monster) {
        float procRate = 0.9f - 0.05f * player.spellbook.frostbolt_lvl;
        if(Math.random() >= procRate) {
            Frostbolt_Explosion explosion = new Frostbolt_Explosion();
            explosion.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), monster.bodyRadius/PPM);
            explosion.setElements(this);
            screen.spellManager.toAdd(explosion);
        }
    }

    public void chargedbolts() {
        if(chargedbolts) {
            int quantity = 5 + player.spellbook.chargedbolt_lvl/3;
            for (int i = 0; i < quantity; i++) {
                ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                bolt.spawnPosition = new Vector2(body.getPosition());
                bolt.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 3);
                bolt.setElements(this);
                screen.spellManager.toAdd(bolt);
            }
        }
    }

}
