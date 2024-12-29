package wizardo.game.Items.Equipment.Book;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Name.RIFTS;
import static wizardo.game.Wizardo.player;

public class Epic_OrbitBook extends Book {

    public Epic_OrbitBook() {
        sprite = new Sprite(new Texture("Items/Spellbook/BlueBook.png"));
        spriteOver = new Sprite(new Texture("Items/Spellbook/BlueBook_Over.png"));
        displayScale = 0.8f;

        name = "Kepler's Grimoire";
        title = "Epic Book";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.FROZENORB);
        quantity_masteries.add(1);
        masteries.add(RIFTS);
        quantity_masteries.add(1);

    }

    public String getDescription() {

        return String.format("""
            Orbiting Mass gains a second layer
            and 20%% increased speed""");
    }

    public String getFlavorText() {
        return "\" With the sun at one focus.\"";
    }
}
