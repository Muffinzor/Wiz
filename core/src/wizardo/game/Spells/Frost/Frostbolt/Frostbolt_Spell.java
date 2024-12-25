package wizardo.game.Spells.Frost.Frostbolt;

import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Frostbolt_Spell extends Spell {

    public int projectiles = 3;

    public boolean missile;
    public boolean rifts;
    public boolean superBolt; // Frostbolt + Fireball + Overheat

    public Frostbolt_Spell() {

        name = "Frostbolts";

        speed = 7;
        radius = 25;
        baseDmg = 24;
        cooldown = 0.8f;

        main_element = SpellUtils.Spell_Element.FROST;

    }

    @Override
    public void update(float delta) {

        if(targetPosition != null) {
            projectiles = 1;
        } else {
            projectiles = 2 + getLvl()/2;
        }

        for (int i = 0; i < projectiles; i++) {
            Frostbolt_Projectile bolt = new Frostbolt_Projectile(getSpawnPosition(), getTargetPosition());
            bolt.inherit(this);
            bolt.missile = missile;
            bolt.rifts = rifts;
            bolt.superBolt = superBolt;
            screen.spellManager.toAdd(bolt);
            screen.spellManager.toRemove(this);
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
        dmg += 6 * getLvl();
        if(superBolt) {
            dmg += 5 * player.spellbook.fireball_lvl;
        }
        dmg = (int) (dmg * (1 + player.spellbook.explosivesBonusDmg/100f));
        return dmg;
    }

    public void inherit(Frostbolt_Spell parent) {
        this.screen = parent.screen;
        this.setElements(parent);
    }
}
