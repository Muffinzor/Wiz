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
        ICE,
        LUCK,
        PROJECTILES,
        RED,
        REGEN,
        SHARP,
        SPACE,
        VOLTAGE,

        FROSTBOLT,
        FLAMEJET,
        CHARGEDBOLT,
        MISSILES,
        ICESPEAR,
        FIREBALL,
        CHAIN,
        BEAM,
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

    public enum LevelUpQuality {
        NORMAL,
        RARE,
        EPIC,
        LEGENDARY
    }


}
