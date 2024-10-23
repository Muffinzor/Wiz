package wizardo.game.Spells;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Spells.SpellBank.AllSpells.allSpells;

public class SpellMixer {

    public static Spell getMixedSpell(ArrayList<SpellUtils.Spell_Name> parts) {

        Collections.sort(parts);

        for(Spell spell : allSpells) {
            Collections.sort(spell.spellParts);
            if(spell.spellParts.equals(parts)) {
                return spell;
            }
        }

        return null;

    }
}
