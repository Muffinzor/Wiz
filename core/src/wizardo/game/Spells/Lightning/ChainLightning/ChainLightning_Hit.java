package wizardo.game.Spells.Lightning.ChainLightning;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import wizardo.game.Items.Equipment.Staff.Epic_ChainStaff;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ChainLightningAnims;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Fire.Fireball.Fireball_Explosion;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class ChainLightning_Hit extends ChainLightning_Spell {

    boolean alreadyChained;
    boolean hasDealtDmg;
    Body body;
    public boolean forked;
    public float duration = 0.3f;

    public Monster monsterFrom;
    public Monster monsterTo;
    public boolean firstChain;

    public ArrayList<Monster> inRange;
    public ArrayList<Monster> monstersHit = new ArrayList<>();

    boolean flipY;

    public Animation<Sprite> longAnim;

    int frameCounter = 0;

    public ChainLightning_Hit(Monster target) {
        monsterTo = target;

        flipY = MathUtils.randomBoolean();

    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            pickAnim();
            if(nested_spell != null) {
                nested_projectiles();
            }
            frostbolts(monsterTo);
            rifts(monsterTo);
            fireball(monsterTo);
            laserBody();
            uniqueStaff();
        }

        if(stateTime > 0.1f && !hasDealtDmg && monsterTo.hp > 0) {
            dealDmg(monsterTo);
            hasDealtDmg = true;
        }

        if(currentHits < maxHits && !alreadyChained && stateTime > 0.15f) {
            alreadyChained = true;
            inRange = findMonstersInRange(monsterTo.body, radius);

            if(splits < maxSplits && inRange.size() > 1) {
                splitChain();
            } else if(!inRange.isEmpty()) {
                singleChain();
            }

        }

        stateTime += delta;
        drawFrame(delta);

        if(stateTime > duration) {
            screen.spellManager.remove(this);
            if(beam) {
                world.destroyBody(body);
            }
        }
    }

    public void drawFrame(float delta) {
        frameCounter++;

        if(!monsterTo.teleporting) {
            if(monsterFrom != null && monsterFrom.teleporting) {
                return;
            }
            Sprite frame = screen.getSprite();

            Vector2 origin = new Vector2(originBody.getPosition().scl(PPM));
            Vector2 target = new Vector2(monsterTo.body.getPosition().scl(PPM));

            Vector2 direction = target.sub(origin);
            float distance = direction.len();
            float angle = direction.angleDeg();

            if (frameCounter > 4 && delta > 0) {
                createLights(direction, distance);
                frameCounter = 0;
            }


            if (distance > 150) {
                frame.set(longAnim.getKeyFrame(stateTime, true));
            } else {
                frame.set(anim.getKeyFrame(stateTime, true));
            }

            frame.setSize(distance, 90);
            frame.setOrigin(0, frame.getHeight() / 2f);
            frame.setRotation(angle);
            frame.flip(false, flipY);
            frame.setPosition(originBody.getPosition().x * PPM, originBody.getPosition().y * PPM - frame.getHeight() / 2);

            screen.addSortedSprite(frame);
        }

    }

    public void handleCollision(Monster monster) {
        boolean monsterFromIsNull = monsterFrom == null;
        if(monster.body != monsterTo.body && monsterFromIsNull) {
            dealDmg(monster);
        } else if(monster.body != monsterTo.body && monster.body != monsterFrom.body) {
            dealDmg(monster);
        }

        if(rifts) {
            float procTreshold = 0.9333f - 0.0333f * player.spellbook.rift_lvl;

            if(Math.random() >= procTreshold) {
                Rift_Zone rift = new Rift_Zone(monster.body.getPosition());
                rift.setElements(this);
                screen.spellManager.add(rift);
            }
        }
    }

    public void singleChain() {

        Monster target;
        if(arcaneMissile) {
            target = missileTargeting();
        } else {
            target = normalTargeting();
        }

        if(target != null) {
            ChainLightning_Hit chain = new ChainLightning_Hit(target);
            chain.setNextChain(this);
            chain.setElements(this);
            monstersHit.add(target);
            chain.monstersHit = monstersHit;
            screen.spellManager.add(chain);
        }

    }
    public void splitChain() {
        splits++;

        Collections.shuffle(inRange);

        Monster target1 = inRange.removeFirst();
        Monster target2 = inRange.removeFirst();

        ChainLightning_Hit chain = new ChainLightning_Hit(target1);
        chain.setNextChain(this);
        chain.setElements(this);
        chain.monstersHit = new ArrayList<>(monstersHit);
        chain.monstersHit.add(target1);
        screen.spellManager.add(chain);

        ChainLightning_Hit chain2 = new ChainLightning_Hit(target1);
        chain2.setNextChain(this);
        chain2.setElements(this);
        chain2.monstersHit = new ArrayList<>(monstersHit);
        chain2.monstersHit.add(target2);
        screen.spellManager.add(chain2);

    }

    public Monster normalTargeting() {
        Collections.shuffle(inRange);
        return inRange.removeFirst();
    }

    public Monster missileTargeting() {

        inRange.sort((m1, m2) -> Float.compare(m2.hp, m1.hp));
        int index = MathUtils.random(0, Math.min(3, inRange.size() - 1));

        Monster newTarget = inRange.get(index);
        monstersHit.clear();

        return newTarget;
    }

    public void setNextChain(ChainLightning_Hit thisHit) {
        maxHits = thisHit.maxHits;
        currentHits = thisHit.currentHits + 1;
        splits = thisHit.splits;
        maxSplits = thisHit.maxSplits;

        nested_spell = thisHit.nested_spell;
        monsterFrom = thisHit.monsterTo;
        originBody = monsterFrom.body;

        beam = thisHit.beam;
        fireball = thisHit.fireball;
        frostbolts = thisHit.frostbolts;
        arcaneMissile = thisHit.arcaneMissile;
        rifts = thisHit.rifts;
    }

    public void createLights(Vector2 direction, float distance) {
        Vector2 unitDirection = new Vector2(direction);
        if (unitDirection.len() > 0) {
            unitDirection.nor();
        }
        Vector2 step = unitDirection.scl(20f / PPM);
        int numLights = (int) distance / 20;
        if(numLights < 1) {
            numLights = 1;
        }

        for (int i = 0; i < numLights; i++) {
            if(forked && i == 0) {
                continue;
            }
            Vector2 position = new Vector2(originBody.getPosition()).add(step.x * i, step.y * i);
            RoundLight light = screen.lightManager.pool.getLight();
            light.setLight(red,green,blue,lightAlpha,23, position);
            light.dimKill(0.1f);
            screen.lightManager.addLight(light);
        }
    }

    public void laserBody() {
        if(beam) {
            // Calculate the direction vector from originBody to monsterTo
            Vector2 direction = monsterTo.body.getPosition().sub(originBody.getPosition());
            float distance = direction.len();  // Get the length of the direction vector
            float angle = direction.angleDeg();  // Get the angle of the direction vector

            // Calculate the midpoint between originBody and monsterTo
            Vector2 origin = new Vector2(originBody.getPosition());
            Vector2 target = new Vector2(monsterTo.body.getPosition());
            Vector2 midpoint = origin.cpy().lerp(target, 0.5f);  // Get the midpoint

            // Create the rectangle body with the midpoint as the position
            body = BodyFactory.spellRectangleBody(midpoint, distance, 0.35f, angle, true);
            body.setUserData(this);
        }
    }

    public void pickAnim() {
        if(beam) {
            anim = ChainLightningAnims.chainlightning_beam_anim;
            longAnim = ChainLightningAnims.chainlightning_long_beam_anim;
            red = 1f;
            green = 0.3f;
            blue = 0.85f;
            return;
        }
        switch(anim_element) {
            case LIGHTNING -> {
                anim = ChainLightningAnims.chainlightning_lightning_anim;
                longAnim = ChainLightningAnims.chainlightning_long_lightning_anim;
                if(arcaneMissile) {
                    red = 1f;
                    green = 0.3f;
                    blue = 0.85f;
                } else {
                    red = 0.2f;
                    green = 0.2f;
                }
            }
            case FROST, COLDLITE -> {
                anim = ChainLightningAnims.chainlightning_lightning_anim;
                longAnim = ChainLightningAnims.chainlightning_long_lightning_anim;
                green = 0.65f;
                blue = 0.65f;
            }
            case ARCANE -> {
                anim = ChainLightningAnims.chainlightning_lightning_anim;
                longAnim = ChainLightningAnims.chainlightning_long_lightning_anim;
                red = 1f;
                green = 0.3f;
                blue = 0.85f;
            }
            case FIRE, FIRELITE -> {
                anim = ChainLightningAnims.chainlightning_lightning_anim;
                longAnim = ChainLightningAnims.chainlightning_long_lightning_anim;
                red = 0.8f;
                green = 0.25f;
            }
        }
    }

    public ArrayList<Monster> findMonstersInRange(Body body, float radius) {
        // List to hold found monsters
        ArrayList<Monster> monstersInRange = new ArrayList<>();

        // Define the AABB that covers the circular area
        float lowerX = body.getPosition().x - radius;
        float lowerY = body.getPosition().y - radius;
        float upperX = body.getPosition().x + radius;
        float upperY = body.getPosition().y + radius;

        // Use an anonymous QueryCallback
        QueryCallback callback = fixture -> {
            // Check if the fixture belongs to a monster
            if (fixture.getBody().getUserData() instanceof Monster monster) {
                Vector2 monsterPosition = fixture.getBody().getPosition();

                // Calculate the distance from the player to the monster
                float distance = body.getPosition().dst(monsterPosition);


                // Check if the distance is within the radius
                if (distance <= radius && monster.body != body && !monstersHit.contains(monster) && monster.hp > 0) {
                    boolean LOS = SpellUtils.hasLineOfSight(body.getPosition(), monster.body.getPosition());
                    if(LOS) {
                        monstersInRange.add(monster);
                    }
                }
            }
            return true; // Continue the query
        };

        // Perform the query using the AABB
        world.QueryAABB(callback, lowerX, lowerY, upperX, upperY);

        HashSet<Monster> seenMonsters = new HashSet<>();
        monstersInRange.removeIf(monster -> !seenMonsters.add(monster));

        return monstersInRange;
    }



    public void nested_projectiles() {
        float procRate = getProcRate();
        float quantity = getQuantity();

        if(Math.random() >= procRate) {
            for (int i = 0; i < quantity; i++) {
                Spell proj = nested_spell.clone();
                Vector2 target = SpellUtils.getRandomVectorInRadius(monsterTo.body.getPosition(), 1);
                proj.spawnPosition = new Vector2(monsterTo.body.getPosition());
                proj.targetPosition = target;
                proj.setElements(this);
                screen.spellManager.add(proj);
            }
        }

    }

    public float getProcRate() {
        float procRate = 1;
        float level = (float) (getLvl() + nested_spell.getLvl()) / 2;
        if(nested_spell instanceof ChargedBolts_Spell) {
            procRate = 0.875f - level * .025f;
        }
        return procRate;
    }
    public int getQuantity() {
        int quantity = 1;
        if(nested_spell instanceof ChargedBolts_Spell) {
            quantity = 2 + nested_spell.getLvl()/3;
        }
        return quantity;
    }
    /**
     * NEED PROC RATE
     * @param monster
     */
    public void frostbolts(Monster monster) {

        if(frostbolts) {
            float procTreshold = 0.5f;
            if(Math.random() >= procTreshold) {

                Vector2 adjusted = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 0.5f);
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.targetPosition = new Vector2(adjusted);
                explosion.screen = screen;
                explosion.setElements(this);
                screen.spellManager.add(explosion);

            }
        }
    }
    public void rifts(Monster monster) {

        if(rifts) {
            float level = (getLvl() + player.spellbook.rift_lvl)/2f;
            float procRate = 0.9f - 0.025f * level;
            if(Math.random() >= procRate) {
                Rift_Zone rift = new Rift_Zone(monster.body.getPosition());
                rift.setElements(this);
                screen.spellManager.add(rift);
            }
        }
    }
    public void fireball(Monster monster) {

        if(fireball) {
            float level = (getLvl() + player.spellbook.fireball_lvl)/2f;
            float procRate = 0.9f - 0.025f * level;
            if(Math.random() >= procRate) {
                Fireball_Explosion explosion = new Fireball_Explosion();
                explosion.targetPosition = new Vector2(monster.body.getPosition());
                explosion.setElements(this);
                screen.spellManager.add(explosion);
            }
        }
    }

    public void uniqueStaff() {
        if(player.inventory.equippedStaff instanceof Epic_ChainStaff) {
            float proc = 0.75f;
            if(Math.random() >= proc) {
                ChainLightning_Splash splash = new ChainLightning_Splash(monsterTo.body.getPosition());
                splash.setElements(this);
                screen.spellManager.add(splash);
            }
        }
    }
}
