package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Spell;
import wizardo.game.Spells.Arcane.Rifts.Rifts_Spell;
import wizardo.game.Spells.Hybrid.Judgement.Judgement_Spell;
import wizardo.game.Spells.Hybrid.EnergyRain.EnergyRain_Spell;

import wizardo.game.Spells.Hybrid.Laser.Laser_Spell;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Spells.SpellUtils.Spell_Name.*;

public class LightningArcane_Spells {

    public static Spell[] litearcaneSpells = new Spell[28];

    public static void createLightningArcane_Spells() {

        // 0. Chargedbolts + ArcaneMissiles

        ChargedBolts_Spell litearcane0 = new ChargedBolts_Spell();
        litearcane0.arcaneMissile = true;
        litearcane0.anim_element = ARCANE;
        litearcane0.spellParts.add(CHARGEDBOLT);
        litearcane0.spellParts.add(MISSILES);
        litearcaneSpells[0] = litearcane0;

        // 1. Chargedbolts + Beam

        EnergyBeam_Spell litearcane1 = new EnergyBeam_Spell();
        litearcane1.anim_element = LIGHTNING;
        litearcane1.chargedbolts = true;
        litearcane1.spellParts.add(CHARGEDBOLT);
        litearcane1.spellParts.add(BEAM);
        litearcaneSpells[1] = litearcane1;

        // 2. Chargedbolts + Rifts

        Rifts_Spell litearcane2 = new Rifts_Spell();
        litearcane2.anim_element = LIGHTNING;
        litearcane2.chargedbolt = true;
        litearcane2.spellParts.add(CHARGEDBOLT);
        litearcane2.spellParts.add(RIFTS);
        litearcaneSpells[2] = litearcane2;

        // 3. Chain + ArcaneMissiles

        ChainLightning_Spell litearcane3 = new ChainLightning_Spell();
        litearcane3.anim_element = ARCANE;
        litearcane3.arcaneMissile = true;
        litearcane3.spellParts.add(CHAIN);
        litearcane3.spellParts.add(MISSILES);
        litearcaneSpells[3] = litearcane3;

        // 4. Chain + Beam

        EnergyBeam_Spell litearcane4 = new EnergyBeam_Spell();
        litearcane4.anim_element = ARCANE;
        litearcane4.chainlightning = true;
        litearcane4.spellParts.add(CHAIN);
        litearcane4.spellParts.add(BEAM);
        litearcaneSpells[4] = litearcane4;

        // 5. Chain + Rifts

        ChainLightning_Spell litearcane5 = new ChainLightning_Spell();
        litearcane5.rifts = true;
        litearcane5.anim_element = LIGHTNING;
        litearcane5.spellParts.add(CHAIN);
        litearcane5.spellParts.add(RIFTS);
        litearcaneSpells[5] = litearcane5;

        // 6. Thunderstorm + ArcaneMissiles

        Thunderstorm_Spell litearcane6 = new Thunderstorm_Spell();
        litearcane6.anim_element = ARCANE;
        litearcane6.arcaneMissile = true;
        litearcane6.spellParts.add(THUNDERSTORM);
        litearcane6.spellParts.add(MISSILES);
        litearcaneSpells[6] = litearcane6;

        // 7. Thunderstorm + Beam

        EnergyBeam_Spell litearcane7 = new EnergyBeam_Spell();
        litearcane7.thunderstorm = true;
        litearcane7.anim_element = ARCANE;
        litearcane7.spellParts.add(THUNDERSTORM);
        litearcane7.spellParts.add(BEAM);
        litearcaneSpells[7] = litearcane7;

        // 8. Thunderstorm + Rifts

        Thunderstorm_Spell litearcane8 = new Thunderstorm_Spell();
        litearcane8.rifts = true;
        litearcane8.anim_element = ARCANE;
        litearcane8.spellParts.add(THUNDERSTORM);
        litearcane8.spellParts.add(RIFTS);
        litearcaneSpells[8] = litearcane8;

        // 9. Chargedbolts + ChainLightning + ArcaneMissiles

        ChainLightning_Spell litearcane9 = new ChainLightning_Spell();
        litearcane9.anim_element = ARCANE;
        litearcane9.nested_spell = litearcaneSpells[0];
        litearcane9.spellParts.add(CHARGEDBOLT);
        litearcane9.spellParts.add(MISSILES);
        litearcane9.spellParts.add(CHAIN);
        litearcaneSpells[9] = litearcane9;

        // 10. Chargedbolts + ChainLightning + Beam

        EnergyBeam_Spell litearcane10 = new EnergyBeam_Spell();
        litearcane10.anim_element = LIGHTNING;
        litearcane10.chainlightning = true;
        litearcane10.chargedbolts = true;
        litearcane10.spellParts.add(CHARGEDBOLT);
        litearcane10.spellParts.add(BEAM);
        litearcane10.spellParts.add(CHAIN);
        litearcaneSpells[10] = litearcane10;

        // 11. Chargedbolts + ChainLightning + Rifts

        Rifts_Spell litearcane11 = new Rifts_Spell();
        litearcane11.anim_element = LIGHTNING;
        litearcane11.chargedbolt = true;
        litearcane11.chainlightning = true;
        litearcane11.spellParts.add(CHARGEDBOLT);
        litearcane11.spellParts.add(RIFTS);
        litearcane11.spellParts.add(CHAIN);
        litearcaneSpells[11] = litearcane11;

        // 12. Chargedbolts + Thunderstorm + ArcaneMissiles

        Thunderstorm_Spell litearcane12 = new Thunderstorm_Spell();
        litearcane12.anim_element = ARCANE;
        litearcane12.nested_spell = litearcaneSpells[0];
        litearcane12.spellParts.add(CHARGEDBOLT);
        litearcane12.spellParts.add(MISSILES);
        litearcane12.spellParts.add(THUNDERSTORM);
        litearcaneSpells[12] = litearcane12;

        // 13. Chargedbolts + Thunderstorm + Beam

        EnergyBeam_Spell litearcane13 = new EnergyBeam_Spell();
        litearcane13.anim_element = ARCANE;
        litearcane13.chargedbolts = true;
        litearcane13.thunderstorm = true;
        litearcane13.spellParts.add(CHARGEDBOLT);
        litearcane13.spellParts.add(BEAM);
        litearcane13.spellParts.add(THUNDERSTORM);
        litearcaneSpells[13] = litearcane13;

        // 14. Chargedbolts + Thunderstorm + Rifts

        Thunderstorm_Spell litearcane14 = new Thunderstorm_Spell();
        litearcane14.anim_element = LIGHTNING;
        litearcane14.rifts = true;
        litearcane14.nested_spell = new ChargedBolts_Spell();
        litearcane14.spellParts.add(CHARGEDBOLT);
        litearcane14.spellParts.add(RIFTS);
        litearcane14.spellParts.add(THUNDERSTORM);
        litearcaneSpells[14] = litearcane14;

        // 15. Chainlightning + Thunderstorm + ArcaneMissiles

        Thunderstorm_Spell litearcane15 = new Thunderstorm_Spell();
        litearcane15.arcaneMissile = true;
        litearcane15.anim_element = ARCANE;
        ChainLightning_Spell chain15 = new ChainLightning_Spell();
        chain15.arcaneMissile = true;
        litearcane15.nested_spell = chain15;
        litearcane15.spellParts.add(MISSILES);
        litearcane15.spellParts.add(CHAIN);
        litearcane15.spellParts.add(THUNDERSTORM);
        litearcaneSpells[15] = litearcane15;

        // 16. Chainlightning + Thunderstorm + Beam

        EnergyBeam_Spell litearcane16 = new EnergyBeam_Spell();
        litearcane16.thunderstorm = true;
        litearcane16.chainlightning = true;
        litearcane16.anim_element = LIGHTNING;
        litearcane16.spellParts.add(BEAM);
        litearcane16.spellParts.add(CHAIN);
        litearcane16.spellParts.add(THUNDERSTORM);
        litearcaneSpells[16] = litearcane16;

        // 17. Chainlightning + Thunderstorm + Rifts

        Thunderstorm_Spell litearcane17 = new Thunderstorm_Spell();
        litearcane17.rifts = true;
        litearcane17.anim_element = LIGHTNING;
        litearcane17.nested_spell = new ChainLightning_Spell();
        litearcane17.spellParts.add(RIFTS);
        litearcane17.spellParts.add(CHAIN);
        litearcane17.spellParts.add(THUNDERSTORM);
        litearcaneSpells[17] = litearcane17;

        // 18. Chargedbolts + ArcaneMissiles + Beam

        Laser_Spell litearcane18 = new Laser_Spell();
        litearcane18.anim_element = LIGHTNING;
        litearcane18.chargedbolt = true;
        litearcane18.spellParts.add(MISSILES);
        litearcane18.spellParts.add(BEAM);
        litearcane18.spellParts.add(CHARGEDBOLT);
        litearcaneSpells[18] = litearcane18;

        // 19. Chargedbolts + ArcaneMissiles + Rifts

        ArcaneMissile_Spell litearcane19 = new ArcaneMissile_Spell();
        litearcane19.rift = true;
        litearcane19.chargedbolts = true;
        litearcane19.anim_element = ARCANE;
        litearcane19.spellParts.add(RIFTS);
        litearcane19.spellParts.add(MISSILES);
        litearcane19.spellParts.add(CHARGEDBOLT);
        litearcaneSpells[19] = litearcane19;

        // 20. Chargedbolts + Beam + Rifts

        EnergyRain_Spell litearcane20 = new EnergyRain_Spell();
        litearcane20.anim_element = LIGHTNING;
        litearcane20.chargedbolts = true;
        litearcane20.spellParts.add(RIFTS);
        litearcane20.spellParts.add(BEAM);
        litearcane20.spellParts.add(CHARGEDBOLT);
        litearcaneSpells[20] = litearcane20;

        // 21. ArcaneMissiles + Beam + ChainLightning

        ChainLightning_Spell litearcane21 = new ChainLightning_Spell();
        litearcane21.beam = true;
        litearcane21.arcaneMissile = true;
        litearcane21.anim_element = ARCANE;
        litearcane21.spellParts.add(MISSILES);
        litearcane21.spellParts.add(BEAM);
        litearcane21.spellParts.add(CHAIN);
        litearcaneSpells[21] = litearcane21;

        // 22. ArcaneMissiles + Rifts + ChainLightning

        Rifts_Spell litearcane22 = new Rifts_Spell();
        litearcane22.anim_element = ARCANE;
        litearcane22.chainlightning = true;
        litearcane22.arcanemissiles = true;
        litearcane22.spellParts.add(MISSILES);
        litearcane22.spellParts.add(RIFTS);
        litearcane22.spellParts.add(CHAIN);
        litearcaneSpells[22] = litearcane22;

        // 23. Rifts + Beam + ChainLightning

        ChainLightning_Spell litearcane23 = new ChainLightning_Spell();
        litearcane23.beam = true;
        litearcane23.rifts = true;
        litearcane23.anim_element = ARCANE;
        litearcane23.spellParts.add(RIFTS);
        litearcane23.spellParts.add(BEAM);
        litearcane23.spellParts.add(CHAIN);
        litearcaneSpells[23] = litearcane23;

        // 24. ArcaneMissiles + Rifts + Thunderstorm

        Thunderstorm_Spell litearcane24 = new Thunderstorm_Spell();
        litearcane24.rifts = true;
        litearcane24.arcaneMissile = true;
        litearcane24.anim_element = ARCANE;
        litearcane24.spellParts.add(THUNDERSTORM);
        litearcane24.spellParts.add(RIFTS);
        litearcane24.spellParts.add(MISSILES);
        litearcaneSpells[24] = litearcane24;

        // 25. ArcaneMissiles + Beam + Thunderstorm

        Judgement_Spell litearcane25 = new Judgement_Spell();
        litearcane25.anim_element = ARCANE;
        litearcane25.ministorm = true;
        litearcane25.spellParts.add(BEAM);
        litearcane25.spellParts.add(MISSILES);
        litearcane25.spellParts.add(THUNDERSTORM);
        litearcaneSpells[25] = litearcane25;

        // 26. Rifts + Beam + Thunderstorm
        EnergyRain_Spell litearcane26 = new EnergyRain_Spell();
        litearcane26.thunderstorm = true;
        litearcane26.anim_element = LIGHTNING;
        litearcane26.spellParts.add(BEAM);
        litearcane26.spellParts.add(RIFTS);
        litearcane26.spellParts.add(THUNDERSTORM);
        litearcaneSpells[26] = litearcane26;

        // 27. Arcane Missiles + Chargedbolts
        ArcaneMissile_Spell litearcane27 = new ArcaneMissile_Spell();
        litearcane27.chargedbolts = true;
        litearcane27.anim_element = LIGHTNING;
        litearcane27.spellParts.add(MISSILES);
        litearcane27.spellParts.add(CHARGEDBOLT);
        litearcaneSpells[27] = litearcane27;

    }
}
