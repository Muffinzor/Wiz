package wizardo.game.Items.Equipment.Ring;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Rare_BeamRing extends Ring{

    public Rare_BeamRing() {
        sprite = new Sprite(new Texture("Items/Rings/BeamRing.png"));
        spriteOver = new Sprite(new Texture("Items/Rings/BeamRing_Over.png"));
        displayScale = 0.75f;

        name = "Amplifying Halo";
        title = "Epic Ring";
        quality = ItemUtils.EquipQuality.RARE;

        masteries.add(SpellUtils.Spell_Name.BEAM);
        quantity_masteries.add(1);

    }

    public String getDescription() {
        return  "Energy Beam is twice as wide";
    }

    public String getFlavorText() {
        return "\"A clumsy shot sure,\nbut did it not still hit?\"";
    }
}
