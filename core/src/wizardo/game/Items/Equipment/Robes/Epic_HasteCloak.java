package wizardo.game.Items.Equipment.Robes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;

import static wizardo.game.Wizardo.player;

public class Epic_HasteCloak extends Robes {

    public Epic_HasteCloak() {
        sprite = new Sprite(new Texture("Items/Robes/HasteRobes.png"));
        spriteOver = new Sprite(new Texture("Items/Robes/HasteRobes_Over.png"));
        displayScale = 0.6f;

        name = "Hasting Cloak";
        title = "Epic Robes";
        quality = ItemUtils.EquipQuality.EPIC;

        gearStats.add(ItemUtils.GearStat.WALKSPEED);
        quantity_gearStats.add(15);
        gearStats.add(ItemUtils.GearStat.CASTSPEED);
        quantity_gearStats.add(10);
        gearStats.add(ItemUtils.GearStat.PROJSPEED);
        quantity_gearStats.add(20);

    }

    public String getFlavorText() {
        return "A staple for Time Mages";
    }

}
