package wizardo.game.Items.Equipment.Robes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.Unique.WarpAura;

import static wizardo.game.Wizardo.player;

public class Epic_IronRobes extends Robes {

    public Epic_IronRobes() {
        sprite = new Sprite(new Texture("Items/Robes/IronRobes.png"));
        spriteOver = new Sprite(new Texture("Items/Robes/IronRobes_Over.png"));
        displayScale = 0.6f;

        name = "Iron Drapes";
        title = "Epic Robes";
        quality = ItemUtils.EquipQuality.EPIC;

        gearStats.add(ItemUtils.GearStat.DEFENSE);
        quantity_gearStats.add(3);
        gearStats.add(ItemUtils.GearStat.MAXSHIELD);
        quantity_gearStats.add(25);

    }

    public String getDescription() {
        return String.format("""
            Normal monsters hits don't push you
            but you move 15%% slower""");
    }

    public String getFlavorText() {
        return "Worn by Carmina's Pyromancers";
    }

    @Override
    public void equip() {
        super.equip();
        player.stats.runSpeed -= 0.27f;
    }

    public void unequip() {
        super.unequip();
        player.stats.runSpeed += 0.27f;
    }
}
