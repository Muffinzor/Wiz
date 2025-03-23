package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Name.*;

public class Epic_JudgementHat extends Hat {

    public Epic_JudgementHat() {
        sprite = new Sprite(new Texture("Items/Hat/PenitentHat.png"));
        spriteOver = new Sprite(new Texture("Items/Hat/PenitentHat_Over.png"));
        displayScale = 0.9f;

        name = "Ballistic Penance";
        title = "Epic Hat";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(BEAM);
        quantity_masteries.add(1);
        masteries.add(OVERHEAT);
        quantity_masteries.add(1);

    }

    public String getDescription() {
        return "Judgement always releases energy explosions\n" +
                "Their number will be tripled if Judgement\n" +
                "has a Rift part";
    }

    public String getFlavorText() {
        return "\" Only the penitent Wizard will pass\"";
    }
}
