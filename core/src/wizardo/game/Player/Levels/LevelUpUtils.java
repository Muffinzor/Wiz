package wizardo.game.Player.Levels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import wizardo.game.Player.Levels.LevelUpEnums.LevelUpQuality;
import wizardo.game.Player.Levels.LevelUpEnums.LevelUps;
import wizardo.game.Spells.MixingUtils;
import wizardo.game.Spells.Spell;

import static wizardo.game.Player.Levels.LevelUpEnums.LevelUpQuality.*;
import static wizardo.game.Player.Levels.LevelUpEnums.LevelUps.*;
import static wizardo.game.Wizardo.player;


public class LevelUpUtils {

    public static LevelUpQuality getRandomQuality() {
        double random = Math.random();


        if(random >= 0.94f) {
            return LEGENDARY;
        }
        if(random >= 0.85f) {
            return EPIC;
        }
        if(random >= 0.54f) {
            return RARE;
        }
        return NORMAL;
    }

    public static LevelUps getPossibleChoice(LevelUpQuality quality, ArrayList<LevelUps> currentList) {
        ArrayList<LevelUps> list = new ArrayList<>();

        list.add(CRYSTAL);
        list.add(REGEN);

        if(player.spellbook.energybeam_lvl > 0) {
            list.add(ENERGY);
        }
        if(player.spellbook.flamejet_lvl > 0 || player.spellbook.thunderstorm_lvl > 0) {
            list.add(FLARE);
        }
        if(canBeEmpyrean()) {
            list.add(EMPYREAN);
        }
        if(canBeExplosive()) {
            list.add(EXPLOSIVES);
        }
        if(canBeFundamental()) {
            list.add(GRAVITY);
        }
        if(player.spellbook.frozenorb_lvl > 0) {
            list.add(ICE);
        }

        if(canBeSharp()) {
            list.add(SHARP);
        }
        if(canBeVoltage()) {
            list.add(VOLTAGE);
        }

        if(quality == EPIC || quality == LEGENDARY) {
            list.add(LUCK);
            list.add(RED);
        }

        if((player.spellbook.overheat_lvl > 0) && (quality == EPIC || quality == LEGENDARY)) {
            list.add(FORCE);
        }

        if(player.spellbook.rift_lvl > 0 && (quality == EPIC || quality == LEGENDARY)) {
            list.add(SPACE);
        }

        for(LevelUps type : currentList) {
            list.remove(type);
        }

        addMastery(quality, currentList, list);

        Collections.shuffle(list);

        if(list.isEmpty()) {
            list.add(LevelUpEnums.getRandomT1());
        }

        return list.getFirst();

    }

    public static void addMastery(LevelUpQuality quality, ArrayList<LevelUps> currentList, ArrayList<LevelUps> tempList) {
        if(quality == NORMAL && !containsT1(currentList)) {
            tempList.add(LevelUpEnums.getRandomT1());
        }
        if(quality == RARE && player.level >= 8 && !containsT2(currentList)) {
            tempList.add(LevelUpEnums.getRandomT2());
        }
    }

    public static boolean containsT1(ArrayList<LevelUps> currentList) {
        boolean maxFlamejet = player.spellbook.flamejetBonus >= 10;
        boolean maxFrostbolt = player.spellbook.frostboltBonus >= 40;
        boolean maxChargedbolt = player.spellbook.chargedboltBonus >= 40;
        boolean maxMissile = player.spellbook.arcanemissileBonus >= 40;

        if(maxFlamejet && maxFrostbolt && maxChargedbolt && maxMissile) {
            return true;
        }
        if(currentList.contains(FROSTBOLT)) {
            return true;
        }
        if(currentList.contains(CHARGEDBOLT)) {
            return true;
        }
        if(currentList.contains(FLAMEJET)) {
            return true;
        }
        if(currentList.contains(MISSILES)) {
            return true;
        }
        return false;
    }
    public static boolean containsT2(ArrayList<LevelUps> currentList) {
        boolean maxFireball = player.spellbook.fireballBonus >= 40;
        boolean maxIcespear = player.spellbook.icespearBonus >= 40;
        boolean maxChain = player.spellbook.chainlightningBonus >= 30;
        boolean maxBeam = player.spellbook.energybeamBonus >= 100;

        if(maxFireball && maxIcespear && maxChain && maxBeam) {
            return true;
        }
        if(currentList.contains(ICESPEAR)) {
            return true;
        }
        if(currentList.contains(BEAM)) {
            return true;
        }
        if(currentList.contains(FIREBALL)) {
            return true;
        }
        if(currentList.contains(CHAIN)) {
            return true;
        }
        return false;
    }

    private static boolean canBeEmpyrean() {
        boolean possible = false;

        if(player.spellbook.thunderstorm_lvl > 0) {
            possible = true;
        }
        if(MixingUtils.canMix_Blizzard()) {
            possible = true;
        }
        if(MixingUtils.canMix_EnergyRain()) {
            possible = true;
        }
        if(MixingUtils.canMix_Meteor()) {
            possible = true;
        }
        return possible;
    }
    private static boolean canBeExplosive() {
        boolean possible = false;

        if(player.spellbook.frostbolt_lvl > 0) {
            possible = true;
        }
        if(player.spellbook.fireball_lvl > 0) {
            possible = true;
        }
        if(player.spellbook.overheat_lvl > 0) {
            possible = true;
        }

        return possible;
    }
    private static boolean canBeFundamental() {
        boolean possible = false;

        if(player.spellbook.frozenorb_lvl > 0) {
            possible = true;
        }
        if(player.spellbook.rift_lvl > 0) {
            possible = true;
        }
        if(MixingUtils.canMix_Repulsion()) {
            possible = true;
        }

        return possible;
    }

    private static boolean canBeSharp() {
        boolean possible = false;

        if(player.spellbook.arcanemissile_lvl > 0) {
            possible = true;
        }
        if(player.spellbook.icespear_lvl > 0) {
            possible = true;
        }

        return possible;
    }
    private static boolean canBeVoltage() {
        boolean possible = false;

        if(player.spellbook.chargedbolt_lvl > 0) {
            possible = true;
        }
        if(player.spellbook.chainlightning_lvl > 0) {
            possible = true;
        }

        return possible;
    }

}
