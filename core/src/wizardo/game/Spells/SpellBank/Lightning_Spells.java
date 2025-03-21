package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Spell;
import wizardo.game.Spells.Spell;

import static wizardo.game.Spells.SpellUtils.Spell_Element.LIGHTNING;
import static wizardo.game.Spells.SpellUtils.Spell_Name.*;

public class Lightning_Spells {

    public static Spell[] lightning_spells = new Spell[7];

    public static void createLightning_Spells() {

        ChargedBolts_Spell lite0 = new ChargedBolts_Spell();
        lite0.anim_element = LIGHTNING;
        lite0.spellParts.add(CHARGEDBOLTS);
        lightning_spells[0] = lite0;

        ChainLightning_Spell lite1 = new ChainLightning_Spell();
        lite1.anim_element = LIGHTNING;
        lite1.spellParts.add(CHAIN);
        lightning_spells[1] = lite1;

        Thunderstorm_Spell lite2 = new Thunderstorm_Spell();
        lite2.anim_element = LIGHTNING;
        lite2.spellParts.add(THUNDERSTORM);
        lightning_spells[2] = lite2;

        ChainLightning_Spell lite3 = new ChainLightning_Spell();
        lite3.nested_spell = new ChargedBolts_Spell();
        lite3.anim_element = LIGHTNING;
        lite3.spellParts.add(CHAIN);
        lite3.spellParts.add(CHARGEDBOLTS);
        lightning_spells[3] = lite3;

        Thunderstorm_Spell lite4 = new Thunderstorm_Spell();
        lite4.nested_spell = new ChargedBolts_Spell();
        lite4.anim_element = LIGHTNING;
        lite4.spellParts.add(THUNDERSTORM);
        lite4.spellParts.add(CHARGEDBOLTS);
        lightning_spells[4] = lite4;

        Thunderstorm_Spell lite5 = new Thunderstorm_Spell();
        lite5.nested_spell = new ChainLightning_Spell();
        lite5.anim_element = LIGHTNING;
        lite5.spellParts.add(CHAIN);
        lite5.spellParts.add(THUNDERSTORM);
        lightning_spells[5] = lite5;

        Thunderstorm_Spell lite6 = new Thunderstorm_Spell();
        lite6.nested_spell = lightning_spells[3];
        lite6.anim_element = LIGHTNING;
        lite6.spellParts.add(CHAIN);
        lite6.spellParts.add(THUNDERSTORM);
        lite6.spellParts.add(CHARGEDBOLTS);
        lightning_spells[6] = lite6;


    }
}
