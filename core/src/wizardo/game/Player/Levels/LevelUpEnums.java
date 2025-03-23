package wizardo.game.Player.Levels;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.player;

public class LevelUpEnums {

    public enum LevelUps {
        CRYSTAL,
        EMPYREAN,
        ENERGY,
        EXPLOSIVES,
        FLARE,
        FORCE,
        GRAVITY,
        LUCK,
        PROJECTILES,
        RED,
        REGEN,
        SHARP,
        VOLTAGE,

        ARCANE,
        FROST,
        FIRE,
        LIGHTNING,

        FROSTBOLT,
        FLAMEJET,
        CHARGEDBOLT,
        MISSILES,
        ICESPEAR,
        FIREBALL,
        CHAIN,
        BEAM,
        FROZENORB,
        OVERHEAT,
        THUNDERSTORM,
        RIFTS,

        JUDGEMENT,
        BLIZZARD,
        CELESTIALSTRIKE,
        ENERGYRAIN,
        FORKEDLIGHTNING,
        FROSTNOVA,
        LASERS,
        LIGHTNINGHANDS,
        METEORS,
        ORBIT,
        REPULSIONFIELD,
        DRAGONBREATH
    }

    public static LevelUps getRandomT1() {
        ArrayList<LevelUps> list = new ArrayList<>();

        if(player.spellbook.flamejetBonus < 10) {
            list.add(LevelUps.FLAMEJET);
        }
        if(player.spellbook.frostboltBonus < 40) {
            list.add(LevelUps.FROSTBOLT);
        }
        if(player.spellbook.arcanemissileBonus < 40) {
            list.add(LevelUps.MISSILES);
        }
        if(player.spellbook.chargedboltBonus < 40) {
            list.add(LevelUps.CHARGEDBOLT);
        }

        Collections.shuffle(list);
        return list.getFirst();
    }
    public static LevelUps getRandomT2() {
        ArrayList<LevelUps> list = new ArrayList<>();

        if(player.spellbook.fireballBonus < 40) {
            list.add(LevelUps.FIREBALL);
        }
        if(player.spellbook.icespearBonus < 40) {
            list.add(LevelUps.ICESPEAR);
        }
        if(player.spellbook.energybeamBonus < 100) {
            list.add(LevelUps.BEAM);
        }
        if(player.spellbook.chainlightningBonus < 30) {
            list.add(LevelUps.CHAIN);
        }

        Collections.shuffle(list);
        return list.getFirst();
    }

    public enum LevelUpQuality {
        NORMAL,
        RARE,
        EPIC,
        LEGENDARY
    }


}
