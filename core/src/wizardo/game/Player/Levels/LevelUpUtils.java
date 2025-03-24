package wizardo.game.Player.Levels;

import java.util.ArrayList;
import java.util.Collections;

import wizardo.game.Player.Levels.LevelUpEnums.LevelUpQuality;
import wizardo.game.Player.Levels.LevelUpEnums.LevelUps;
import wizardo.game.Spells.MixingUtils;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Player.Levels.LevelUpEnums.LevelUpQuality.*;
import static wizardo.game.Player.Levels.LevelUpEnums.LevelUps.*;
import static wizardo.game.Wizardo.player;


public class LevelUpUtils {

    public static LevelUpQuality getRandomQuality() {
        double random = Math.random();

        if(random >= 0.9f) {
            return LEGENDARY;
        }
        if(random >= 0.75f) {
            return EPIC;
        }
        if(random >= 0.45f) {
            return RARE;
        }
        return NORMAL;
    }

}
