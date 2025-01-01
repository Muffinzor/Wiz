package wizardo.game.Items.Equipment.Book;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Name.RIFTS;
import static wizardo.game.Wizardo.player;

public class Epic_FireAcaneBook extends Book {

    boolean buffApplied;

    public Epic_FireAcaneBook() {
        sprite = new Sprite(new Texture("Items/Spellbook/Daynightbook.png"));
        spriteOver = new Sprite(new Texture("Items/Spellbook/Daynightbook_Over.png"));
        displayScale = 0.8f;

        name = "Heretic Incantations";
        title = "Epic Book";
        quality = ItemUtils.EquipQuality.EPIC;

        if(MathUtils.randomBoolean()) {
            gearStats.add(ItemUtils.GearStat.MASTERY_FIRE);
        } else {
            gearStats.add(ItemUtils.GearStat.MASTERY_ARCANE);
        }
        quantity_gearStats.add(1);

    }

    public String getDescription() {

        return String.format("""
            If you only have arcane and fire
            spells equipped, gain an extra
            20%% multicast chance""");
    }

    public String getFlavorText() {
        return "\" Purified by the fire,\njudged by the Gods\"";
    }

    @Override
    public void applySelfVerification() {
        boolean active = true;
        for(Spell spell : player.spellbook.equippedSpells) {
            boolean frost = spell.anim_element == SpellUtils.Spell_Element.FROST;
            boolean lightning = spell.anim_element == SpellUtils.Spell_Element.LIGHTNING;
            boolean coldlite = spell.anim_element == SpellUtils.Spell_Element.COLDLITE;
            boolean firelite = spell.anim_element == SpellUtils.Spell_Element.FIRELITE;
            if(frost || lightning || coldlite || firelite) {
                active = false;
            }
        }

        if(buffApplied && (player.inventory.equippedBook != this)) {
            player.spellbook.multicast -= 20;
            buffApplied = false;

        } else if(active && !buffApplied) {
            player.spellbook.multicast += 20;
            buffApplied = true;

        } else if(!active && buffApplied) {
            player.spellbook.multicast -= 20;
            buffApplied = false;
        }

    }
}
