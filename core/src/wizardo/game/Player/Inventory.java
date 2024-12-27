package wizardo.game.Player;

import wizardo.game.Items.Equipment.Amulet.Amulet;
import wizardo.game.Items.Equipment.Amulet.Fireball_LegendaryAmulet;
import wizardo.game.Items.Equipment.Book.Book;
import wizardo.game.Items.Equipment.Book.Gold_LegendaryBook;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.Hat.Hat;
import wizardo.game.Items.Equipment.Hat.Rare_Hat;
import wizardo.game.Items.Equipment.Ring.Ring;
import wizardo.game.Items.Equipment.Robes.Robes;
import wizardo.game.Items.Equipment.SoulStone.SoulStone;
import wizardo.game.Items.Equipment.Staff.Fireball_EpicStaff;
import wizardo.game.Items.Equipment.Staff.Frozenorb_EpicStaff;
import wizardo.game.Items.Equipment.Staff.Icespear_EpicStaff;
import wizardo.game.Items.Equipment.Staff.Staff;

public class Inventory {

    public Staff equippedStaff;
    public Book equippedBook;
    public Robes equippedRobes;
    public Amulet equippedAmulet;
    public Ring equippedRing;
    public Hat equippedHat;
    public SoulStone equippedStone;


    public int dual_reagents;
    public int triple_reagents;
    public int gold = 5400;
    public Equipment[] holdingBox;

    public Inventory() {

        dual_reagents = 30;
        triple_reagents = 30;
        holdingBox = new Equipment[15];
        holdingBox[0] = new Fireball_EpicStaff();
        holdingBox[1] = new Icespear_EpicStaff();
        holdingBox[2] = new Frozenorb_EpicStaff();
        holdingBox[3] = new Fireball_LegendaryAmulet();
        holdingBox[4] = new Gold_LegendaryBook();
        holdingBox[5] = new Rare_Hat();


    }

}
