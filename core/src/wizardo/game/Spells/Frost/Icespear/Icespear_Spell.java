package wizardo.game.Spells.Frost.Icespear;

import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class Icespear_Spell extends Spell {

    float minimumTimeForSplit = 0.15f;
    public int currentSplits = 0;
    public int maxSplits = 2;
    boolean canSplit;

    int maxCollisions = 5;

    public boolean celestialStrike;

    public boolean flamejet;
    public boolean frostbolts;
    public boolean fireball;
    public boolean overheat; //for Icespear+Overheat+Fireball

    public Icespear_Spell() {

        name = "Ice Spear";

        baseDmg = 32;
        speed = 400f/PPM;
        cooldown = 1.5f;

        main_element = SpellUtils.Spell_Element.FROST;

    }

    public void update(float delta) {
        stateTime += delta;
        setup();

        Icespear_Projectile spear = new Icespear_Projectile(getSpawnPosition(), getTargetPosition());
        spear.inherit(this);
        screen.spellManager.toAdd(spear);
        screen.spellManager.toRemove(this);
    }

    public void setNextSpear(Icespear_Spell spear) {
        minimumTimeForSplit = spear.minimumTimeForSplit;
        maxCollisions = spear.maxCollisions;
        maxSplits = spear.maxSplits;
        castByPawn = spear.castByPawn;
        speed = spear.speed;

        this.inherit(spear);
    }


    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.icespear_lvl;
    }

    public void inherit(Icespear_Spell parent) {
        this.nested_spell = parent.nested_spell;
        this.fireball = parent.fireball;
        this.overheat = parent.overheat;
        this.celestialStrike = parent.celestialStrike;
        this.flamejet = parent.flamejet;
        this.frostbolts = parent.frostbolts;
        this.setElements(parent);

        this.maxSplits = parent.maxSplits;
    }

    @Override
    public int getDmg() {
        int dmg = baseDmg;
        dmg += 8 * getLvl();
        return dmg;
    }


    public void setup() {

    }
}
