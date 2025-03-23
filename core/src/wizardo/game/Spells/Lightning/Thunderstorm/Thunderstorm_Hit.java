package wizardo.game.Spells.Lightning.Thunderstorm;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ThunderstormAnims;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_Explosion;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Hybrid.ForkedLightning.ForkedLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class Thunderstorm_Hit extends Thunderstorm_Spell {

    boolean initialized;

    Body body;
    RoundLight light;

    boolean flipX;

    public Thunderstorm_Hit(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);
        flipX = MathUtils.randomBoolean();
    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            pickAnim();
            createBody();
            createLight();
            nestedProjectiles();
            overheat();
            frostbolts();
        }

        drawFrame();
        stateTime += delta;

        if(stateTime > 0.2f) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        rifts(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setScale(0.7f);
        frame.flip(flipX, false);
        frame.setPosition(body.getPosition().x * PPM - frame.getWidth()/2, body.getPosition().y * PPM - 170);
        screen.centerSort(frame, body.getPosition().y * PPM - 10);
        screen.addSortedSprite(frame);
    }

    public void pickAnim() {
        lightAlpha = 0.8f;
        switch(anim_element) {
            case LIGHTNING -> {
                anim = ThunderstormAnims.thunder_lightning_anim;
                red = 0.9f;
                green = 0.9f;
                blue = 0.4f;
                if(rifts && nested_spell != null) {
                    lightAlpha = 0.65f;      // for rifts + thunder + chargedbolts/chain
                }
            }
            case ARCANE -> {
                anim = ThunderstormAnims.thunder_arcane_anim;
                red = 0.7f;
                green = 0.2f;
                blue = 0.9f;
            }
            case FROST, COLDLITE -> {
                anim = ThunderstormAnims.thunder_frost_anim;
                green = 0.5f;
                blue = 0.65f;
            }
            case FIRE -> {
                anim = ThunderstormAnims.thunder_fire_anim;
                red = 0.75f;
                green = 0.3f;
            }
        }
    }

    public void createBody() {
        Vector2 adjustedPosition = new Vector2(SpellUtils.getRandomVectorInRadius(targetPosition, 15f/PPM));
        body = BodyFactory.spellExplosionBody(adjustedPosition, 40);
        body.setUserData(this);
    }

    public void createLight() {
        for (int i = 1; i < 5; i++) {
            RoundLight lighty = screen.lightManager.pool.getLight();
            Vector2 position = new Vector2(body.getPosition().x, body.getPosition().y + i);
            lighty.setLight(red, green, blue, lightAlpha, 50, position);
            screen.lightManager.addLight(lighty);
            lighty.dimKill(0.015f);
        }

        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, 160, body.getPosition());
        screen.lightManager.addLight(light);
        light.dimKill(0.015f);
    }

    public void nestedProjectiles() {
        if(nested_spell != null) {

            float procRate = getProcRate();
            int quantity = getQuantity();

            if (Math.random() >= procRate) {
                for (int i = 0; i < quantity; i++) {
                    Spell proj = nested_spell.clone();
                    proj.setElements(this);
                    proj.originBody = body;
                    proj.spawnPosition = new Vector2(body.getPosition());
                    proj.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), getProjRadius());
                    screen.spellManager.add(proj);
                }
            }
        }
    }
    public void frostbolts() {
        if(frostbolts) {
            float procRate = 0.9f - 0.1f * player.spellbook.frostbolt_lvl;
            if(Math.random() >= procRate) {
                int quantity = 2 + player.spellbook.frostbolt_lvl + player.spellbook.frostbolts_bonus_proj;
                for (int i = 0; i < quantity; i++) {
                    Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                    explosion.targetPosition = SpellUtils.getClearRandomPosition(body.getPosition(), 1.2f);
                    explosion.setElements(this);
                    screen.spellManager.add(explosion);
                }
            }
        }
    }

    public void rifts(Monster monster) {
        if(rifts) {
            float procRate = 0.095f - 0.05f * player.spellbook.rift_lvl;
            if(Math.random() >= procRate) {
                Rift_Zone rift = new Rift_Zone(monster.body.getPosition());
                rift.setElements(this);
                screen.spellManager.add(rift);
            }
        }
    }

    public void overheat() {
        if(overheat) {
            float procRate = 1f - 0.05f * player.spellbook.overheat_lvl;
            if(Math.random() >= procRate) {
                Overheat_Explosion explosion = new Overheat_Explosion(body.getPosition());
                explosion.fromThunder = true;
                explosion.setElements(this);
                screen.spellManager.add(explosion);
            }
        }
    }

    public float getProjRadius() {
        float projRadius = 2.5f;
        return projRadius;
    }

    public float getProcRate() {
        float procRate = 1;
        if(nested_spell instanceof ChargedBolts_Spell) {
            procRate = 0.8f - 0.1f * player.spellbook.chargedbolt_lvl;
        }
        if(nested_spell instanceof Flamejet_Spell) {
            procRate = 0.85f - 0.1f * player.spellbook.flamejet_lvl;
        }
        if(nested_spell instanceof ForkedLightning_Spell) {
            float level = player.spellbook.chargedbolt_lvl + player.spellbook.chainlightning_lvl;
            level = level/2f;
            procRate = 0.9f - level * 0.1f;
        }
        return procRate;
    }
    public int getQuantity() {
        int quantity = 1;
        if(nested_spell instanceof ChargedBolts_Spell) {
            quantity = 3 + player.spellbook.chargedbolts_bonus_proj;
        }
        if(nested_spell instanceof Frostbolt_Spell) {
            quantity = 3 + player.spellbook.frostbolts_bonus_proj;
        }
        if(nested_spell instanceof Flamejet_Spell) {
            quantity = 3 + player.spellbook.flamejet_lvl;
        }
        if(nested_spell instanceof ForkedLightning_Spell) {
            float level = (player.spellbook.chainlightning_lvl + player.spellbook.flamejet_lvl)/2f;
            quantity = (int) (1 + level);
        }
        return quantity;
    }

}
