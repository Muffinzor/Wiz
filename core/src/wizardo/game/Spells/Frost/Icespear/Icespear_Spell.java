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
    public float duration = 1.5f;
    public boolean indestructible;


    public boolean celestialStrike;
    public boolean arcaneMissile;
    public boolean flamejet;
    public boolean frostbolts;
    public boolean fireball;
    public boolean beam;
    public boolean rift;
    public boolean overheat;
    public boolean overheatBall; //for Icespear+Overheat+Fireball
    public boolean frozenorb; // for Icespear+Frozenorb+Beam
    public boolean thunderspear; // for Icespear+Thunderstorm+Chargedbolts;

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
        spear.setNextSpear(this);
        screen.spellManager.toAdd(spear);
        screen.spellManager.toRemove(this);
    }




    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.icespear_lvl;
    }

    public void setNextSpear(Icespear_Spell parent) {
        this.nested_spell = parent.nested_spell;
        this.arcaneMissile = parent.arcaneMissile;
        this.fireball = parent.fireball;
        this.overheatBall = parent.overheatBall;
        this.frozenorb = parent.frozenorb;
        this.overheat = parent.overheat;
        this.celestialStrike = parent.celestialStrike;
        this.flamejet = parent.flamejet;
        this.beam = parent.beam;
        this.rift = parent.rift;
        this.thunderspear = parent.thunderspear;
        this.frostbolts = parent.frostbolts;
        this.setElements(parent);
        this.duration = parent.duration;

        this.maxSplits = parent.maxSplits;
        this.indestructible = parent.indestructible;
        this.speed = parent.speed;
        this.maxCollisions = parent.maxCollisions;
        this.minimumTimeForSplit = parent.minimumTimeForSplit;
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
