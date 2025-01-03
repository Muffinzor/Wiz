package wizardo.game.Player;

import wizardo.game.Items.Equipment.Amulet.Amulet;
import wizardo.game.Items.Equipment.Amulet.Legendary_FireballAmulet;
import wizardo.game.Items.Equipment.Book.Book;
import wizardo.game.Items.Equipment.Book.Legendary_GoldBook;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.Equipment.Hat.Hat;
import wizardo.game.Items.Equipment.Hat.Rare_Hat;
import wizardo.game.Items.Equipment.Ring.Ring;
import wizardo.game.Items.Equipment.Robes.Robes;
import wizardo.game.Items.Equipment.SoulStone.SoulStone;
import wizardo.game.Items.Equipment.Staff.Epic_FireballStaff;
import wizardo.game.Items.Equipment.Staff.Epic_FrozenorbStaff;
import wizardo.game.Items.Equipment.Staff.Epic_IcespearStaff;
import wizardo.game.Items.Equipment.Staff.Staff;

import java.util.ArrayList;

import static wizardo.game.Items.ItemUtils.EquipQuality.EPIC;
import static wizardo.game.Items.ItemUtils.EquipQuality.RARE;
import static wizardo.game.Items.ItemUtils.EquipSlot.HAT;
import static wizardo.game.Items.ItemUtils.EquipSlot.STAFF;

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
    public int gold = 5400;
    public Equipment[] holdingBox;

    public Inventory() {

        dual_reagents = 30;
        triple_reagents = 30;
        holdingBox = new Equipment[15];
        equippedGear = new ArrayList<>();

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
