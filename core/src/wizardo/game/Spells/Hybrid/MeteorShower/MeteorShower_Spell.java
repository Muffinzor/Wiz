package wizardo.game.Spells.Hybrid.MeteorShower;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Amulet.Epic_StormAmulet;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Player.Levels.LevelUpEnums;
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
    public float frequency;
    public float duration = 4f;

    public boolean thunderstorm;
    public boolean rift;
    public boolean overheat;
    public boolean flamejets;
    public boolean arcaneMissile;

    public float showerRadius = 14;

    public MeteorShower_Spell() {

        multicastable = false;

        string_name = "Meteors";
        levelup_enum = LevelUpEnums.LevelUps.METEORS;

        cooldown = 12;

        speed = 8f;

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
            screen.spellManager.add(meteor);

        }

        if(stateTime >= duration) {
            screen.spellManager.remove(this);
        }

    }

    public Vector2 findTarget() {
        if(thunderstorm || arcaneMissile) {
            return autoTargeting();
        } else {
            return randomTargeting();
        }
    }

    public Vector2 autoTargeting() {
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
            frequency = 1.5f + 0.5f * player.spellbook.thunderstorm_lvl;
        } else if (rift || arcaneMissile) {
            frequency = 1.5f + 0.5f * player.spellbook.rift_lvl;
        }
        frequency = frequency * (1 + player.spellbook.meteors_bonus_frequency/100f);
        interval = 1/frequency;

        duration += player.spellbook.meteors_bonus_duration;
        if(player.inventory.equippedAmulet instanceof Epic_StormAmulet) {
            duration *= 1.3f;
        }

    }

    public void setMeteor(MeteorShower_Spell parentSpell) {
        this.nested_spell = parentSpell.nested_spell;
        this.overheat = parentSpell.overheat;
        this.thunderstorm = parentSpell.thunderstorm;
        this.flamejets = parentSpell.flamejets;
        this.arcaneMissile = parentSpell.arcaneMissile;
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
        int dmg = 50 + 50 * player.spellbook.fireball_lvl;
        return dmg;
    }
}
