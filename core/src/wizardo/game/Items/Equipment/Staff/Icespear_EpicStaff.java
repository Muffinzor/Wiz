package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;

public class Icespear_EpicStaff extends Staff{

    public Icespear_EpicStaff() {
        sprite = new Sprite(new Texture("Items/Staff/Icespear.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/Icespear_Over.png"));
        displayRotation = 25;

        name = "Sorcerer Fern's Cane";
        title = "Epic Staff";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.ICESPEAR);
        quantity_masteries.add(2);
    }

    public String getDescription() {
        return String.format("""
            Ice Spears can split immediately
            and all contain Flamejet
            Their speed is increased by 20%%""");
    }

    public String getFlavorText() {
        return "\"...To shreds you say?\"";
    }

}
