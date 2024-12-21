package wizardo.game.Utils.Contacts;

public class Masks {

    // IDs

    public static final short SPELL = 0x0001;
    public static final short PAWN = 0x0002;
    public static final short FLYING_MONSTER = 0x0004;
    public static final short MONSTER = 0x0008;

    public static final short OBSTACLE = 0x0010;
    public static final short AVOID_ZONE = 0x0020;
    public static final short LIGHTS_OFF_ZONE = 0x0040;
    public static final short MONSTER_PROJECTILE = 0x0080;

    public static final short LIGHT_PROJECTILE = 0x0100;
    public static final short DECOR = 0x0200;
    public static final short MONSTER_BODY = 0x0400;

    public static final short ITEM = 0x1000;
    public static final short TRIGGER = 0x2000;


    // MASKS

    public static final short SPELL_MASK = (short) ( MONSTER | OBSTACLE | DECOR | MONSTER_BODY | MONSTER_PROJECTILE | FLYING_MONSTER);

    public static final short EVENT_MASK = PAWN;

    public static final short MONSTER_MASK = (short) ( SPELL | OBSTACLE | PAWN | DECOR | MONSTER | AVOID_ZONE | LIGHT_PROJECTILE);

    public static final short DECOR_MASK = (short) ( SPELL | MONSTER_PROJECTILE | PAWN | MONSTER );

    public static final short MONSTER_PROJECTILE_MASK = (short) (PAWN | SPELL | OBSTACLE | DECOR);


}
