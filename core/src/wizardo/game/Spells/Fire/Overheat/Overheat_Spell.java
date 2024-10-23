package wizardo.game.Spells.Fire.Overheat;

import wizardo.game.Spells.Spell;

import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class Overheat_Spell extends Spell {

    public Overheat_Spell() {

        name = "Overheat";

        radius = 175;
        cooldown = 4f;
        dmg = 100;
    }

    public void update(float delta) {
        stateTime += delta;

        Overheat_Explosion explosion = new Overheat_Explosion(getSpawnPosition());
        currentScreen.spellManager.toAdd(explosion);
        currentScreen.spellManager.toRemove(this);

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.overheat_lvl;
    }
}
