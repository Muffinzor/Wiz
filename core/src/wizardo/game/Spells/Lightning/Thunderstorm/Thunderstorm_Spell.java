package wizardo.game.Spells.Lightning.Thunderstorm;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Amulet.Epic_StormAmulet;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;

public class Thunderstorm_Spell extends Spell {

    public float interval;
    public float hits_per_second = 2.5f;
    public float duration = 4f;

    public ArrayList<Monster> inRange;

    public boolean arcaneMissile; // reduced immunityTimer
    public boolean rifts;
    public boolean overheat;
    public boolean frostbolts;

    int thunderSent;

    public Thunderstorm_Spell() {
        string_name = "Thunderstorm";
        levelup_enum = LevelUpEnums.LevelUps.THUNDERSTORM;
        cooldown = 8f;
        dmg = 60;
        radius = 14;

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
            thunderSent++;
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
                thunder.overheat = overheat;
                thunder.arcaneMissile = arcaneMissile;
                thunder.nested_spell = nested_spell;
                thunder.frostbolts = frostbolts;
                screen.spellManager.add(thunder);
            }
        }

        if(stateTime >= duration) {
            screen.spellManager.remove(this);

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
                if(inRange.get(index).thunderImmunityTimer <= 0 && inRange.get(index).hp > 0) {
                    target = inRange.get(index).body.getPosition();
                    inRange.get(index).thunderImmunityTimer = 0.5f;
                    break;
                }
            }
        }
        return target;
    }

    /** tries to target higher hp monsters */
    public Vector2 arcaneMissileTargeting() {
        Vector2 target = null;
        if(!rifts)
            inRange = SpellUtils.findMonstersInRangeOfVector(getSpawnPosition(), radius, false);
        if(!inRange.isEmpty()) {
            System.out.println("ARCANE TARGET");
            inRange.sort((m1, m2) -> Float.compare(m2.hp, m1.hp));
            int attempts = 0;
            while(attempts < 10) {
                int index = MathUtils.random(0, Math.min(inRange.size() - 1, Math.max(0, 4 - player.spellbook.arcanemissile_lvl)));
                attempts++;
                if(inRange.get(index).thunderImmunityTimer <= 0 && inRange.get(index).hp > 0) {
                    target = inRange.get(index).body.getPosition();
                    inRange.get(index).thunderImmunityTimer = 0.5f - 0.1f * player.spellbook.arcanemissile_lvl;
                    break;
                }
            }
        }
        return target;
    }

    public Vector2 riftTargeting() {
        Vector2 target = null;
        if(!inRange.isEmpty()) {
            int attempts = 0;
            while(attempts < 10) {
                int index = (int) (Math.random() * inRange.size());
                attempts++;
                if(inRange.get(index).thunderImmunityTimer <= 0 && inRange.get(index).hp > 0) {
                    target = inRange.get(index).body.getPosition();
                    inRange.get(index).thunderImmunityTimer = 0.5f;
                    break;
                }
            }
        }
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
        int dmg = this.dmg + 30 * getLvl();
        return dmg;
    }

    public void setup() {
        hits_per_second += 0.5f * player.spellbook.thunderstorm_lvl;
        hits_per_second = hits_per_second * (1 + player.spellbook.empyreanFrequencyBonus/100f);
        interval = 1/hits_per_second;
        if(rifts) {
            spawnPosition = getTargetPosition();
            inRange = SpellUtils.findMonstersInRangeOfVector(spawnPosition, 6, false);
            if(inRange.isEmpty()) {
                inRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), 14, false);
            }
        }
        if(player.inventory.equippedAmulet instanceof Epic_StormAmulet) {
            duration *= 1.3f;
        }
    }
}
