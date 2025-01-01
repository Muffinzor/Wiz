package wizardo.game.Items.Equipment.Robes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Rare_Robes extends Robes {

    public Rare_Robes() {
        sprite = new Sprite(new Texture("Items/Robes/RandomCloak4.png"));
        spriteOver = new Sprite(new Texture("Items/Robes/RandomCloak4_Over.png"));
        displayScale = 0.75f;

        title = "Rare Robes";
        quality = ItemUtils.EquipQuality.RARE;

        getRareRobesStats(this, 2);

        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getNormalRobesStats(this, 1);
        }

    }

    public String getFlavorText() {
        return "Simple yet elegant";
    }

}
