package wizardo.game.Spells.Unique.DukeLightning;

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
import wizardo.game.Spells.Hybrid.EnergyRain.ChainLightning_Splash;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class DukeLightningHit extends Spell {

    public int maxHits = 4;
    public int currentHits = 1;
    public float radius = 5;

    boolean alreadyChained;
    public float duration = 0.3f;

    public Monster monsterFrom;
    public Monster monsterTo;

    public ArrayList<Monster> inRange;
    public ArrayList<Monster> monstersHit = new ArrayList<>();

    boolean flipY;

    public Animation<Sprite> longAnim;

    int frameCounter = 0;

    public DukeLightningHit(Monster target) {
        monsterTo = target;
        flipY = MathUtils.randomBoolean();
    }

    public void update(float delta) {
        if(!initialized) {
            if(originBody == null) {
                originBody = player.pawn.body;
            }
            initialized = true;
            pickAnim();
            if(!monsterTo.elite) {
                monsterTo.hp = 0;
            } else {
                monsterTo.hp -= 50 * player.level;
            }
        }

        if(currentHits < maxHits && !alreadyChained && stateTime > 0.03f) {
            alreadyChained = true;
            inRange = findMonstersInRange(monsterTo.body, radius);
            singleChain();
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

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }

    @Override
    public int getDmg() {
        return 0;
    }

    public void singleChain() {
        Monster target;
        target = normalTargeting();

        if(target != null) {
            DukeLightningHit chain = new DukeLightningHit(target);
            chain.setNextChain(this);
            monstersHit.add(target);
            chain.monstersHit = monstersHit;
            screen.spellManager.add(chain);
        }
    }

    public Monster normalTargeting() {
        if(!inRange.isEmpty()) {
            Collections.shuffle(inRange);
            return inRange.removeFirst();
        } else {
            return null;
        }
    }

    public void setNextChain(DukeLightningHit thisHit) {
        currentHits = thisHit.currentHits + 1;
        monsterFrom = thisHit.monsterTo;
        originBody = monsterFrom.body;
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
            Vector2 position = new Vector2(originBody.getPosition()).add(step.x * i, step.y * i);
            RoundLight light = screen.lightManager.pool.getLight();
            light.setLight(red,green,blue,lightAlpha,30, position);
            light.dimKill(0.1f);
            screen.lightManager.addLight(light);
        }
    }



    public void pickAnim() {
        anim = ChainLightningAnims.chainlightning_lightning_anim;
        longAnim = ChainLightningAnims.chainlightning_long_lightning_anim;
        green = 0.6f;

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

}
