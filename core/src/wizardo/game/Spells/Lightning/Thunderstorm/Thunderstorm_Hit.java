package wizardo.game.Spells.Lightning.Thunderstorm;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.ThunderstormAnims;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
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
        }

        drawFrame();

        stateTime += delta;

        if(stateTime > 0.2f) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
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
            }
            case ARCANE -> {
                anim = ThunderstormAnims.thunder_arcane_anim;
                red = 0.7f;
                green = 0.2f;
                blue = 0.9f;
            }
            case FROST -> {
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
        body = BodyFactory.spellExplosionBody(adjustedPosition, radius);
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
                    proj.screen = screen;
                    proj.originBody = body;
                    proj.spawnPosition = new Vector2(body.getPosition());
                    proj.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), getProjRadius());
                    screen.spellManager.toAdd(proj);
                }
            }

        }
    }

    public void rifts(Monster monster) {
        if(rifts) {
            float procRate = 0.925f - 0.025f * player.spellbook.rift_lvl;
            if(Math.random() >= procRate) {
                Rift_Zone rift = new Rift_Zone(monster.body.getPosition());
                rift.setElements(this);
                screen.spellManager.toAdd(rift);
            }
        }
    }

    public float getProjRadius() {
        float projRadius = 2.5f;
        return projRadius;
    }


    public float getProcRate() {
        float procRate = 0.5f;
        float level = (float) (getLvl() + nested_spell.getLvl()) / 2;
        if(nested_spell instanceof ChargedBolts_Spell) {
            procRate = 0.525f - level * .025f;
        }
        if(nested_spell instanceof Flamejet_Spell) {
            procRate = 0.675f - level * 0.025f;
        }
        if(nested_spell instanceof ForkedLightning_Spell) {
            procRate = 0;
        }
        return procRate;
    }
    public int getQuantity() {
        int quantity = 1;
        if(nested_spell instanceof ChargedBolts_Spell) {
            quantity = 2 + nested_spell.getLvl();
        }
        if(nested_spell instanceof Frostbolt_Spell) {
            quantity = 2 + nested_spell.getLvl()/2;
        }
        if(nested_spell instanceof Flamejet_Spell) {
            quantity = 2 + nested_spell.getLvl()/2;
        }
        if(nested_spell instanceof ForkedLightning_Spell) {
            float level = (player.spellbook.chainlightning_lvl + player.spellbook.flamejet_lvl)/2f;
            quantity = (int) (1 + level/5);
        }
        return quantity;
    }

}
