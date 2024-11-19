package wizardo.game.Player;

import wizardo.game.Spells.Hybrid.Orbit.Orbit_Spell;
import wizardo.game.Spells.Spell;

import java.util.ArrayList;

public class Spellbook {

    public ArrayList<Spell> equippedSpells;
    public ArrayList<Spell> knownSpells;
    public Spell defensive_spell;
    public Spell utility_spell;

    public int frostbolt_lvl = 5;
    public int icespear_lvl = 5;
    public int frozenorb_lvl = 5;

    public int fireball_lvl = 10;
    public int flamejet_lvl = 10;
    public int overheat_lvl = 5;

    public int chainlightning_lvl = 5;
    public int chargedbolt_lvl = 5;
    public int thunderstorm_lvl = 5;

    public int arcanemissile_lvl = 5;
    public int energybeam_lvl = 5;
    public int rift_lvl = 5;

    public float arcaneBonusDmg;
    public float frostBonusDmg;
    public float fireBonusDmg;
    public float lightningBonusDmg;
    public float allBonusDmg;


    public Spellbook() {
        equippedSpells = new ArrayList<>();
        knownSpells = new ArrayList<>();
    }

}
