package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Spell;
import wizardo.game.Spells.Hybrid.Judgement.Judgement_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

public class Epic_TeleportHat extends Hat {

    public Epic_TeleportHat() {
        sprite = new Sprite(new Texture("Items/Hat/PurpleFlameHat.png"));
        spriteOver = new Sprite(new Texture("Items/Hat/PurpleFlameHat_Over.png"));

        name = "Navigator's Hat";
        title = "Epic Hat";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.getRandomMastery(SpellUtils.Spell_Element.ARCANE, 3, true));
        quantity_masteries.add(1);
        gearStats.add(ItemUtils.GearStat.ARCANEDMG);
        quantity_gearStats.add(MathUtils.random(10,15));

    }

    public String getDescription() {

        return String.format("""
            Arcane damage has a chance to
            teleport monsters backwards""");
    }

    public static float getProcTreshold(Spell spell) {
        if(spell instanceof EnergyBeam_Spell) {
            return 0.5f;
        }
        if(spell instanceof Judgement_Spell) {
            return 0.25f;
        }


        return 0.8f;

    }

    public String getFlavorText() {
        return "\" Space, like paper, can be folded.\"";
    }

}
