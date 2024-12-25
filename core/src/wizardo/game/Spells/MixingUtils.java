package wizardo.game.Spells;

import wizardo.game.Player.Spellbook;

import static wizardo.game.Wizardo.player;

public class MixingUtils {

    public static boolean canMix_Meteor() {
        Spellbook book = player.spellbook;

        if(book.rift_lvl > 0 && book.fireball_lvl > 0) {
            return true;
        }
        if(book.thunderstorm_lvl > 0 && book.fireball_lvl > 0) {
            return true;
        }
        return false;
    }

    public static boolean canMix_Repulsion() {
        Spellbook book = player.spellbook;

        if(book.overheat_lvl > 0 && book.rift_lvl > 0) {
            return true;
        }

        return false;
    }

    public static boolean canMix_Blizzard() {
        Spellbook book = player.spellbook;

        if(book.thunderstorm_lvl > 0 && book.icespear_lvl > 0) {
            return true;
        }
        if(book.energybeam_lvl > 0 && book.icespear_lvl > 0 && book.rift_lvl > 0) {
            return true;
        }

        return false;
    }

    public static boolean canMix_EnergyRain() {
        Spellbook book = player.spellbook;

        if(book.rift_lvl > 0 && book.energybeam_lvl > 0) {
            return true;
        }

        return false;
    }

    public static boolean canMix_Artillery() {
        Spellbook book = player.spellbook;

        if(book.rift_lvl > 0 && book.energybeam_lvl > 0) {
            return true;
        }

        if(book.rift_lvl > 0 && book.thunderstorm_lvl > 0 && book.arcanemissile_lvl > 0) {
            return true;
        }

        return false;
    }
}
