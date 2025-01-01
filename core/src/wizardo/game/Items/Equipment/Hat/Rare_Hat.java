package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Rare_Hat extends Hat {

    public Rare_Hat() {
        setSprite();

        title = "Rare Hat";
        quality = ItemUtils.EquipQuality.RARE;

        getRareHatStats(this, 2);

        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getNormalHatStats(this, 1);
        }

    }

    public String getFlavorText() {
        return "The classic attire";
    }

    public void setSprite() {
        reversedSprite = MathUtils.randomBoolean();
        int random = MathUtils.random(1, 3);
        switch (random) {
            case 1 -> {
                sprite = new Sprite(new Texture("Items/Hat/rare1.png"));
                spriteOver = new Sprite(new Texture("Items/Hat/rare1_over.png"));
                displayScale = 1.3f;
            }
            case 2 -> {
                sprite = new Sprite(new Texture("Items/Hat/RareHat1.png"));
                spriteOver = new Sprite(new Texture("Items/Hat/RareHat1_Over.png"));
            }
            case 3 -> {
                sprite = new Sprite(new Texture("Items/Hat/YellowHat.png"));
                spriteOver = new Sprite(new Texture("Items/Hat/YellowHat_Over.png"));
            }
        }
    }
}
