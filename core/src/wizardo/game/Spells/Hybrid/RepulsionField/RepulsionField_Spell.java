package wizardo.game.Spells.Hybrid.RepulsionField;

import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

public class RepulsionField_Spell extends Spell {

    public boolean fireball;
    public boolean arcaneMissile;


    public RepulsionField_Spell() {

        name = "Repulsion Field";

        cooldown = 2.5f;

        anim_element = SpellUtils.Spell_Element.ARCANE;

    }

    @Override
    public void update(float delta) {

        RepulsionField_Explosion explosion = new RepulsionField_Explosion();
        explosion.fireball = fireball;
        explosion.arcaneMissile = arcaneMissile;
        screen.spellManager.toAdd(explosion);

        screen.spellManager.toRemove(this);
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
        return 0;
    }
}
