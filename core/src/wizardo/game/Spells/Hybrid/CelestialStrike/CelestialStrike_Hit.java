package wizardo.game.Spells.Hybrid.CelestialStrike;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.CelestialStrikeAnims;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FROST;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class CelestialStrike_Hit extends CelestialStrike_Spell {

    RoundLight light;
    Body body;

    boolean flipX;

    public CelestialStrike_Hit() {

        anim = CelestialStrikeAnims.celestial_strike_anim;

        flipX = MathUtils.randomBoolean();

        anim_element = FROST;

    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            createLight();
            createOrb();
        }
        drawSprite();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.2f) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.toRemove(this);
            world.destroyBody(body);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
    }

    public void drawSprite() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setOrigin(frame.getWidth()/2f, 0);
        frame.setScale(1.2f);
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM + 115);
        frame.flip(flipX, false);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 100);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0, 0.5f, 0.65f, 1, 250, targetPosition);
        screen.lightManager.addLight(light);
        light.dimKill(0.035f);
    }

    public void createOrb() {
        Frozenorb_Spell orb = new Frozenorb_Spell();

        if(frostbolts) {
            orb.nested_spell = new Frostbolt_Explosion();
            orb.nested_spell.lightAlpha = 0.4f;
        } else if(chargedbolts) {
            orb.nested_spell = new ChargedBolts_Spell();
        } else if(chain) {
            orb.nested_spell = new ChainLightning_Spell();
        }
        orb.speed = 0;
        orb.anim_element = SpellUtils.Spell_Element.LIGHTNING;
        orb.bonus_element = FROST;
        orb.duration = 1.5f;
        orb.spawnPosition = new Vector2(targetPosition);
        screen.spellManager.toAdd(orb);
    }
}
