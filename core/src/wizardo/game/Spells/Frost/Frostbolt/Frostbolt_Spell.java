package wizardo.game.Spells.Frost.Frostbolt;

import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class Frostbolt_Spell extends Spell {

    public int projectiles = 3;

    public boolean missile;

    public Frostbolt_Spell() {

        name = "Frostbolts";

        speed = 7;
        radius = 25;
        baseDmg = 15;
        cooldown = 1f;

        main_element = SpellUtils.Spell_Element.FROST;

    }

    @Override
    public void update(float delta) {

        if(targetPosition != null) {
            projectiles = 1;
        }

        for (int i = 0; i < projectiles; i++) {
            Frostbolt_Projectile bolt = new Frostbolt_Projectile(getSpawnPosition(), getTargetPosition());
            bolt.inherit(this);
            bolt.missile = missile;
            currentScreen.spellManager.toAdd(bolt);
            currentScreen.spellManager.toRemove(this);
        }

    }

    public void dispose() {

    }

    public int getLvl() {
        return player.spellbook.frostbolt_lvl;
    }

    @Override
    public int getDmg() {
        int dmg = baseDmg;
        dmg += 5 * getLvl();
        return dmg;
    }

    public void inherit(Frostbolt_Spell parent) {
        this.screen = parent.screen;
        this.setElements(parent);
    }
}
