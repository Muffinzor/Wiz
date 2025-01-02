package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;

import static wizardo.game.Wizardo.player;

public class Rare_TwigStaff extends Staff{

    public Rare_TwigStaff() {
        setupSprites();

        name = "The Twig";
        title = "Rare Staff";
        quality = ItemUtils.EquipQuality.RARE;

        gearStats.add(ItemUtils.GearStat.REGEN);
        quantity_gearStats.add(60);
        gearStats.add(ItemUtils.GearStat.WALKSPEED);
        quantity_gearStats.add(5);
        gearStats.add(ItemUtils.GearStat.LUCK);
        quantity_gearStats.add(10);

    }

    public void setupSprites() {
        sprite = new Sprite(new Texture("Items/Staff/Twig.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/Twig_Over.png"));
    }

    public String getFlavorText() {
        return "A lively branch";
    }
}
