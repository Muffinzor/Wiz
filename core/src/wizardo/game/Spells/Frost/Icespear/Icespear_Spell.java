package wizardo.game.Spells.Frost.Icespear;

import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Projectile;
import wizardo.game.Spells.Spell;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class Icespear_Spell extends Spell {

    float minimumTimeForSplit = 0.15f;
    public int currentSplits = 0;
    public int maxSplits = 2;
    boolean canSplit;

    int maxCollisions = 5;

    public boolean celestialStrike;

    public Icespear_Spell() {

        name = "Ice Spear";

        dmg = 50;
        speed = 400f/PPM;
        cooldown = 1.5f;

    }

    public void update(float delta) {
        stateTime += delta;


        if(delta > 0) {
            Icespear_Projectile spear = new Icespear_Projectile(getSpawnPosition(), getTargetPosition());
            spear.nested_spell = nested_spell;
            spear.celestialStrike = celestialStrike;
            spear.setElements(this);
            currentScreen.spellManager.toAdd(spear);
            currentScreen.spellManager.toRemove(this);
        }

    }

    public void setNextSpear(Icespear_Spell spear) {
        minimumTimeForSplit = spear.minimumTimeForSplit;
        maxCollisions = spear.maxCollisions;
        maxSplits = spear.maxSplits;
        castByPawn = spear.castByPawn;
        speed = spear.speed;
        celestialStrike = spear.celestialStrike;

        nested_spell = spear.nested_spell;
        setElements(spear);
    }


    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.icespear_lvl;
    }
}
