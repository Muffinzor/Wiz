package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Rare_FrostboltHat extends Hat{

    public Rare_FrostboltHat() {
        sprite = new Sprite(new Texture("Items/Hat/BlueHat.png"));
        spriteOver = new Sprite(new Texture("Items/Hat/BlueHat_Over.png"));
        displayScale = 0.9f;

        name = "Sum's Hat";
        title = "Rare Hat";
        quality = ItemUtils.EquipQuality.RARE;

        masteries.add(SpellUtils.Spell_Name.FROSTBOLT);
        quantity_masteries.add(1);

    }

    public String getDescription() {

        return String.format("""
            Frostbolts gain an extra
            projectile""");
    }

    public String getFlavorText() {
        return "\" For the amateur wizard.\"";
    }
}
