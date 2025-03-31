package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Name.RIFTS;

public class Epic_ForkedLightningHat extends Hat {

    public Epic_ForkedLightningHat() {
        sprite = new Sprite(new Texture("Items/Hat/BlackHat.png"));
        spriteOver = new Sprite(new Texture("Items/Hat/BlackHat_Over.png"));
        displayScale = 0.9f;

        name = "The Emperor's Cowl";
        title = "Epic Hat";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.FLAMEJET);
        quantity_masteries.add(1);
        masteries.add(SpellUtils.Spell_Name.CHAIN);
        quantity_masteries.add(1);
    }

    public String getDescription() {
        return "Forked Lightning is cast 20% faster\nand can sometimes chain";
    }

    public String getFlavorText() {
        return "\" There is a great disturbance in the Weave...\"";
    }
}
