package wizardo.game.Player;

import wizardo.game.Spells.Spell;

import java.util.ArrayList;

public class Spellbook {

    public ArrayList<Spell> equippedSpells;
    public ArrayList<Spell> knownSpells;

    public int frostbolt_lvl = 0;
    public int icespear_lvl = 0;
    public int frozenorb_lvl = 0;

    public int fireball_lvl = 0;
    public int flamejet_lvl = 0;
    public int overheat_lvl = 0;

    public int chainlightning_lvl = 0;
    public int chargedbolt_lvl = 0;
    public int thunderstorm_lvl = 0;

    public int arcanemissile_lvl = 0;
    public int energybeam_lvl = 0;
    public int rift_lvl = 0;

    public Spellbook() {
        equippedSpells = new ArrayList<>();
        knownSpells = new ArrayList<>();
    }
}
