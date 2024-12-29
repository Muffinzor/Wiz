package wizardo.game.Items.Equipment.Amulet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_StormAmulet extends Amulet {

    public Epic_StormAmulet() {
        sprite = new Sprite(new Texture("Items/Amulets/StormAmmy.png"));
        spriteOver = new Sprite(new Texture("Items/Amulets/StormAmmy_Over.png"));
        displayScale = 0.85f;

        name = "Eye of the Storm";
        title = "Epic Amulet";
        quality = ItemUtils.EquipQuality.EPIC;
        pickMasteries();
    }

    public void pickMasteries() {
        if(MathUtils.randomBoolean()) {
            masteries.add(SpellUtils.Spell_Name.FIREBALL);
            quantity_masteries.add(1);
            masteries.add(SpellUtils.Spell_Name.RIFTS);
            quantity_masteries.add(1);
        } else {
            masteries.add(SpellUtils.Spell_Name.THUNDERSTORM);
            quantity_masteries.add(1);
            masteries.add(SpellUtils.Spell_Name.ICESPEAR);
            quantity_masteries.add(1);
        }
    }

    public String getDescription() {
        return String.format("""
            Thunderstorm, Blizzard, Meteor Shower
            and Energy Rain last 50%% longer""");
    }

    public String getFlavorText() {
        return "\" There is no peace of mind in stillness.\"";
    }

}
