package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Normal_Hat extends Hat{

    public Normal_Hat() {
        sprite = new Sprite(new Texture("Items/Hat/normal1.png"));
        spriteOver = new Sprite(new Texture("Items/Hat/normal1_over.png"));
        displayScale = 1.3f;

        name = "Paper Cone";
        title = "Common Hat";
        quality = ItemUtils.EquipQuality.NORMAL;

        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getNormalHatStats(this, 2);
        } else {
            getNormalHatStats(this, 1);
        }

    }

    @Override
    public String getFlavorText() {
        return "Truly an honor";
    }
}
