package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;

public class Rare_FrostboltHat extends Hat{

    public Rare_FrostboltHat() {
        sprite = new Sprite(new Texture("Items/Hat/BlueHat.png"));
        spriteOver = new Sprite(new Texture("Items/Hat/BlueHat_Over.png"));
        displayScale = 0.9f;

        name = "Sum's Hat";
        title = "Rare Hat";
        quality = ItemUtils.EquipQuality.RARE;

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
