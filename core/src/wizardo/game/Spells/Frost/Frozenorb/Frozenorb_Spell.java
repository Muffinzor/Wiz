package wizardo.game.Spells.Frost.Frozenorb;

import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class Frozenorb_Spell extends Spell {

    public float duration = 6f;
    public float slowRatio = 0.8f;
    public boolean frostnova;

    public Frozenorb_Spell() {

        name = "Frozen Orb";

        speed = 60f/PPM;
        cooldown = 8f;
        baseDmg = 50;   // per second

        main_element = SpellUtils.Spell_Element.FROST;

    }

    public void update(float delta) {

        Frozenorb_Projectile orb = new Frozenorb_Projectile(getSpawnPosition(), getTargetPosition());
        orb.inherit(this);
        screen.spellManager.toAdd(orb);
        screen.spellManager.toRemove(this);

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.frozenorb_lvl;
    }

    public void inherit(Frozenorb_Spell parent) {
        this.nested_spell = parent.nested_spell;
        this.speed = parent.speed;
        this.duration = parent.duration;
        this.frostnova = parent.frostnova;
        this.lightAlpha = parent.lightAlpha;

        this.setElements(parent);
    }

    @Override
    public int getDmg() {
        int dmg = baseDmg;
        dmg += 10 * getLvl();
        return dmg;
    }

}
