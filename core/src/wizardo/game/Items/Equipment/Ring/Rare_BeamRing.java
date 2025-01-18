package wizardo.game.Items.Equipment.Ring;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;

public class Rare_BeamRing extends Ring{

    public Rare_BeamRing() {
        sprite = new Sprite(new Texture("Items/Rings/BeamRing.png"));
        spriteOver = new Sprite(new Texture("Items/Rings/BeamRing_Over.png"));
        displayScale = 0.75f;

        name = "Amplifying Halo";
        title = "Epic Ring";
        quality = ItemUtils.EquipQuality.RARE;

    }

    public String getDescription() {
        return  "Energy Beam is twice as wide";
    }

    public String getFlavorText() {
        return "\"A clumsy shot sure,\nbut did it not still hit?\"";
    }
}
