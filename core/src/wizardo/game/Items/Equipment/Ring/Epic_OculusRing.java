package wizardo.game.Items.Equipment.Ring;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

public class Epic_OculusRing extends Ring {

    SpellUtils.Spell_Element element1;
    SpellUtils.Spell_Element element2;
    SpellUtils.Spell_Name mastery1;
    SpellUtils.Spell_Name mastery2;
    SpellUtils.Spell_Name mastery3;

    public Epic_OculusRing() {
        sprite = new Sprite(new Texture("Items/Rings/Occy.png"));
        spriteOver = new Sprite(new Texture("Items/Rings/Occy_Over.png"));
        displayScale = 0.7f;

        name = "The Oculus Ring";
        title = "Epic Ring";
        quality = ItemUtils.EquipQuality.EPIC;

        picKElements();

        masteries.add(mastery1);
        quantity_masteries.add(MathUtils.random(1,3));
        masteries.add(mastery2);
        quantity_masteries.add(MathUtils.random(1,3));
        masteries.add(mastery3);
        quantity_masteries.add(MathUtils.random(1,3));

    }

    public String getDescription() {

        return String.format("""
            You can mix the masteries given by
            this ring without needing a reagent
            
            If you take this ring off, the spell
            made from it will be forgotten""");
    }

    public void picKElements() {
        element1 = SpellUtils.getRandomClassicElement();
        element2 = SpellUtils.getRandomClassicElement();

        if(MathUtils.randomBoolean()) {
            mastery1 = SpellUtils.getRandomMastery(element1, 1, false);
        } else {
            mastery1 = SpellUtils.getRandomMastery(element2, 1, false);
        }

        while(mastery2 == null || mastery2 == mastery1) {
            if (MathUtils.randomBoolean()) {
                mastery2 = SpellUtils.getRandomMastery(element1, 2, false);
            } else {
                mastery2 = SpellUtils.getRandomMastery(element2, 2, false);
            }
        }

        while(mastery3 == null || mastery3 == mastery1 || mastery3 == mastery2) {
            if (MathUtils.randomBoolean()) {
                mastery3 = SpellUtils.getRandomMastery(element1, 3, false);
            } else {
                mastery3 = SpellUtils.getRandomMastery(element2, 3, false);
            }
        }
    }

    public String getFlavorText() {
        return "A very unusual ring";
    }
}
