package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;

import static wizardo.game.Wizardo.player;

public class Normal_Staff extends Staff{

    public Normal_Staff() {

        setupSprites();

        title = "Normal Staff";
        quality = ItemUtils.EquipQuality.NORMAL;

        getNormalStaffStats(this, 1);

        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getNormalStaffStats(this, 1);
        }

    }

    public void setupSprites() {
        int random = MathUtils.random(1,3);
        switch(random) {
            case 1 -> {
                sprite = new Sprite(new Texture("Items/Staff/RandomCommon1.png"));
                spriteOver = new Sprite(new Texture("Items/Staff/RandomCommon1_Over.png"));
            }
            case 2 -> {
                sprite = new Sprite(new Texture("Items/Staff/RandomCommon2.png"));
                spriteOver = new Sprite(new Texture("Items/Staff/RandomCommon2_Over.png"));
            }
            case 3 -> {
                sprite = new Sprite(new Texture("Items/Staff/RandomCommon3.png"));
                spriteOver = new Sprite(new Texture("Items/Staff/RandomCommon3_Over.png"));
            }
        }
    }

    public String getFlavorText() {
        return "A sturdy piece of work";
    }
}
