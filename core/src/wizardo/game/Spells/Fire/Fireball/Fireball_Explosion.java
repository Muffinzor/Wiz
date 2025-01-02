package wizardo.game.Spells.Fire.Fireball;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.FireballAnims;
import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Fireball_Explosion extends Fireball_Spell {

    Body body;
    RoundLight light;

    float frameScale = 1;
    float rotation;
    boolean flipX;
    boolean flipY;

    public boolean firelite;

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
            thunderstorm();
            flameRifts();
            flamejets();
            spearOrb();
        }
        stateTime += delta;

        if(stateTime >= 0.15f && body.isActive()) {
            body.setActive(false);
            light.dimKill(0.01f);
        }

        drawFrame();
        frostbolts(delta);

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);

        Vector2 direction = monster.body.getPosition().sub(body.getPosition());
        monster.movementManager.applyPush(direction, 3, 0.2f, 0.9f);

        frozenOrb(monster);
    }

    public void drawFrame() {

        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);

        frame.rotate(rotation);
        frame.flip(flipX, flipY);

        frame.setScale(frameScale * effectRatio * (1 + player.spellbook.fireballBonus/100f));

        screen.centerSort(frame, body.getPosition().y * PPM - 10);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, radius * effectRatio * (1 + player.spellbook.fireballBonus/100f));
        body.setUserData(this);
    }

    public void createLight() {
        lightAlpha = 1;
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, 250 * effectRatio, body.getPosition());
        light.toLightManager();
    }

    public void frostbolts(float delta) {
        float interval = 0.35f - 0.033f * ((getLvl() + player.spellbook.frostbolt_lvl)/1.5f);
        if(frostbolts && stateTime % interval < delta) {
            for (int i = 0; i < 2; i++) {
                Vector2 random = SpellUtils.getRandomVectorInRadius(body.getPosition(), 3);
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                if(interval < 0.22f) {
                    explosion.lightAlpha -= 0.15f;
                }
                explosion.setElements(this);
                explosion.targetPosition = random;
                screen.spellManager.add(explosion);
            }
        }
    }

    public void flameRifts() {
        if(flameRift) {
            int quantity = 2 + (player.spellbook.rift_lvl / 4);
            for (int i = 0; i < quantity; i++) {
                Vector2 randomPosition = SpellUtils.getClearRandomPosition(body.getPosition(), 3);
                Rift_Zone rift = new Rift_Zone(randomPosition);
                rift.setElements(this);
                rift.nested_spell = new Flamejet_Spell();
                screen.spellManager.add(rift);
            }
        }
    }

    public void spearOrb() {
        if(spearOrb) {
            Frozenorb_Spell orb = new Frozenorb_Spell();
            orb.duration = 1.2f;
            orb.spawnPosition = new Vector2(body.getPosition());
            orb.speed = 0;
            Icespear_Spell spear = new Icespear_Spell();
            spear.duration = 0.3f;
            spear.maxSplits = 0;
            orb.nested_spell = spear;
            orb.setElements(this);
            screen.spellManager.add(orb);
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
            screen.spellManager.add(orb);
        } else if(nested_spell != null) {
            int quantity = getProjQuantity();
            for (int i = 0; i < quantity; i++) {
                Spell proj = nested_spell.clone();
                proj.setElements(this);
                proj.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 4);
                proj.spawnPosition = new Vector2(body.getPosition());
                screen.spellManager.add(proj);
                if(nested_spell instanceof ArcaneMissile_Spell) {
                    ArcaneMissile_Spell missile = (ArcaneMissile_Spell) proj;
                    missile.scale = 0.7f;
                }
            }
        }
    }

    public void flamejets() {
        if(flamejets) {
            int quantity = 2 + player.spellbook.flamejet_lvl;
            for (int i = 0; i < quantity; i++) {
                Flamejet_Spell flame = new Flamejet_Spell();
                flame.setElements(this);
                flame.originBody = body;
                flame.spawnPosition = new Vector2(body.getPosition());
                flame.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                screen.spellManager.add(flame);
            }
        }
    }

    public int getProjQuantity() {
        int quantity = 0;
        float level = (getLvl() + nested_spell.getLvl()) / 2f;

        if(nested_spell instanceof Flamejet_Spell) {
            quantity = 2 + (int) (level);
        }
        if(nested_spell instanceof ChargedBolts_Spell) {
            quantity = 5 + (int) (level);
        }
        if(nested_spell instanceof ArcaneMissile_Spell) {
            quantity = 2 + (int) (level);
        }

        return quantity;
    }

    public void thunderstorm() {
        if(chainThunder || flameThunder) {
            Thunderstorm_Spell storm = new Thunderstorm_Spell();
            if(chainThunder) {
                storm.nested_spell = new ChainLightning_Spell();
            } else {
                storm.nested_spell = new Flamejet_Spell();
            }
            storm.setElements(this);
            storm.spawnPosition = new Vector2(body.getPosition());
            storm.radius = 5;
            storm.duration = 1;
            screen.spellManager.add(storm);
        }
    }


    public void picKAnim() {
        anim = FireballAnims.getAnim(anim_element);
        switch(anim_element) {
            case FIRE -> {
                red = 1f;
                green = 0.25f;
                lightAlpha = 1;
                frameScale = 0.75f;
            }
            case FROST -> {
                red = 0.1f;
                green = 0.3f;
                blue = 0.8f;
                frameScale = 0.75f;
            }
            case LIGHTNING, FIRELITE -> {
                red = 0.55f;
                green = 0.25f;
                lightAlpha = 1;
                frameScale = 0.8f;
            }
            case ARCANE -> {
                red = 0.6f;
                blue = 0.85f;
                frameScale = 0.75f;
            }
        }
    }
}
