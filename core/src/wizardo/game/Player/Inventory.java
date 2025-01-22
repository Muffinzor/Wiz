package wizardo.game.Player;

import wizardo.game.Items.Equipment.Amulet.Amulet;
import wizardo.game.Items.Equipment.Amulet.Legendary_MarkAmulet;
import wizardo.game.Items.Equipment.Book.Book;
import wizardo.game.Items.Equipment.Book.Legendary_PulseBook;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.Hat.Epic_TeleportHat;
import wizardo.game.Items.Equipment.Hat.Hat;
import wizardo.game.Items.Equipment.Ring.Epic_FrostRing;
import wizardo.game.Items.Equipment.Ring.Rare_BeamRing;
import wizardo.game.Items.Equipment.Ring.Ring;
import wizardo.game.Items.Equipment.Robes.*;
import wizardo.game.Items.Equipment.SoulStone.SoulStone;
import wizardo.game.Items.Equipment.Staff.*;

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

        holdingBox = new Equipment[15];
        equippedGear = new ArrayList<>();


        holdingBox[0] = new Legendary_PulseBook();
        holdingBox[1] = new Legendary_MarkAmulet();

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
