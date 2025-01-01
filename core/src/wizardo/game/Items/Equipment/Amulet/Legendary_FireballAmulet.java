package wizardo.game.Items.Equipment.Amulet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Legendary_FireballAmulet extends Amulet {

    public Legendary_FireballAmulet() {
        sprite = new Sprite(new Texture("Items/Amulets/3Stones.png"));
        spriteOver = new Sprite(new Texture("Items/Amulets/3Stones_Over.png"));
        displayScale = 0.85f;

        name = "The Cinder Lords";
        title = "Legendary Amulet";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        masteries.add(SpellUtils.Spell_Name.FIREBALL);
        quantity_masteries.add(2);
        masteries.add(SpellUtils.Spell_Name.FROSTBOLT);
        quantity_masteries.add(2);
    }

    public String getDescription() {
        return String.format("""
            Fireballs and Frostbolts pass through
            monsters and damage everything they hit
            
            Monsters hit have a chance to explode
            
            The first monster hit will always
            trigger an explosion""");
    }

    public String getFlavorText() {
        return "\" We're not even fit to lick\n their boots.\"";
    }

}
