package wizardo.game.Spells.Hybrid.ForkedLightning;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import wizardo.game.Items.Equipment.Hat.Epic_ForkedLightningHat;
import wizardo.game.Items.Equipment.Staff.Epic_ChainStaff;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ChainLightningAnims;

import wizardo.game.Spells.Fire.Fireball.Fireball_Explosion;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Splash;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.SpellUtils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRELITE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class ForkedLightning_Hit extends ForkedLightning_Spell {

    public float duration = 0.3f;

    public Monster monsterFrom;
    public Monster monsterTo;

    public ArrayList<Monster> inRange;

    boolean flipY;

    boolean fromUniqueHat;

    public Animation<Sprite> longAnim;

    int frameCounter = 0;

    public ForkedLightning_Hit(Monster target) {
        monsterTo = target;

        flipY = MathUtils.randomBoolean();

    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            pickAnim();
            uniqueStaff();
            singleChain();
            dealDmg(monsterTo);
            fireball(monsterTo);
            chargedbolts(monsterTo);
        }


        stateTime += delta;
        drawFrame(delta);

        if(stateTime > duration) {
            screen.spellManager.remove(this);
        }
    }

    public void drawFrame(float delta) {
        frameCounter++;

        Sprite frame = screen.getSprite();

        Vector2 origin = new Vector2(originBody.getPosition().scl(PPM));
        Vector2 target = new Vector2(monsterTo.body.getPosition().scl(PPM));

        Vector2 direction = target.sub(origin);
        float distance = direction.len();
        float angle = direction.angleDeg();

        if(frameCounter > 4 && delta > 0) {
            createLights(direction, distance);
            frameCounter = 0;
        }


        if(distance > 150) {
            frame.set(longAnim.getKeyFrame(stateTime, true));
        } else {
            frame.set(anim.getKeyFrame(stateTime, true));
        }

        frame.setSize(distance, 90);
        frame.setOrigin(0,frame.getHeight() / 2f);
        frame.setRotation(angle);
        frame.flip(false, flipY);
        frame.setPosition(originBody.getPosition().x * PPM, originBody.getPosition().y * PPM - frame.getHeight() / 2);

        screen.addSortedSprite(frame);

    }

    public void singleChain() {
        if(player.inventory.equippedHat instanceof Epic_ForkedLightningHat && !fromUniqueHat) {
            inRange = findMonstersInRange(monsterTo.body, 3);
            if(!inRange.isEmpty()) {
                Monster target;
                target = normalTargeting();

                if(target != null) {
                    ForkedLightning_Hit chain = new ForkedLightning_Hit(target);
                    chain.setNextHit(this);
                    chain.setElements(this);
                    screen.spellManager.add(chain);
                }
            }
        }
    }


    public Monster normalTargeting() {
        Collections.shuffle(inRange);
        return inRange.removeFirst();
    }

    public void setNextHit(ForkedLightning_Hit thisHit) {
        fromUniqueHat = true;

        monsterFrom = thisHit.monsterTo;
        originBody = monsterFrom.body;

        fireball = thisHit.fireball;
        chargedbolts = thisHit.chargedbolts;
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
            if(i == 0 && !fromUniqueHat) {
                continue;
            }
            Vector2 position = new Vector2(originBody.getPosition()).add(step.x * i, step.y * i);
            RoundLight light = screen.lightManager.pool.getLight();
            light.setLight(red,green,blue,lightAlpha,23, position);
            light.dimKill(0.1f);
            screen.lightManager.addLight(light);
        }
    }


    public void pickAnim() {

        if(player.inventory.equippedHat instanceof Epic_ForkedLightningHat) {
            anim = ChainLightningAnims.sith_lightning_anim;
            longAnim = ChainLightningAnims.sith_lightning_long_anim;
            red = 1;
            green = 0;
        } else {
            anim = ChainLightningAnims.chainlightning_lightning_anim;
            longAnim = ChainLightningAnims.chainlightning_long_lightning_anim;
            red = 0.85f;
            green = 0.25f;
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
                if (distance <= radius && monster.body != body && monster.hp > 0) {
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

    public void chargedbolts(Monster monster) {
        if(chargedbolts) {
            float procRate = 0.95f - 0.05f * player.spellbook.chargedbolt_lvl;
            int quantity = 3 + player.spellbook.chargedbolt_lvl / 5;
            if (Math.random() >= procRate) {
                for (int i = 0; i < quantity; i++) {
                    ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                    bolt.flamejet = true;
                    bolt.setElements(this);
                    bolt.spawnPosition = new Vector2(monster.body.getPosition());
                    bolt.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 2);
                    screen.spellManager.add(bolt);
                }
            }
        }
    }

    public void fireball(Monster monster) {
        if(fireball) {
            float procRate = 0.98f - 0.02f * player.spellbook.fireball_lvl;
            if(Math.random() >= procRate) {
                Fireball_Explosion explosion = new Fireball_Explosion();
                explosion.targetPosition = new Vector2(monster.body.getPosition());
                explosion.setElements(this);
                explosion.anim_element = FIRELITE;
                explosion.firelite = true;
                screen.spellManager.add(explosion);
            }
        }
    }

}
