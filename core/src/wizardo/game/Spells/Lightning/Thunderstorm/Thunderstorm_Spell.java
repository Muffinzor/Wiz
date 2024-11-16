package wizardo.game.Spells.Lightning.Thunderstorm;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class Thunderstorm_Spell extends Spell {

    public float interval;
    public float frequency = 10f;
    public float duration = 4f;

    public ArrayList<Monster> inRange;

    public boolean arcaneMissile;
    public boolean rifts;

    public Thunderstorm_Spell() {

        name = "Thunderstorm";

        interval = 1/frequency;
        cooldown = 8f;
        baseDmg = 60;
        radius = 16;

        main_element = SpellUtils.Spell_Element.LIGHTNING;
    }


    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            setup();
        }

        stateTime += delta;

        if(stateTime % interval < delta) {
            Vector2 target;
            if(arcaneMissile) {
                target = arcaneMissileTargeting();
            } else if (rifts) {
                target = riftTargeting();
            } else {
                target = randomTargeting();
            }

            if(target != null) {
                Thunderstorm_Hit thunder = new Thunderstorm_Hit(target);
                thunder.setElements(this);
                thunder.rifts = rifts;
                thunder.nested_spell = nested_spell;
                screen.spellManager.toAdd(thunder);
            }
        }

        if(stateTime >= duration) {
            currentScreen.spellManager.toRemove(this);
        }

    }

    /** any monster at random within radius */
    public Vector2 randomTargeting() {
        Vector2 target = null;
        inRange = SpellUtils.findMonstersInRangeOfVector(getSpawnPosition(), radius, false);
        if(!inRange.isEmpty()) {
            int attempts = 0;
            while(attempts < 10) {
                int index = (int) (Math.random() * inRange.size());
                attempts++;
                if(inRange.get(index).thunderImmunityTimer <= 0) {
                    target = inRange.get(index).body.getPosition();
                    break;
                }
            }
        }
        return target;
    }

    /** tries to target higher hp monsters */
    public Vector2 arcaneMissileTargeting() {
        Vector2 target = null;
        inRange = SpellUtils.findMonstersInRangeOfVector(getSpawnPosition(), radius, false);
        if(!inRange.isEmpty()) {
            inRange.sort((m1, m2) -> Float.compare(m2.hp, m1.hp));
            int attempts = 0;
            while(attempts < 10) {
                int index = MathUtils.random(0, Math.min(inRange.size() - 1, 5));
                attempts++;
                if(inRange.get(index).thunderImmunityTimer <= 0) {
                    target = inRange.get(index).body.getPosition();
                    break;
                }
            }
        }
        return target;
    }

    public Vector2 riftTargeting() {
        Vector2 target = SpellUtils.getClearRandomPosition(spawnPosition, 3.5f);
        return target;
    }



    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.thunderstorm_lvl;
    }

    @Override
    public int getDmg() {
        return baseDmg + 30 * getLvl();
    }

    public void setup() {
        frequency = 5 * (0.9f + getLvl()/10f);
        interval = 1/frequency;
        if(rifts) {
            spawnPosition = getTargetPosition();
            radius = 3.5f;
        }
    }

}
