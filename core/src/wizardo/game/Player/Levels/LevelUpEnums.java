package wizardo.game.Screens.LevelUp;

import java.util.Random;

public class LevelUpUtils {

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
        //RED,
        REGEN,
        SHARP,
        SPACE,
        VOLTAGE
    }

    public enum LevelUpQuality {
        NORMAL,
        RARE,
        EPIC,
        LEGENDARY
    }

    public static LevelUps getRandomLevelUp() {
        LevelUps[] levelUps = LevelUps.values();
        Random random = new Random();
        return levelUps[random.nextInt(levelUps.length)];
    }

    public static LevelUpQuality getRandomQuality() {
        LevelUpQuality[] qualities = LevelUpQuality.values();
        Random random = new Random();
        return qualities[random.nextInt(qualities.length)];
    }
}
