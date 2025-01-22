package wizardo.game.Spells.Frost.Frozenorb;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Items.Equipment.Ring.Epic_FrostRing;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.FrozenorbAnims;
import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Hybrid.FrostNova.FrostNova_Explosion;
import wizardo.game.Spells.Hybrid.Laser.Laser_Spell;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Frozenorb_Projectile extends Frozenorb_Spell {

    Body body;
    RoundLight light;
    boolean lightKilled;
    boolean hasCollided;
    float scale = 1;
    int frameCounter = 0;
    int rotation;

    int castCounter = 0;
    float castInterval = 0.05f;

    boolean frostNovaSpent;

    public Frozenorb_Projectile(Vector2 spawnPosition, Vector2 targetPosition) {
        this.spawnPosition = new Vector2(spawnPosition);
        this.targetPosition = new Vector2(targetPosition);
        rotation = MathUtils.random(360);
    }

    public void setup() {
        radius = 2.85f + 0.15f * getLvl();
        radius += (1 + player.spellbook.iceRadiusBonus/100f);
        pickAnim();
        createBody();
        createLight();
        if(nested_spell != null) {
            castInterval = getInterval();
        }
    }

    public void update(float delta) {
        if(!initialized) {
            setup();
            initialized = true;
        }

        stateTime += delta;
        drawFrame(delta);

        areaDmg(delta);
        adjustMovement();
        adjustLight();
        adjustScale();

        if(frostnova && scale < 0.5f) {
            frostNova();
        }

        if(nested_spell != null && scale > 0.75f && delta > 0) {
            shootProjectiles();
        }

    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().x * PPM, body.getPosition().y * PPM);

        if((hasCollided || scale < 0.5f) && !lightKilled) {
            light.dimKill(0.01f);
            lightKilled = true;
        }
    }

    public void adjustMovement() {
        if(hasCollided) {
            body.setLinearVelocity(0,0);
        }
    }

    public void createBody() {
        Vector2 direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction.set(1,0);
        }

        if(castByPawn) {
            Vector2 offset = new Vector2(direction.cpy().scl(30));
            spawnPosition = new Vector2(spawnPosition.add(offset));
        }

        float bodyRadius = 22 * (1 + player.spellbook.iceRadiusBonus/100f);
        body = BodyFactory.spellProjectileCircleBody(spawnPosition, bodyRadius, true);
        body.setUserData(this);

        Vector2 velocity = new Vector2(direction.scl(speed));
        body.setLinearVelocity(velocity);
    }

    public void createLight() {
        float lightRadius = 120 + 10 * getLvl();
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue, lightAlpha, lightRadius, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void drawFrame(float delta) {
        if(delta > 0) {
            rotation += 5;
        }
        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));

        float immediatesScale = scale * (1 + player.spellbook.iceRadiusBonus/100f);
        if(anim_element == COLDLITE) {
            frame.setScale(0.7f * immediatesScale);
        } else {
            frame.setScale(immediatesScale);
        }
        frame.setRotation(rotation);
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
        screen.centerSort(frame, body.getPosition().y * PPM - 20);
    }

    public void adjustScale() {
        if(stateTime > duration - 0.5f || hasCollided) {
            scale = scale - 0.025f;
            if(scale <= 0.1f) {
                screen.spellManager.remove(this);
                world.destroyBody(body);
                body = null;
                if(light != null) {
                    light.dimKill(0.04f);
                }
            }
        }
    }

    public void areaDmg(float delta) {

        if(delta > 0) {
            frameCounter++;
            if (frameCounter == 5) {
                frameCounter = 0;
                for (Monster monster : screen.monsterManager.liveMonsters) {
                    if (monster.body.getPosition().dst(body.getPosition()) < radius) {
                        float dmg = getDmg()/12f;
                        dmg = getScaledDmg(dmg);
                        monster.hp -= dmg;
                        if(anim_element == FROST) {
                            monster.applySlow(2, slowRatio - 0.025f * getLvl());
                            if(player.inventory.equippedRing instanceof Epic_FrostRing) {
                                if(Math.random() > 0.9f) {
                                    monster.applyFreeze(1.5f, 3f);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void shootProjectiles() {
        if(stateTime > castInterval * castCounter) {
            Spell proj = nested_spell.clone();

            setProj(proj);

            screen.spellManager.add(proj);
            castCounter++;
        }

    }

    public void setProj(Spell proj) {

        proj.setElements(this);
        proj.originBody = body;
        proj.spawnPosition = body.getPosition();
        proj.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2.5f);

        if(proj instanceof Flamejet_Spell) {
            proj.lightAlpha = 0.5f + getInterval() * 2.5f;
        }
    }

    public void pickAnim() {
        switch(anim_element) {
            case FROST -> {
                red = 0.1f;
                anim = FrozenorbAnims.frozenorb_anim_frost;
                blue = 0.5f;
            }
            case FIRE -> {
                anim = FrozenorbAnims.frozenorb_anim_fire;
                red = 0.5f;
                green = 0.25f;
            }
            case LIGHTNING, COLDLITE -> {
                anim = FrozenorbAnims.frozenorb_anim_lightning;
                green = 0.6f;
                blue = 0.75f;
                if(anim_element == COLDLITE) {
                    green = 0.2f;
                }
                scale = 0.9f;
            }
            case ARCANE -> {
                anim = FrozenorbAnims.frozenorb_anim_arcane;
                red = 0.6f;
                green = 0.15f;
                blue = 0.9f;
            }

        }
    }

    public float getInterval() {
        float interval = 1;
        float level = (getLvl() + nested_spell.getLvl()) / 2f;

        if(nested_spell instanceof ChainLightning_Spell) {
            interval = 0.65f - 0.05f * level;
        }
        if(nested_spell instanceof Frostbolt_Spell) {
            interval = 0.25f - 0.02f * level;
        }
        if(nested_spell instanceof ChargedBolts_Spell) {
            interval = 0.21f - 0.02f * level;
        }
        if(nested_spell instanceof Flamejet_Spell) {
            interval = 0.15f - 0.0125f * level;
        }
        if(nested_spell instanceof Icespear_Spell) {
            interval = 0.15f - 0.01f * level;
        }
        if(nested_spell instanceof ArcaneMissile_Spell) {
            interval = 0.3f - 0.0125f * level;
        }
        if(nested_spell instanceof Laser_Spell) {
            interval = 0.2f - 0.015f * level;
        }

        return interval;
    }

    public void frostNova() {
        if(!frostNovaSpent) {
            FrostNova_Explosion nova = new FrostNova_Explosion();
            nova.setElements(this);
            nova.targetPosition = new Vector2(body.getPosition());
            screen.spellManager.add(nova);

            if(nested_spell instanceof Frostbolt_Spell) {
                nova.frostbolts = true;
            } else if(nested_spell instanceof Icespear_Spell) {
                int projs = 10 + nested_spell.getLvl() * 2;
                for (int i = 0; i < projs; i++) {
                    Spell spear = nested_spell.clone();
                    setProj(spear);
                    screen.spellManager.add(spear);
                }
            }
            frostNovaSpent = true;
        }
    }
}
