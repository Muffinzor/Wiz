package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Name.*;

public class Legendary_TripleCastHat extends Hat {

    public Legendary_TripleCastHat() {
        sprite = new Sprite(new Texture("Items/Hat/ScraggsHat.png"));
        spriteOver = new Sprite(new Texture("Items/Hat/ScraggsHat_Over.png"));
        displayScale = 0.9f;

        name = "Scraggs' Lucky Hat";
        title = "Legendary Hat";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.MULTICAST);
        gearStats.add(ItemUtils.GearStat.LUCK);
        quantity_gearStats.add(20);
        quantity_gearStats.add(20);


    }

    public String getDescription() {

        return String.format("""
            Multicast repeats your spells
            an additional time""");
    }

    public String getFlavorText() {
        return "\" There's gotta be a place ahead\n where poker's played fair.\"";
    }
}
