package wizardo.game.Spells;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Spells.SpellBank.AllSpells.allSpells;

public class SpellMixer {

    public static Spell get_mixed_spell(ArrayList<SpellUtils.Spell_Name> parts) {

        Spell spellio = null;

        ArrayList<SpellUtils.Spell_Name> thisSpellPartsCopy = new ArrayList<>(parts);

        if (thisSpellPartsCopy.size() > 2) {
            Collections.sort(thisSpellPartsCopy);
        }

        for(Spell spell : allSpells) {
            if(spell.spellParts.equals(thisSpellPartsCopy)) {
                spellio = spell;
            }
        }

        if(spellio == null) {
            Collections.sort(thisSpellPartsCopy);
            for(Spell spell : allSpells) {
                ArrayList<SpellUtils.Spell_Name> spellpartsCopy = new ArrayList<>(spell.spellParts);
                Collections.sort(spellpartsCopy);
                if(spellpartsCopy.equals(thisSpellPartsCopy)) {
                    spellio = spell;
                }
            }
        }

        return spellio;
    }
    public static boolean inverted_spell_exists(ArrayList<SpellUtils.Spell_Name> parts) {
        ArrayList<SpellUtils.Spell_Name> tempList = new ArrayList<>();
        tempList.add(parts.get(1));
        tempList.add(parts.get(0));

        Spell initial_spell = get_mixed_spell(parts);
        Spell inverted_spell = get_mixed_spell(tempList);

        return !(initial_spell == inverted_spell);

    }
}
