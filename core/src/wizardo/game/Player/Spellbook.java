package wizardo.game.Player;

import wizardo.game.Spells.Spell;

import java.util.ArrayList;

import static wizardo.game.Spells.SpellBank.Frost_Spells.frostspells;

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

    // CHARGEDBOLTS
    public float chargedbolts_bonus_dmg;
    public int chargedbolts_bonus_proj;

    // CHAIN LIGHTNING
    public int chainlightning_bonus_dmg;
    public int chainlightning_bonus_jump;

    // ARCANE MISSILES
    public float arcane_missile_bonus_dmg;
    public int arcane_missile_bonus_proj;
    public int arcane_missile_bonus_rotation;

    // ENERGY BEAM
    public float energybeam_bonus_dmg;
    public float energybeam_bonus_width;

    // FROSTBOLTS
    public float frostbolts_bonus_dmg;
    public int frostbolts_bonus_proj;

    // FROZENORB
    public float frozenorb_bonus_dmg;
    public float frozenorb_bonus_radius;

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
    public int conductiveBonusDmg;
    public float divineBonusDmg;

    // Wide Modifiers
    public int empyreanFrequencyBonus;
    public int pushbackBonus;
    public int iceRadiusBonus;
    public int projSpeedBonus;

    public float castSpeed;
    public float multicast;
    
    // Specific Modifiers
    public int repulsionRadiusBonus;
    public int orbitingIceRadius;
    public float orbitSpeed;
    public int riftPullBonus;
    public int flamejetBonus;
    public int energybeamBonus;
    public int chainlightningBonus;
    public int icespearBonus;
    public int fireballBonus;


    public Spellbook() {
        equippedSpells = new ArrayList<>();
        knownSpells = new ArrayList<>();
        equippedSpells.add(frostspells[0]);
    }

}
