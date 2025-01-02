package wizardo.game.Items.Equipment.Book;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;

import static wizardo.game.Wizardo.player;

public class Legendary_NecronomiconBook extends Book {

    public Legendary_NecronomiconBook() {
        sprite = new Sprite(new Texture("Items/Spellbook/Necronomicon.png"));
        spriteOver = new Sprite(new Texture("Items/Spellbook/Necronomicon_Over.png"));
        displayScale = 0.8f;

        name = "Necronomicon";
        title = "Legendary Book";
        quality = ItemUtils.EquipQuality.LEGENDARY;
    }

    public String getDescription() {
        return String.format("""
            Causes monsters to sometimes explode
            when they die, dealing damage proportional
            to their maximum hit points""");
    }

    public String getFlavorText() {
        return "\" Klaatu barada nik...\"";
    }

}
