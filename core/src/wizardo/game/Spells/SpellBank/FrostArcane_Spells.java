package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Projectile;
import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Spell;
import wizardo.game.Spells.Arcane.Rifts.Rifts_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Hybrid.Blizzard.Blizzard_Spell;
import wizardo.game.Spells.Hybrid.EnergyRain.EnergyRain_Spell;
import wizardo.game.Spells.Hybrid.Laser.Laser_Spell;
import wizardo.game.Spells.Hybrid.Orbit.Orbit_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
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

        // 3. Icespear + ArcaneMissile

        Icespear_Spell frostarcane3 = new Icespear_Spell();
        frostarcane3.anim_element = ARCANE;
        frostarcane3.arcaneMissile = true;
        frostarcane3.spellParts.add(ICESPEAR);
        frostarcane3.spellParts.add(MISSILES);
        frostarcaneSpells[3] = frostarcane3;

        // 4. Icespear + Beam

        Icespear_Spell frostarcane4 = new Icespear_Spell();
        frostarcane4.anim_element = ARCANE;
        frostarcane4.beam = true;
        frostarcane4.spellParts.add(BEAM);
        frostarcane4.spellParts.add(ICESPEAR);
        frostarcaneSpells[4] = frostarcane4;

        // 5. Icespear + Rift

        Icespear_Spell frostarcane5 = new Icespear_Spell();
        frostarcane5.anim_element = FROST;
        frostarcane5.rift = true;
        frostarcane5.frostbolts = true;
        frostarcane5.spellParts.add(ICESPEAR);
        frostarcane5.spellParts.add(RIFTS);
        frostarcaneSpells[5] = frostarcane5;

        // 6. Frozenorb + Arcane Missile

        Frozenorb_Spell frostarcane6 = new Frozenorb_Spell();
        frostarcane6.anim_element = ARCANE;
        frostarcane6.nested_spell = new ArcaneMissile_Spell();
        frostarcane6.spellParts.add(FROZENORB);
        frostarcane6.spellParts.add(MISSILES);
        frostarcaneSpells[6] = frostarcane6;

        // 7. Frozenorb + EnergyBeam

        EnergyBeam_Spell frostarcane7 = new EnergyBeam_Spell();
        frostarcane7.anim_element = FROST;
        frostarcane7.frozenorb = true;
        frostarcane7.spellParts.add(FROZENORB);
        frostarcane7.spellParts.add(BEAM);
        frostarcaneSpells[7] = frostarcane7;

        // 8. Frozenorb + Rift

        Orbit_Spell frostarcane8 = new Orbit_Spell();
        frostarcane8.spellParts.add(FROZENORB);
        frostarcane8.spellParts.add(RIFTS);
        frostarcaneSpells[8] = frostarcane8;

        // 9. Frostbolt + ArcaneMissile + Beam

        Laser_Spell frostarcane9 = new Laser_Spell();
        frostarcane9.frostbolt = true;
        frostarcane9.spellParts.add(FROSTBOLT);
        frostarcane9.spellParts.add(MISSILES);
        frostarcane9.spellParts.add(BEAM);
        frostarcane9.anim_element = FROST;
        frostarcaneSpells[9] = frostarcane9;

        // 10. Frostbolt + ArcaneMissile + Rifts

        Frostbolt_Spell frostarcane10 = new Frostbolt_Spell();
        frostarcane10.missile = true;
        frostarcane10.rifts = true;
        frostarcane10.anim_element = FROST;
        frostarcane10.spellParts.add(FROSTBOLT);
        frostarcane10.spellParts.add(MISSILES);
        frostarcane10.spellParts.add(RIFTS);
        frostarcaneSpells[10] = frostarcane10;

        // 11. Frostbolt + Beam + Rifts

        EnergyRain_Spell frostarcane11 = new EnergyRain_Spell();
        frostarcane11.frostbolt = true;
        frostarcaneSpells[11] = frostarcane11;






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
