package wizardo.game.Spells.Frost.Frostbolt;

import wizardo.game.Spells.Spell;

import static wizardo.game.Wizardo.currentScreen;

public class Frostbolt_Spell extends Spell {

    public int projectiles = 3;

    public Frostbolt_Spell() {

        speed = 7;
        radius = 25;
        dmg = 15;
        cooldown = 1f;

    }

    @Override
    public void update(float delta) {

        for (int i = 0; i < projectiles; i++) {
            Frostbolt_Projectile bolt = new Frostbolt_Projectile(getSpawnPosition(), getTargetPosition());
            currentScreen.spellManager.toAdd(bolt);
            currentScreen.spellManager.toRemove(this);
        }

    }

    public void dispose() {

    }
}
