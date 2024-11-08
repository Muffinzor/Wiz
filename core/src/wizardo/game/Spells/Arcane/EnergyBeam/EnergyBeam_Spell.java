package wizardo.game.Spells.Arcane.EnergyBeam;

import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class EnergyBeam_Spell extends Spell {

    public boolean frostbolt;
    public boolean frozenorb;

    public EnergyBeam_Spell() {

        name = "Energy Beam";

        baseDmg = 80;

        cooldown = 2.5f;

        main_element = SpellUtils.Spell_Element.ARCANE;

    }

    @Override
    public void update(float delta) {

        if(delta > 0) {

            EnergyBeam_Projectile beam = new EnergyBeam_Projectile(getSpawnPosition(), getTargetPosition());
            beam.frostbolt = frostbolt;
            beam.frozenorb = frozenorb;
            beam.setElements(this);
            currentScreen.spellManager.toAdd(beam);
            currentScreen.spellManager.toRemove(this);

        }

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.energybeam_lvl;
    }

    @Override
    public int getDmg() {
        int dmg = baseDmg;
        dmg += getLvl() * 20;
        return dmg;
    }
}
