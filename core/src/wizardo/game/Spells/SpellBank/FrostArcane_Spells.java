package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Projectile;
import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Spell;
import wizardo.game.Spells.Arcane.Rifts.Rifts_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FROST;
import static wizardo.game.Spells.SpellUtils.Spell_Name.*;

public class FrostArcane_Spells {

    public static Spell[] frostarcaneSpells = new Spell[27];

    public static void createFrostArcane_Spells() {

        // 0. Frostbolt + ArcaneMissile

        Frostbolt_Spell frostarcane0 = new Frostbolt_Spell();
        frostarcane0.missile = true;
        frostarcane0.anim_element = FROST;
        frostarcane0.spellParts.add(FROSTBOLT);
        frostarcane0.spellParts.add(MISSILES);
        frostarcaneSpells[0] = frostarcane0;

        // 1. Frostbolt + Beam

        EnergyBeam_Spell frostarcane1 = new EnergyBeam_Spell();
        frostarcane1.frostbolt = true;
        frostarcane1.anim_element = FROST;
        frostarcane1.spellParts.add(FROSTBOLT);
        frostarcane1.spellParts.add(BEAM);
        frostarcaneSpells[1] = frostarcane1;

        // 2. Frostbolt + Rift

        Rifts_Spell frostarcane2 = new Rifts_Spell();
        frostarcane2.frostbolt = true;
        frostarcane2.anim_element = FROST;
        frostarcane2.spellParts.add(FROSTBOLT);
        frostarcane2.spellParts.add(RIFTS);
        frostarcaneSpells[2] = frostarcane2;




        for (Spell frostfireSpell : frostarcaneSpells) {

            if(frostfireSpell != null && frostfireSpell.bonus_element == null) {

                if (frostfireSpell.main_element == SpellUtils.Spell_Element.ARCANE) {
                    frostfireSpell.bonus_element = FROST;
                } else {
                    frostfireSpell.bonus_element = SpellUtils.Spell_Element.ARCANE;
                }

            }
        }

    }
}
