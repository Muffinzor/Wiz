package wizardo.game.Spells.Hybrid.MeteorShower;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Wizardo.player;

public class MeteorShower_Spell extends Spell {

    public float shower_radius;
    public ArrayList<Monster> inRange;

    public float interval;
    public float frequency = 10;
    public float duration = 4f;

    public boolean thunderstorm;
    public boolean rift;
    public boolean overheat;
    public boolean flamejets;

    public float showerRadius = 12;

    public MeteorShower_Spell() {
        name = "Meteor Shower";

        cooldown = 16;

        speed = 8f;

        main_element = FIRE;
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            setup();
            initialized = true;
        }

        stateTime += delta;

        if(stateTime % interval < delta) {


            Vector2 target = findTarget();
            Meteor_Projectile meteor = new Meteor_Projectile(target);
            meteor.setElements(this);
            meteor.setMeteor(this);
            screen.spellManager.toAdd(meteor);


        }

        if(stateTime >= duration) {
            screen.spellManager.toRemove(this);
        }

    }

    public Vector2 findTarget() {
        if(thunderstorm) {
            return thunderTargeting();
        } else {
            return randomTargeting();
        }
    }

    public Vector2 thunderTargeting() {
        Vector2 target;
        inRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), showerRadius, false);
        if(!inRange.isEmpty()) {
            Collections.shuffle(inRange);
            target = inRange.getFirst().body.getPosition();
        } else {
            target = SpellUtils.getClearRandomPosition(player.pawn.body.getPosition(), showerRadius);
            if(target == null) {
                target = SpellUtils.getRandomVectorInRadius(player.pawn.body.getPosition(), showerRadius);
            }
        }
        return target;
    }

    public Vector2 randomTargeting() {
        return SpellUtils.getClearRandomPosition(player.pawn.getPosition(), showerRadius);
    }

    public void setup() {
        if(thunderstorm) {
            duration = 5;
            frequency = 1.8f + 0.4f * player.spellbook.thunderstorm_lvl;
            showerRadius += player.spellbook.thunderstorm_lvl * 0.75f;
            main_element = LIGHTNING;
            bonus_element = FIRE;
        } else if (rift) {
            duration = 8;
            frequency = 1.8f + 0.4f * player.spellbook.rift_lvl;
            showerRadius += player.spellbook.rift_lvl * 0.75f;
            main_element = FIRE;
            bonus_element = ARCANE;
        }
        interval = 1/frequency;
    }

    public void setMeteor(MeteorShower_Spell parentSpell) {
        this.nested_spell = parentSpell.nested_spell;
        this.overheat = parentSpell.overheat;
        this.thunderstorm = parentSpell.thunderstorm;
        this.flamejets = parentSpell.flamejets;
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
        int dmg = 80 + 20 * player.spellbook.fireball_lvl;
        return dmg;
    }
}
