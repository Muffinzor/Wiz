package wizardo.game.Spells.Frost.Frozenorb;

import wizardo.game.Items.Equipment.Staff.Epic_FrozenorbStaff;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Constants.PPM;
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
        autoaimable = true;

        main_element = SpellUtils.Spell_Element.FROST;

    }

    public void setup() {
        if(player.inventory.equippedStaff instanceof Epic_FrozenorbStaff) {
            speed = speed * (2/3f);
            duration = duration * (2/3f);
        }
    }

    public void update(float delta) {

        setup();

        autoAimCheck();

        if(targetPosition == null) {
            return;
        }

        Frozenorb_Projectile orb = new Frozenorb_Projectile(getSpawnPosition(), targetPosition);
        orb.inherit(this);
        screen.spellManager.add(orb);
        screen.spellManager.remove(this);

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
        dmg = (int) (dmg * (1 + player.spellbook.gravityBonusDmg/100f));
        if(player.inventory.equippedStaff instanceof Epic_FrozenorbStaff) {
            dmg = (dmg * 150)/100;
        }
        return dmg;
    }

    public float getCooldown() {
        if(player.inventory.equippedStaff instanceof Epic_FrozenorbStaff) {
            return cooldown * (2/3f);
        }
        return cooldown;
    }

}
