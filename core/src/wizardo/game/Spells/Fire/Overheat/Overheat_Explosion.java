package wizardo.game.Spells.Fire.Overheat;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.OverheatAnims;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Overheat_Explosion extends Overheat_Spell {

    Body body;
    RoundLight light;

    boolean flipX;
    boolean flipY;
    int rotation;

    public Overheat_Explosion(Vector2 targetPosition) {

        this.targetPosition = new Vector2(targetPosition);

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            pickAnim();
            createBody();
            createLight();
            sendProjectiles();
        }

        drawFrame();

        stateTime += delta;

        if(stateTime >= 0.2f) {
            body.setActive(false);
        }

        delayedFrostbolts(delta);

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);

        fireball(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.setScale(1.2f);
        frame.flip(flipX, flipY);
        screen.centerSort(frame, body.getPosition().y * PPM - 30);
        screen.addSortedSprite(frame);
    }

    public void pickAnim() {
        switch(anim_element) {
            case FROST -> {
                anim = OverheatAnims.overheat_anim_frost;
                green = 0.15f;
                blue = 0.5f;
            }
            case FIRE -> {
                anim = OverheatAnims.overheat_anim_fire;
                red = 0.7f;
                green = 0.3f;
            }
        }
    }

    public void createBody() {
        if(targetPosition == null) {
            targetPosition = new Vector2(player.pawn.body.getPosition());
        }
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, 250, targetPosition);
        light.dimKill(0.015f);
        screen.lightManager.addLight(light);
    }

    public void immediateFrostbolts() {
        if(frostbolts) {
            int quantity = 2 + 2 * player.spellbook.frostbolt_lvl;
            float radius = 5 + 0.12f * player.spellbook.frostbolt_lvl;
            for (int i = 0; i < quantity; i++) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.setElements(this);
                explosion.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), radius);
                screen.spellManager.toAdd(explosion);
            }
        }
    }

    public void delayedFrostbolts(float delta) {

        if(frostbolts) {

            float level = (getLvl() + player.spellbook.frostbolt_lvl)/2f;
            float interval = 0.2f - 0.015f * level;

            if(stateTime % interval < delta) {

                float radius = 5 + 0.12f * player.spellbook.frostbolt_lvl;
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.setElements(this);
                explosion.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), radius);
                screen.spellManager.toAdd(explosion);

            }
        }
    }

    public void fireball(Monster monster) {
        if(fireball) {

            float level = (getLvl() + player.spellbook.fireball_lvl)/2f;
            float procRate = 0.8f - 0.05f * level;

            if(Math.random() >= procRate) {
                Overheat_TriggerExplosion fireball = new Overheat_TriggerExplosion();
                fireball.frozenorb = frozenorb;
                fireball.setElements(this);
                fireball.targetPosition = monster.body.getPosition();
                screen.spellManager.toAdd(fireball);
            }
        }
    }

    public void sendProjectiles() {
        immediateFrostbolts();

        if(nested_spell != null) {

            int quantity = getQuantity();

            for (int i = 0; i < quantity; i++) {
                Spell proj = nested_spell.clone();
                proj.setElements(this);
                proj.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 5);
                proj.spawnPosition = new Vector2(body.getPosition());
                screen.spellManager.toAdd(proj);

            }
        }

    }


    public int getQuantity() {
        int quantity = 0;
        double level = (getLvl() + nested_spell.getLvl()) /2f;

        if(nested_spell instanceof Icespear_Spell) {
            quantity = 5 + (int) (3 * level);
        }

        return quantity;
    }
}
