package wizardo.game.Items.Equipment.Amulet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_RiftAmulet extends Amulet{

    public Epic_RiftAmulet() {
        sprite = new Sprite(new Texture("Items/Amulets/EyeAmmy.png"));
        spriteOver = new Sprite(new Texture("Items/Amulets/EyeAmmy_Over.png"));
        displayScale = 0.85f;

        name = "Beholder's Gaze";
        title = "Epic Amulet";
        quality = ItemUtils.EquipQuality.EPIC;
        pickMasteries();
        gearStats.add(ItemUtils.GearStat.ALLDMG);
        quantity_gearStats.add(5);
    }

    public void pickMasteries() {
        masteries.add(SpellUtils.Spell_Name.RIFTS);
        quantity_masteries.add(1);
    }

    public String getDescription() {
        return String.format("""
            Rifts are cast all around you
            and in larger numbers""");
    }

    public String getFlavorText() {
        return "\" The mind of a Beholder can envision all possibilities.\"";
    }
}
