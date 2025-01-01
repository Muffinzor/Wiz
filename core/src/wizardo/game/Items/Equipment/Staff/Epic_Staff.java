package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;

import static wizardo.game.Wizardo.player;

public class Epic_Staff extends Staff{

    public Epic_Staff() {
        setupSprites();

        title = "EPIC Staff";
        quality = ItemUtils.EquipQuality.EPIC;

        getEpicStaffStats(this, 3);

        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getRareStaffStats(this, 1);
        }

    }

    public void setupSprites() {
        int random = MathUtils.random(1,3);
        switch(random) {
            case 1 -> {
                sprite = new Sprite(new Texture("Items/Staff/RandomEpic1.png"));
                spriteOver = new Sprite(new Texture("Items/Staff/RandomEpic1_Over.png"));
            }
            case 2 -> {
                sprite = new Sprite(new Texture("Items/Staff/RandomEpic2.png"));
                spriteOver = new Sprite(new Texture("Items/Staff/RandomEpic2_Over.png"));
            }
            case 3 -> {
                sprite = new Sprite(new Texture("Items/Staff/RandomEpic3.png"));
                spriteOver = new Sprite(new Texture("Items/Staff/RandomEpic3_Over.png"));
            }
        }
    }

    public String getFlavorText() {
        return "This staff is vibrating with power";
    }
}
