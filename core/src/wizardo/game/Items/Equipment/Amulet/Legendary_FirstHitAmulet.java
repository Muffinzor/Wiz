package wizardo.game.Items.Equipment.Amulet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Legendary_FirstHitAmulet extends Amulet {

    public Legendary_FirstHitAmulet() {
        sprite = new Sprite(new Texture("Items/Amulets/FirstHitAmmy.png"));
        spriteOver = new Sprite(new Texture("Items/Amulets/FirstHitAmmy_Over.png"));
        displayScale = 0.85f;

        name = "The Huntress Locket";
        title = "Legendary Amulet";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        masteries.add(SpellUtils.Spell_Name.MISSILES);
        quantity_masteries.add(2);
        masteries.add(SpellUtils.Spell_Name.ICESPEAR);
        quantity_masteries.add(2);
        gearStats.add(ItemUtils.GearStat.PROJSPEED);
        quantity_gearStats.add(10);

    }

    public String getDescription() {
        return String.format("""
            Arcane missiles, Ice spears and Lasers deal
            damage twice to the first monster they hit""");
    }

    public String getFlavorText() {
        return "\" My aim is true.\"";
    }
}
