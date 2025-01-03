package wizardo.game.Items;

import com.badlogic.gdx.math.MathUtils;

public class ItemUtils {


    public enum EquipSlot {
        HAT,
        STAFF,
        SPELLBOOK,
        ROBE,
        RUNESTONE,
        AMULET,
        RING,
        ALL
    }

    public enum EquipQuality {
        NORMAL,
        RARE,
        EPIC,
        LEGENDARY,
        ALL
    }

    public enum GearStat {
        FIREDMG,
        FROSTDMG,
        LITEDMG,
        ARCANEDMG,
        ALLDMG,

        CASTSPEED,
        MULTICAST,
        PROJSPEED,

        REGEN,
        MAXSHIELD,
        DEFENSE,
        WALKSPEED,
        LUCK,

        MASTERY_ALL,
        MASTERY_T1,
        MASTERY_T2,
        MASTERY_T3,
        MASTERY_FIRE,
        MASTERY_FROST,
        MASTERY_LIGHTNING,
        MASTERY_ARCANE,
        MASTERY_FLAMEJET,
        MASTERY_FIREBALL,
        MASTERY_OVERHEAT,
        MASTERY_FROSTBOLT,
        MASTERY_ICESPEAR,
        MASTERY_FROZENORB,
        MASTERY_CHARGEDBOLT,
        MASTERY_CHAIN,
        MASTERY_STORM,
        MASTERY_MISSILES,
        MASTERY_BEAM,
        MASTERY_RIFTS,
    }

    public static GearStat getEleDmgStat() {
        GearStat ele = null;
        int random = MathUtils.random(1,4);
        switch (random) {
            case 1 -> ele = GearStat.FIREDMG;
            case 2 -> ele = GearStat.FROSTDMG;
            case 3 -> ele = GearStat.ARCANEDMG;
            case 4 -> ele = GearStat.LITEDMG;
        }
        return ele;
    }

}
