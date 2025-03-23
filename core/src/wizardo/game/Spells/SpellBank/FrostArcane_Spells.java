package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Spell;
import wizardo.game.Spells.Arcane.Rifts.Rifts_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Hybrid.Judgement.Judgement_Spell;
import wizardo.game.Spells.Hybrid.Blizzard.Blizzard_Spell;
import wizardo.game.Spells.Hybrid.EnergyRain.EnergyRain_Spell;
import wizardo.game.Spells.Hybrid.Laser.Laser_Spell;
import wizardo.game.Spells.Hybrid.Orbit.Orbit_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellBank.Arcane_Spells.arcane_spells;
import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Spells.SpellUtils.Spell_Name.*;

public class FrostArcane_Spells {

    public static Spell[] frostarcaneSpells = new Spell[30];

    public static void createFrostArcane_Spells() {

        // 0. Frostbolt + ArcaneMissile

        ArcaneMissile_Spell frostarcane0 = new ArcaneMissile_Spell();
        frostarcane0.anim_element = FROST;
        frostarcane0.frostbolt = true;
        frostarcane0.spellParts.add(MISSILES);
        frostarcane0.spellParts.add(FROSTBOLT);
        frostarcaneSpells[0] = frostarcane0;

        // 1. Frostbolt + Beam

        Frostbolt_Spell frostarcane1 = new Frostbolt_Spell();
        frostarcane1.beam = true;
        frostarcane1.anim_element = ARCANE;
        frostarcane1.spellParts.add(FROSTBOLT);
        frostarcane1.spellParts.add(BEAM);
        frostarcaneSpells[1] = frostarcane1;

        // 2. Frostbolt + Rift

        Frostbolt_Spell frostarcane2 = new Frostbolt_Spell();
        frostarcane2.rifts = true;
        frostarcane2.anim_element = ARCANE;
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
        frostarcane8.anim_element = ARCANE;
        frostarcane8.spellParts.add(FROZENORB);
        frostarcane8.spellParts.add(RIFTS);
        frostarcaneSpells[8] = frostarcane8;

        // 9. Frostbolt + ArcaneMissile + Beam

        Laser_Spell frostarcane9 = new Laser_Spell();
        frostarcane9.anim_element = FROST;
        frostarcane9.frostbolt = true;
        frostarcane9.spellParts.add(FROSTBOLT);
        frostarcane9.spellParts.add(MISSILES);
        frostarcane9.spellParts.add(BEAM);
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
        frostarcane11.anim_element = FROST;
        frostarcane11.frostbolt = true;
        frostarcane11.spellParts.add(FROSTBOLT);
        frostarcane11.spellParts.add(BEAM);
        frostarcane11.spellParts.add(RIFTS);
        frostarcaneSpells[11] = frostarcane11;

        // 12. Frostbolt + Icespear + ArcaneMissile

        Icespear_Spell frostarcane12 = new Icespear_Spell();
        frostarcane12.arcaneMissile = true;
        frostarcane12.frostbolts = true;
        frostarcane12.anim_element = FROST;
        frostarcane12.spellParts.add(FROSTBOLT);
        frostarcane12.spellParts.add(MISSILES);
        frostarcane12.spellParts.add(ICESPEAR);
        frostarcaneSpells[12] = frostarcane12;

        // 13. Frostbolt + Icespear + Beam

        Icespear_Spell frostarcane13 = new Icespear_Spell();
        frostarcane13.beam = true;
        frostarcane13.frostbolts = true;
        frostarcane13.anim_element = FROST;
        frostarcane13.spellParts.add(FROSTBOLT);
        frostarcane13.spellParts.add(BEAM);
        frostarcane13.spellParts.add(ICESPEAR);
        frostarcaneSpells[13] = frostarcane13;

        // 14. Frostbolt + Icespear + Rifts

        Icespear_Spell frostarcane14 = new Icespear_Spell();
        frostarcane14.rift = true;
        frostarcane14.frostbolts = true;
        frostarcane14.anim_element = FROST;
        frostarcane14.spellParts.add(FROSTBOLT);
        frostarcane14.spellParts.add(RIFTS);
        frostarcane14.spellParts.add(ICESPEAR);
        frostarcaneSpells[14] = frostarcane14;

        // 15. Frostbolt + Frozenorb + ArcaneMissile

        Frozenorb_Spell frostarcane15 = new Frozenorb_Spell();
        frostarcane15.nested_spell = frostarcaneSpells[0];
        frostarcane15.anim_element = FROST;
        frostarcane15.spellParts.add(FROSTBOLT);
        frostarcane15.spellParts.add(FROZENORB);
        frostarcane15.spellParts.add(MISSILES);
        frostarcaneSpells[15] = frostarcane15;

        // 16. Frostbolt + Frozenorb + Beam

        EnergyBeam_Spell frostarcane16 = new EnergyBeam_Spell();
        frostarcane16.frozenorb = true;
        frostarcane16.frostbolt = true;
        frostarcane16.anim_element = FROST;
        frostarcane16.spellParts.add(FROSTBOLT);
        frostarcane16.spellParts.add(FROZENORB);
        frostarcane16.spellParts.add(BEAM);
        frostarcaneSpells[16] = frostarcane16;

        // 17. Frostbolt + Frozenorb + Rift

        Orbit_Spell frostarcane17 = new Orbit_Spell();
        frostarcane17.frostbolt = true;
        frostarcane17.anim_element = FROST;
        frostarcane17.spellParts.add(FROSTBOLT);
        frostarcane17.spellParts.add(FROZENORB);
        frostarcane17.spellParts.add(RIFTS);
        frostarcaneSpells[17] = frostarcane17;

        // 18. Icespear + Frozenorb + ArcaneMissiles

        Frozenorb_Spell frostarcane18 = new Frozenorb_Spell();
        frostarcane18.nested_spell = frostarcaneSpells[3];
        frostarcane18.anim_element = ARCANE;
        frostarcane18.spellParts.add(ICESPEAR);
        frostarcane18.spellParts.add(FROZENORB);
        frostarcane18.spellParts.add(MISSILES);
        frostarcaneSpells[18] = frostarcane18;

        // 19. Icespear + Frozenorb + Beam

        Icespear_Spell frostarcane19 = new Icespear_Spell();
        frostarcane19.frozenorb = true;
        frostarcane19.beam = true;
        frostarcane19.anim_element = FROST;
        frostarcane19.spellParts.add(ICESPEAR);
        frostarcane19.spellParts.add(FROZENORB);
        frostarcane19.spellParts.add(BEAM);
        frostarcaneSpells[19] = frostarcane19;

        // 20. Icespear + Frozenorb + Rift

        Frozenorb_Spell frostarcane20 = new Frozenorb_Spell();
        Icespear_Spell spear20 = new Icespear_Spell();
        spear20.maxSplits = 0;
        spear20.duration = 0.5f;
        spear20.rift = true;
        frostarcane20.nested_spell = spear20;
        frostarcane20.anim_element = FROST;
        frostarcane20.spellParts.add(ICESPEAR);
        frostarcane20.spellParts.add(FROZENORB);
        frostarcane20.spellParts.add(RIFTS);
        frostarcaneSpells[20] = frostarcane20;

        // 21. Icespear + ArcaneMissiles + Beam

        Icespear_Spell frostarcane21 = new Icespear_Spell();
        frostarcane21.indestructible = true;
        frostarcane21.arcaneMissile = true;
        frostarcane21.anim_element = ARCANE;
        frostarcane21.speed *= 2f;
        frostarcane21.duration = 2;
        frostarcane21.spellParts.add(ICESPEAR);
        frostarcane21.spellParts.add(MISSILES);
        frostarcane21.spellParts.add(BEAM);
        frostarcaneSpells[21] = frostarcane21;

        // 22. Icespear + ArcaneMissiles + Rift

        Icespear_Spell frostarcane22 = new Icespear_Spell();
        frostarcane22.anim_element = ARCANE;
        frostarcane22.arcaneMissile = true;
        frostarcane22.rift = true;
        frostarcane22.spellParts.add(ICESPEAR);
        frostarcane22.spellParts.add(MISSILES);
        frostarcane22.spellParts.add(RIFTS);
        frostarcaneSpells[22] = frostarcane22;

        // 23. Icespear + Beam + Rift

        Blizzard_Spell frostarcane23 = new Blizzard_Spell();
        frostarcane23.rift = true;
        frostarcane23.anim_element = ARCANE;
        frostarcane23.spellParts.add(ICESPEAR);
        frostarcane23.spellParts.add(BEAM);
        frostarcane23.spellParts.add(RIFTS);
        frostarcaneSpells[23] = frostarcane23;

        // 24. Frozenorb + Arcanemissile + Beam

        Frozenorb_Spell frostarcane24 = new Frozenorb_Spell();
        frostarcane24.nested_spell = arcane_spells[3];
        frostarcane24.anim_element = ARCANE;
        frostarcane24.spellParts.add(FROZENORB);
        frostarcane24.spellParts.add(MISSILES);
        frostarcane24.spellParts.add(BEAM);
        frostarcaneSpells[24] = frostarcane24;

        // 25. Frozenorb + ArcaneMissile + Rift

        Frozenorb_Spell frostarcane25 = new Frozenorb_Spell();
        frostarcane25.nested_spell = arcane_spells[4];
        frostarcane25.anim_element = ARCANE;
        frostarcane25.spellParts.add(FROZENORB);
        frostarcane25.spellParts.add(MISSILES);
        frostarcane25.spellParts.add(RIFTS);
        frostarcaneSpells[25] = frostarcane25;

        // 26. Frozenorb + Beam + Rift

        Judgement_Spell frostarcane26 = new Judgement_Spell();
        frostarcane26.frozenorb = true;
        frostarcane26.rift = true;
        frostarcane26.anim_element = FROST;
        frostarcane26.spellParts.add(FROZENORB);
        frostarcane26.spellParts.add(BEAM);
        frostarcane26.spellParts.add(RIFTS);
        frostarcaneSpells[26] = frostarcane26;

        // 27. Frostbolt + ArcaneMissile

        Frostbolt_Spell frostarcane27 = new Frostbolt_Spell();
        frostarcane27.anim_element = FROST;
        frostarcane27.missile = true;
        frostarcane27.spellParts.add(FROSTBOLT);
        frostarcane27.spellParts.add(MISSILES);
        frostarcaneSpells[27] = frostarcane27;

        // 28. Beam + Frostbolt

        EnergyBeam_Spell frostarcane28 = new EnergyBeam_Spell();
        frostarcane28.frostbolt = true;
        frostarcane28.anim_element = FROST;
        frostarcane28.spellParts.add(BEAM);
        frostarcane28.spellParts.add(FROSTBOLT);
        frostarcaneSpells[28] = frostarcane28;

        // 29. Rift + Frostbolt

        Rifts_Spell frostarcane29 = new Rifts_Spell();
        frostarcane29.frostbolt = true;
        frostarcane29.anim_element = FROST;
        frostarcane29.spellParts.add(RIFTS);
        frostarcane29.spellParts.add(FROSTBOLT);
        frostarcaneSpells[29] = frostarcane29;

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
