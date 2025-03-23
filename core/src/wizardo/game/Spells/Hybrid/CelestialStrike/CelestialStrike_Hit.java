package wizardo.game.Spells.Hybrid.CelestialStrike;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.CelestialStrikeAnims;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class CelestialStrike_Hit extends CelestialStrike_Spell {

    RoundLight light;
    Body body;

    boolean flipX;

    public CelestialStrike_Hit() {

        anim = CelestialStrikeAnims.celestial_strike_anim;

        flipX = MathUtils.randomBoolean();

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

        if(body.isActive() && stateTime >= 0.1f) {
            body.setActive(false);
            explosion();
            chargedbolts();
        }

        frostbolts(delta);

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.remove(this);
            world.destroyBody(body);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        freeze(monster);

        float freezerate = 0.75f - 0.05f * player.spellbook.frozenorb_lvl;
        if(Math.random() >= freezerate) {
            monster.applyFreeze(3, 6);
        }
    }

    public void drawSprite() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setOrigin(frame.getWidth()/2f, 0);
        frame.setScale(1.4f);
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM + 100);
        frame.flip(flipX, false);
        screen.centerSort(frame, targetPosition.y * PPM);
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
        light.dimKill(0.02f);

        RoundLight upperLight = screen.lightManager.pool.getLight();
        Vector2 position;
        if(flipX) {
            position = new Vector2(targetPosition.x + 2, targetPosition.y + 4);
        } else {
            position = new Vector2(targetPosition.x - 2, targetPosition.y + 4);
        }
        upperLight.setLight(0, 0.5f, 0.65f, 1, 250, position);
        upperLight.dimKill(0.02f);
        screen.lightManager.addLight(upperLight);
    }

    public void explosion() {
        CelestialStrike_Explosion explosion = new CelestialStrike_Explosion(body.getPosition());
        screen.spellManager.add(explosion);
    }

    public void frostbolts(float delta) {
        if(frostbolts && stateTime <= anim.getAnimationDuration()/2) {
            float interval = 0.3f - 0.05f * (player.spellbook.frostbolt_lvl + player.spellbook.frostbolts_bonus_proj/2f);
            float radius = 2f + 0.4f * player.spellbook.frostbolt_lvl;
            if(interval < 0.05f) {
                interval = 0.05f;
            }
            if (stateTime % interval <= delta) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.targetPosition = SpellUtils.getClearRandomPosition(body.getPosition(), radius);
                explosion.setElements(this);
                explosion.lightAlpha -= 0.1f * player.spellbook.frozenorb_lvl;
                screen.spellManager.add(explosion);
            }
        }
    }

    public void chargedbolts() {
        if(chargedbolts) {
            int quantity = 6 + player.spellbook.chargedbolt_lvl * 2 + player.spellbook.chargedbolts_bonus_proj * 2;
            for (int i = 0; i < quantity; i++) {
                ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                bolt.spawnPosition = new Vector2(body.getPosition());
                bolt.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                bolt.setElements(this);
                screen.spellManager.add(bolt);
            }
        }
    }

    public void freeze(Monster monster) {
        float proc = 1 - player.spellbook.celestialstrike_bonus_freezechance/100f;
        if(Math.random() >= proc) {
            monster.applyFreeze(2.5f, 4);
        }
    }

    public void createOrb() {
        Frozenorb_Spell orb = new Frozenorb_Spell();

        orb.speed = 0;
        orb.lightAlpha = 0.6f;
        orb.anim_element = SpellUtils.Spell_Element.COLDLITE;
        orb.duration = 0.6f;
        orb.spawnPosition = new Vector2(targetPosition);
        screen.spellManager.add(orb);
    }
}
