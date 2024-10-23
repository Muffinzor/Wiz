package wizardo.game.Spells.Fire.Flamejet;

import wizardo.game.Spells.Spell;

import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class Flamejet_Spell extends Spell {

    int flames = 2;

    public Flamejet_Spell() {

        name = "Flamejet";

        speed = 20;
        cooldown = 0.5f;
        dmg = 20;

    }

    @Override
    public void update(float delta) {

        if(spawnPosition != null) {
            flames = 1;
        }

        if(delta > 0) {
            Flamejet_Projectile flame = new Flamejet_Projectile(getSpawnPosition(), getTargetPosition());
            currentScreen.spellManager.toAdd(flame);
            currentScreen.spellManager.toRemove(this);
        }

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.flamejet_lvl;
    }
}
