package wizardo.game.Spells.Fire.Fireball;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.FireballAnims;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Fireball_Explosion extends Fireball_Spell {

    Body body;
    RoundLight light;

    float rotation;
    boolean flipX;
    boolean flipY;

    public Fireball_Explosion() {
        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

    }

    public void update(float delta) {
        if(!initialized) {
            picKAnim();
            createBody();
            createLight();
            sendProjectiles();
            initialized = true;
        }
        stateTime += delta;

        if(stateTime >= 0.15f && body.isActive()) {
            body.setActive(false);
        }

        drawFrame();
        frostbolts(delta);

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);

        frozenOrb(monster);
    }

    public void drawFrame() {

        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        if(anim_element == SpellUtils.Spell_Element.FROST) {
            frame.rotate(rotation);
            frame.flip(flipX, flipY);
        } else {
            frame.flip(flipX, false);
        }
        screen.centerSort(frame, body.getPosition().y * PPM - 30);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, 250, body.getPosition());
        light.toLightManager();
        light.dimKill(0.01f);
    }

    public void frostbolts(float delta) {
        float interval = 0.35f - 0.033f * ((getLvl() + player.spellbook.frostbolt_lvl)/2f);
        if(frostbolts && stateTime % interval < delta) {
            Vector2 random = SpellUtils.getRandomVectorInRadius(body.getPosition(), 3);
            Frostbolt_Explosion explosion = new Frostbolt_Explosion();
            explosion.lightAlpha -= interval / 0.15f;
            explosion.screen = screen;
            explosion.setElements(this);
            explosion.anim_element = SpellUtils.Spell_Element.FIRE;
            explosion.targetPosition = random;
            screen.spellManager.toAdd(explosion);
        }
    }

    public void frozenOrb(Monster monster) {
        float lvl = (getLvl() + player.spellbook.frozenorb_lvl)/2f;
        float duration = 1.4f + 0.1f * lvl;
        float slowRatio = .84f - 0.04f * lvl;

        if(frozenorb) {
            if(monster.freezeImmunityTimer <= 0) {
                monster.applyFreeze(duration, 6f);
            } else {
                monster.applySlow(2.5f, slowRatio);
            }
        }
    }

    public void sendProjectiles() {
        if(nested_spell instanceof Frozenorb_Spell) {
            Frozenorb_Spell orb = (Frozenorb_Spell) nested_spell;
            orb.duration = 1.5f;
            orb.speed = 0;
            orb.spawnPosition = new Vector2(body.getPosition());
            orb.targetPosition = new Vector2(1,0);
            orb.setElements(this);
            screen.spellManager.toAdd(orb);
        } else if(nested_spell != null) {
            int quantity = getProjQuantity();
            for (int i = 0; i < quantity; i++) {
                Spell proj = nested_spell.clone();
                proj.setElements(this);
                proj.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 4);
                proj.spawnPosition = new Vector2(body.getPosition());
                screen.spellManager.toAdd(proj);
            }
        }
    }

    public int getProjQuantity() {
        int quantity = 0;
        float level = (getLvl() + nested_spell.getLvl()) / 2f;

        if(nested_spell instanceof Flamejet_Spell) {
            quantity = 2 + (int) (level);
        }

        return quantity;
    }


    public void picKAnim() {
        switch(anim_element) {
            case FIRE -> {
                anim = FireballAnims.fireball_explosion_anim_fire;
                red = 1f;
                green = 0.25f;
                lightAlpha = 0.8f;
            }
            case FROST -> {
                anim = FireballAnims.fireball_explosion_anim_frost;
                red = 0.1f;
                green = 0.3f;
                blue = 0.8f;
                lightAlpha = 1f;
            }
        }
    }
}
