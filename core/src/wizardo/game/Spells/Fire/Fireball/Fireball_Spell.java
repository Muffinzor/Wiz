package wizardo.game.Spells.Fire.Fireball;

import wizardo.game.Spells.Spell;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;

public class Fireball_Spell extends Spell {

    public Fireball_Spell() {

        dmg = 60;
        cooldown = 2;
        radius = 75;
        speed = 200f/PPM;

    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        Fireball_Projectile ball = new Fireball_Projectile(getSpawnPosition(), getTargetPosition());
        currentScreen.spellManager.toAdd(ball);
        currentScreen.spellManager.toRemove(this);
    }

    @Override
    public void dispose() {

    }

}
