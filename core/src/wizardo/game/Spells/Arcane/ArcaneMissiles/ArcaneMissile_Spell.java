package wizardo.game.Spells.Arcane.ArcaneMissiles;

import wizardo.game.Spells.Spell;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class ArcaneMissile_Spell extends Spell {

    public ArcaneMissile_Spell() {

        name = "Arcane Missiles";

        dmg = 35;
        speed = 225f/PPM;
        cooldown = 0.75f;

    }

    @Override
    public void update(float delta) {

        if(delta > 0) {

            int missiles = 2;

            for (int i = 0; i < missiles; i++) {
                ArcaneMissile_Projectile missile = new ArcaneMissile_Projectile(getSpawnPosition(), getTargetPosition());
                currentScreen.spellManager.toAdd(missile);
            }
            currentScreen.spellManager.toRemove(this);

        }

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.arcanemissile_lvl;
    }
}
