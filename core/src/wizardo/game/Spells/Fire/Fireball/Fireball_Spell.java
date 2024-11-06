package wizardo.game.Spells.Fire.Fireball;

import wizardo.game.Spells.Spell;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class Fireball_Spell extends Spell {

    public boolean frostbolts;
    public boolean frozenorb;

    public Fireball_Spell() {

        name = "Fireball";

        baseDmg = 50;
        cooldown = 2;
        radius = 45;
        speed = 200f/PPM;

        main_element = FIRE;

    }

    @Override
    public void update(float delta) {
        stateTime += delta;

        Fireball_Projectile ball = new Fireball_Projectile(getSpawnPosition(), getTargetPosition());
        ball.inherit(this);
        screen.spellManager.toAdd(ball);
        screen.spellManager.toRemove(this);
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.fireball_lvl;
    }

    public void inherit(Fireball_Spell parent) {
        this.frostbolts = parent.frostbolts;
        this.frozenorb = parent.frozenorb;

        this.nested_spell = parent.nested_spell;

        setElements(parent);
        this.screen = parent.screen;
    }

    @Override
    public int getDmg() {
        int dmg = baseDmg;
        dmg += 15 * getLvl();
        return dmg;
    }

}
