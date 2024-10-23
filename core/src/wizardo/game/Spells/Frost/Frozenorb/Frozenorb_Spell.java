package wizardo.game.Spells.Frost.Frozenorb;

import wizardo.game.Spells.Spell;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class Frozenorb_Spell extends Spell {

    public float duration = 6f;

    public Frozenorb_Spell() {

        name = "Frozen Orb";

        speed = 60f/PPM;
        cooldown = 8f;
        dmg = 1;
    }

    public void update(float delta) {
        if(delta > 0) {
            Frozenorb_Projectile orb = new Frozenorb_Projectile(getSpawnPosition(), getTargetPosition());
            orb.nested_spell = nested_spell;
            orb.setElements(this);
            orb.speed = speed;
            orb.duration = duration;
            currentScreen.spellManager.toAdd(orb);
            currentScreen.spellManager.toRemove(this);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.frozenorb_lvl;
    }

}
