package wizardo.game.Spells.Hybrid.RepulsionField;

import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class RepulsionField_Spell extends Spell {

    public boolean fireball;
    public boolean arcaneMissile;


    public RepulsionField_Spell() {

        name = "Repulsion Field";

        cooldown = 3.2f;

        anim_element = SpellUtils.Spell_Element.ARCANE;

        dmg = 10;

    }

    @Override
    public void update(float delta) {

        RepulsionField_Explosion explosion = new RepulsionField_Explosion();
        explosion.fireball = fireball;
        explosion.arcaneMissile = arcaneMissile;
        screen.spellManager.add(explosion);

        screen.spellManager.remove(this);
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }

    @Override
    public int getDmg() {
        int dmg = this.dmg + 10 * player.spellbook.rift_lvl;
        dmg = (int) (dmg * (1 + player.spellbook.gravityBonusDmg/100f));
        return dmg;
    }
}
