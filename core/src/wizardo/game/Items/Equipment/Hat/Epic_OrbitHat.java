package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Name.RIFTS;
import static wizardo.game.Wizardo.player;

public class Epic_OrbitHat extends Hat {

    public Epic_OrbitHat() {
        sprite = new Sprite(new Texture("Items/Hat/orbit_hat.png"));
        spriteOver = new Sprite(new Texture("Items/Hat/orbit_hat_over.png"));
        displayScale = 1.3f;

        name = "The Blue Straggler";
        title = "Epic Hat";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.FROZENORB);
        quantity_masteries.add(1);
        masteries.add(RIFTS);
        quantity_masteries.add(1);

    }

    public String getDescription() {

        return String.format("""
            Orbiting Mass has a chance to create
            a rift zone when it hits a monster""");
    }

    public String getFlavorText() {
        return "\" When immeasurable giants collide\"";
    }
}
