package wizardo.game.Spells.Hybrid.Judgement;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Hat.Epic_JudgementHat;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Elemental;
import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FROST;
import static wizardo.game.Spells.SpellUtils.getClearRandomPosition;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Judgement_Trigger_Explosion extends Judgement_Spell {


    RoundLight light;
    boolean flipX;
    boolean flipY;
    int rotation;

    float explosionInterval = 0.15f;
    float explosions = 0;
    int explosionsCast = 0;

    public Judgement_Trigger_Explosion(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
    }

    public void setup() {
        if(player.inventory.equippedHat instanceof Epic_JudgementHat && !rift) {
            explosions = 9;
        }
        if(rift) {
            explosions = 3 + 2 * player.spellbook.rift_lvl;
            if(player.inventory.equippedHat instanceof Epic_JudgementHat) {
                explosions *= 3;
            }
        }
        explosionInterval = 1.5f/explosions;
    }

    public void update(float delta) {
        if(!initialized) {
            setup();
            initialized = true;
            pickAnim();
            createBody();
            createLight();
            frozenorb();
            arcaneMissiles();
        }

        drawFrame();
        stateTime += delta;

        if(explosionsCast < explosions && stateTime > explosionsCast * explosionInterval + Math.min(explosionInterval, 0.25f)) {
            Judgement_Explosion explosion = new Judgement_Explosion();
            explosion.targetPosition = getClearRandomPosition(targetPosition, 4);
            explosion.arcaneMissiles = arcaneMissiles;
            explosion.setElements(this);
            explosion.lightAlpha = Math.max(explosion.lightAlpha - 0.03f * explosions, 0.5f);
            screen.spellManager.add(explosion);
            explosionsCast++;
        }

        if(body.isActive() && stateTime >= 0.3f) {
            body.setActive(false);
            light.dimKill(0.016f);
        }

        if(stateTime >= anim.getAnimationDuration() && explosionsCast >= explosions) {
            screen.spellManager.remove(this);
            world.destroyBody(body);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        screen.centerSort(frame, targetPosition.y * PPM - 35);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 120);
        body.setUserData(this);
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, 275, targetPosition);
        screen.lightManager.addLight(light);
    }

    public void pickAnim() {
        anim = ExplosionAnims_Elemental.getExplosionAnim(anim_element);
        switch(anim_element) {
            case FROST -> {
                green = 0.15f;
                blue = 0.75f;
            }
            case ARCANE -> {
                red = 0.6f;
                green = 0.15f;
                blue = 0.9f;
            }
            case FIRE -> {
                red = 0.75f;
                green = 0.15f;

            }
        }
    }

    public void frozenorb() {
        if(frozenorb) {
            Frozenorb_Spell orb = new Frozenorb_Spell();
            orb.spawnPosition = new Vector2(targetPosition);
            orb.targetPosition = new Vector2(0,0);
            orb.speed = 0;
            orb.duration = 1.5f + 0.5f * player.spellbook.frozenorb_lvl;
            orb.lightAlpha = 0.85f;
            orb.anim_element = FROST;
            screen.spellManager.add(orb);
        }
    }

    public void arcaneMissiles() {
        if(arcaneMissiles) {
            int quantity = 4 + (player.spellbook.arcanemissile_lvl * 2) + (player.spellbook.arcane_missile_bonus_proj * 2);
            for (int i = 0; i < quantity; i++) {
                Vector2 targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                ArcaneMissile_Spell missile = new ArcaneMissile_Spell();
                missile.spawnPosition = new Vector2(body.getPosition());
                missile.targetPosition = targetPosition;
                missile.setElements(this);
                screen.spellManager.add(missile);
            }
        }
    }
}
