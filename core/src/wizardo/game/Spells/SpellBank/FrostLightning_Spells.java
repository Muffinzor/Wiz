package wizardo.game.Spells.SpellBank;

import wizardo.game.Resources.Skins;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Hybrid.Blizzard.Blizzard_Spell;
import wizardo.game.Spells.Hybrid.CelestialStrike.CelestialStrike_Spell;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Spell;
import wizardo.game.Spells.Spell;

import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Spells.SpellUtils.Spell_Name.*;


public class FrostLightning_Spells {

    public static Spell[] frostliteSpells = new Spell[34];

    public static void createFrostLite_Spells() {

        // 0. Frostbolts + Chargedbolts

        Frostbolt_Spell frostlite0 = new Frostbolt_Spell();
        frostlite0.chargedbolt = true;
        frostlite0.anim_element = LIGHTNING;
        frostlite0.spellParts.add(FROSTBOLT);
        frostlite0.spellParts.add(CHARGEDBOLTS);
        frostliteSpells[0] = frostlite0;

        // 1. Icespear + ChargedBolts

        Icespear_Spell frostlite1 = new Icespear_Spell();
        frostlite1.nested_spell = new ChargedBolts_Spell();
        frostlite1.anim_element = LIGHTNING;
        frostlite1.spellParts.add(ICESPEAR);
        frostlite1.spellParts.add(CHARGEDBOLTS);
        frostliteSpells[1] = frostlite1;

        // 2. FrozenOrb + ChargedBolts

        Frozenorb_Spell frostlite2 = new Frozenorb_Spell();
        frostlite2.nested_spell = new ChargedBolts_Spell();
        frostlite2.anim_element = LIGHTNING;
        frostlite2.spellParts.add(FROZENORB);
        frostlite2.spellParts.add(CHARGEDBOLTS);
        frostliteSpells[2] = frostlite2;

        // 3. Frostbolts + ChainLightning

        Frostbolt_Spell frostlite3 = new Frostbolt_Spell();
        frostlite3.chainlightning = true;
        frostlite3.anim_element = LIGHTNING;
        frostlite3.spellParts.add(FROSTBOLT);
        frostlite3.spellParts.add(CHAIN);
        frostliteSpells[3] = frostlite3;

        // 4. IceSpear + ChainLightning

        ChainLightning_Spell frostlite4 = new ChainLightning_Spell();
        frostlite4.spear = true;
        frostlite4.anim_element = LIGHTNING;
        frostlite4.spellParts.add(ICESPEAR);
        frostlite4.spellParts.add(CHAIN);
        frostliteSpells[4] = frostlite4;

        // 5. Frozenorb + Chainlightning

        Frozenorb_Spell frostlite5 = new Frozenorb_Spell();
        frostlite5.nested_spell = new ChainLightning_Spell();
        frostlite5.anim_element = LIGHTNING;
        frostlite5.spellParts.add(FROZENORB);
        frostlite5.spellParts.add(CHAIN);
        frostliteSpells[5] = frostlite5;

        // 6. Frostbolts + Thunderstorm

        Frostbolt_Spell frostlite6 = new Frostbolt_Spell();
        frostlite6.thunderstorm = true;
        frostlite6.anim_element = FROST;
        frostlite6.spellParts.add(FROSTBOLT);
        frostlite6.spellParts.add(THUNDERSTORM);
        frostliteSpells[6] = frostlite6;

        // 7. Thunderstorm + Icespear

        Blizzard_Spell frostlite7 = new Blizzard_Spell();
        frostlite7.anim_element = FROST;
        frostlite7.spellParts.add(THUNDERSTORM);
        frostlite7.spellParts.add(ICESPEAR);
        frostliteSpells[7] = frostlite7;

        // 8. Thunderstorm + FrozenOrb

        CelestialStrike_Spell frostlite8 = new CelestialStrike_Spell();
        frostlite8.spellParts.add(FROZENORB);
        frostlite8.spellParts.add(THUNDERSTORM);
        frostliteSpells[8] = frostlite8;

        // 9. ChainLightning + ChargedBolts + Frostbolts

        ChainLightning_Spell frostlite9 = new ChainLightning_Spell();
        ChargedBolts_Spell proj9 = new ChargedBolts_Spell();
        proj9.frostbolts = true;
        frostlite9.nested_spell = proj9;
        frostlite9.anim_element = FROST;
        frostlite9.spellParts.add(CHAIN);
        frostlite9.spellParts.add(CHARGEDBOLTS);
        frostlite9.spellParts.add(FROSTBOLT);
        frostliteSpells[9] = frostlite9;

        // 10. ChainLightning + Chargedbolts + Spear

        ChainLightning_Spell frostlite10 = new ChainLightning_Spell();
        frostlite10.spear = true;
        frostlite10.nested_spell = new ChargedBolts_Spell();
        frostlite10.anim_element = LIGHTNING;
        frostlite10.spellParts.add(CHAIN);
        frostlite10.spellParts.add(CHARGEDBOLTS);
        frostlite10.spellParts.add(ICESPEAR);
        frostliteSpells[10] = frostlite10;

        // 11. ChainLightning + Chargedbolts + FrozenOrb

        Frozenorb_Spell frostlite11 = new Frozenorb_Spell();
        ChainLightning_Spell chain11 = new ChainLightning_Spell();
        chain11.nested_spell = new ChargedBolts_Spell();
        frostlite11.nested_spell = chain11;
        frostlite11.anim_element = LIGHTNING;
        frostlite11.spellParts.add(CHAIN);
        frostlite11.spellParts.add(CHARGEDBOLTS);
        frostlite11.spellParts.add(FROZENORB);
        frostliteSpells[11] = frostlite11;

        // 12. ChainLightning + Thunderstorm + Frostbolts

        Thunderstorm_Spell frostlite12 = new Thunderstorm_Spell();
        ChainLightning_Spell chain12 = new ChainLightning_Spell();
        chain12.frostbolts = true;
        frostlite12.nested_spell = chain12;
        frostlite12.anim_element = FROST;
        frostlite12.spellParts.add(CHAIN);
        frostlite12.spellParts.add(THUNDERSTORM);
        frostlite12.spellParts.add(FROSTBOLT);
        frostliteSpells[12] = frostlite12;

        // 13. ChainLightning + Thunderstorm + Icespear

        Thunderstorm_Spell frostlite13 = new Thunderstorm_Spell();
        frostlite13.nested_spell = frostlite4;
        frostlite13.textColor = Skins.light_teal;
        frostlite13.anim_element = FROST;
        frostlite13.spellParts.add(CHAIN);
        frostlite13.spellParts.add(THUNDERSTORM);
        frostlite13.spellParts.add(ICESPEAR);
        frostliteSpells[13] = frostlite13;

        // 14. ChainLightning + Thunderstorm + FrozenOrb

        CelestialStrike_Spell frostlite14 = new CelestialStrike_Spell();
        frostlite14.chain = true;
        frostlite14.spellParts.add(CHAIN);
        frostlite14.spellParts.add(THUNDERSTORM);
        frostlite14.spellParts.add(FROZENORB);
        frostliteSpells[14] = frostlite14;

        // 15. ChainLightning + Frostbolts + Icespear

        ChainLightning_Spell frostlite15 = new ChainLightning_Spell();
        frostlite15.frostbolts = true;
        frostlite15.spear = true;
        frostlite15.anim_element = COLDLITE;
        frostlite15.textColor = Skins.light_blue;
        frostlite15.spellParts.add(CHAIN);
        frostlite15.spellParts.add(ICESPEAR);
        frostlite15.spellParts.add(FROSTBOLT);
        frostliteSpells[15] = frostlite15;

        // 16. ChainLightning + Frostbolts + FrozenOrb

        Frozenorb_Spell frostlite16 = new Frozenorb_Spell();
        ChainLightning_Spell chain16 = new ChainLightning_Spell();
        chain16.frostbolts = true;
        frostlite16.nested_spell = chain16;
        frostlite16.anim_element = COLDLITE;
        frostlite16.spellParts.add(CHAIN);
        frostlite16.spellParts.add(FROSTBOLT);
        frostlite16.spellParts.add(FROZENORB);
        frostliteSpells[16] = frostlite16;

        // 17. ChainLightning + Icespear + Frozenorb

        Icespear_Spell frostlite17 = new Icespear_Spell();
        frostlite17.frozenorb = true;
        frostlite17.textColor = Skins.light_teal;
        frostlite17.anim_element = LIGHTNING;
        frostlite17.spellParts.add(CHAIN);
        frostlite17.spellParts.add(ICESPEAR);
        frostlite17.spellParts.add(FROZENORB);
        frostliteSpells[17] = frostlite17;

        // 18. Thunderstorm + Frozenorb + Chargedbolt

        CelestialStrike_Spell frostlite18 = new CelestialStrike_Spell();
        frostlite18.chargedbolts = true;
        frostlite18.spellParts.add(THUNDERSTORM);
        frostlite18.spellParts.add(CHARGEDBOLTS);
        frostlite18.spellParts.add(FROZENORB);
        frostliteSpells[18] = frostlite18;

        // 19. Thunderstorm + Frozenorb + Icespear

        Icespear_Spell frostlite19 = new Icespear_Spell();
        frostlite19.anim_element = COLDLITE;
        frostlite19.celestialStrike = true;
        frostlite19.spellParts.add(ICESPEAR);
        frostlite19.spellParts.add(THUNDERSTORM);
        frostlite19.spellParts.add(FROZENORB);
        frostliteSpells[19] = frostlite19;

        // 20. Thunderstorm + Frozenorb + Frostbolts

        CelestialStrike_Spell frostlite20 = new CelestialStrike_Spell();
        frostlite20.frostbolts = true;
        frostlite20.spellParts.add(THUNDERSTORM);
        frostlite20.spellParts.add(FROZENORB);
        frostlite20.spellParts.add(FROSTBOLT);
        frostliteSpells[20] = frostlite20;

        // 21. Thunderstorm + Chargedbolts + Frostbolts

        Thunderstorm_Spell frostlite21 = new Thunderstorm_Spell();
        ChargedBolts_Spell proj21 = new ChargedBolts_Spell();
        proj21.frostbolts = true;
        frostlite21.nested_spell = frostliteSpells[0];
        frostlite21.anim_element = FROST;
        frostlite21.spellParts.add(THUNDERSTORM);
        frostlite21.spellParts.add(CHARGEDBOLTS);
        frostlite21.spellParts.add(FROSTBOLT);
        frostliteSpells[21] = frostlite21;

        // 22. Thunderstorm + Chargedbolts + Icespear

        Icespear_Spell frostlite22 = new Icespear_Spell();
        frostlite22.thunderspear = true;
        frostlite22.anim_element = LIGHTNING;
        frostlite22.textColor = Skins.light_teal;
        frostlite22.spellParts.add(THUNDERSTORM);
        frostlite22.spellParts.add(ICESPEAR);
        frostlite22.spellParts.add(CHARGEDBOLTS);
        frostliteSpells[22] = frostlite22;

        // 23. Thunderstorm + Icespear + Frostbolts

        Blizzard_Spell frostlite23 = new Blizzard_Spell();
        frostlite23.frostbolts = true;
        frostlite23.anim_element = FROST;
        frostlite23.spellParts.add(THUNDERSTORM);
        frostlite23.spellParts.add(ICESPEAR);
        frostlite23.spellParts.add(FROSTBOLT);
        frostliteSpells[23] = frostlite23;

        // 24. Icespear + Chargedbolts + Frostbolts

        ChargedBolts_Spell frostlite24 = new ChargedBolts_Spell();
        frostlite24.frostbolts = true;
        frostlite24.spear = true;
        frostlite24.anim_element = FROST;
        frostlite24.textColor = Skins.light_blue;
        frostlite24.spellParts.add(ICESPEAR);
        frostlite24.spellParts.add(CHARGEDBOLTS);
        frostlite24.spellParts.add(FROSTBOLT);
        frostliteSpells[24] = frostlite24;

        // 25. Frozenorb + Chargedbolts + Frostbolts

        Frozenorb_Spell frostlite25 = new Frozenorb_Spell();
        frostlite25.nested_spell = frostliteSpells[0];
        frostlite25.textColor = Skins.light_teal;
        frostlite25.anim_element = LIGHTNING;
        frostlite25.spellParts.add(FROZENORB);
        frostlite25.spellParts.add(CHARGEDBOLTS);
        frostlite25.spellParts.add(FROSTBOLT);
        frostliteSpells[25] = frostlite25;

        // 26. Frozenorb + Icespear + Chargedbolts

        Frozenorb_Spell frostlite26 = new Frozenorb_Spell();
        frostlite26.textColor = Skins.light_teal;
        frostlite26.anim_element = LIGHTNING;
        Icespear_Spell spear26 = new Icespear_Spell();
        spear26.duration = 0.75f;
        spear26.nested_spell = new ChargedBolts_Spell();
        spear26.maxSplits = 0;
        frostlite26.nested_spell = spear26;
        frostlite26.spellParts.add(FROZENORB);
        frostlite26.spellParts.add(CHARGEDBOLTS);
        frostlite26.spellParts.add(ICESPEAR);
        frostliteSpells[26] = frostlite26;

        // 27. ChargedBolts + Frostbolts

        ChargedBolts_Spell frostlite27 = new ChargedBolts_Spell();
        frostlite27.anim_element = FROST;
        frostlite27.frostbolts = true;
        frostlite27.spellParts.add(CHARGEDBOLTS);
        frostlite27.spellParts.add(FROSTBOLT);
        frostliteSpells[27] = frostlite27;

        // 28. ChargedBolts + Icespear

        ChargedBolts_Spell frostlite28 = new ChargedBolts_Spell();
        frostlite28.anim_element = FROST;
        frostlite28.spear = true;
        frostlite28.spellParts.add(CHARGEDBOLTS);
        frostlite28.spellParts.add(ICESPEAR);
        frostliteSpells[28] = frostlite28;

        // 29. ChargedBolts + Frozenorb

        ChargedBolts_Spell frostlite29 = new ChargedBolts_Spell();
        frostlite29.anim_element = FROST;
        frostlite29.frozenorb = true;
        frostlite29.spellParts.add(CHARGEDBOLTS);
        frostlite29.spellParts.add(FROZENORB);
        frostliteSpells[29] = frostlite29;

        // 30. Chainlightning + Frostbolts

        ChainLightning_Spell frostlite30 = new ChainLightning_Spell();
        frostlite30.frostbolts = true;
        frostlite30.anim_element = FROST;
        frostlite30.spellParts.add(CHAIN);
        frostlite30.spellParts.add(FROSTBOLT);
        frostliteSpells[30] = frostlite30;

        // 31. Chainlightning + Spear

        ChainLightning_Spell frostlite31 = new ChainLightning_Spell();
        frostlite31.spear = true;
        frostlite31.anim_element = FROST;
        frostlite31.spellParts.add(CHAIN);
        frostlite31.spellParts.add(ICESPEAR);
        frostliteSpells[31] = frostlite31;

        // 32. Chainlightning + Frozenorb

        ChainLightning_Spell frostlite32 = new ChainLightning_Spell();
        frostlite32.frozenorb = true;
        frostlite32.anim_element = FROST;
        frostlite32.spellParts.add(CHAIN);
        frostlite32.spellParts.add(FROZENORB);
        frostliteSpells[32] = frostlite32;

        // 33. Thunderstorm + Frostbolts

        Thunderstorm_Spell frostlite33 = new Thunderstorm_Spell();
        frostlite33.nested_spell = new Frostbolt_Explosion();
        frostlite33.anim_element = FROST;
        frostlite33.spellParts.add(THUNDERSTORM);
        frostlite33.spellParts.add(FROSTBOLT);
        frostliteSpells[33] = frostlite33;





        for (Spell frostLiteSpell : frostliteSpells) {

            if(frostLiteSpell != null && frostLiteSpell.bonus_element == null) {

                if (frostLiteSpell.main_element == LIGHTNING) {
                    frostLiteSpell.bonus_element = FROST;
                } else {
                    frostLiteSpell.bonus_element = LIGHTNING;
                }

            }
        }
    }
}
