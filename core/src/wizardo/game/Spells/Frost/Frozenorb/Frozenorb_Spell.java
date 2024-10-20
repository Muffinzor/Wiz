package wizardo.game.Spells.Frost.Frozenorb;

import wizardo.game.Spells.Spell;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;

public class Frozenorb_Spell extends Spell {

    public float duration = 6f;

    public Frozenorb_Spell() {
        speed = 60f/PPM;
        cooldown = 8f;
        dmg = 1;
    }

    public void update(float delta) {
        Frozenorb_Projectile orb = new Frozenorb_Projectile(getSpawnPosition(), getTargetPosition());
        currentScreen.spellManager.toAdd(orb);
        currentScreen.spellManager.toRemove(this);
    }

    @Override
    public void dispose() {

    }

}
