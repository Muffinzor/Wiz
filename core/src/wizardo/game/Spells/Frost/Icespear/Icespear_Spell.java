package wizardo.game.Spells.Frost.Icespear;

import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Projectile;
import wizardo.game.Spells.Spell;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;

public class Icespear_Spell extends Spell {

    float minimumTimeForSplit = 0.15f;
    public int currentSplits = 0;
    public int maxSplits = 2;
    boolean canSplit;

    int maxCollisions = 5;

    public Icespear_Spell() {

        dmg = 50;
        speed = 400f/PPM;
        cooldown = 1.5f;

    }

    public void update(float delta) {
        stateTime += delta;

        Icespear_Projectile spear = new Icespear_Projectile(getSpawnPosition(), getTargetPosition());
        currentScreen.spellManager.toAdd(spear);
        currentScreen.spellManager.toRemove(this);

    }

    public void setNextSpear(Icespear_Spell spear) {
        minimumTimeForSplit = spear.minimumTimeForSplit;
        maxCollisions = spear.maxCollisions;
        maxSplits = spear.maxSplits;
        castByPawn = spear.castByPawn;
        speed = spear.speed;
    }


    @Override
    public void dispose() {

    }
}
