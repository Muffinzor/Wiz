package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Spell;

import java.util.ArrayList;
import java.util.Arrays;

import static wizardo.game.Spells.SpellBank.FrostFire_Spells.createFrostFire_Spells;
import static wizardo.game.Spells.SpellBank.FrostFire_Spells.frostfireSpells;
import static wizardo.game.Spells.SpellBank.FrostLightning_Spells.createFrostLite_Spells;
import static wizardo.game.Spells.SpellBank.FrostLightning_Spells.frostliteSpells;
import static wizardo.game.Spells.SpellBank.Frost_Spells.*;

public class AllSpells {

    public static ArrayList<Spell> allSpells = new ArrayList<>();

    public static void createAllSpells() {
        createFrost_Spells();
        allSpells.addAll(Arrays.asList(frostspells));

        createFrostLite_Spells();
        allSpells.addAll(Arrays.asList(frostliteSpells));

        createFrostFire_Spells();
        allSpells.addAll(Arrays.asList(frostfireSpells));
    }
}
