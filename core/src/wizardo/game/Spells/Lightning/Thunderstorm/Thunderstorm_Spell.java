package wizardo.game.Spells.Lightning.Thunderstorm;

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

    public Thunderstorm_Spell() {

        name = "Thunderstorm";

        interval = 1/frequency;

        cooldown = 8f;

        baseDmg = 60;

        main_element = SpellUtils.Spell_Element.LIGHTNING;
    }


    @Override
    public void update(float delta) {

        stateTime += delta;

        if(stateTime % interval < delta) {

            inRange = SpellUtils.findMonstersInRangeOfVector(getSpawnPosition(), 15);
            if(!inRange.isEmpty()) {

                Collections.shuffle(inRange);

                int hits = 0;

                for(Monster monster : inRange) {

                    if(monster.thunderImmunityTimer <= 0) {
                        Thunderstorm_Hit thunder = new Thunderstorm_Hit(monster.body.getPosition());
                        thunder.setElements(this);
                        thunder.screen = currentScreen;
                        thunder.nested_spell = nested_spell;
                        monster.thunderImmunityTimer = 0.5f;
                        currentScreen.spellManager.toAdd(thunder);
                        hits++;

                        if(hits > 0) {
                            break;
                        }
                    }
                }


            }

        }

        if(stateTime >= duration) {
            currentScreen.spellManager.toRemove(this);
        }

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
        int dmg = baseDmg;
        dmg += 30 * getLvl();
        return dmg;
    }
}
