package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Spell;

import java.util.ArrayList;
import java.util.Arrays;

import static wizardo.game.Spells.SpellBank.Arcane_Spells.arcanespells;
import static wizardo.game.Spells.SpellBank.Arcane_Spells.createArcane_Spells;
import static wizardo.game.Spells.SpellBank.FireArcane_Spells.createFireArcane_Spells;
import static wizardo.game.Spells.SpellBank.FireArcane_Spells.firearcaneSpells;
import static wizardo.game.Spells.SpellBank.Fire_Spells.createFire_Spells;
import static wizardo.game.Spells.SpellBank.Fire_Spells.firespells;
import static wizardo.game.Spells.SpellBank.FrostArcane_Spells.createFrostArcane_Spells;
import static wizardo.game.Spells.SpellBank.FrostArcane_Spells.frostarcaneSpells;
import static wizardo.game.Spells.SpellBank.FrostFire_Spells.createFrostFire_Spells;
import static wizardo.game.Spells.SpellBank.FrostFire_Spells.frostfireSpells;
import static wizardo.game.Spells.SpellBank.FrostLightning_Spells.createFrostLite_Spells;
import static wizardo.game.Spells.SpellBank.FrostLightning_Spells.frostliteSpells;
import static wizardo.game.Spells.SpellBank.Frost_Spells.*;
import static wizardo.game.Spells.SpellBank.LightningArcane_Spells.createLightningArcane_Spells;
import static wizardo.game.Spells.SpellBank.LightningArcane_Spells.litearcaneSpells;
import static wizardo.game.Spells.SpellBank.LightningFire_Spells.createLightningFire_Spells;
import static wizardo.game.Spells.SpellBank.LightningFire_Spells.litefireSpells;
import static wizardo.game.Spells.SpellBank.Lightning_Spells.createLightning_Spells;
import static wizardo.game.Spells.SpellBank.Lightning_Spells.litespells;

public class AllSpells {

    public static ArrayList<Spell> allSpells = new ArrayList<>();

    public static void createAllSpells() {

        createFrost_Spells();
        allSpells.addAll(Arrays.asList(frostspells));

        createFire_Spells();
        allSpells.addAll(Arrays.asList(firespells));

        createLightning_Spells();
        allSpells.addAll(Arrays.asList(litespells));

        createArcane_Spells();
        allSpells.addAll(Arrays.asList(arcanespells));

        createFrostLite_Spells();
        allSpells.addAll(Arrays.asList(frostliteSpells));

        createFrostFire_Spells();
        allSpells.addAll(Arrays.asList(frostfireSpells));

        createFrostArcane_Spells();
        allSpells.addAll(Arrays.asList(frostarcaneSpells));

        createLightningArcane_Spells();
        allSpells.addAll(Arrays.asList(litearcaneSpells));

        createLightningFire_Spells();
        allSpells.addAll(Arrays.asList(litefireSpells));

        createFireArcane_Spells();
        allSpells.addAll(Arrays.asList(firearcaneSpells));

    }
}
