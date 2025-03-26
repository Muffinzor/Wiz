package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Spell;
import wizardo.game.Spells.Arcane.Rifts.Rifts_Spell;
import wizardo.game.Spells.Fire.Fireball.Fireball_Spell;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_Spell;
import wizardo.game.Spells.Hybrid.Judgement.Judgement_Spell;
import wizardo.game.Spells.Hybrid.DragonBreath.DragonBreath_Spell;
import wizardo.game.Spells.Hybrid.EnergyRain.EnergyRain_Spell;
import wizardo.game.Spells.Hybrid.Laser.Laser_Spell;
import wizardo.game.Spells.Hybrid.MeteorShower.MeteorShower_Spell;
import wizardo.game.Spells.Hybrid.RepulsionField.RepulsionField_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Spells.SpellUtils.Spell_Name.*;

public class FireArcane_Spells {

    public static Spell[] firearcaneSpells = new Spell[27];

    public static void createFireArcane_Spells() {

        // 0. ArcaneMissile + Flamejet

        Flamejet_Spell firearcane0 = new Flamejet_Spell();
        firearcane0.anim_element = ARCANE;
        firearcane0.arcaneMissile = true;
        firearcane0.spellParts.add(MISSILES);
        firearcane0.spellParts.add(FLAMEJET);
        firearcaneSpells[0] = firearcane0;

        // 1. ArcaneMissile + Fireball

        Fireball_Spell firearcane1 = new Fireball_Spell();
        firearcane1.anim_element = ARCANE;
        firearcane1.nested_spell = new ArcaneMissile_Spell();
        firearcane1.spellParts.add(FIREBALL);
        firearcane1.spellParts.add(MISSILES);
        firearcaneSpells[1] = firearcane1;

        // 2. ArcaneMissile + Overheat

        ArcaneMissile_Spell firearcane2 = new ArcaneMissile_Spell();
        firearcane2.anim_element = FIRE;
        firearcane2.overheat = true;
        firearcane2.spellParts.add(MISSILES);
        firearcane2.spellParts.add(OVERHEAT);
        firearcaneSpells[2] = firearcane2;

        // 3. EnergyBeam + Flamejet

        EnergyBeam_Spell firearcane3 = new EnergyBeam_Spell();
        firearcane3.anim_element = FIRE;
        firearcane3.flamejet = true;
        firearcane3.spellParts.add(BEAM);
        firearcane3.spellParts.add(FLAMEJET);
        firearcaneSpells[3] = firearcane3;

        // 4. EnergyBeam + Fireball

        EnergyBeam_Spell firearcane4 = new EnergyBeam_Spell();
        firearcane4.anim_element = FIRE;
        firearcane4.fireball = true;
        firearcane4.spellParts.add(BEAM);
        firearcane4.spellParts.add(FIREBALL);
        firearcaneSpells[4] = firearcane4;

        // 5. EnergyBeam + Overheat

        Judgement_Spell firearcane5 = new Judgement_Spell();
        firearcane5.anim_element = FIRE;
        firearcane5.spellParts.add(BEAM);
        firearcane5.spellParts.add(OVERHEAT);
        firearcaneSpells[5] = firearcane5;

        // 6. Rifts + Flamejet

        Rifts_Spell firearcane6 = new Rifts_Spell();
        firearcane6.flamejet = true;
        firearcane6.nested_spell = new Flamejet_Spell();
        firearcane6.anim_element = FIRE;
        firearcane6.spellParts.add(FLAMEJET);
        firearcane6.spellParts.add(RIFTS);
        firearcaneSpells[6] = firearcane6;

        // 7. Rifts + Fireball

        MeteorShower_Spell firearcane7 = new MeteorShower_Spell();
        firearcane7.anim_element = FIRE;
        firearcane7.rift = true;
        firearcane7.spellParts.add(FIREBALL);
        firearcane7.spellParts.add(RIFTS);
        firearcaneSpells[7] = firearcane7;

        // 8. Rifts + Overheat

        RepulsionField_Spell firearcane8 = new RepulsionField_Spell();
        firearcane8.spellParts.add(OVERHEAT);
        firearcane8.spellParts.add(RIFTS);
        firearcaneSpells[8] = firearcane8;

        // 9. Flamejet + Fireball + ArcaneMissile

        Fireball_Spell firearcane9 = new Fireball_Spell();
        firearcane9.anim_element = ARCANE;
        firearcane9.flamejets = true;
        firearcane9.nested_spell = new ArcaneMissile_Spell();
        firearcane9.spellParts.add(FIREBALL);
        firearcane9.spellParts.add(FLAMEJET);
        firearcane9.spellParts.add(MISSILES);
        firearcaneSpells[9] = firearcane9;

        // 10. Flamejet + Fireball + Beam

        EnergyBeam_Spell firearcane10 = new EnergyBeam_Spell();
        firearcane10.anim_element = FIRE;
        firearcane10.fireball = true;
        firearcane10.flamejet = true;
        firearcane10.spellParts.add(BEAM);
        firearcane10.spellParts.add(FIREBALL);
        firearcane10.spellParts.add(FLAMEJET);
        firearcaneSpells[10] = firearcane10;

        // 11. Flamejet + Fireball + Rifts

        Fireball_Spell firearcane11 = new Fireball_Spell();
        firearcane11.flameRift = true;
        firearcane11.anim_element = FIRE;
        firearcane11.spellParts.add(RIFTS);
        firearcane11.spellParts.add(FIREBALL);
        firearcane11.spellParts.add(FLAMEJET);
        firearcaneSpells[11] = firearcane11;

        // 12. Flamejet + Overheat + ArcaneMissile

        ArcaneMissile_Spell firearcane12 = new ArcaneMissile_Spell();
        firearcane12.anim_element = FIRE;
        firearcane12.overheat = true;
        firearcane12.flamejet = true;
        firearcane12.spellParts.add(MISSILES);
        firearcane12.spellParts.add(OVERHEAT);
        firearcane12.spellParts.add(FLAMEJET);
        firearcaneSpells[12] = firearcane12;

        // 13. FLamejet + Overheat + Beam

        Overheat_Spell firearcane13 = new Overheat_Spell();
        firearcane13.flameBeam = true;
        firearcane13.anim_element = FIRE;
        firearcane13.spellParts.add(BEAM);
        firearcane13.spellParts.add(OVERHEAT);
        firearcane13.spellParts.add(FLAMEJET);
        firearcaneSpells[13] = firearcane13;

        // 14. Flamejet + Overheat + Rifts

        DragonBreath_Spell firearcane14 = new DragonBreath_Spell();
        firearcane14.anim_element = FIRE;
        firearcane14.rift = true;
        firearcane14.spellParts.add(RIFTS);
        firearcane14.spellParts.add(OVERHEAT);
        firearcane14.spellParts.add(FLAMEJET);
        firearcaneSpells[14] = firearcane14;

        // 15. Fireball + Overheat + ArcaneMissile

        Fireball_Spell firearcane15 = new Fireball_Spell();
        firearcane15.anim_element = ARCANE;
        ArcaneMissile_Spell proj15 = new ArcaneMissile_Spell();
        proj15.overheat = true;
        proj15.anim_element = ARCANE;
        firearcane15.nested_spell = proj15;
        firearcane15.spellParts.add(FIREBALL);
        firearcane15.spellParts.add(OVERHEAT);
        firearcane15.spellParts.add(MISSILES);
        firearcaneSpells[15] = firearcane15;

        // 16. Fireball + Overheat + Beam

        EnergyBeam_Spell firearcane16 = new EnergyBeam_Spell();
        firearcane16.fireball = true;
        firearcane16.overheat = true;
        firearcane16.anim_element = FIRE;
        firearcane16.spellParts.add(OVERHEAT);
        firearcane16.spellParts.add(BEAM);
        firearcane16.spellParts.add(FIREBALL);
        firearcaneSpells[16] = firearcane16;

        // 17. Fireball + Overheat + Rifts

        MeteorShower_Spell firearcane17 = new MeteorShower_Spell();
        firearcane17.overheat = true;
        firearcane17.rift = true;
        firearcane17.anim_element = FIRE;
        firearcane17.spellParts.add(OVERHEAT);
        firearcane17.spellParts.add(RIFTS);
        firearcane17.spellParts.add(FIREBALL);
        firearcaneSpells[17] = firearcane17;

        // 18. ArcaneMissile + Beam + Flamejet

        Laser_Spell firearcane18 = new Laser_Spell();
        firearcane18.anim_element = FIRE;
        firearcane18.flamejet = true;
        firearcane18.spellParts.add(MISSILES);
        firearcane18.spellParts.add(BEAM);
        firearcane18.spellParts.add(FLAMEJET);
        firearcaneSpells[18] = firearcane18;

        // 19. ArcaneMissile + Beam + Fireball

        EnergyBeam_Spell firearcane19 = new EnergyBeam_Spell();
        firearcane19.anim_element = ARCANE;
        firearcane19.fireball = true;
        firearcane19.arcaneMissile = true;
        firearcane19.spellParts.add(BEAM);
        firearcane19.spellParts.add(MISSILES);
        firearcane19.spellParts.add(FIREBALL);
        firearcaneSpells[19] = firearcane19;

        // 20. ArcaneMissile + Beam + Overheat

        Judgement_Spell firearcane20 = new Judgement_Spell();
        firearcane20.anim_element = ARCANE;
        firearcane20.arcaneMissiles = true;
        firearcane20.spellParts.add(OVERHEAT);
        firearcane20.spellParts.add(MISSILES);
        firearcane20.spellParts.add(BEAM);
        firearcaneSpells[20] = firearcane20;

        // 21. ArcaneMissile + Rifts + Flamejet

        Flamejet_Spell firearcane21 = new Flamejet_Spell();
        firearcane21.anim_element = ARCANE;
        firearcane21.arcaneMissile = true;
        firearcane21.rift = true;
        firearcane21.spellParts.add(RIFTS);
        firearcane21.spellParts.add(MISSILES);
        firearcane21.spellParts.add(FLAMEJET);
        firearcaneSpells[21] = firearcane21;

        // 22. ArcaneMissile + Rifts + Fireball

        MeteorShower_Spell firearcane22 = new MeteorShower_Spell();
        firearcane22.anim_element = ARCANE;
        firearcane22.arcaneMissile = true;
        firearcane22.spellParts.add(RIFTS);
        firearcane22.spellParts.add(MISSILES);
        firearcane22.spellParts.add(FIREBALL);
        firearcaneSpells[22] = firearcane22;

        // 23. ArcaneMissile + Rifts + Overheat

        RepulsionField_Spell firearcane23 = new RepulsionField_Spell();
        firearcane23.arcaneMissile = true;
        firearcane23.spellParts.add(OVERHEAT);
        firearcane23.spellParts.add(RIFTS);
        firearcane23.spellParts.add(MISSILES);
        firearcaneSpells[23] = firearcane23;

        // 24. Rifts + Beam + Flamejet

        EnergyRain_Spell firearcane24 = new EnergyRain_Spell();
        firearcane24.anim_element = FIRE;
        firearcane24.flamejet = true;
        firearcane24.spellParts.add(FLAMEJET);
        firearcane24.spellParts.add(RIFTS);
        firearcane24.spellParts.add(BEAM);
        firearcaneSpells[24] = firearcane24;

        // 25. Rifts + Beam + Fireball

        EnergyBeam_Spell firearcane25 = new EnergyBeam_Spell();
        firearcane25.anim_element = ARCANE;
        firearcane25.fireball = true;
        firearcane25.rift = true;
        firearcane25.spellParts.add(FIREBALL);
        firearcane25.spellParts.add(RIFTS);
        firearcane25.spellParts.add(BEAM);
        firearcaneSpells[25] = firearcane25;

        // 26. Rifts + Beam + Overheat

        Judgement_Spell firearcane26 = new Judgement_Spell();
        firearcane26.rift = true;
        firearcane26.anim_element = ARCANE;
        firearcane26.spellParts.add(OVERHEAT);
        firearcane26.spellParts.add(RIFTS);
        firearcane26.spellParts.add(BEAM);
        firearcaneSpells[26] = firearcane26;

    }
}
