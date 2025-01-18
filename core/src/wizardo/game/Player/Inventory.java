package wizardo.game.Player;

import wizardo.game.Items.Equipment.Amulet.Amulet;
import wizardo.game.Items.Equipment.Amulet.Legendary_FirstHitAmulet;
import wizardo.game.Items.Equipment.Amulet.Rare_IcespearAmulet;
import wizardo.game.Items.Equipment.Book.Book;
import wizardo.game.Items.Equipment.Book.Rare_FireballBook;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.Hat.Hat;
import wizardo.game.Items.Equipment.Ring.Rare_BeamRing;
import wizardo.game.Items.Equipment.Ring.Ring;
import wizardo.game.Items.Equipment.Robes.*;
import wizardo.game.Items.Equipment.SoulStone.SoulStone;
import wizardo.game.Items.Equipment.Staff.Staff;
import java.util.ArrayList;

public class Inventory {

    public Staff equippedStaff;
    public Book equippedBook;
    public Robes equippedRobes;
    public Amulet equippedAmulet;
    public Ring equippedRing;
    public Hat equippedHat;
    public SoulStone equippedStone;

    public ArrayList<Equipment> equippedGear;

    public int dual_reagents;
    public int triple_reagents;
    public int gold = 0;
    public Equipment[] holdingBox;

    public Inventory() {

        dual_reagents = 1;
        triple_reagents = 1;
        holdingBox = new Equipment[15];
        equippedGear = new ArrayList<>();

        holdingBox[0] = new Legendary_FirstHitAmulet();
        holdingBox[1] = new Legendary_FireRobes();
    }

    public boolean hasSpace() {
        for (int i = 0; i < holdingBox.length; i++) {
            if(holdingBox[i] == null) {
                return true;
            }
        }
        return false;
    }

    public void destroyItem(Equipment piece) {
        for (int i = 0; i < holdingBox.length; i++) {
            if(holdingBox[i] == piece) {
                holdingBox[i] = null;
            }
        }
    }

}
