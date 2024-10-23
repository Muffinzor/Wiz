package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Hybrid.Blizzard.Blizzard_Spell;
import wizardo.game.Spells.Hybrid.CelestialStrike.CelestialStrike_Spell;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Hit;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Projectile;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Name.*;


public class FrostLightning_Spells {

    public static Spell[] frostliteSpells = new Spell[27];

    public static void createFrostLite_Spells() {

        // 0. ChargedBolts + FrostBolts

        ChargedBolts_Spell frostlite0 = new ChargedBolts_Spell();
        frostlite0.frostbolts = true;
        frostlite0.anim_element = SpellUtils.Spell_Element.FROST;
        frostlite0.spellParts.add(CHARGEDBOLTS);
        frostlite0.spellParts.add(FROSTBOLT);
        frostliteSpells[0] = frostlite0;

        // 1. ChargedBolts + Icespear

        Icespear_Spell frostlite1 = new Icespear_Spell();
        frostlite1.nested_spell = new ChargedBolts_Spell();
        frostlite1.anim_element = SpellUtils.Spell_Element.FROST;
        frostlite1.spellParts.add(CHARGEDBOLTS);
        frostlite1.spellParts.add(ICESPEAR);
        frostliteSpells[1] = frostlite1;

        // 2. ChargedBolts + FrozenOrb

        Frozenorb_Spell frostlite2 = new Frozenorb_Spell();
        frostlite2.nested_spell = new ChargedBolts_Spell();
        frostlite2.anim_element = SpellUtils.Spell_Element.LIGHTNING;
        frostlite2.spellParts.add(FROZENORB);
        frostlite2.spellParts.add(CHARGEDBOLTS);
        frostliteSpells[2] = frostlite2;

        // 3. ChainLightning + FrostBolts

        ChainLightning_Spell frostlite3 = new ChainLightning_Spell();
        frostlite3.frostbolts = true;
        frostlite3.anim_element = SpellUtils.Spell_Element.LIGHTNING;
        frostlite3.spellParts.add(CHAIN);
        frostlite3.spellParts.add(FROSTBOLT);
        frostliteSpells[3] = frostlite3;

        // 4. ChainLightning + Icespear

        // 5. ChainLightning + Frozenorb

        Frozenorb_Spell frostlite5 = new Frozenorb_Spell();
        frostlite5.nested_spell = new ChainLightning_Spell();
        frostlite5.anim_element = SpellUtils.Spell_Element.LIGHTNING;
        frostlite5.spellParts.add(FROZENORB);
        frostlite5.spellParts.add(CHAIN);
        frostliteSpells[5] = frostlite5;

        // 6. Thunderstorm + Frostbolts

        Thunderstorm_Spell frostlite6 = new Thunderstorm_Spell();
        frostlite6.nested_spell = new Frostbolt_Explosion();
        frostlite6.anim_element = SpellUtils.Spell_Element.LIGHTNING;
        frostlite6.spellParts.add(FROSTBOLT);
        frostlite6.spellParts.add(THUNDERSTORM);
        frostliteSpells[6] = frostlite6;

        // 7. Thunderstorm + Icespear

        Blizzard_Spell frostlite7 = new Blizzard_Spell();
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
        frostlite9.anim_element = SpellUtils.Spell_Element.FROST;
        frostlite9.spellParts.add(CHAIN);
        frostlite9.spellParts.add(CHARGEDBOLTS);
        frostlite9.spellParts.add(FROSTBOLT);
        frostliteSpells[9] = frostlite9;

        // 10. ChainLightning + Chargedbolts + Spear

        // 11. ChainLightning + Chargedbolts + FrozenOrb

        // 12. ChainLightning + Thunderstorm + Frostbolts

        // 13. ChainLightning + Thunderstorm + Icespear

        // 14. ChainLightning + Thunderstorm + Frozenorb

        CelestialStrike_Spell frostlite14 = new CelestialStrike_Spell();
        frostlite14.chain = true;
        frostlite14.spellParts.add(CHAIN);
        frostlite14.spellParts.add(THUNDERSTORM);
        frostlite14.spellParts.add(FROZENORB);
        frostliteSpells[14] = frostlite14;



        for (Spell frostLiteSpell : frostliteSpells) {

            if(frostLiteSpell != null) {

                if (frostLiteSpell.main_element == SpellUtils.Spell_Element.LIGHTNING) {
                    frostLiteSpell.bonus_element = SpellUtils.Spell_Element.FROST;
                } else {
                    frostLiteSpell.bonus_element = SpellUtils.Spell_Element.LIGHTNING;
                }

            }
        }
    }
}
