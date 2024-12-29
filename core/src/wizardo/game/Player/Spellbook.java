package wizardo.game.Player;

import wizardo.game.Spells.Hybrid.Orbit.Orbit_Spell;
import wizardo.game.Spells.Spell;

import java.util.ArrayList;

public class Spellbook {

    public ArrayList<Spell> equippedSpells;
    public ArrayList<Spell> knownSpells;
    public Spell defensive_spell;
    public Spell utility_spell;

    public int frostbolt_lvl = 1;
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

    // Elemental Dmg
    public float arcaneBonusDmg;
    public float frostBonusDmg;
    public float fireBonusDmg;
    public float lightningBonusDmg;
    public float allBonusDmg;

    // LevelUp Mastery Modifiers
    public int chargedboltBonus;
    public int frostboltBonus;
    public int arcanemissileBonus;
    
    // Family Dmg
    public int explosivesBonusDmg;
    public int flashBonusDmg;
    public int energyBonusDmg;
    public int gravityBonusDmg;
    public int sharpBonusDmg;
    public int voltageBonusDmg;
    public float divineBonusDmg;

    // Wide Modifiers
    public int empyreanFrequencyBonus;
    public int pushbackBonus;
    public int iceRadiusBonus;
    public int projSpeedBonus;
    
    // Specific Modifiers
    public int repulsionRadiusBonus;
    public int orbitingIceRadius;
    public float orbitSpeed;
    public int riftPullBonus;
    public int flamejetBonus;


    public float castSpeed;
    public float multicast;


    public Spellbook() {
        equippedSpells = new ArrayList<>();
        knownSpells = new ArrayList<>();
    }

}
